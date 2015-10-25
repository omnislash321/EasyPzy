package com.money2020.easypzy;

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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CheckoutActivity extends AppCompatActivity implements OnClickListener{

	private Button addCard, checkoutButton;
	private TableLayout checkoutTableLayout;
	private double totalPrice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		
		checkoutTableLayout = (TableLayout)findViewById(R.id.checkoutTable);
		
		addCard = (Button)findViewById(R.id.addCard);
		checkoutButton = (Button)findViewById(R.id.checkout);
		
		addCard.setOnClickListener(this);
		checkoutButton.setOnClickListener(this);
		
		Intent i = getIntent();
		totalPrice = Double.parseDouble(i.getStringExtra("total"));
		
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
		if(v.getId() == R.id.addCard){
//			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
//			scanIntegrator.initiateScan();
		}
		
		if(v.getId() == R.id.checkoutButton){
			sendRequest();
		}
		
	}

	public void sendRequest(){
		CallAPI call = new CallAPI("https://api-cert.payeezy.com/v1/transactions", this, 0);

//		CallAPI call = new CallAPI("http://httpbin.org/post");

		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("merchant_ref","Astonishing-Sale");
			jsonObject.put("transaction_type","purchase");
			jsonObject.put("method","credit_card");
			jsonObject.put("amount", (int)(totalPrice*100));
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
		
		
		Log.d("testDebug", "executing");
		call.execute(jsonObject);
		Log.d("testDebug", "ending");
		
	}
	
	public void addResult(Product selectedProduct){

		TableRow row = new TableRow(getApplicationContext());
		TextView resultText = new TextView(getApplicationContext());
		
		resultText.setText(selectedProduct.productName);
		
		row.addView(resultText);
		
		checkoutTableLayout.addView(row);
	}
}

