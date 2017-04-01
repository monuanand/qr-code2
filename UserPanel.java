package com.example.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.example.scanner.R;
import com.example.scanner.Scanner;

public class UserPanel extends Activity {
	String userid;
	private ProgressDialog prgDialog;
	
	String value;
	
	private SharedPreferences preferences;
	private String username;
TextView txtResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_panel);
		username=getIntent().getStringExtra("username");
		txtResult= (TextView) findViewById(R.id.txtResult);
		preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
	}

public void profile(View v){
	Intent i = new Intent(getBaseContext(), Profile.class);
	i.putExtra("username", username);
	i.putExtra("username", username);
	startActivity(i);
}
	public void updateProfile(View v){
		Intent i = new Intent(getBaseContext(), UpdateProfile.class);
		i.putExtra("username", username);
		i.putExtra("username", username);
		startActivity(i);
	}
	public void signout(View v){
		preferences.edit().clear().commit();
		Intent i1 = new Intent(getBaseContext(),
				LoginActivity.class);
		i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i1);
	}
	public void scanQRCode(View v) {
		Intent i=new Intent(getBaseContext(),Scanner.class);
		startActivityForResult(i,001);
		
		
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 001) {
			if (resultCode == RESULT_OK) {
				String value = data.getStringExtra("value");
				txtResult.setText(value);
			}
		}
	}

	public void check(View v){
	
	}
	
	
	
	
}
