package com.sdx.platform.quartz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.ws.rs.*;

@Path("/SDX")
public class HTMLFileService {
	
	@javax.ws.rs.core.Context 
	ServletContext context;
	
	public HTMLFileService() {
		System.out.println("HTML File serices Initialized");
	}
	
	@GET
	@Path("/{id}")
	public InputStream getHTMLFile(@PathParam("id") String fileName) {
		System.out.println("Look for is "+fileName);
		
		try {
            String base = context.getRealPath("");
            
            System.out.println("BASE is "+base);
            
            File findFile = new File(String.format("%s/%s", base, fileName));
            System.out.println("Finding File "+findFile.getAbsolutePath());
            return new FileInputStream(findFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
	}

}