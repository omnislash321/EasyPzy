package com.money2020.easypzy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;

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
import org.json.JSONObject;

import android.util.Log;

@SuppressWarnings("deprecation")
public class HttpUtility {
	
	private final static HttpClient mHhttpclient = new DefaultHttpClient();
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	
	private static String apiKey = "haHBGCvFGdHWf0qGejVIjLGZAY2PwS9W";
	private static String apiSecret = "746a6a9cae476ee863e3f588ae9676dfa41c047d2146d30353d271fabbc892a0";
	private static String token = "fdoa-a480ce8951daa73262734cf102641994c1e55e7cdf4c02b6";

	
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
	public static String POST_PAYMENT(String url, JSONObject obj){
		
		Log.i("JSONPOSTBEGIN", "Beginning of JSON POST");
		InputStream inputStream = null;
        String result = "";
	    
	    //HttpClient httpclient = new DefaultHttpClient();

	    try{
	    	HttpPost post = new HttpPost(url);
	    	post.setHeader("Content-type", "application/json");
	    	post.setHeader("Accept", "application/json");
	    	

			Long epoch = System.currentTimeMillis();
			
			Long nonce = Math.abs(SecureRandom.getInstance("SHA1PRNG").nextLong());
	    	
	    	post.setHeader("apikey",apiKey);
	    	post.setHeader("Authorization",getMacValue(obj.toString(), nonce, epoch));
	    	post.setHeader("token",token);
	    	post.setHeader("nonce", nonce.toString());
	    	post.setHeader("timestamp", epoch.toString());
	    	
	    	Log.d("TEST", getMacValue(obj.toString(),nonce, epoch));
	    	
	    	
	    	StringEntity se = new StringEntity(obj.toString());
//	    	//se.setContentType("application/json;charset=UTF-8");
//	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
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
	
public static String POST_TOKENIZE(String url, JSONObject obj){
		
		Log.i("JSONPOSTBEGIN", "Beginning of JSON POST");
		InputStream inputStream = null;
        String result = "";
	    
	    //HttpClient httpclient = new DefaultHttpClient();

	    try{
	    	HttpPost post = new HttpPost(url);
	    	post.setHeader("Content-type", "application/json");
	    	post.setHeader("Accept", "application/json");
	    	

			Long epoch = System.currentTimeMillis();
			
			Long nonce = Math.abs(SecureRandom.getInstance("SHA1PRNG").nextLong());
	    	
	    	post.setHeader("apikey",apiKey);
	    	post.setHeader("Authorization",getMacValue(obj.toString(), nonce, epoch));
	    	post.setHeader("token",token);
	    	post.setHeader("nonce", nonce.toString());
	    	post.setHeader("timestamp", epoch.toString());
	    	
	    	Log.d("TEST", getMacValue(obj.toString(),nonce, epoch));
	    	
	    	
	    	StringEntity se = new StringEntity(obj.toString());
//	    	//se.setContentType("application/json;charset=UTF-8");
//	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
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
	
	
	public static String getMacValue(String payload, long nonce, long epoch) throws Exception{ 
		//428346598171629000

//		nonce = Long.parseLong("2389190687565133000");
//		epoch = Long.parseLong("1445757048698");
		
		Mac mac=Mac.getInstance("HmacSHA256"); 
//		MessageLogger.logMessage(String.format("API_SECRET:{}",apiSecret)); 
		SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256"); 
		mac.init(secret_key); StringBuilder buff=new StringBuilder(); 
		buff.append(apiKey).append(nonce).append(epoch);
		buff.append(token); 
		buff.append(payload); 
		
		Log.d("TEST", "apikey: " + apiKey);
		Log.d("TEST", "nonce: " + nonce);
		Log.d("TEST", "epoch: " + epoch);
		Log.d("TEST", "token: " + token);
		Log.d("TEST", "payload: " + payload);
		
		
		String bufferData = buff.toString(); 
//		MessageLogger.logMessage(String.format(bufferData)); 
		byte[] macHash=mac.doFinal(bufferData.getBytes("UTF-8")); 
		Log.d("TEST", "machasH: " + Integer.toString(macHash.length));
//		MessageLogger.logMessage(Integer.toString(macHash.length)); 
//		MessageLogger.logMessage(String.format("MacHAsh:{}",Arrays.toString( macHash))); 
		
		String authorizeString=android.util.Base64.encodeToString(toHex(macHash).getBytes(), android.util.Base64.NO_WRAP); 
//		MessageLogger.logMessage(String.format("Authorize: {}",authorizeString)); 
		return authorizeString;
	}
	
	private static String toHex(byte[] data){
		final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = hexArray[(0xF0 & data[i]) >>> 4];
            out[j++] = hexArray[0x0F & data[i]];
        }
        return new String(out);
		//return Hex.encodeHexString(data);
	}
	
}