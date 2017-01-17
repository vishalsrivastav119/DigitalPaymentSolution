package com.example.dpouch;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegisterPhoneActivitySuccess extends Activity implements OnClickListener {
	
private TextView make,model,imei,status,msisdn;
private String mssdnString,manufacturerString,modelString,statusString,imeiString,sdkString;
private Button yesButtonPhoneRegisterSuccess,noButtonPhoneRegisterSuccess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_phone_activity_success);
		make=(TextView) findViewById(R.id.makePhoneRegisterSuccess);
		model=(TextView) findViewById(R.id.modelPhoneRegisterSuccess);
		imei=(TextView) findViewById(R.id.imeiPhoneRegisterSuccess);
		msisdn=(TextView) findViewById(R.id.msisdnPhoneRegisterSuccess);
		status=(TextView)findViewById(R.id.statusPhoneRegisterSuccess);
		Intent intent=getIntent();
		mssdnString=intent.getStringExtra("mssdnString");
		manufacturerString=intent.getStringExtra("manufacturerString");
		modelString=intent.getStringExtra("modelString");
		sdkString=intent.getStringExtra("sdkString");
		imeiString=intent.getStringExtra("imeiString");
		statusString=intent.getStringExtra("status");
		status.setText(statusString);
		make.setText(manufacturerString);
		model.setText(modelString);
		imei.setText(imeiString);
		msisdn.setText(mssdnString);
		yesButtonPhoneRegisterSuccess=(Button)findViewById(R.id.YesButtonPhoneRegisterSuccess);
		noButtonPhoneRegisterSuccess=(Button)findViewById(R.id.NoButtonPhoneRegisterSuccess);
		yesButtonPhoneRegisterSuccess.setOnClickListener(this);
		noButtonPhoneRegisterSuccess.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId()==R.id.YesButtonPhoneRegisterSuccess)
		{
			Intent intent=new Intent(this,CardRegistration.class);
			startActivity(intent);
			
		}
		else if(v.getId()==R.id.NoButtonPhoneRegisterSuccess)
		{
		
			Intent intent=new Intent(this,Home.class);
			startActivity(intent);
		}
		
	}

	
}
