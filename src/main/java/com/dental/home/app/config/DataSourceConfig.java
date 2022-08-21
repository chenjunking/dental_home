package com.dental.home.app.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * 多数据源配置
 */
@Configuration
public class DataSourceConfig{

    @Bean(name = "master")
    @ConfigurationProperties(prefix = "master-spring.datasource")
    public DataSource master() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "cluster")
    @ConfigurationProperties(prefix = "cluster-spring.datasource")
    public DataSource cluster() {
        DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
        druidDataSource.setInitialSize(10);
        return druidDataSource;
    }


    @Bean("jtaTransactionManager")
    @Primary
    public JtaTransactionManager activitiTransactionManager() throws SystemException {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
}
