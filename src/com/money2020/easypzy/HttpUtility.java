package com.money2020.easypzy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.util.Log;

public class HttpUtility {
	
	private final static HttpClient mHhttpclient = new DefaultHttpClient();
	private static String apiKey = "haHBGCvFGdHWf0qGejVIjLGZAY2PwS9W";
	private static String apiSecret = "746a6a9cae476ee863e3f588ae9676dfa41c047d2146d30353d271fabbc892a0";
	private static String token = "84040f1b4d698e3e";
	private static String 
	
	public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            //HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = mHhttpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null){
            	result = convertInputStreamToString(inputStream);
            	//inputStream.close();
            }
                
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
	
	public static String POST(String url, HashMap<Object, Object> mParams){
		InputStream inputStream = null;
        String result = "";
		try{
			//HttpClient httpclient = new DefaultHttpClient();
	        HttpPost post = new HttpPost(url);
	        post.setEntity(new UrlEncodedFormEntity((List<? extends NameValuePair>) mParams, "UTF-8"));
	        HttpResponse httpResponse = mHhttpclient.execute(post);
	        
	     // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null){
            	result = convertInputStreamToString(inputStream);
            	//inputStream.close();
            }
                
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
		
        
	}
	public static String POST(String url, JSONObject obj){
		
		Log.i("JSONPOSTBEGIN", "Beginning of JSON POST");
		InputStream inputStream = null;
        String result = "";
	    
	    //HttpClient httpclient = new DefaultHttpClient();

	    try{
	    	HttpPost post = new HttpPost(url);
	    	post.setHeader("Content-type", "application/json");
	    	post.setHeader("Accept", "application/json");
	    	
	    	
	    	
	    	
	    	
	    	StringEntity se = new StringEntity(obj.toString());
//	    	//se.setContentType("application/json;charset=UTF-8");
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	        post.setEntity(se);
	        HttpResponse httpResponse = mHhttpclient.execute(post);
	        
	        // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

	     
            // convert inputstream to string
            if(inputStream != null){
            	result = convertInputStreamToString(inputStream);
            }
                
            else
                result = "Did not work!";

	        Log.i("JSONTEST", result);
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
	    Log.i("JSONPOSTEND", "End of JSON data post method...");
	    return result;
	}
	
	public static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
    }
	
	
	public String getMacValue(Map<String,String> data) throws Exception{ 
		//428346598171629000
		Random rand = new Random(); 
		long nonce = rand.nextLong() * 999999999999999999;
		
		Mac mac=Mac.getInstance("HmacSHA256"); 
		String apiSecret= data.get(apiSecret); 
//		MessageLogger.logMessage(String.format("API_SECRET:{}",apiSecret)); 
		SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256"); 
		mac.init(secret_key); StringBuilder buff=new StringBuilder(); 
		buff.append(data.get(apiKey)) .append(data.get(NONCE)) .append(data.get(System.currentTimeMillis()/1000)); 
		if(data.get(token)!=null) 
			buff.append(data.get(token)); 
		if(data.get(PAYLOAD)!=null) 
			buff.append(data.get(PAYLOAD)); 
		String bufferData = buff.toString(); 
//		MessageLogger.logMessage(String.format(bufferData)); 
		byte[] macHash=mac.doFinal(bufferData.getBytes("UTF-8")); 
//		MessageLogger.logMessage(Integer.toString(macHash.length)); 
//		MessageLogger.logMessage(String.format("MacHAsh:{}",Arrays.toString( macHash))); 
		String authorizeString=android.util.Base64.encodeToString(toHex(macHash), android.util.Base64.NO_WRAP); 
//		MessageLogger.logMessage(String.format("Authorize: {}",authorizeString)); 
		return authorizeString;
	}
}