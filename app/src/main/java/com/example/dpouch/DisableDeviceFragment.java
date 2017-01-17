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

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class DisableDeviceFragment extends Fragment implements OnClickListener{
	Button yesButtonDisableDevice,noButtonDisableDevice;
	ProgressDialog progressDailog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.disable_device_fragment, container,false);
		yesButtonDisableDevice=(Button)view.findViewById(R.id.yesButtonDisableDevice);
		noButtonDisableDevice=(Button)view.findViewById(R.id.noButtonDisableDevice);
		yesButtonDisableDevice.setOnClickListener(this);
		noButtonDisableDevice.setOnClickListener(this);

		return view;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.noButtonDisableDevice:
			Intent intent =new Intent(getActivity().getBaseContext(),Home.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

			break;
		case R.id.yesButtonDisableDevice:
			Context context=getActivity();
			System.out.println("conetxt"+context);
			String id=CriticalInfoBean.deviceId;
			// WebServer Request URL
            String serverURL = "http://52.204.172.172/tsp/api/v1/devices/"+id+"?flag=0";
			
            BackgroundTask task=new BackgroundTask(context);
            task.execute(serverURL);
			break;


		}

	}
	
	
	private class BackgroundTask extends AsyncTask<String, Void, String>
	{
		Context asynContext;

		public BackgroundTask(Context context) {
			
			asynContext=context;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			 progressDailog=new ProgressDialog(asynContext);
				progressDailog.setMessage("Disabling Device");
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
				conn.setRequestMethod("PUT");
				conn.connect();
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
			
			JSONObject obj2;

				String deviceId,status=null,nextStepToken;
				try {
					obj2 = new JSONObject(result);
					deviceId=obj2.getString("deviceId");
					 status=obj2.getString("status");
					 progressDailog.dismiss();
						Toast.makeText(asynContext.getApplicationContext(), "Your Device has been disabled successfully  ", Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(asynContext,Welcome.class);
						startActivity(intent);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			

//			else
//			{
//				progressDailog.dismiss();
//				Toast.makeText(asynContext.getApplicationContext(), "Please Check Internet Connectivity ", Toast.LENGTH_SHORT).show();
//
//			}
			
			
			
			
		}
		
		
	}
}
