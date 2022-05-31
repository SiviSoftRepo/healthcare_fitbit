/*
 ***************************************************************************************
 *  Copyright (C) 2006 EsperTech, Inc. All rights reserved.                            *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 ***************************************************************************************
 */
package com.sdx.platform.util;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.DeploymentOptions;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;

public class EPLCompileUtil {
    public static EPDeployment compileDeploy(EPRuntime runtime, String epl) {
        try {
        	
            Configuration configuration = runtime.getConfigurationDeepCopy();
            CompilerArguments args = new CompilerArguments(configuration);
            
            args.getPath().add(runtime.getRuntimePath());
            EPCompiled compiled = EPCompilerProvider.getCompiler().compile(epl, args);
            EPDeployment retCMP = runtime.getDeploymentService().deploy(compiled);
            
            System.out.println("runtime.getDeploymentService() "+Arrays.toString(runtime.getDeploymentService().getDeployments()));
            
            return retCMP;
            
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    
    
    public static EPDeployment compileDeploy(EPRuntime runtime, String epl, String deploymentName) {
        try {
        	
            Configuration configuration = runtime.getConfigurationDeepCopy();
            CompilerArguments args = new CompilerArguments(configuration);
            args.getPath().add(runtime.getRuntimePath());
            
            EPCompiled compiled = EPCompilerProvider.getCompiler().compile(epl, args);
            EPDeployment retCMP = null;
            if (StringUtils.isNotBlank(deploymentName)) {
                retCMP = runtime.getDeploymentService().deploy(compiled, new DeploymentOptions().setDeploymentId(deploymentName)); 
            } else {
            	retCMP = runtime.getDeploymentService().deploy(compiled); 
            }
            
            return retCMP;
            
            
            
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
