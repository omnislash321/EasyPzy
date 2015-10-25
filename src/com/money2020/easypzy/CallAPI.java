package com.money2020.easypzy;

import org.json.JSONObject;

import android.os.AsyncTask;

@SuppressWarnings("deprecation")
public class CallAPI extends AsyncTask<JSONObject, Integer, String>{
	private String path;

	public CallAPI(String url){
		path = url;
	}

	@Override
	protected String doInBackground(JSONObject... params) {
		return HttpUtility.POST(path, params[0] );
	}
	
	protected void onPostExecute(Long result) {
		
    }

}