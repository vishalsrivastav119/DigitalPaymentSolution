package com.example.dpouch;
import java.util.ArrayList;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class Home extends Activity implements OnItemClickListener {

	private String[] menuTitle; 
	private TypedArray menuIcons;
	private DrawerLayout dlayout;
	private ListView dList;
	private RelativeLayout mDrawerPane;
	private ArrayList<MenuBean> menuList;
    private NavigationDrawerlistAdapter adapter;
    ActionBarDrawerToggle mDrawerTogggle;
    private CharSequence mTitle;
	 private CharSequence mDrawerTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

menuTitle=getResources().getStringArray(R.array.nav_drawer_items);
menuIcons=getResources().obtainTypedArray(R.array.nav_drawer_icons);
dlayout=(DrawerLayout) findViewById(R.id.drawer_layout);
dList=(ListView)findViewById(R.id.left_drawer);
mDrawerPane = (RelativeLayout) findViewById(R.id.content_frame);
menuList=new ArrayList<MenuBean>();
menuList.add(new MenuBean(menuTitle[0], menuIcons.getResourceId(0, -1)));
menuList.add(new MenuBean(menuTitle[1], menuIcons.getResourceId(1, -1)));
menuList.add(new MenuBean(menuTitle[2], menuIcons.getResourceId(2, -1)));
menuList.add(new MenuBean(menuTitle[3], menuIcons.getResourceId(3, -1)));
menuList.add(new MenuBean(menuTitle[4], menuIcons.getResourceId(4, -1)));
menuList.add(new MenuBean(menuTitle[5], menuIcons.getResourceId(5, -1)));
menuList.add(new MenuBean(menuTitle[6], menuIcons.getResourceId(6, -1)));
menuIcons.recycle();
adapter=new NavigationDrawerlistAdapter(getApplicationContext(),menuList);
dList.setAdapter(adapter);

getActionBar().setDisplayHomeAsUpEnabled(true);
getActionBar().setHomeButtonEnabled(true);
getActionBar().setDisplayShowHomeEnabled(false);
 mDrawerTogggle=new ActionBarDrawerToggle(this, dlayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name)
 {
		public void onDrawerClosed(View view)
		{
			super.onDrawerClosed(view);
			// calling onPrepareOptionsMenu() to show action bar icons
			invalidateOptionsMenu();

		}
		public void onDrawerOpened(View drawerView) {
			super.onDrawerOpened(drawerView);
			// calling onPrepareOptionsMenu() to hide action bar icons
		
			invalidateOptionsMenu();
		}

	};
	
	dlayout.setDrawerListener(mDrawerTogggle);

	dList.setSelector(android.R.color.holo_blue_dark);
	dList.setOnItemClickListener(this);
	
	HomeFragment homeFragmnt=new HomeFragment();
	
	FragmentManager fragmentManager = getFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.mainContent, homeFragmnt).commit();

	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (mDrawerTogggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = dlayout.isDrawerOpen(mDrawerPane);
		
		return super.onPrepareOptionsMenu(menu);
	}


	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerTogggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerTogggle.onConfigurationChanged(newConfig);
	}

	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Fragment fragment=null;
		if(menuList.get(position).getTitle().equalsIgnoreCase("Touch To pay"))
		{
		
		fragment=new HomeFragment();
			
		}
		else if(menuList.get(position).getTitle().equalsIgnoreCase("Disable Device"))
		{
			fragment=new DisableDeviceFragment();
		}
		else if(menuList.get(position).getTitle().equalsIgnoreCase("Disable Card"))
		{
			fragment=new DisableCardFragment();
		}
		else if(menuList.get(position).getTitle().equalsIgnoreCase("Add a new Card"))
		{
			fragment=new AddCardFragment();
		}
		else if(menuList.get(position).getTitle().equalsIgnoreCase("Transaction History"))
		{
         fragment=new TransactionHistoryFragment();
		}
		else
		{
			
			
		 fragment=new HomeFragment();
		}
		
		if (fragment!=null)
		{
			dlayout.closeDrawers();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.mainContent, fragment).commit();
			dList.setItemChecked(position, true);
			setTitle(menuList.get(position).getTitle());
			dlayout.closeDrawer(mDrawerPane);
			
		}
		
	}


}
