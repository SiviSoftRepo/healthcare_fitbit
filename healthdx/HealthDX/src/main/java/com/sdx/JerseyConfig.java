package com.sdx;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;
import com.sdx.controller.*;


import lombok.extern.slf4j.Slf4j;

@Component 
@Slf4j
public class JerseyConfig extends ResourceConfig
{
    public JerseyConfig()
    {
    	
    	log.info("REGISTERING the Resources");
    	packages(true, "com.sdx");
    	
    	register(HomeCareController.class);
    	
       // register(UserResource.class);
       // register(AppProperties.class);
        
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }
}