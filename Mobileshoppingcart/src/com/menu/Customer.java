package com.menu;

import static com.fedorvlasov.lazylist.Constant.FIFTH_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FIRST_COLUMN;
import static com.fedorvlasov.lazylist.Constant.SECOND_COLUMN;
import static com.fedorvlasov.lazylist.Constant.THIRD_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FOURTH_COLUMN;
import static com.fedorvlasov.lazylist.Constant.CODE_COLUMN;
import static com.fedorvlasov.lazylist.Constant.ORDERTYPE;
import static com.fedorvlasov.lazylist.Constant.NEEDREF1;
import static com.fedorvlasov.lazylist.Constant.NEEDTYPE;


import java.util.ArrayList;
import java.util.HashMap;

import org.panel.App;
import org.panel.NothingSelectedSpinnerAdapter;
import org.panel.R;
import org.panel.R.id;
import org.panel.R.layout;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
 import com.fedorvlasov.lazylist.LazyAdapter;
import com.sales.Common;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
 

public class Customer extends SherlockFragmentActivity {


	int textSize;
	ArrayList<HashMap<String,String>> arraylist;
	LazyAdapter adapter;
	ListView customerlist;
	TextView dummytext;
    TextView accountid,name,mobile,email;
    Tab tab;
    String date[],status[],product[],price[],ordertype[],code[],quantity[];
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);
        
        
        ActionBar mActionBar;
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        
        dummytext = (TextView)findViewById(R.id.customerdummy);      
        textSize = (int)(dummytext.getTextSize()*0.55);      
        
        accountid = (TextView)findViewById(R.id.accountid);
        name = (TextView)findViewById(R.id.name);
        mobile = (TextView)findViewById(R.id.mobile);
        email = (TextView)findViewById(R.id.email);
         customerlist = (ListView)findViewById(R.id.customerlist);
        
        
        Context c = App.getContext();
        SharedPreferences preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);
        String total = preferences.getString("total", "");
        String invoice = preferences.getString("invoice", "");
        String username = preferences.getString("username", "");
        String userphone = preferences.getString("userphone", "");

        
        accountid.setText("帳單編號  : " +invoice );
        name.setText("姓名: "+username );
        mobile.setText("電話:"+   userphone );
        
         email.setText("總共: $"+total);
        
        //settextproperty(accountid);
        //settextproperty(name);
        //settextproperty(mobile);
        //settextproperty(email);
 
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {       	 
           @Override
           public void onTabSelected(Tab tab, FragmentTransaction ft) {
           
           }

           @Override
           public void onTabUnselected(Tab tab, FragmentTransaction ft) {
           }

           @Override
           public void onTabReselected(Tab tab, FragmentTransaction ft) {
           }
       }; 
      
       
       TextView t= new TextView(this);        
       Common common = new Common ();
           
       tab = mActionBar.newTab().setTabListener(tabListener);
       tab.setCustomView(common.gettitletext(t,"紀錄",textSize));
       mActionBar.addTab(tab);

       update();
      
       

    }
    
public void update(){
	
	Context c = App.getContext();
    SharedPreferences preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);
    

    int sentnumber =  Integer.parseInt(preferences.getString("sentnumber","0"));
    String testurl [] = new String[sentnumber];
    code = new String[sentnumber+1] ; date = new String[sentnumber+1]; status = new String[sentnumber+1]; 
    product = new String[sentnumber+1] ; price = new String[sentnumber+1] ; ordertype = new String[sentnumber+1];
    quantity = new String [sentnumber + 1];
    Time now = new Time();
    now.setToNow();
    
    int month = now.month + 1;
    
    for (int i = 1;i<=sentnumber;i++){
    	date[i] = now.monthDay+"-"+month+"-"+now.year;   
    	status[i] = "Pending";
        product[i] = ""; code[i] = "";
        price[i]=""; ordertype[i]="";
    }


    for (int i=1; i<=sentnumber ;i++)
        
    {
    	
    	if (!preferences.getString("senttype"+i, "0").equals("n")){
        	String tempproduct []= preferences.getString("sentdescrp"+i, "0").split(",");
           for (int j = 0; j < tempproduct.length ; j++){
        	   
        	   if (j == tempproduct.length-2)
        	    product[i]  = tempproduct[0] ;
        	   //if (j == tempproduct.length-2)
        		 // product[i] += tempproduct[j];
        	  if (j == tempproduct.length-1) 
        		  price[i] = tempproduct[j];   
        	   } 
 
          Log.i("i"+ i,"sent");

          code[i] = preferences.getString("sentitem"+i, "0");
          
           quantity[i] = preferences.getString("sentquantity"+i,"0");
           //Log.i("i"+preferences.getString("sentquantity"+i,"0"),"sent");
           //Log.i( total +": "+preferences.getString("sentquantity"+i,"0" ),"sent");

          /*
          if (preferences.getString("sentorder"+i, "0").equals("b"))
        	  ordertype[i] = "¶R";
          if (preferences.getString("sentorder"+i, "0").equals("r"))
              ordertype[i] = "¯²";
              */
          
    	}
    	else
    		product[i] = preferences.getString("sentdescrp"+i, "0");
          
          Log.i(date[i]+" "+status[i]+" "+product[i]+" "+price[i]+" "+quantity[i],"sent");
          
    }   

    
    
    
	populateList(code,date,status,product,price,quantity,sentnumber);
	
    adapter = new LazyAdapter(App.getContext(),testurl,arraylist,"cs");
    adapter.settab(tab);
    adapter.notifyDataSetChanged();
    customerlist.setAdapter(adapter);
     

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
  int selectnumber = Integer.parseInt(selectnumberstring);
  
  menu.getItem(0).setTitle("選擇: "+selectnumber);
  menu.getItem(1).setTitle("出售貨品");
  menu.getItem(2).setTitle("紀錄");
  menu.getItem(3).setTitle("主頁");
  menu.getItem(4).setTitle("離開");
  
  return super.onPrepareOptionsMenu(menu);
}

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    	Intent updateIntent = new Intent();
        updateIntent.setAction("update");
                
       // updateIntent.putExtra("text", "This is the string to show");
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(App.getContext());
        manager.sendBroadcast(updateIntent);
    	
    }
    return super.onKeyDown(keyCode, event);
}

@Override
public boolean onOptionsItemSelected(MenuItem item)
{
     
	
    //Toast.makeText(MainActivity.this, "detected", Toast.LENGTH_SHORT).show();
    
    
    switch (item.getItemId())
    {
    
    case R.id.myselectionmenu:       
    {
    	Intent intent = new Intent();
    	intent.setClass(this,Myselection.class);
		startActivity(intent);
		this.finish();
		break;
    }
    case R.id.sellermenu:           
    {
    	Intent intent = new Intent();
    	intent.setClass(this,SellerpageActivity.class);
		startActivity(intent);
		this.finish();
		break;
    }
    
    case R.id.customermenu:
    {
    	
    	Toast.makeText(App.getContext(),"§A¤w¸g¦b¦¹­¶",Toast.LENGTH_SHORT).show();
		break;
    }
    
    case R.id.carmenu:
    {
    	onBackPressed();
        this.finish();
		break;
    	   	
    }
    
    case R.id.exitmenu:
    {

    	Common common = new Common();
    	common.setcontext(Customer.this);
    	common.showalert(); 
        break;
        
    }
		
    default:
        return super.onOptionsItemSelected(item);
    }
    
    return true;
}    


private void populateList(String Code[],String Date[],String Status[],String Product[],String Price[],String quantity[],int Value) {

	 
    	 
	
    	arraylist = new ArrayList<HashMap<String,String>>();
    	HashMap<String,String> temp;
    	
    	
    	for (int i = 1 ; i<=Value ; i++)
    	{
    		
    		
    	temp = new HashMap<String,String>();
    	temp.put(CODE_COLUMN, Code[i]);
	    temp.put(FIRST_COLUMN,Date[i]);
		temp.put(SECOND_COLUMN, Status[i]);
		temp.put(THIRD_COLUMN, Product[i]);
		temp.put(FOURTH_COLUMN, Price[i]);
		temp.put(ORDERTYPE, quantity[i]);
		//temp.put(NEEDREF1,needref[i]);
		//temp.put(NEEDTYPE,needtype[i]);
		arraylist.add(temp);
		
    	}
	  }
	  
	 
    

 
    public void settextproperty (TextView textview){
    	
    	textview.setTextSize(textSize);
    	textview.setTextColor(Color.WHITE);
    	
    }
}