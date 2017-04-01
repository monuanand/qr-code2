package com.example.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scanner.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Registeration extends Activity {

	String strFirstname = "";
	String strLastname = "";
	String strusername = "";
	String strpassword = "";
	String strFathername = "";
	String strMobile = "";
	String strEmailid = "";
	String strAddress = "";
	String strUsertype = "";

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registeration);
		TextView tvtitle = (TextView) findViewById(R.id.tvTitle);
		Intent i = getIntent();
		String title = i.getStringExtra("title");
		tvtitle.setText(title);
		strUsertype = i.getStringExtra("usertype");
		System.out.println("usertype=" + strUsertype);
		final EditText etFirstname = (EditText) findViewById(R.id.etFirstName);
		final EditText etLastname = (EditText) findViewById(R.id.etLastname);
		final EditText etusername = (EditText) findViewById(R.id.etusername1);
		final EditText etpassword = (EditText) findViewById(R.id.etpassword);
		final EditText etFathername = (EditText) findViewById(R.id.etfathername);
		final EditText etMobile = (EditText) findViewById(R.id.etmobile);
		final EditText etEmailid = (EditText) findViewById(R.id.etemailid);
		final EditText etAddress = (EditText) findViewById(R.id.etaddress);

		Button btnSubmit = (Button) findViewById(R.id.btnUpdate);

		btnSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					strFirstname = etFirstname.getText().toString();
					strLastname = etLastname.getText().toString();

					strusername = etusername.getText().toString();
					strpassword = etpassword.getText().toString();
					strFathername = etFathername.getText().toString();
					strMobile = etMobile.getText().toString();
					strEmailid = etEmailid.getText().toString();
					strAddress = etAddress.getText().toString();
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("name=" + strFirstname + ":" + strLastname
						+ ":" + strusername + ":" + strUsertype);
				Context context = Registeration.this;
				String title = "Warning!!";
				String message = "Save Details";
				String button1String = "Save";
				String button2String = "Cancel";
				AlertDialog.Builder ad = new AlertDialog.Builder(context);
				ad.setTitle(title);
				ad.setMessage(message);
				ad.setPositiveButton(button1String, new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						saveDetails();
					}
				});
				ad.setNegativeButton(button2String, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(getBaseContext(), "Canceled!",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				});

				ad.show();

			}
		});

	}

	public void saveDetails() {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");

		Calendar cal = Calendar.getInstance();

		String reg_date = dateFormat.format(cal.getTime());// "11/03/14 12:33:43";
		String reg_time = dateFormat1.format(cal.getTime());
		long check1 = 0;
		DBAdapter1 db1 = new DBAdapter1(getBaseContext());
		db1.open();
		if (strusername.length() <6) {

			Toast.makeText(getBaseContext(),
					"username should be greater than 5 digits", Toast.LENGTH_SHORT)
					.show();
		}else if(strpassword.length() < 6
				) {
			Toast.makeText(getBaseContext(),
					"Password should be greater than 5 digits", Toast.LENGTH_SHORT)
					.show();
		}else if(strFirstname.length() < 3){
			Toast.makeText(getBaseContext(),
					"First Name should be greater than 2 digits", Toast.LENGTH_SHORT)
					.show();
		}
		else if ((strEmailid.length() < 6)
				&& !(strEmailid.contains("@") && strEmailid.contains("."))) {
			Toast.makeText(getBaseContext(),
					"Please check email id", Toast.LENGTH_SHORT)
					.show();

		} else {
			if (verifyName(strFirstname) && verifyName(strLastname)
					) {
				if (strMobile.length() == 10) {
					check1 = db1.registerUser(strusername, strUsertype,
							strpassword, strFirstname, strLastname,
							strFathername, strMobile, strEmailid, strAddress,
							reg_date, reg_time, "True");
					
				} else {
					Toast.makeText(getBaseContext(),
							"Mobile no. should be of 10 digits Current Length: "+strMobile.length(),
							Toast.LENGTH_SHORT).show();
				}

			} else {
				Toast.makeText(getBaseContext(),
						"Names should be alphabeticals only!!", Toast.LENGTH_SHORT)
						.show();
			}

			if (check1 > 0) {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager
						.sendTextMessage(
								strMobile,
								null,
								"You have been registered in Digital Identification System & your userid & password are given below: Loginid="
										+ strEmailid
										+ " Password="
										+ strpassword, null, null);
				
				
				Log.d("sms1 ", "sent");
				Toast.makeText(getBaseContext(), "Registration successfull",
						Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(getBaseContext(), "An Error occurred",
						Toast.LENGTH_SHORT).show();
			}
		}

		db1.close();
		
		
			
	}

	private boolean verifyName(String lname) {
		lname = lname.trim();

		if (lname == null || lname.equals(""))
			return false;

		if (!lname.matches("[a-zA-Z]*"))
			return false;

		return true;
	}
}