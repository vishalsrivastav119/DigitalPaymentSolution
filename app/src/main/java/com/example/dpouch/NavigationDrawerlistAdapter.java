package com.example.dpouch;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationDrawerlistAdapter extends BaseAdapter {
	
    private ArrayList<MenuBean> navDrawerItems;
	Context context;
	public NavigationDrawerlistAdapter(Context baseContext) {
	this.context=baseContext;
	
	}
	 public NavigationDrawerlistAdapter(Context baseContext, ArrayList<MenuBean> navDrawerItems) {
		 this.context = baseContext;
	        this.navDrawerItems = navDrawerItems;
		
	}
	@Override
	public int getCount() {
		
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
         View view;
		if (convertView==null) {
			LayoutInflater mInflater = (LayoutInflater)
	                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		view  =mInflater.inflate(R.layout.menu_row_item_layout, parent,false);

		}
		else
		{
			view=convertView;
		}
			
		
	ImageView icon_menu=(ImageView)view.findViewById(R.id.menuIcon);
	TextView text_menu=(TextView)view.findViewById(R.id.menuText);
	icon_menu.setImageResource(navDrawerItems.get(position).getIcon());
    text_menu.setText(navDrawerItems.get(position).getTitle());
   //subtitle.setText(navDrawerItems.get(position).getSubtitle());
	
		return view;
	}
	

}
