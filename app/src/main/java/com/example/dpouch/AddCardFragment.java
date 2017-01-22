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
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//Created By Ajitesh (586924)

public class AddCardFragment extends Fragment implements OnClickListener {
	
	EditText cardNumber,cvv,nameOnCard;	
	TextView expiryMonth,expiryYear;
	String cardNnumberString,cvvString;
	Button cardReg;
	ProgressDialog progressDailog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.add_card_fragment, container,false);
		expiryMonth=(TextView) view.findViewById(R.id.spinnerPickUpMonth);
		expiryYear=(TextView) view.findViewById(R.id.spinnerPickUpYear);
		cardReg=(Button)view.findViewById(R.id.cardRegisterButton);
		cardNumber=(EditText) view.findViewById(R.id.cardNum);
		cvv=(EditText)view.findViewById(R.id.cvv);
		nameOnCard=(EditText)view.findViewById(R.id.NameOnCard);
		cardNnumberString=cardNumber.getText().toString();
        cvvString=cvv.getText().toString();
		expiryMonth.setOnClickListener(this);
		expiryYear.setOnClickListener(this);
		cardReg.setOnClickListener(this);
		
		return view;
}

	@Override
	public void onClick(View v) {
		Bundle args=new Bundle();

		switch (v.getId()) {
		case R.id.spinnerPickUpMonth:
			FragmentTransaction ft=getFragmentManager().beginTransaction();
			DialogFragment monthFragment=new MonthPickerFragment();

			args.putInt("spinnerMonth",R.id.spinnerPickUpMonth);
			monthFragment.setArguments(args);
			monthFragment.show(ft, "Pick Expiry Month");
			
		break;
		
		case R.id.spinnerPickUpYear:
			FragmentTransaction fy=getFragmentManager().beginTransaction();
			DialogFragment yearFragment=new YearPickerFragment();
			args.putInt("spinnerYear",R.id.spinnerPickUpYear);
			yearFragment.setArguments(args);
			yearFragment.show(fy, "Pick Expiry year");
			break;
		case R.id.cardRegisterButton:
			if(cardNumber.getText().toString().trim().length()<=0)
			{
				Toast.makeText(getActivity().getApplicationContext(), "Please enter the card number", Toast.LENGTH_SHORT).show();	
				cardNumber.setError("Please enter the card number ");
			}
			else if (cvv.getText().toString().trim().length()<=0)
			{

				Toast.makeText(getActivity().getApplicationContext(), "Please enter cvv details for card ", Toast.LENGTH_SHORT).show();	
				cvv.setError("Please enter cvv details for card ");
				
			}
			else if (nameOnCard.getText().toString().trim().length()<=0)
			{
				Toast.makeText(getActivity().getApplicationContext(), "Please enter the cardholder name ", Toast.LENGTH_SHORT).show();	
				cvv.setError("Please enter the cardholder name");
				
			}
			else if (validateCard(cardNumber.getText().toString().trim()))
			{
				
			}
			else
			{
				Context context=getActivity();

	            
				// WebServer Request URL
	            String serverURL = "http://52.204.172.172/tsp/api/v1/cards";
	            BackgroundTask task=new BackgroundTask(context);
	            task.execute(serverURL);
				
			}
			
			break;
		}
		
	}
	private boolean validateCard(String cardNumber) {
		
		//int sum=sumOfEvenPlaces(Integer.valueOf(cardNumber)) + sumOfOddPlaces(Integer.valueOf(cardNumber));

		
		return false;
	}
	private int sumOfEvenPlaces(int cardNumber2) {
		
		return 1;
	}
	private int sumOfOddPlaces(int cardNumber2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private class BackgroundTask extends AsyncTask<String, Void, String>
	{
		Context asynContext;

		public BackgroundTask(Context context) {
			
			asynContext=context;
		}
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
	   progressDailog=new ProgressDialog(asynContext);
		progressDailog.setMessage("Registering Card  ");
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
				System.out.println("DeviceId"+CriticalInfoBean.deviceId);
				obj.put("deviceId",CriticalInfoBean.deviceId);
				obj.put("productType",CriticalInfoBean.productType);
				obj.put("requestor",CriticalInfoBean.requestor);
				obj.put("aid",CriticalInfoBean.aid);
				obj.put("aidVersion",CriticalInfoBean.aidVersion);
				obj.put("panSource",CriticalInfoBean.panSource);
				obj.put("deviceLanguage",CriticalInfoBean.deviceLanguage);		
				JSONObject cardInfo= new JSONObject();
				cardInfo.put("pan",cardNnumberString);
				cardInfo.put("psn","00");
				cardInfo.put("cvv", cvvString);
				cardInfo.put("panExpiryDate","2017-12-12");
				obj.put("cardInfo", cardInfo);
				OutputStreamWriter out=new OutputStreamWriter(conn.getOutputStream());
				out.write(obj.toString());
				System.out.println(obj.toString());
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
				String provisionId,status=null,nextStepToken;
				try {
					obj2 = new JSONObject(result);
					provisionId=obj2.getString("provisionId");
					 status=obj2.getString("status");
					 nextStepToken=obj2.getString("nextStepToken");
					 CriticalInfoBean.NextStepToken=nextStepToken;
					 progressDailog.dismiss();
						Toast.makeText(getActivity().getApplicationContext(), "Thank you for registering card ", Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(asynContext,Home.class);
						startActivity(intent);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
		}
			else
			{
				progressDailog.dismiss();
				Toast.makeText(getActivity().getApplicationContext(), "Please enter Correct card  details ", Toast.LENGTH_SHORT).show();
				
			}
		
	}

	
}
}
