package com.example.dpouch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 586924 on 10/24/2016.
 */
public class ListViewAdapter extends BaseAdapter {
    Context ctx;
    List<CardDetailsBean> listCards;
    LayoutInflater layoutInflater;
    int position;

    public ListViewAdapter(Context ctx, List<CardDetailsBean> listCards)
    {
        this.ctx=ctx;
        this.listCards=listCards;
    }

    @Override
    public int getCount() {
        return listCards.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=layoutInflater.inflate(R.layout.list_row_item,viewGroup,false);
        ImageView itemImg= (ImageView) rowView.findViewById(R.id.list_row_img);
        TextView itemtxt=(TextView)rowView.findViewById(R.id.list_row_text);
        ImageView delImg=(ImageView)rowView.findViewById(R.id.list_row_img_del);
        delImg.setImageResource(R.drawable.delete);
        delImg.setTag(i);

        delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 position=(Integer)view.getTag();
                AlertDialog.Builder builder= new AlertDialog.Builder(ctx,position);
                builder.setMessage("Are you sure ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BackgroundTask task=new BackgroundTask(ctx,position);
                        task.execute();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                }).show();


            }
        });
        itemImg.setImageResource(listCards.get(i).getCardImg());
        itemtxt.setText(listCards.get(i).getCardNumber());
        return rowView;
    }




    	private class BackgroundTask extends AsyncTask<String, Void, String>
{
		Context asynContext;
    int position;
    ProgressDialog progressDailog;
    String cardNumber;


		public BackgroundTask(Context context, int position) {

			asynContext=context;
            this.position=position;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
                progressDailog=new ProgressDialog(asynContext);
				progressDailog.setMessage("Disabling Card");
				progressDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDailog.setIndeterminate(true);
				progressDailog.setCancelable(false);
				progressDailog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			try {
               cardNumber= listCards.get(position).getCardNumber();
                listCards.remove(position);

				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {

		super.onPostExecute(result);
             progressDailog.dismiss();
			Toast.makeText(asynContext.getApplicationContext(), "Your Card"+ "ending with "+ cardNumber+" has been disabled successfully ", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
// Intent intent=new Intent(asynContext,Home.class);
//			asynContext.startActivity(intent);


	}


	}
}
