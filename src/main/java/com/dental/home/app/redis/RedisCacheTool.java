package com.dental.home.app.redis;

import cn.hutool.core.util.StrUtil;
import com.dental.home.app.utils.SerializeUtil;
import com.dental.home.app.vo.result.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gyz on 2017-11-23.
 *
 * redis工具类，对jedis进行封装，具体可以参考 https://github.com/xetorthio/jedis
 * 1、一般key,value的数据存储
 * 2、list数据模型存储
 * 3、hashmap数据模型存储
 */
@Component
@Slf4j
public class RedisCacheTool {

    /**
     * 线程同步的失败次数
     */
    private AtomicInteger connectionFailCount = new AtomicInteger(0);

    /**
     * 连续连接redis失败的最大次数
     */
    private static final int MAX_FAIL_CONNECTION_COUNT = 5;

    /**
     * 缓存池配置
     */
    private JedisPoolConfig poolConfig;

    /**
     * 静态单例
     */
    private static RedisCacheTool instance;

    /**
     * 缓存连接池
     */
    private JedisPool jedisPool;

    /**
     * 同步锁
     */
    private static final Object cacheLock = new Object();

    /**
     * redis缓存配置
     */
    @Resource
    private RedisCacheConf redisCacheConf;

    private String appName = "ICBC_PARTY";

    /**
     * 私有构造方法
     */
    @PostConstruct
    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisCacheConf.getMaxTotal());
        config.setMaxIdle(redisCacheConf.getMaxIdle());
        config.setMinIdle(redisCacheConf.getMinIdle());
        config.setMaxWaitMillis(redisCacheConf.getMaxWait());
        config.setTestOnBorrow(redisCacheConf.getTestOnBorrow());
        config.setTestOnReturn(redisCacheConf.getTestOnReturn());
        poolConfig = config;
        reinitPool();
    }

    /**
     * 获取缓存池配置
     *
     * @return 缓冲池配置
     */
    private JedisPoolConfig getPoolConfig() {
        return poolConfig;
    }

    private String getFinalKey(String key) {
        if (StrUtil.isEmpty(key)) {
            return "test-key";
        }
        String finalKey = key;
        if (!StrUtil.isEmpty(appName)) {
            finalKey = appName + "|" + key;
        }
        return finalKey;
    }

    /**
     * 重新初始化缓存池
     */
    private void reinitPool() {
        synchronized (cacheLock) {
            if (jedisPool != null) {
                return;
            }
            jedisPool = new JedisPool(getPoolConfig(), redisCacheConf.getHost(), redisCacheConf.getPort(),
                    redisCacheConf.getTimeout(), (String) null);
            log.info("initiate jedisPool finished.");
        }
    }

    /**
     * 获取缓存直连连接
     *
     * @return 缓存直连连接
     */
    private Jedis getPoolResource() {
        if (jedisPool == null) { // check pool is ready
            reinitPool();
            log.info("Re-initiate pool finished.");
        }

        Jedis result;
        try {
            result = jedisPool.getResource();
            /*System.out.println("pool acticve num : " + jedisPool.getNumActive());
            System.out.println("pool idle num : " + jedisPool.getNumIdle());
            System.out.println("pool wait num : " + jedisPool.getNumWaiters());*/
            connectionFailCount = new AtomicInteger(0);
            return result;
        } catch (JedisConnectionException e) {
            int count = connectionFailCount.incrementAndGet();
            log.error("getting resource fail: " + e.getMessage());
            if (count >= MAX_FAIL_CONNECTION_COUNT) {
                log.warn("Reach max continiose connection fail count:" + MAX_FAIL_CONNECTION_COUNT
                        + ",try to destroy the pool.");
                jedisPool.destroy();
                jedisPool = null;
                connectionFailCount = new AtomicInteger(0);
            }
            throw new JedisConnectionException(e);
        }
    }

    /**
     * 统一的错误处理
     *
     * @param msg:错误消息
     * @return ServiceResult 返回调用方
     */
    private HttpResult errorDeal(String msg, Throwable e) {
        if (null != e) {
            log.error(msg + ": " + e.getClass() + e.getMessage(), e);
        }else{
            log.error(msg);
        }
        return HttpResult.error(msg);
    }

    /*********************************************************key-value begin*********************************************************************/

    /**
     * 设置缓存的值同时设置超时时间
     *
     * @param key 键
     * @param value 值
     * @param minutes 超时分钟数
     * @return 成功与否
     */
    public HttpResult putTimeKey(String key, Object value, int minutes) {
        Jedis jedis = null;
        //错误参数判断
        if (minutes <= 0) {
            return errorDeal("超时间设置错误!", null);
        }
        if (StrUtil.isEmpty(key)) {
            return errorDeal("缓存值异常!", null);
        }
        try {
            jedis = getPoolResource();
            byte[] finalvalue = SerializeUtil.serialize(value);
            jedis.setex(getFinalKey(key).getBytes("utf-8"),minutes * 60,finalvalue);
        }  catch (Exception e) {
            return errorDeal("redis操作异常!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return HttpResult.ok(true);
    }

    /**
     * 设置缓存的值
     *
     * @param key 键
     * @param value 值
     * @return 成功与否
     */
    public HttpResult putKey(String key, Object value) {
        Jedis jedis = null;
        //错误参数判断
        if (StrUtil.isEmpty(key)) {
            return errorDeal("缓存值异常!", null);
        }
        try {
            jedis = getPoolResource();
            byte[] finalvalue = SerializeUtil.serialize(value);
            jedis.set(getFinalKey(key).getBytes("utf-8"), finalvalue);
        } catch (Exception e) {
            return errorDeal("redis操作异常!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return HttpResult.ok(true);
    }

    /**
     * 获取设置key值
     *
     * @param key 键
     * @return 值
     */
    public HttpResult getKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            byte[] obj = jedis.get(getFinalKey(key).getBytes("utf-8"));
            return HttpResult.ok((Object) SerializeUtil.unserialize(obj));
        } catch (Exception e) {
            return HttpResult.error("redis操作异常" + e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 删除key
     *
     * @param key 键值
     * @return 成功与否
     */
    public HttpResult delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            jedis.del(getFinalKey(key).getBytes("utf-8"));
        } catch (Exception e) {
            return errorDeal("redis出现故障", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return HttpResult.ok(true);
    }

    /**
     * 将会过期的key延期
     * @param key 键值
     * @param minutes 超时分钟数
     * @return 执行结果
     */
    public HttpResult expireTimeKey(final String key,final int minutes) {
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            jedis.expire(getFinalKey(key).getBytes("utf-8"),minutes * 60);
            return HttpResult.ok(true);
        }catch (Exception e){
            return errorDeal("redis出现故障", e);
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * key自增计数
     *
     * @param key
     * @return
     */
    public HttpResult incrKey(final String key){
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            Long number = jedis.incr(getFinalKey(key).getBytes("utf-8"));
            return HttpResult.ok(true);
        }catch (Exception e){
            return HttpResult.error("redis出现故障");
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 删除所有正则匹配的key
     *
     * @param patternkey 键值
     * @return 成功与否
     */
    public HttpResult delPatternKey(String patternkey) {
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            Set<String> set = jedis.keys(patternkey +"*");
            Iterator<String> it = set.iterator();
            while(it.hasNext()){
                String keyStr = it.next();
                System.out.println(keyStr);
                jedis.del(keyStr);
            }
            return HttpResult.ok(true);
        } catch (Exception e) {
            return errorDeal("redis出现故障", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 是否存在key
     * @param key
     * @return
     */
    public HttpResult existsKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            Boolean exists = jedis.exists(getFinalKey(key).getBytes("utf-8"));
            return HttpResult.ok(exists);
        } catch (Exception e) {
            return errorDeal("redis出现故障", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /*********************************************************key-value end*********************************************************************/

    /*********************************************************key-list begin*********************************************************************/
    /**
     * 在名称为key的list头添加list
     * @param key
     * @param list
     * @return
     */
    public HttpResult lPush(String key, List<?> list){
        Jedis jedis = null;
        if (StrUtil.isEmpty(key)) {
            return errorDeal("缓存值异常!", null);
        }
        try {
            jedis = getPoolResource();
            byte[][] finalvalue = {};
            if(!CollectionUtils.isEmpty(list)){
                finalvalue = new byte[list.size()][];
                for(int i = 0; i < list.size(); i++){
                    byte[] value = SerializeUtil.serialize(list.get(i));
                    finalvalue[i] = value;
                }
            }
            jedis.lpush(getFinalKey(key).getBytes("utf-8"), finalvalue);
            return HttpResult.ok(true);
        } catch (Exception e) {
            return errorDeal("redis操作异常!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    /**
     * 在名称为key的list头添加value
     *
     * @param key
     * @param value
     * @return
     */
    public HttpResult lPush(String key, Object value){
        Jedis jedis = null;
        if (StrUtil.isEmpty(key)) {
            return errorDeal("缓存值异常!", null);
        }
        try {
            jedis = getPoolResource();
            byte[] finalvalue = SerializeUtil.serialize(value);
            jedis.lpush(getFinalKey(key).getBytes("utf-8"), finalvalue);
            return HttpResult.ok(true);
        } catch (Exception e) {
            return errorDeal("redis操作异常!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 在名称为key的list尾添加list
     * @param key
     * @param list
     * @return
     */
    public HttpResult rPush(String key, List<?> list){
        Jedis jedis = null;
        if (StrUtil.isEmpty(key)) {
            return errorDeal("缓存值异常!", null);
        }
        try {
            jedis = getPoolResource();
            byte[][] finalvalue = {};
            if(!CollectionUtils.isEmpty(list)){
                finalvalue = new byte[list.size()][];
                for(int i = 0; i < list.size(); i++){
                    byte[] value = SerializeUtil.serialize(list.get(i));
                    finalvalue[i] = value;
                }
            }
            jedis.rpush(getFinalKey(key).getBytes("utf-8"), finalvalue);
            return HttpResult.ok(true);
        } catch (Exception e) {
            return errorDeal("redis操作异常!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 在名称为key的list尾添加value
     *
     * @param key
     * @param value
     * @return
     */
    public HttpResult rPush(String key, Object value){
        Jedis jedis = null;
        if (StrUtil.isEmpty(key)) {
            return errorDeal("缓存值异常!", null);
        }
        try {
            jedis = getPoolResource();
            byte[] finalvalue = SerializeUtil.serialize(value);
            jedis.rpush(getFinalKey(key).getBytes("utf-8"), finalvalue);
            return HttpResult.ok(true);
        } catch (Exception e) {
            return errorDeal("redis操作异常!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 返回名称为key的list中start至end之间的元素（下标从0开始)
     * @param key
     * @param start
     * @param end
     * @return
     */
    public HttpResult lRange(String key, long start, long end){
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            List<byte[]> list = jedis.lrange(getFinalKey(key).getBytes("utf-8"), start, end);
            List<Object> retList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(list)){
                for (byte[] obj : list){
                    retList.add((Object)SerializeUtil.unserialize(obj));
                }
            }
            return HttpResult.ok(retList);
        } catch (Exception e) {
            return HttpResult.error("redis操作异常" + e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 返回名称为key的list的长度
     *
     * @param key
     * @return
     */
    public HttpResult lLen(String key) {
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            long count = jedis.llen(getFinalKey(key).getBytes("utf-8"));
            return HttpResult.ok(new Integer(new Long(count).intValue()));
        } catch (Exception e) {
            return HttpResult.error("redis出现故障!" + e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 截取名称为key的list，保留start至end之间的元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public HttpResult lTrim(String key, long start, long end){
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            jedis.ltrim(getFinalKey(key).getBytes("utf-8"), start, end);
            return HttpResult.ok(true);
        } catch (Exception e) {
            return HttpResult.error("redis出现故障!" + e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 删除count个名称为key的list中值为value的元素。
     *                                          count为0，删除所有值为value的元素，
     *                                          count>0      从头至尾删除count个值为value的元素，
     *                                          count<0从尾到头删除|count|个值为value的元素
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    public HttpResult lRem(String key, long count, Object value) {
        Jedis jedis = null;
        if(null == value){
            return errorDeal("删除值异常!", null);
        }
        try {
            jedis = getPoolResource();
            byte[] finalvalue = SerializeUtil.serialize(value);
            jedis.lrem(getFinalKey(key).getBytes("utf-8"), count, finalvalue);
            return HttpResult.ok(true);
        } catch (Exception e) {
            return errorDeal("redis出现故障", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /*********************************************************key-list end*********************************************************************/

    /*********************************************************key-hashmap begin****************************************************************/

    /**
     * 向名称为key的hash中添加元素field<—>value
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public HttpResult hSet(String key, String field, Object value){
        Jedis jedis = null;
        //错误参数判断
        if (StrUtil.isEmpty(key) || StrUtil.isEmpty(field)) {
            return errorDeal("缓存值异常!", null);
        }
        try {
            jedis = getPoolResource();
            byte[] finalvalue = SerializeUtil.serialize(value);
            jedis.hset(getFinalKey(key).getBytes("utf-8"), field.getBytes("utf-8"), finalvalue);
            return HttpResult.ok(true);
        } catch (Exception e) {
            return errorDeal("redis操作异常!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 返回名称为key的hash中field对应的value
     *
     * @param key
     * @param field
     * @return
     */
    public HttpResult hGet(String key, String field){
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            byte[] obj = jedis.hget(getFinalKey(key).getBytes("utf-8"), field.getBytes("utf-8"));
            return HttpResult.ok((Object) SerializeUtil.unserialize(obj));
        } catch (Exception e) {
            return HttpResult.error("redis操作异常" + e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 向名称为key的hash中添加多个field value
     *
     * @param key
     * @return
     */
    public HttpResult hMSet(String key, Map<String, Object> hash){
        Jedis jedis = null;
        //错误参数判断
        if (StrUtil.isEmpty(key)) {
            return errorDeal("缓存值异常!", null);
        }
        try {
            jedis = getPoolResource();
            Map<byte[], byte[]> map = new HashMap<>();
            if(!CollectionUtils.isEmpty(hash)){
                for (Map.Entry<String, Object> entry : hash.entrySet()) {
                    map.put(entry.getKey().getBytes("utf-8"), SerializeUtil.serialize(entry.getValue()));
                }
            }
            jedis.hmset(getFinalKey(key).getBytes("utf-8"), map);
            return HttpResult.ok(true);
        } catch (Exception e) {
            return errorDeal("redis操作异常!", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 返回名称为key的hash中f多个field对应的value
     *
     * @param key
     * @param fields
     * @return List<?>
     */
    public HttpResult hMGet(String key, String... fields){
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            byte[][] finalFields = {};
            if(fields.length > 0){
                finalFields = new byte[fields.length][];
                for(int i = 0; i < fields.length; i++){
                    if(StrUtil.isNotEmpty(fields[i])){
                        finalFields[i] = fields[i].getBytes("utf-8");
                    }
                }
            }
            List<byte[]> list = jedis.hmget(getFinalKey(key).getBytes("utf-8"), finalFields);
            List<Object> retList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(list)){
                for(byte[] value : list){
                    Object obj = (Object) SerializeUtil.unserialize(value);
                    retList.add(obj);
                }
            }
            return HttpResult.ok(retList);
        } catch (Exception e) {
            return HttpResult.error("redis操作异常" + e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 名称为key的hash中是否存在键为field的域
     *
     * @param key
     * @param field
     * @return
     */
    public HttpResult hExists(String key, String field){
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            Boolean exists = jedis.hexists(getFinalKey(key).getBytes("utf-8"), field.getBytes("utf-8"));
            return HttpResult.ok(exists);
        } catch (Exception e) {
            return errorDeal("redis出现故障", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 删除名称为key的hash中键为field的域
     *
     * @param key
     * @param field
     * @return
     */
    public HttpResult hDel(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            jedis.hdel(getFinalKey(key).getBytes("utf-8"), field.getBytes("utf-8"));
            return HttpResult.ok(true);
        } catch (Exception e) {
            return errorDeal("redis出现故障", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 返回名称为key的hash中的元素个数
     *
     * @param key
     * @return
     */
    public HttpResult hLen(String key) {
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            long count = jedis.hlen(getFinalKey(key).getBytes("utf-8"));
            return HttpResult.ok(new Integer(new Long(count).intValue()));
        } catch (Exception e) {
            return errorDeal("redis出现故障!" + e,e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 返回名称为key的hash中所有field
     *
     * @param key
     * @return
     */
    public HttpResult hKeys(String key){
        Jedis jedis = null;
        try {
            jedis = getPoolResource();
            Set<byte[]> fieldSet = jedis.hkeys(getFinalKey(key).getBytes("utf-8"));
            List<String> list = new ArrayList<>();
            if(!CollectionUtils.isEmpty(fieldSet)){
                for(byte[] field : fieldSet){
                    if(field.length > 0){
                        list.add(new String(field));
                    }
                }
            }
            return HttpResult.ok(list);
        } catch (Exception e) {
            return errorDeal("redis出现故障!" + e,e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /*********************************************************key-hashmap end****************************************************************/
}
