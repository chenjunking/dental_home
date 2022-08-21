package com.dental.home.app.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局事务配置
 */
@Aspect
@Configuration
@Slf4j
public class TransactionManagerConfig {
    /**
     * 配置方法过期时间（秒），默认-1,永不超时
     */
    private static final int AOP_TIME_OUT = 10;

    /**
     * 全局事务位置配置 在哪些地方需要进行事务处理：具体如下
     * 配置切入点表达式,这里解释一下表达式的含义
     * 1.execution(): 表达式主体
     * 2.第一个*号:表示返回类型，*号表示所有的类型
     * 3.com.test.lee.service表示切入点的包名
     * 4.第二个*号:表示实现包
     * 5.*(..)表示所有方法名,..表示所有类型的参数
     */
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.dental.home.app.service.*.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 全局事务配置
     * REQUIRED ：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
     * SUPPORTS ：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
     * MANDATORY ：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
     * REQUIRES_NEW ：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
     * NOT_SUPPORTED ：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
     * NEVER ：以非事务方式运行，如果当前存在事务，则抛出异常。
     * NESTED ：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于 REQUIRED 。
     * 指定方法：通过使用 propagation 属性设置，例如：@Transactional(propagation = Propagation.REQUIRED)
     */
    @Bean
    public TransactionInterceptor txAdvice(){
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        /** 配置事务管理规则 */
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        /** 只读事务，不做更新操作 */
        readOnlyTx.setReadOnly(true);
        /** 设置事务的传播机制，当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 */
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);


        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        /** 什么异常需要回滚 */
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /** 设置超时时间，超时则抛出异常回滚 */
        requiredTx.setTimeout(AOP_TIME_OUT);

        /**
         * methodMap声明具备需要管理事务的方法名.
         */
        Map<String, TransactionAttribute> methodMap = new HashMap<>();

        /** 可以提交事务或回滚事务的方法 */
        methodMap.put("add*", requiredTx);
        methodMap.put("save*", requiredTx);
        methodMap.put("update*", requiredTx);
        methodMap.put("modify*", requiredTx);
        methodMap.put("edit*", requiredTx);
        methodMap.put("insert*", requiredTx);
        methodMap.put("delete*", requiredTx);
        methodMap.put("remove*", requiredTx);
        methodMap.put("repair*", requiredTx);
        methodMap.put("binding*", requiredTx);

        /** 其他方法无事务，只读 */
        methodMap.put("*", readOnlyTx);
        source.setNameMap(methodMap);

        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
        return txAdvice;
    }

    /** 设置切面=切点pointcut+通知TxAdvice */
    @Bean(name = "txAdviceAdvisor")
    public Advisor txAdviceAdvisor() {
        log.info("===============================创建txAdviceAdvisor===================================");
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
