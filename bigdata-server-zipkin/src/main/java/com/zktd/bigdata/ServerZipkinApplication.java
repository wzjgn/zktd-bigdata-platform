package com.zktd.bigdata;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import zipkin.server.EnableZipkinServer;
import zipkin.storage.mysql.MySQLStorage;


@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer
public class ServerZipkinApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(ServerZipkinApplication.class, args);
    }

    @Bean
    @Primary
    public MySQLStorage mySQLStorage(DataSource datasource) {
        return MySQLStorage.builder().datasource(datasource).executor(Runnable::run).build();
    }
}