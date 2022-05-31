package com.sdx;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.sdx.platform.config.Memory;
import com.sdx.platform.quartz.DefaultActions;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class SDXHealthCareApplication {

    public static void main(String[] args) throws Exception {
    	
    	Memory.init();
    	DefaultActions.init();
        SpringApplication.run(SDXHealthCareApplication.class, args);
    }
}
