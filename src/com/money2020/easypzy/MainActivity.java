package com.money2020.easypzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements OnClickListener{

	private Button storeSubmitButton;
	private Spinner storeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		storeSubmitButton = (Button)findViewById(R.id.submitStore);
		
		storeSpinner = (Spinner)findViewById(R.id.storeSelector);

		storeSubmitButton.setOnClickListener(this);
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
			Intent i = new Intent(getApplicationContext(), ShoppingListActivity.class);
			i.putExtra("storeId", storeSpinner.getSelectedItem().toString());
			startActivity(i);
//			
//			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
//			scanIntegrator.initiateScan();

		}
	}
}
