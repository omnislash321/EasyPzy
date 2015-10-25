package com.money2020.easypzy;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

public class CallAPI extends AsyncTask<JSONObject, Integer, String>{
	private String path;
	private Activity activity;
	
	public CallAPI(String url, Activity activity){
		path = url;
		this.activity = activity;
	}

	@Override
	protected String doInBackground(JSONObject... params) {
		return HttpUtility.POST(path, params[0] );
	}
	
	@Override
	protected void onPostExecute(String result) {
		TextView resultsField = (TextView)activity.findViewById(R.id.api_Results);
		resultsField.setText(result);
    }

}