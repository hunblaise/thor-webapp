package com.balazs.hajdu.config;

import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


/**
 * A class to configure the database connection.
 * @author Balazs Hajdu
 */
@Configuration
@Lazy
@EnableMongoRepositories(basePackages = "com.balazs.hajdu.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    private static final String DATABASE_NAME = "homecontrol";
    private static final String HOST = "localhost";
    private static final int PORT = 27017;

    @Value("${mongodb.host}")
    private String host;

    @Value("${mongodb.port}")
    private int port;

    @Value(("${mongodb.databasename}"))
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Bean
    @Override
    public MongoClient mongo() throws Exception {
        MongoClient mongoClient = new MongoClient(HOST, PORT);
        mongoClient.setWriteConcern(WriteConcern.SAFE);
        return mongoClient;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(mongo(), DATABASE_NAME);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory(), mongoConverter());
    }

    @Bean
    public MongoTypeMapper mongoTypeMapper() {
        return new DefaultMongoTypeMapper(null);
    }

    @Bean
    public MongoMappingContext mongoMappingContext() {
        return new MongoMappingContext();
    }

    @Bean
    public MappingMongoConverter mongoConverter() throws Exception {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext());
        converter.setTypeMapper(mongoTypeMapper());
        return converter;
    }
}
