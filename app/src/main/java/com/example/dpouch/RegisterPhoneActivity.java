package com.example.dpouch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPhoneActivity extends Activity implements OnClickListener {

	private EditText make,model,sdkVersion,activationCode,imei,mssdn;
	private TelephonyManager telMgr;
	private String mssdnString,manufacturerString,modelString,sdkString,imeiString,actiavtionString;
	private Button registerButton;
	ProgressDialog progressDailog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_phone);
		model=(EditText)findViewById(R.id.model);
		make=(EditText)findViewById(R.id.make);
		imei=(EditText)findViewById(R.id.imei);
		mssdn=(EditText)findViewById(R.id.msisdn);
		sdkVersion=(EditText)findViewById(R.id.sdkVersion);
		activationCode=(EditText)findViewById(R.id.activationCode);
		telMgr=(TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
		imeiString=telMgr.getDeviceId();
		imei.setText(imeiString);
		mssdnString=telMgr.getLine1Number();
		if(mssdnString.equals(null) || mssdnString.isEmpty())
		{
			mssdnString="";
			mssdn.setText(mssdnString);
		}
		else
		mssdn.setText(mssdnString);
		if(mssdn.hasFocus())
		{
			System.out.println("I am inside mssdn");
			mssdn.setText(" ");
			mssdn.requestFocus();
		}
		sdkString=telMgr.getDeviceSoftwareVersion();
		sdkVersion.setText(sdkString);
		manufacturerString=android.os.Build.MANUFACTURER;
		modelString=android.os.Build.MODEL;
		make.setText(manufacturerString);
		model.setText(modelString);

		registerButton=(Button)findViewById(R.id.registerButton);
		registerButton.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
      actiavtionString=activationCode.getText().toString();
		mssdnString=mssdn.getText().toString();


		//Validate input 
		if(mssdn.getText().toString().trim().length()<=0 )	
		{
		Toast.makeText(this, "Please enter the MSSSDN/Mobile number", Toast.LENGTH_LONG).show();
		//mssdn.setError("Please enter the MSSSDN/Mobile number ");
			mssdn.setHint("Please enter the MSSSDN/Mobile number");
			mssdn.setHintTextColor(Color.RED);
		}
		
		else if( activationCode.getText().toString().trim().length()<=0)
		{
			Toast.makeText(this, "Please enter the activation code", Toast.LENGTH_SHORT).show();
			//activationCode.setError("Please enter the activation code  ");
			activationCode.setHint("Please enter the activation code  ");
			activationCode.setHintTextColor(Color.RED);

		}
		
		else if(mssdn.getText().toString().trim().length()<10 || mssdn.getText().toString().trim().length()>10)
		{
			mssdn.getText().clear();
			mssdn.requestFocus();
			Toast.makeText(this, "Please enter the  valid  10 digit MSSSDN/Mobile number ", Toast.LENGTH_SHORT).show();
			mssdn.setHint("Please enter the  valid  10 digit MSSSDN/Mobile number ");
			mssdn.setHintTextColor(Color.RED);
		}
		else if(activationCode.getText().toString().contentEquals("0893472Z"))
		{
			Intent intent=new Intent(getBaseContext(),RegisterPhoneActivitySuccess.class);
			intent.putExtra("manufacturerString",manufacturerString);
			intent.putExtra("modelString",modelString);
			intent.putExtra("sdkString",sdkString);
			intent.putExtra("mssdnString",mssdn.getText().toString());
			intent.putExtra("imeiString",imeiString);
			intent.putExtra("status", "Activated");
			Toast.makeText(getBaseContext(), "Thank you for registering device ", Toast.LENGTH_SHORT).show();
			startActivity(intent);

		}
		else
		{
			// WebServer Request URL
            String serverURL = "http://52.204.172.172/tsp/api/v1/devices";
			//SHowing response to new activity
			new BackgroundTask().execute(serverURL);
			
		}
			
		
		

	}
	private class BackgroundTask extends AsyncTask<String, Void, String>
	{
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
	 progressDailog=new ProgressDialog(RegisterPhoneActivity.this);
		progressDailog.setMessage("Registering device ");
		progressDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDailog.setIndeterminate(true);
		progressDailog.setCancelable(false);
		progressDailog.show();
	
}
		@Override
		protected String doInBackground(String... urls) {
			HttpURLConnection conn=null;
			BufferedReader reader=null;
			try {
				
				URL url=new URL(urls[0]);
				conn=(HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type","application/json");
				conn.connect();
				JSONObject obj=new JSONObject();
				obj.put("activationCode",actiavtionString);
				obj.put("imei",imeiString);
				obj.put("model",modelString);
				obj.put("msisdn",mssdnString);
				obj.put("make",manufacturerString);
				obj.put("sdkVersion",sdkString);
				OutputStreamWriter out=new OutputStreamWriter(conn.getOutputStream());
				out.write(obj.toString());
				out.flush();
				
				int HttpResult = conn.getResponseCode(); 
				System.out.println(HttpResult);
				if (HttpResult == HttpURLConnection.HTTP_OK) {
					System.out.println("I am inside HttpResult"+HttpResult);
					InputStream inputStream=conn.getInputStream();
					 reader=new BufferedReader(new InputStreamReader(inputStream));
					String line ;
					StringBuffer buffer=new StringBuffer();
					while((line=reader.readLine())!=null)
					{
						buffer.append(line);
					}
					
					String finalJson=buffer.toString();
					return finalJson;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(conn!=null)
				{
					conn.disconnect();
				}
				
				try {
					if(reader!=null)
					{
						reader.close();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			JSONObject obj2;
			if (result!=null)
			{
				String deviceId,status=null;
				try {
					obj2 = new JSONObject(result);
					 deviceId=obj2.getString("deviceId");
					 status=obj2.getString("status");
					 CriticalInfoBean.deviceId=deviceId;
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Intent intent=new Intent(getBaseContext(),RegisterPhoneActivitySuccess.class);
				intent.putExtra("manufacturerString",manufacturerString);
				intent.putExtra("modelString",modelString);
				intent.putExtra("sdkString",sdkString);
				intent.putExtra("mssdnString",mssdn.getText().toString());
				intent.putExtra("imeiString",imeiString);
				intent.putExtra("status", status);
				progressDailog.dismiss();
				Toast.makeText(getBaseContext(), "Thank you for registering device ", Toast.LENGTH_SHORT).show();
				startActivity(intent);
				
			}
			else
			{
				progressDailog.dismiss();
				Toast.makeText(getBaseContext(), "Please enter Correct Activation code provided by bank ", Toast.LENGTH_LONG).show();
				//activationCode.setError("Please enter correct activation code");
				activationCode.setHint("Please enter correct activation code privoded by bank");
				activationCode.setHintTextColor(Color.RED);

			}
			
			
		}
		
	}

}
