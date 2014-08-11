package com.sales;

import static com.fedorvlasov.lazylist.Constant.CODE_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FIRST_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FOURTH_COLUMN;
import static com.fedorvlasov.lazylist.Constant.SECOND_COLUMN;
import static com.fedorvlasov.lazylist.Constant.SELECTNUMBER;
import static com.fedorvlasov.lazylist.Constant.THIRD_COLUMN;

import java.util.HashMap;

import org.panel.App;
import org.panel.R;
import org.panel.Registration;
import org.panel.ViewPagerAdapter;
import org.panel.R.id;
import org.panel.R.layout;
import org.panel.R.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockActivity;
import com.fedorvlasov.lazylist.LazyAdapter;
import com.menu.Customer;
import com.menu.Myselection;
import com.menu.Newcar;
import com.menu.SellerpageActivity;
import com.menu.SellerpageActivity.FetchTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;

import com.actionbarsherlock.view.*;


public class MainActivity extends SherlockFragmentActivity
	{
	
    public static Context contextOfApplication;
	
	ActionBar mActionBar;
    ViewPager mPager;
    View cartbtn,cartcontent;
    Tab tab,tabselect;
    String carname,motorname,parkinname,wishname;
    Button cartbutton;
    Menu myselection;
    TextView dummy;
    int textSize;
    boolean status = false;
    //Activity a ;
    
    ViewPagerAdapter viewpageradapter;
    FragmentManager fm;
    ActionBar.TabListener tabListener;
    int finalHeight, finalWidth;
    Activity a;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getInstance().addActivity(this);
        App.setContext(this);
        
        a = this;
        // Activate Navigation Mode Tabs
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
          
       // mActionBar.setDisplayShowCustomEnabled(true);
        dummy = (TextView)findViewById(R.id.dummyTextView2);
        textSize = (int)dummy.getTextSize();
        // Locate ViewPager in activity_main.xml
        mPager = (ViewPager) findViewById(R.id.pager);
 
        tabselect= mActionBar.newTab();
        
       // mActionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        mActionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
       // actionBar.setStackedBackgroundDrawable(getResources().getDrawable(
         //       R.drawable.coupon_header));

        
        // Activate Fragment Manager
        fm = getSupportFragmentManager();
        
        viewpageradapter = new ViewPagerAdapter(fm,tabselect,contextOfApplication,a);
        viewpageradapter.notifyDataSetChanged();
        
        // Set the View Pager Adapter into ViewPager
        mPager.setAdapter(viewpageradapter);
        mPager.setOffscreenPageLimit(3);
 
        // Capture ViewPager page swipes
        ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);   
                Log.i("position ="+position,"delete");
                
                

                if (position == 3){
                	
                	Intent intent = new Intent();
                	intent.setClass(MainActivity.this,Myselection.class);
                    startActivityForResult(intent, 1);       
                }
                

                // Find the ViewPager Position
                mActionBar.setSelectedNavigationItem(position);
            }
        };
 
        mPager.setOnPageChangeListener(ViewPagerListener);
        // Locate the adapter class called ViewPagerAdapter.java


        // Capture tab button clicks
        tabListener = new ActionBar.TabListener() {
 
            @Override
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                // Pass the position on tab click to ViewPager
                mPager.setCurrentItem(tab.getPosition());
            }
 
            @Override
            public void onTabUnselected(Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
 
            @Override
            public void onTabReselected(Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
        };
 
        carname = "衣服"; motorname = "褲子"; parkinname = "鞋或其他"; wishname = "選擇: 0";
      
        
        
            
        Tab tabcar = mActionBar.newTab().setTabListener(tabListener);
        Tab tabmotor = mActionBar.newTab().setTabListener(tabListener);
        Tab tabparkin = mActionBar.newTab().setTabListener(tabListener);
        
       //  final TextView t = (TextView)tabcar.findViewById(android.R.id.title);
       // t.setTextColor(getResources().getColor(R.color.white));
        
         
        
        tabcar.setText(carname);
        //tabcar.setCustomView(t);
         mActionBar.addTab(tabcar)  ;
 
        tabmotor.setText(motorname);
        mActionBar.addTab(tabmotor);
 
        tabparkin.setText(parkinname);
        mActionBar.addTab(tabparkin);
        
         
        
        //  final TextView tv = (TextView) mActionBar.getChildAt(0).findViewById(android.R.id.title);        
        // tv.setTextColor(this.getResources().getColorStateList(R.color.text_tab_indicator));
 
        TextView tv = new TextView(this);
        tv.setTextColor(Color.BLACK);
        
         
        IntentFilter filter = new IntentFilter();
        filter.addAction("update");
        
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(App.getContext());
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
               
                update();
            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver, filter);     
        update();     
        mActionBar.addTab(tabselect);
        
       
 }
    
    public void update(){
    	
    	SharedPreferences preferences = App.getContext().getSharedPreferences("info", App.getContext().MODE_PRIVATE);
        String selectnumber = preferences.getString("selectnumber","0");        
        
        int number = Integer.parseInt(selectnumber);   	
    	if (number == 0)
            tabselect.setText("選擇: 0").setTabListener(tabListener);
          else
            tabselect.setText("選擇:"+number).setTabListener(tabListener);
    	
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
        	
        	Common common = new Common();
        	common.setcontext(MainActivity.this);
        	common.showalert(); 
        }
        return super.onKeyDown(keyCode, event);
    }
    
    
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
        switch (requestCode)  
        {  
        case 1:  
            mPager.setCurrentItem(0);

            break;  
 
        }  
    }  
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getSupportMenuInflater();
       inflater.inflate(R.menu.menu_option, menu);

       return true;
    }
    
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {   
   

      Context c = App.getContext();
      SharedPreferences preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);	   
	  String selectnumberstring = preferences.getString("selectnumber","0");
	  //String firsttime = preferences.getString("firsttime", "0");
      int selectnumber = Integer.parseInt(selectnumberstring);
      int register =  preferences.getInt("userregister", 0);
      Log.i("selectnumber"+selectnumber,"sel");
      
      
      menu.getItem(0).setTitle("選擇: "+selectnumber);
      menu.getItem(1).setTitle("上載你的貨品");
      menu.getItem(2).setTitle("紀錄");
      menu.getItem(3).setTitle("主頁");
      menu.getItem(4).setTitle("離開");
       
      if (register == 2)
          menu.getItem(5).setTitle("登出");
      if (register == 3)
          menu.getItem(5).setTitle("註冊");
      if (register == 4)
          menu.getItem(5).setTitle("登入");     	  


      super.onPrepareOptionsMenu(menu);

      return true;
    	  
 }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         
    	
        
        
        switch (item.getItemId())
        {
        
        case R.id.myselectionmenu:       
        {
        	Intent intent = new Intent();
        	intent.setClass(this,Myselection.class);
			startActivity(intent);

			break;
        }
        case R.id.sellermenu:           
        {
        	Intent intent = new Intent();
       	    intent.setClass(this,SellerpageActivity.class);
			startActivity(intent);

			break;
        }
        
        case R.id.customermenu:
        {
        	
        	Intent intent = new Intent();
        	intent.setClass(this,Customer.class);
			startActivity(intent);
			break;
        }
        
    
    case R.id.carmenu:{
    	
    	Toast.makeText(App.getContext(),"你已在此頁",Toast.LENGTH_SHORT).show();
		break;
    }
    
        
        case R.id.exitmenu:
        {

        	Common common = new Common();
        	common.setcontext(MainActivity.this);
        	common.showalert(); 

            break;
            
        }
        
        case R.id.reset:
        {

            Context c = App.getContext();
            SharedPreferences settings = getSharedPreferences ("info", c.MODE_PRIVATE);
            SharedPreferences.Editor PE = settings.edit();
            int register = settings.getInt("userregister", 3);
            
            
            if (register == 2) {
            PE.putInt("userregister", 4);
            PE.commit();
            
        	Toast.makeText(App.getContext(),"你已經登出",Toast.LENGTH_SHORT).show(); 
        	invalidateOptionsMenu();
            }
        	//showdialog();
            
            if (register == 3){
            	showregisterdialog();
            	         
            	}
            
            if (register == 4)
              showlogindialog();    

            break;
            
        }
			
        default:
            return super.onOptionsItemSelected(item);
        }
        
        return true;
    }    


    
    
    public void showlogindialog  (){
    	
    	
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);

    	alert.setTitle("登入");
    	//alert.setMessage("o");

    	// Set an EditText view to get user input 
     
    	LayoutInflater factory = LayoutInflater.from(this);
    	final View textEntryView = factory.inflate(R.layout.personallayout2, null);
    	
    	final EditText emailinput = (EditText) textEntryView.findViewById(R.id.edittext1 );
    	final EditText passwordinput = (EditText) textEntryView.findViewById(R.id.edittext3 );

        alert.setView(textEntryView) ;	 

        
        
    	 alert.setPositiveButton("登入", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    		
    	  String email = emailinput.getText().toString();
    	  String password = passwordinput.getText().toString()  ;

    	  Context c = App.getContext();
          SharedPreferences settings = getSharedPreferences ("info", c.MODE_PRIVATE);
          String useremail = settings.getString("email", "");
          String userpassword = settings.getString("password", "");
     
    	  if (email.length() <=0 || password.length() <=0 ){
    		  
    		  Toast.makeText(MainActivity.this , "請輸入個人資料", Toast.LENGTH_SHORT).show();
    	  }
    	  
    	  
     
    	  if (useremail.equals(email) && password.equals(userpassword)) {
    		   
 	          SharedPreferences.Editor PE = settings.edit();
	          PE.putInt("userregister", 2);
	          PE.commit();
	        	Toast.makeText(App.getContext(),"登入成功",Toast.LENGTH_SHORT).show(); 

                
     	  }
    	  
    	  else {
    		  
    		  Toast.makeText(MainActivity.this , "密碼不正確", Toast.LENGTH_SHORT).show();
    		  //showdialog2();
    		  
    	  }
             
    	  
    	  }
    	});

     
    	 alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    		  public void onClick(DialogInterface dialog, int whichButton) {
    			  Context c = App.getContext();
    		          SharedPreferences settings = getSharedPreferences ("info", c.MODE_PRIVATE);
    		          SharedPreferences.Editor PE = settings.edit();
    		          PE.putInt("userregister", 4);
    		          PE.commit();

    	         
    		  }
    		});

    	alert.show();	
    	 
    }
    
    
public void showregisterdialog (){
		
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("註冊");
		 
	 
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.personallayout, null);
		
		final EditText emailinput = (EditText) textEntryView.findViewById(R.id.edittext1 );
		final EditText passwordinput = (EditText) textEntryView.findViewById(R.id.edittext3 );

        alert.setView(textEntryView) ;	 

        
 		alert.setPositiveButton("註冊", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String email = emailinput.getText().toString();
		  String password = passwordinput.getText().toString()  ;

		  if (email.length() <=0 || password.length() <=0 ){
			  
			  Toast.makeText(MainActivity.this , "請輸入個人資料", Toast.LENGTH_SHORT).show();
		  }
	 
		  else {
			  Toast.makeText(MainActivity.this, "註冊成功!", Toast.LENGTH_SHORT).show();
			  Context c = App.getContext();
	          SharedPreferences settings = getSharedPreferences ("info", c.MODE_PRIVATE);

              SharedPreferences.Editor PE = settings.edit();
              PE.putInt("userregister", 2);
	          PE.putString("email",email );
	          PE.putString("password",password );
 
	          PE.commit();
	          
              
 		  }
	         
		  
		  }
		});

		 
 		 alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
 			  public void onClick(DialogInterface dialog, int whichButton) {
 			    // Canceled.
 				  Context c = App.getContext();
 		          SharedPreferences settings = getSharedPreferences ("info", c.MODE_PRIVATE);
 		          SharedPreferences.Editor PE = settings.edit();
 		          PE.putInt("userregister", 3);
 		          PE.commit();

 		          
 			  }
 			});
 		 
		alert.show();	
		 
		
	}
    
    

    
    public static Context getContextOfApplication(){
        return contextOfApplication;
    }
    
	}