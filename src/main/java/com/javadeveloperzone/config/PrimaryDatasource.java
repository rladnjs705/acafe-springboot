//package com.javadeveloperzone.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class PrimaryDatasource {
//	@Bean
//	@ConfigurationProperties(prefix="spring.primary.datasource.hikari")
//	public HikariConfig hikariConfig() {
//		return new HikariConfig();
//	}
//
//    @Primary
//    @Bean(name="dataSource")
//    public DataSource dataSource() {
//    	HikariConfig config = hikariConfig();
//    	DataSource dataSource = new HikariDataSource(config);
//		return dataSource;
//    }
//
//    @Primary
//    @Bean(name="transactionManager")
//    public DataSourceTransactionManager transactionManager(@Autowired @Qualifier("dataSource") DataSource dataSource) {
//    	DataSourceTransactionManager transactionMgr = new DataSourceTransactionManager(dataSource);
//    	transactionMgr.setDefaultTimeout(600);
//        return transactionMgr;
//    }
//
//}