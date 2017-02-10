package com.example.dpouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Welcome extends Activity implements OnClickListener {
	Button welcome_register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_welcome);
		
		welcome_register=(Button) findViewById(R.id.welcome_register);
		welcome_register.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) 
        {
		Intent intent=new Intent(this,RegisterPhoneActivity.class);
		startActivity(intent);
		
	}

	
}
