package com.example.dpouch;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 586924 on 10/22/2016.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    private Context ctx;
    private LayoutInflater layoutInflater;
    private List<CardDetailsBean> list;
    public CustomSwipeAdapter(Context ctx, List<CardDetailsBean> list)
    {
        this.ctx=ctx;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater= (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =layoutInflater.inflate(R.layout.swipe_layout,container,false);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(ctx,"Selected", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        TextView txtView= (TextView) view.findViewById(R.id.textView);
        TextView txtViewN= (TextView) view.findViewById(R.id.textViewCard);
        txtViewN.setText(list.get(position).getCardNumber());
        txtView.setText(list.get(position).getCardType());
        System.out.println("AJji"+list.size()+list.get(0).getCardNumber()+list.get(1).getCardNumber()+list.get(position).getCardNumber());
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);

    }
}
