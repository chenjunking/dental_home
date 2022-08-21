package com.dental.home.app.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
public class RedisCacheConf implements Serializable {

    /**
     * redis服务器地址
     */
    @Value("${redis.host}")
    private String host;

    /**
     * 端口号
     */
    @Value("${redis.port}")
    private Integer port;

    /**
     *  密码
     */
    @Value("${redis.pass}")
    private String pass;

    /**
     *  超时时间
     */
    @Value("${redis.timeout}")
    private Integer timeout;

    /**
     * 总数
     */
    @Value("${redis.pool.maxTotal}")
    private Integer maxTotal;

    /**
     * 最大空闲
     */
    @Value("${redis.pool.maxIdle}")
    private Integer maxIdle;

    /**
     * 最最小空闲
     */
    @Value("${redis.pool.minIdle}")
    private Integer minIdle;

    /**
     * 最大等待
     */
    @Value("${redis.pool.maxWaitMillis}")
    private Integer maxWait;

    /**
     * testOnBorrow参数
     */
    @Value("${redis.pool.testOnBorrow}")
    private Boolean testOnBorrow;

    /**
     * testOnReturn
     */
    @Value("${redis.pool.testOnReturn}")
    private Boolean testOnReturn;

    public RedisCacheConf() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public Boolean getTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(Boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

}
