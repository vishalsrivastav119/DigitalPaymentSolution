package com.example.dpouch;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by 586924 on 11/8/2016.
 */


public class TransactionHistoryFragment extends Fragment {
    ListView listView;
    private CardSqlHelper sqlHelper;
    private SQLiteDatabase mydb;
    private Cursor mCursor;
    private ArrayList<CardDetailsBean> arrayList = new ArrayList<CardDetailsBean>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_history_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.TransactionHistoryList);

        sqlHelper = new CardSqlHelper(getActivity());

        mydb = sqlHelper.getWritableDatabase();

        String[] columns = new String[]{"_id", sqlHelper.COL_CARD_NUMBER,sqlHelper.COL_TIMESTAMP, sqlHelper.COL_TOKEN_ID, sqlHelper.COL_AMOUNT
        };

        mCursor = mydb.query(sqlHelper.TABLE_NAME, columns, null, null, null, null, null);
        System.out.println("Ajitesh"+mCursor.getCount()) ;
        if (mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                CardDetailsBean myDBBean = new CardDetailsBean();
                myDBBean.setCardNumber(mCursor.getString(mCursor.getColumnIndex(sqlHelper.COL_CARD_NUMBER)));
                myDBBean.setAmount(mCursor.getInt(mCursor.getColumnIndex(sqlHelper.COL_AMOUNT)));
                myDBBean.setTokenId(mCursor.getString(mCursor.getColumnIndex(sqlHelper.COL_TOKEN_ID)));
                myDBBean.setTimestmp(mCursor.getString(mCursor.getColumnIndex(sqlHelper.COL_TIMESTAMP)));
                arrayList.add(myDBBean);

            }
        }

        myDbAdapter myadapter = new myDbAdapter(getActivity());
        listView.setAdapter(myadapter);
        return view;
    }

    private class myDbAdapter extends BaseAdapter
    {
        private Context ctx;
        public myDbAdapter(Context ctx)
        {
            this.ctx=ctx;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater  layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.transaction_row_item, parent, false);
            TextView amount=(TextView)convertView.findViewById(R.id.bookedHistoryAmount);
            TextView cardNumber=(TextView)convertView.findViewById(R.id.bookedHistoryCardNumber);
            TextView status=(TextView) convertView.findViewById(R.id.bookedHistoryStatus);
            TextView date=(TextView)convertView.findViewById(R.id.bookedHistorydateView) ;
            String amount2= String.valueOf(arrayList.get(position).getAmount());
            String cardNumbr=arrayList.get(position).getCardNumber();
            System.out.println("List Item"+amount2);
            amount.setText("$"+amount2);
            cardNumber.setText(cardNumbr);
//            Calendar c = Calendar.getInstance();
//            System.out.println("Current time => "+c.getTime());
//
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String formattedDate = df.format(c.getTime());
            date.setText(arrayList.get(position).getTimestmp());
            status.setText("Payment Successfull");
            return convertView;
        }


    }


}
