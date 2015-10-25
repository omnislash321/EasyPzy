package com.money2020.easypzy;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

public class CallAPI extends AsyncTask<JSONObject, Integer, String>{
	private String path;
	private Activity activity;
	private int type;
	
	public CallAPI(String url, Activity activity, int type){
		path = url;
		this.activity = activity;
		this.type = type;
	}

	@Override
	protected String doInBackground(JSONObject... params) {
		if(type == 0){
			return HttpUtility.POST_PAYMENT(path, params[0] );
		}else if(type == 1){
			return HttpUtility.POST_TOKENIZE(path, params[0] );
		}else{
			return "";
		}
	}
	
	@Override
	protected void onPostExecute(String result) {
//		TextView resultsField = (TextView)activity.findViewById(R.id.api_Results);
//		resultsField.setText(result);
    }

}