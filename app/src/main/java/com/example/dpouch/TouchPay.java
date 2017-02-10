package com.example.dpouch;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TouchPay extends Activity implements NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {


    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    private List<CardDetailsBean> list;
    Button paymentButtonYes,paymentButtonNo;
    NfcAdapter nfcAdapter;
    TextView textInfo;
    private SQLiteDatabase sqllite;
    String amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_pay);
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        list=new ArrayList<CardDetailsBean>();
        CardDetailsBean bean=new CardDetailsBean();
        bean.setCardNumber("************3453");
        bean.setCardType("Visa Classic");
        list.add(bean);
        CardDetailsBean bean2=new CardDetailsBean();
        bean2.setCardNumber("************7990");
        bean2.setCardType("Visa Classic");
        list.add(bean2);
        adapter=new CustomSwipeAdapter(this,list);
        viewPager.setAdapter(adapter);
        nfcAdapter=NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.setNdefPushMessageCallback(this,this);
        nfcAdapter.setOnNdefPushCompleteCallback(this,this);
        textInfo = (TextView)findViewById(R.id.textInfo);
        amount=textInfo.getText().toString();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("Ajitesh card2 "+list.get(position).getCardNumber()+list.get(position).getCardType());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Ajitesh Inside resume");
        Intent intent = getIntent();
        String action = intent.getAction();
        System.out.println("Ajitesh Inside resume"+intent+action);
        if(action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)){
            System.out.println("Ajitesh Inside resume");
            Parcelable[] parcelables =
                    intent.getParcelableArrayExtra(
                            NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage inNdefMessage = (NdefMessage)parcelables[0];
            NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
            NdefRecord NdefRecord_0 = inNdefRecords[0];
            String inMsg = new String(NdefRecord_0.getPayload());
            System.out.println("Ajitesh Inside resume"+inMsg);
            textInfo.setText(inMsg+" ?");
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }




    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {

        String stringOut = "Ajitesh Sent";
        byte[] bytesOut = stringOut.getBytes();

        NdefRecord ndefRecordOut = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                "text/plain".getBytes(),
                new byte[] {},
                bytesOut);

        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut);
        return ndefMessageout;
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {

        final String eventString = "onNdefPushComplete\n" + event.toString();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),
                        eventString,
                        Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PaymentProcess process=new PaymentProcess(TouchPay.this);
                process.execute("ajitesh");

            }
        });

    }

    private class PaymentProcess extends AsyncTask<String,Void,String> {
        ProgressDialog progressDailog;
        Context ctx;
        public PaymentProcess(Context ctx)
        {
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {
            progressDailog = new ProgressDialog(ctx);
            progressDailog.setMessage("Payment in Progress ");
            progressDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDailog.setIndeterminate(true);
            progressDailog.setCancelable(false);
            amount=textInfo.getText().toString();
            progressDailog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => "+c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                CardSqlHelper sqlhelp=new CardSqlHelper(TouchPay.this);
                sqllite=sqlhelp.getWritableDatabase();
                ContentValues cv=new ContentValues();
                cv.put(sqlhelp.COL_CARD_NUMBER,"************3453");
                cv.put(sqlhelp.COL_AMOUNT,amount );
                cv.put(sqlhelp.COL_TOKEN_ID,"4962160000001123");
                cv.put(sqlhelp.COL_TIMESTAMP,formattedDate);
                sqllite.insert(sqlhelp.TABLE_NAME, null, cv);
              Intent intent =new Intent(ctx,PaymentAcknowledgeActivity.class );
                startActivity(intent);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDailog.dismiss();
        }

    }

}
