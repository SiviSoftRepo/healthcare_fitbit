	package com.sdx.camel;
import java.net.*;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit; 

public class URLConnectionReader {
    public static void main(String[] args) throws Exception {
    	TimeUnit time = TimeUnit.MINUTES;
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	
    	
    	while(true) {
    		LocalDate localDate = LocalDate.now();
    		String date = dtf.format(localDate);
    		
    	try {
        URL fibit = new URL("http://localhost:10101/healthDX/device/pull/fitbitsivi/"+date);
        URLConnection yc = fibit.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        //in.close();
    }catch (Exception e) {
	}
    	time.sleep(2);
}
    }
}