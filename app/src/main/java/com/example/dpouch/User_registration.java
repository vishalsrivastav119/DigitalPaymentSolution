package com.example.dpouch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BufferedHeader;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class User_registration extends Activity implements OnClickListener {

	private Button userRegistration;
	private EditText userId;
	ProgressDialog progressDailog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_registration);
		userId=(EditText)findViewById(R.id.userId);
		userRegistration=(Button) findViewById(R.id.userRegisterButton);
		userRegistration.setOnClickListener(this);


	}
@Override
public void onBackPressed() {
	//clear contents 
	super.onBackPressed();
	userId.setText("");
}
@Override
protected void onRestart() {
	// TODO Auto-generated method stub
	super.onRestart();
	userId.setText("");
	Toast.makeText(getBaseContext(), "On Restart", Toast.LENGTH_LONG).show();
	System.out.println("On Restart");
}

	@Override
	public void onClick(View v) {

		if(userId.getText().toString().trim().length()<=0)
		{
			Toast.makeText(this, "Please enter userID provided by Bank", Toast.LENGTH_SHORT).show();;
			userId.setError("Please enter userID provided by Bank");
		}
		else
		{
			// WebServer Request URL
            String serverURL = "http://52.204.172.172/tsp/api/v1/issuers/4542";
			
			new BackgroundTask().execute(serverURL);
			
		}

	}
	private class BackgroundTask extends AsyncTask<String, Void, String>
	{
		private final HttpClient client=new DefaultHttpClient();
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
	  progressDailog=new ProgressDialog(User_registration.this);
		progressDailog.setMessage("Registering User ");
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
				conn.connect();
				//conn.setDoOutput(true);
				InputStream inputStream=conn.getInputStream();
				 reader=new BufferedReader(new InputStreamReader(inputStream));
				String line ;
				StringBuffer buffer=new StringBuffer();
				while((line=reader.readLine())!=null)
				{
					buffer.append(line);
				}
				
				String finalJson=buffer.toString();
				JSONObject obj=new JSONObject(finalJson);
				String name=obj.getString("issuerName");
				return name;
				
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
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
			
			super.onPostExecute(result);
			Intent intent =new Intent(getBaseContext(),RegisterPhoneActivity.class);
			progressDailog.dismiss();
			Toast.makeText(getBaseContext(),result, Toast.LENGTH_SHORT).show();
			startActivity(intent);
		}
		
	}

}
