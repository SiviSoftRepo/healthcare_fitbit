package com.sdx.fitbit;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import java.io.IOException;


/**
 * Created by hd on 10/09/16.
 */
public class FitbitApi20Example {

    private static final String NETWORK_NAME = "Fitbit";

    // Replace these with your client id and secret fron your app
    private static final String CLIENT_ID = "22BFMM";
    private static final String CLIENT_SECRET = "04efb7253c260779765812783014694c";
    
    // Replace with user ID to test API against
    private static final String USER_ID = "89RXM6";
    
    //this url is used to get user's profile
   // private static final String PROTECTED_RESOURCE_URL = "https://api.fitbit.com/1/user/" + USER_ID + "/profile.json";
    
    private static final String PROTECTED_RESOURCE_URL = "https://api.fitbit.com/1/user/" + USER_ID + "/activities/steps/date/2020-03-03/1d.json";
    
   // private static final String PROTECTED_RESOURCE_URL = "https://api.fitbit.com/1.2/user/" + USER_ID + "/sleep/date/2020-02-27.json";
  //private static final String PROTECTED_RESOURCE_URL = "https://api.fitbit.com/1/user/"+USER_ID+"/activities/list/date/2020-03-03.json";


    private FitbitApi20Example() {
    }

    public static void main(String... args) throws Exception {
    	
    	
    	
    	
    	getValue();
    

        //System.out.println();
    }
    public static HashMap getValue() throws InterruptedException, ExecutionException, IOException {

        final OAuth20Service service = new ServiceBuilder(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .scope("activity heartrate location nutrition profile settings sleep social weight") // replace with desired scope
                //.scope("profile settings")  
                .callback("http://localhost:8080/fitbit/get")  //your callback URL to store and handle the authorization code sent by Fitbit
                //.state("some_params")
                .build(FitbitApi20.instance());
        final Scanner in = new Scanner(System.in);

        System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
        System.out.println();
    	
        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        final String authorizationUrl = service.getAuthorizationUrl();
        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize ScribeJava here:");
        System.out.println(authorizationUrl);
        System.out.println("And paste the authorization code here");
        System.out.print(">>");
        final String code = in.nextLine();
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        final OAuth2AccessToken accessToken = service.getAccessToken(code);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken
                + ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
        System.out.println();

        // Now let's go and ask for a protected resource!
        // This will get the profile for this user
        System.out.println("Now we're going to access a protected resource...");

        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        request.addHeader("x-li-format", "json");

        //add header for authentication (why make it so complicated, Fitbit?)
        request.addHeader("Authorization", "Bearer " + accessToken.getAccessToken());

        
        HashMap map = new HashMap();
        
        
        final Response response = service.execute(request);
        
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        map.put("FitBit", response.getBody());
        System.out.println("Map Value "+ map);
       
    	
		return map;
    	
    	
    	
    }
}
