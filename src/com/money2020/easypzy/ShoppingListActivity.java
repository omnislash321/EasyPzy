package com.money2020.easypzy;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ShoppingListActivity extends AppCompatActivity implements OnClickListener{

	private ArrayList<Product> products;
	
	private TextView totalPrice;
	private Button openScannerButton, checkoutButton;
	private TableLayout shoppingTableLayout;
	private String storeId;
	private double totalDoublePrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_list);
		
		totalPrice = (TextView)findViewById(R.id.totalPrice);
		
		shoppingTableLayout = (TableLayout)findViewById(R.id.shoppingTable);
		
		openScannerButton = (Button)findViewById(R.id.openScanner);
		checkoutButton = (Button)findViewById(R.id.checkoutButton);
		
		openScannerButton.setOnClickListener(this);
		checkoutButton.setOnClickListener(this);
		
		Intent i = getIntent();
		storeId = i.getStringExtra("storeId");
		
		products = new ArrayList<Product>();
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
		if(v.getId() == R.id.openScanner){
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
		}
		
		if(v.getId() == R.id.checkoutButton){
			Intent i = new Intent(getApplicationContext(), CheckoutActivity.class);
			i.putExtra("total", totalPrice.getText());
			startActivity(i);
		}
		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();
			
			ArrayList<Product> parsedResult = (ArrayList<Product>) Parsing.parseEntries(scanContent);
			Log.d("testing", "products" + products);
//			Intent shoppingIntent = new Intent(this, ShoppingList.class);
			
			if(parsedResult.size() > 0){
				products.add(parsedResult.get(0));
				totalDoublePrice += parsedResult.get(0).getPrice();
				addResult(parsedResult.get(0));
			}
			Log.d("testing", "results scaned");
		}
	}
	
	public void addResult(Product selectedProduct){

		TableRow row = new TableRow(getApplicationContext());
		TextView resultText = new TextView(getApplicationContext());
		
		resultText.setText(selectedProduct.productName);
		
		row.addView(resultText);
		
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		double finalPrice = Double.valueOf(twoDForm.format(totalDoublePrice));
		
		totalPrice.setText(Double.toString(finalPrice));
		
		shoppingTableLayout.addView(row);
	}
}

