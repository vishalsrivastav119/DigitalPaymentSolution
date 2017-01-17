package com.example.dpouch;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisableCardFragment extends Fragment {
	ProgressDialog progressDailog;
	ListView delCardsView;
	ListViewAdapter listViewAdapter;
	List<CardDetailsBean> listCards;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view=inflater.inflate(R.layout.disable_card_fragment, container,false);
		delCardsView=(ListView)view.findViewById(R.id.delCardsView);
		listCards =new ArrayList<CardDetailsBean>();
		CardDetailsBean bean=new CardDetailsBean();
		bean.setCardNumber("************3453");
		bean.setCardImg(R.drawable.mastercard);
		listCards.add(bean);
		CardDetailsBean bean2=new CardDetailsBean();
		bean2.setCardNumber("************7990");
		bean2.setCardImg(R.drawable.visa);
		listCards.add(bean2);
		listViewAdapter=new ListViewAdapter(getActivity(),listCards);
		delCardsView.setAdapter(listViewAdapter);

//		ImageView delete1=(ImageView)view.findViewById(R.id.delete1);
//		ImageView delete2=(ImageView)view.findViewById(R.id.delete2);
//		delete1.setOnClickListener(this);
//		delete2.setOnClickListener(this);
		return view;
	}

//	@Override
//	public void onClick(View v) {
//		if(v.getId()==R.id.delete1)
//		{
//			Context context=getActivity();
//
//            BackgroundTask task=new BackgroundTask(context);
//            task.execute();
//
//		}
//		else if(v.getId()==R.id.delete2)
//		{
//			Context context=getActivity();
//
//            BackgroundTask task=new BackgroundTask(context);
//            task.execute();
//		}
//
//	}
//
//	private class BackgroundTask extends AsyncTask<String, Void, String>
//	{
//		Context asynContext;
//
//		public BackgroundTask(Context context) {
//
//			asynContext=context;
//		}
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			 progressDailog=new ProgressDialog(asynContext);
//				progressDailog.setMessage("Disabling Card");
//				progressDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//				progressDailog.setIndeterminate(true);
//				progressDailog.setCancelable(false);
//				progressDailog.show();
//
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//
//				e.printStackTrace();
//			}
//			return null;
//		}
//		@Override
//		protected void onPostExecute(String result) {
//
//			super.onPostExecute(result);
//			progressDailog.dismiss();
//			Toast.makeText(asynContext.getApplicationContext(), "Your Card has been disabled successfully  ", Toast.LENGTH_SHORT).show();
//			Intent intent=new Intent(asynContext,Home.class);
//			startActivity(intent);
//
//		}
//
//
//	}


}
