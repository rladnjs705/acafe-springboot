package com.javadeveloperzone.config;//package com.example.acafekiosk.config;
//
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//@Configuration
//@EnableMongoRepositories
//public class MongoConfig extends AbstractMongoClientConfiguration {
//
//    @Value("${spring.data.mongodb.username}")
//    private String userName;
//
//    @Value("${spring.data.mongodb.password}")
//    private String password;
//    @Value("${spring.data.mongodb.database}")
//    private String database;
//
//
//    @Override
//    protected String getDatabaseName() {
//        return database;
//    }
//
//    public MongoClient mongoClient() {
//        ConnectionString connStr = new ConnectionString("mongodb://localhost:27017/sungbok-acafe");
//        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connStr).build();
//        return MongoClients.create(mongoClientSettings);
//    }
//}
