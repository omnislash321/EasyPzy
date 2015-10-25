package com.money2020.easypzy;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements OnClickListener{

	private TextView scannerResult, apiResults;
	private Button storeSubmitButton, apiCall;
	private Spinner storeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		scannerResult = (TextView)findViewById(R.id.scannerResult);
		apiResults = (TextView)findViewById(R.id.api_Results);
		
		storeSubmitButton = (Button)findViewById(R.id.submitStore);
		apiCall = (Button)findViewById(R.id.callAPI);
		
		storeSpinner = (Spinner)findViewById(R.id.storeSelector);

		storeSubmitButton.setOnClickListener(this);
		apiCall.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void submitStore(View view){

	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

//		
//		Context context = getApplicationContext();
//		CharSequence text = "toast!";
//		int duration = Toast.LENGTH_LONG;
//
//		Toast toast = Toast.makeText(context, text, duration);
//		toast.setGravity(Gravity.BOTTOM, 0, 0);
//
//		toast.setText("clicked");
//		toast.show();
//		
		if(v.getId() == R.id.submitStore){

			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();

		}
		
		if(v.getId() == R.id.callAPI){
			CallAPI call = new CallAPI("https://api-cert.payeezy.com/v1/transactions", this);

//			CallAPI call = new CallAPI("http://httpbin.org/post");

			JSONObject jsonObject = new JSONObject();
			
			try {
				jsonObject.put("merchant_ref","Astonishing-Sale");
				jsonObject.put("transaction_type","purchase");
				jsonObject.put("method","credit_card");
				jsonObject.put("amount", "1299");
				jsonObject.put("currency_code","USD");

				JSONObject creditCard = new JSONObject();
				creditCard.put("type","visa");
				creditCard.put("cardholder_name","John Smith");
				creditCard.put("card_number", "4788250000028291");
				creditCard.put("exp_date","1020");
				creditCard.put("cvv","123");

				jsonObject.put("credit_card",creditCard);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			

			String request = "";
			
			Log.d("testDebug", "executing");
			call.execute(jsonObject);
			Log.d("testDebug", "ending");
			
			apiResults.setText(request);
			
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();

			ArrayList<Product> products = (ArrayList<Product>) Parsing.parseEntries(scanContent);
			Log.d("testing", "products" + products);
//			Intent shoppingIntent = new Intent(this, ShoppingList.class);
			
			scannerResult.setText(scanContent + " " + scanFormat);
			Log.d("testing", "results scaned");
		}
	}
}
