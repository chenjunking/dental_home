package com.dental.home.app.aop;

import com.dental.home.app.common.DBTypeEnum;
import com.dental.home.app.config.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * aop的实现的数据源切换<br> * aop切点，实现mapper类找寻，找到所属大本营以后，如db1Aspect(),则会调用<br> * db1()前面之前的操作，进行数据源的切换。
 */
@Component
@Order(value = -100)
@Slf4j
@Aspect
public class DataSourceAspect {

    @Pointcut("execution(* com.dental.home.app.dao.mysql..*.*(..))")
    private void masterAspect() {
    }

    @Pointcut("execution(* com.dental.home.app.dao.cluster..*.*(..))")
    private void clusterAspect() {
    }

    @Before("masterAspect()")
    public void db1() {
        log.info("切换到master 数据源...");
        DataSourceContextHolder.setDbType(DBTypeEnum.master);
    }

    @Before("clusterAspect()")
    public void db2() {
        log.info("切换到cluster 数据源...");
        DataSourceContextHolder.setDbType(DBTypeEnum.cluster);
    }
}
