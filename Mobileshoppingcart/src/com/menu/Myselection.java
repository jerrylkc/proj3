package com.menu;

import static com.fedorvlasov.lazylist.Constant.CODE_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FIRST_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FOURTH_COLUMN;
import static com.fedorvlasov.lazylist.Constant.SECOND_COLUMN;
import static com.fedorvlasov.lazylist.Constant.THIRD_COLUMN;
import static com.fedorvlasov.lazylist.Constant.ORDERTYPE;
import static com.fedorvlasov.lazylist.Constant.PRODUCTTYPE;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.panel.App;
import org.panel.R;
import org.panel.R.id;
import org.panel.R.layout;
import org.panel.R.menu;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.KeyEventCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

  import com.fedorvlasov.lazylist.LazyAdapter;
import com.fedorvlasov.lazylist.Utils;
import com.menu.SellerpageActivity.FetchTask;
import com.sales.Common;

 
public class Myselection extends SherlockFragmentActivity {
 
	
    private static final String EditText = null;
	private static final String TextView = null;
	private static final String Button = null;
	int textSize;
    LazyAdapter adapter;
    Tab tab;
    ArrayList<HashMap<String,String>> arraylist;
    int selectnumber;
    SharedPreferences preferences;
    AlertDialog.Builder alertDialog;
    Common common;
    TextView wishnumber;
    ListView wishlist;
    Button update,send;
     
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish);
        App.getInstance().addActivity(this);

        
        
        ActionBar mActionBar;
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        
        
          
        TextView dummytext = (TextView)findViewById(R.id.wishdummy);   
        
        wishnumber = (TextView)findViewById(R.id.wishnumber);
        send = (Button)findViewById(R.id.sendbutton);
        update = (Button)findViewById(R.id.updatebutton);
        wishlist = (ListView)findViewById(R.id.wishlist);
        
        update.setOnClickListener(onclicklistener);        
        send.setOnClickListener(onclicklistener);           
       
        Context c = App.getContext();
        SharedPreferences preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);
        
	    String selectnumberstring = preferences.getString("selectnumber","0");
        selectnumber = Integer.parseInt(selectnumberstring);
        
       
        
        DisplayMetrics metrics = App.getContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        		
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height/5);
      	params.setMargins(0, 0, 0, 0);
 
        

        textSize = (int)(dummytext.getTextSize()*0.6); 
        //settextproperty(wishtext,textSize);
        //settextproperty(wishinput,textSize);
        settextproperty(wishnumber,textSize);
        
       
        
        if (selectnumber == 0){
        	wishnumber.setText("選擇: "+selectnumber);
        	send.setVisibility(View.GONE);
        	//wishnumber.setVisibility(View.GONE);
        	wishlist.setVisibility(View.GONE);
        	update.setVisibility(View.GONE);
        	//wishtext.setText("»Ý¨D:");

        }
        else{
        	
        	wishnumber.setVisibility(View.VISIBLE);
        	update.setVisibility(View.VISIBLE);
        	wishlist.setVisibility(View.VISIBLE);
        	send.setVisibility(View.VISIBLE);

        	wishnumber.setText("選擇: "+selectnumber);
        	//wishtext.setText("wish:");
        	
        }
        
        
        update(true);
      
        wishlist.setOnItemClickListener(new OnItemClickListener() 
        {
        	
        	
        	
        	
        	public void onItemClick(AdapterView<?> arg0, View view,int position, long arg3) 
        	{
        		CheckBox checkbox = (CheckBox)arg0.findViewById(R.id.checkbox);
        		
        		/*
        		  if (checkbox.isChecked())
                     Toast.makeText(view.getContext(), "check", Toast.LENGTH_SHORT).show();        			  
      		  
        		  else
                      Toast.makeText(view.getContext(), "chec2k", Toast.LENGTH_SHORT).show();        
                      		*/	  

        		  
        		
    		}
    	});
    
        
        
      	
        TextView t= new TextView(this);        
        common = new Common ();
            
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
        
        Tab tab = mActionBar.newTab().setTabListener(tabListener);
        tab.setCustomView(common.gettitletext(t,"選擇",textSize));
        mActionBar.addTab(tab);
        
       
              
    }

OnClickListener onclicklistener = new OnClickListener(){
	
	 public void onClick(View v) {
	 
		 if (v.getId() == update.getId()){
			 update(false);			 
		 }
		 if (v.getId() == send.getId()){
			 
			 Context c = App.getContext();
             SharedPreferences preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);
             int usertaken = preferences.getInt("usertaken",0);
             
              
             if (usertaken == 0)
         	  showdialog () ;
         	   
             else
            	  showdialog2 () ;
			// senddata();
			 //openImageIntent();
	           // new FetchTask().execute();
 
		 }
		 
	 }
	
};

public void send(){
	
	Context c = App.getContext();
    SharedPreferences preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);
	SharedPreferences.Editor editor = preferences.edit();

    int sentnumber =  Integer.parseInt(preferences.getString("sentnumber","0"));
    int current = sentnumber+1;    
    int sendnumber = Integer.parseInt(preferences.getString("selectnumber","0"));        
	int currentnumber = 1;
    int total = sentnumber+sendnumber;

	
    for (int i=current; i<=total ;i++)
    
    {
    editor.putString("sentitem"+i, preferences.getString("productitem"+currentnumber, "0"));
    editor.putString("sentdescrp"+i, preferences.getString("productdescrp"+currentnumber,"0"));
    editor.putString("sentorder"+i,preferences.getString("ordertype"+currentnumber, "0"));
    editor.putString("senttype"+i,preferences.getString("producttype"+currentnumber,"0"));
    editor.putString("sentquantity"+i,preferences.getString("quantity"+currentnumber,"0"));

    
    
    Log.i(preferences.getString("quantity"+currentnumber,"0"),"sent");

     

	editor.remove("productitem" + currentnumber);
	editor.remove("productdescrp" + currentnumber);
	editor.remove("ordertype"+currentnumber);
	editor.remove("producttype"+currentnumber);
	editor.remove("quantity"+currentnumber);
	editor.commit();
	
	
    Log.i( total +": "+preferences.getString("sentquantity"+i,"0" ),"sent");

    currentnumber++;
    
    }
    
    
     
    

    
    editor.putString("sentnumber",total+"" );
    editor.remove("selectnumber");
    editor.commit();
    
    sendmessage("end");

}

    public void checkvalidate(){
    	Context c = App.getContext();
        SharedPreferences preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);
        String validate = preferences.getString("validate","0");
        
        if (validate.equals("0")){
            sendmessage("send");
          
        }
        else{}
       // 	Toast.makeText(App.getContext(), "§AÁÙ¦³¥¼¿ï¨ú¶µ¥Ø", Toast.LENGTH_SHORT).show();

        	
    }
 
    public void update(boolean firstime){
    	
        Context c = App.getContext();
        SharedPreferences preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);
        
	    String selectnumberstring = preferences.getString("selectnumber","0");
        selectnumber = Integer.parseInt(selectnumberstring);
        
    	populateList(selectnumber );
        String testurl [] = new String[selectnumber];  
        adapter = new LazyAdapter(App.getContext(),testurl,arraylist,"w");
        adapter.settab(tab);
        adapter.setTextView(wishnumber);
        adapter.notifyDataSetChanged();        
        wishlist.setAdapter(adapter);
        
  	    SharedPreferences.Editor editor = preferences.edit();
  	    editor.putString("validate", "0");
        editor.commit();
    	

    	
    }
    
 
    public void endupdate(){
    	
        
    	Intent intent = new Intent();
        intent.setClass(this, Customer.class);
        startActivity(intent);
        this.finish();
    	
    }
    
	public void settab (Tab tab){
		this.tab = tab;
		
	}
	
	
	
//*****//	
private void populateList( int value ) {

	
	
	Context c = App.getContext();
	SharedPreferences preferences  = c.getSharedPreferences("info", c.MODE_PRIVATE);
    	  
	  if (selectnumber > 0){
	  String productcode [] = new String[selectnumber+1];
	  String firstcol [] = new String [selectnumber+1];
	  String secondcol [] = new String [selectnumber+1];
	  String thirdcol [] = new String [selectnumber+1];
	  String forthcol [] = new String [selectnumber+1];
	  String ordertypecol [] = new String [selectnumber+1];
	  String producttype_array [] = new String [selectnumber+1];
	  
	    for (int i = 1 ; i<=selectnumber; i++) {

    		
    		
	       productcode[i] = preferences.getString("productitem" + i,"0");
	       thirdcol[i] = preferences.getString("quantity"+i, "0");
	       
  	       String dscrp[] = preferences.getString("productdescrp" + i, "0").split(",");
  	       
  	       for (int j = 0; j < dscrp.length ; j++){
  	       if (j==0) firstcol [i] = dscrp[j]; 
  	       if (j==1)secondcol [i] = dscrp[j]; 
  	       //if (j==2)thirdcol[i] = dscrp[j];  
  	       if (j==3)forthcol[i] = dscrp[j]; 
  	      
  	       }
  	       
  	       ordertypecol[i] = preferences.getString("ordertype"+i, "0");
  	       producttype_array[i] = preferences.getString("producttype"+i,"0");
  	 
           
	    }

	
    	for (String elements:forthcol){
    		elements = "" + elements;
        	Log.i("e"+elements,"elements");

    	}
  	     
	    
    	arraylist = new ArrayList<HashMap<String,String>>();
    	HashMap<String,String> temp;
    	
    	for (int i = 1 ; i<=selectnumber ; i++)
    	{
    		
    		Log.i("wish1" + productcode[i]+" "+firstcol[i]+" "+
    				secondcol[i]+" "+thirdcol[i]+" "+forthcol[i]+" "+ordertypecol[i] , "m");
    	temp = new HashMap<String,String>();
    	temp.put(CODE_COLUMN, productcode[i]);
    	
	    temp.put(FIRST_COLUMN,firstcol[i]);  //brand
		 temp.put(SECOND_COLUMN, secondcol[i]); //model
		 temp.put(THIRD_COLUMN, thirdcol[i]);  //year
		 temp.put(FOURTH_COLUMN, forthcol[i]); //price
		
		
		temp.put(ORDERTYPE, ordertypecol[i]);
		temp.put(PRODUCTTYPE, producttype_array[i]);
		
		arraylist.add(temp);
    	}
    	
    	
	  }
	  
	
    }
 

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    	Intent updateIntent = new Intent();
        updateIntent.setAction("update");
                
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(App.getContext());
        manager.sendBroadcast(updateIntent);
    	
    }
    return super.onKeyDown(keyCode, event);
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
  menu.getItem(1).setTitle("上載你的貨品");
  menu.getItem(2).setTitle("紀錄");
  menu.getItem(3).setTitle("主頁");
  menu.getItem(4).setTitle("離開");
  
  return super.onPrepareOptionsMenu(menu);
}


@Override
public boolean onOptionsItemSelected(MenuItem item)
{
     
	
    //Toast.makeText(MainActivity.this, "detected", Toast.LENGTH_SHORT).show();
    
    
    switch (item.getItemId())
    {
    
    case R.id.myselectionmenu:       
    {
    	Toast.makeText(App.getContext(),"你己在此頁",Toast.LENGTH_SHORT).show();
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
    	
    	Intent intent = new Intent();
    	intent.setClass(this,Customer.class);
		startActivity(intent);
    	this.finish();
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
    	common.setactivity(Myselection.this);
    	common.showalert(); 
        break;
        
    }
		
    default:
        return super.onOptionsItemSelected(item);
    }
    
    return true;
}    


public void sendmessage(String messagetype){
	
	
	 AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
     
	 
	  if (messagetype.equals("send")){

	  alertDialog.setTitle("I don't remember");
      alertDialog.setMessage("forget");
      
        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
          send();
          
      	  
         }
     });

     alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int which) {
       
         }}
	  
    		 );}
	  
	  if (messagetype.equals("end")){
		  
	    
      	   endupdate();
   
	  }

     alertDialog.show();
     
}


@Override
public void onStop(){
	
	   Context c = App.getContext();
       SharedPreferences preferences  = c.getSharedPreferences("info", c.MODE_PRIVATE);
   
       int deletenumber = Integer.parseInt(preferences.getString("deletenumber","0"));
       String deleteproduct [] = new String [deletenumber+1];
       for (int i = 1 ; i<=deletenumber ; i++){
   	     deleteproduct[i] = preferences.getString("deleteproduct"+i, "0");
         Log.i("You have" + deleteproduct[i], "delete");}
       
	super.onStop();
}
 public void settextproperty (TextView textview,int size){
    	
    	textview.setTextSize(size);
    	textview.setTextColor(Color.BLACK);
    	
    }
 
 
 public void senddata() {
 	
 	
	 String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
     Date currentDate = new Date();  
     String time = currentDate.toString();
     
     String uriAPI = "http://54.254.179.218/try.php";
     
     
     HttpPost httpRequest = new HttpPost(uriAPI); 
     List <NameValuePair> params = new ArrayList <NameValuePair>(); 
     
     
	 Log.i("fuck3","fuck3");

     	 
     //String add = jamtextview.getText().toString();
     //GeoPoint adgp = new GeoPoint((int) currentLocation.getLatitude(), (int) currentLocation.getLongitude());
     /*
     String mtime = time;
     GeoPoint adgp = new GeoPoint((int) currentLocation.getLatitude(), (int) currentLocation.getLongitude());
     Log.i("Getlat","g"+currentLocation.getLatitude());
     Log.i("Getlong","l"+currentLocation.getLongitude());
     
     
     //Address mAddress2 = getAddressbyGeoPoint(currentLocation);
     //String maddress = ""+mAddress2;
     int x = 0;
     //String maddress = ""+getAddressbyGeoPoint(currentLocation);
     Address maddress = getAddressbyGeoPoint(currentLocation);
     //String address2 = maddress.getLocality();
     String address2 = maddress.getCountryName();
     for( int i = 0; i < 50; i++)
     Log.i("address", ""+address2);
     */
     //String maddress = "ShaTin";
     //String maddress = currentLocation.getLatitude() + "," + currentLocation.getLongitude();

        //String uriAPI = "http://kaliser.zxq.net";
        /*建立HTTP Post連線*/
        //HttpPost httpRequest = new HttpPost(uriAPI); 
        /*
         * Post�?�作傳�?變數必須用NameValuePair[]陣列儲存
        */
       // List <NameValuePair> params = new ArrayList <NameValuePair>(); 
         params.add(new BasicNameValuePair("catid", "1")); //I am Post String
        // params.add(new BasicNameValuePair("address", address2 )); //I am Post String
     
     
     //params.add(new BasicNameValuePair("mode",""+mode));
        try 
        { 
          /*發出HTTP request*/
        	
        	 Log.i("fuck2","fuck2");
          httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
          /*�?�得HTTP response*/
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
          /*若狀態碼為200 ok*/
          
          if(httpResponse.getStatusLine().getStatusCode() == 200)  
          { 
            /*�?�出回應字串*/
        	  
	        	 Log.i("fuck1","fuck1");

              String strResult = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
        	  
        	  
        	  
				//	String strResult = EntityUtils.toString(httpResponse.getEntity());
					//Log.i("debug", strResult);

		 
              JSONObject demoJson = new JSONObject(strResult);
              JSONArray numberList = demoJson.getJSONArray("success");
              
              for(int i=0; i<numberList.length(); i++){
                    Log.i(numberList.getJSONObject(i).getString("descrp"),"descrp");
              }
              
					
        	  //JSONObject json_data= new JSONObject(strResult);
        	 // String descrp = json_data.getString("descrp") ;
        	  
        	//  Log.i("descrp"+descrp,"descrp");
        	  
        	 // JSONArray arrJson = json_data.getJSONArray("descrp");
        	  
            //String strResult = EntityUtils.toString(httpResponse.getEntity());
          } 
          else 
          { 
	        	 Log.i("fucked","fucked");

           // mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString()); 
          } 
        } 
        catch (ClientProtocolException e) 
        {  
         // mTextView1.setText(e.getMessage().toString()); 
          e.printStackTrace(); 
        } 
        catch (IOException e) 
        {  
         // mTextView1.setText(e.getMessage().toString()); 
          e.printStackTrace(); 
        } 
        catch (Exception e) 
        {  
        //  mTextView1.setText(e.getMessage().toString()); 
          e.printStackTrace();  }
        //Toast.makeText(ChangeviewActivity.this, "GPS and Photo are send", Toast.LENGTH_LONG).show();

        //changeactivity(2);

}
 
 
 
 public class FetchTask extends AsyncTask<Void, Void, JSONArray> {
     @Override
     protected JSONArray doInBackground(Void... params) {
         try {
             HttpClient httpclient = new DefaultHttpClient();
             HttpPost httppost = new HttpPost("http://54.254.179.218/try.php");

             
             // Add your data
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
             nameValuePairs.add(new BasicNameValuePair("catid","1"));
             /*nameValuePairs.add(new BasicNameValuePair("up",mup));
             nameValuePairs.add(new BasicNameValuePair("low",mlow));
             nameValuePairs.add(new BasicNameValuePair("pulse",mpluse));*/
             
           //  Toast.makeText(DeviceControlActivity.this, "cccc", Toast.LENGTH_LONG).show();
             
           //  nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
             httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

             // Execute HTTP Post Request
             HttpResponse response = httpclient.execute(httppost);

             JSONArray numberList = new JSONArray();   
             
             
             Log.i(""+response.getStatusLine().getStatusCode(),"status");
             
             if(response.getStatusLine().getStatusCode() == 200)  
	          { 
	            /*�?�出回應字串*/
	        	  
		        	 Log.i("fuck1","fuck1");

	                String strResult = EntityUtils.toString(response.getEntity(), "utf-8");
	        	  
	        	  
	        	  
					//	String strResult = EntityUtils.toString(httpResponse.getEntity());
						//Log.i("debug", strResult);

			 
	              JSONObject demoJson = new JSONObject(strResult);
	              numberList = demoJson.getJSONArray("success");
	              
	              int count = numberList.length();
	              
	              for(int i=0; i<numberList.length(); i++){
	                    Log.i(numberList.getJSONObject(i).getString("pid"),"status");
	                    Log.i(numberList.getJSONObject(i).getString("name"),"status");
	                    Log.i(numberList.getJSONObject(i).getString("price"),"status");

	                    Log.i(numberList.getJSONObject(i).getString("descrp"),"status");
	                    Log.i(numberList.getJSONObject(i).getString("quantity"),"status");
	                    Log.i(numberList.getJSONObject(i).getString("catid"),"status");


	                    
	              }
	              
	              
	           
	        	  //JSONObject json_data= new JSONObject(strResult);
	        	 // String descrp = json_data.getString("descrp") ;
	        	  
	        	//  Log.i("descrp"+descrp,"descrp");
	        	  
	        	 // JSONArray arrJson = json_data.getJSONArray("descrp");
	        	  
	            //String strResult = EntityUtils.toString(httpResponse.getEntity());
	          } 
             
             /*
             BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"), 8);
             StringBuilder sb = new StringBuilder();
             sb.append(reader.readLine() + "\n");
             String line = "0";
             while ((line = reader.readLine()) != null) {
                 sb.append(line + "\n");
             }
             reader.close();
             String result11 = sb.toString();
             
*/
             return  numberList ;

             // parsing data
          } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
         finally{
         	
         	//Toast.makeText(DeviceControlActivity.this, "你的血壓已經成功傳到網上", Toast.LENGTH_LONG).show();
         }
     }
      

     @Override
     protected void onPostExecute(JSONArray result) {
         if (result != null) {
         	
         	Log.i("success","status");
         	
         	/*
            for(int i=0; i<result.length(); i++){
            	
            	
           }
         	*/
             // do something
         } else {
         	
         	Log.i("fail","status");

             // error occured
         }
     }
 }
 
 
 
 
 
 
 
 private Uri outputFileUri;

 private void openImageIntent() {

 // Determine Uri of camera image to save.
 final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
 root.mkdirs();
  
// final String fname = Utils.getUniqueImageFilename();
 final String fname = "img_"+ System.currentTimeMillis() + ".jpg";
 final File sdImageMainDirectory = new File(root, fname);
 outputFileUri = Uri.fromFile(sdImageMainDirectory);

     // Camera.
     final List<Intent> cameraIntents = new ArrayList<Intent>();
     final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
     final PackageManager packageManager = getPackageManager();
     final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
     for(ResolveInfo res : listCam) {
         final String packageName = res.activityInfo.packageName;
         final Intent intent = new Intent(captureIntent);
         intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
         intent.setPackage(packageName);
     intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
         cameraIntents.add(intent);
     }

     // Filesystem.
     final Intent galleryIntent = new Intent();
     galleryIntent.setType("image/*");
     galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

     // Chooser of filesystem options.
     final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

     // Add the camera options.
     chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

     startActivityForResult(chooserIntent, 2);
 }

 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data)
 {
     if(resultCode == RESULT_OK)
     {
         if(requestCode == 2)
         {
             final boolean isCamera;
             if(data == null)
             {
                 isCamera = true;
             }
             else
             {
                 final String action = data.getAction();
                 if(action == null)
                 {
                     isCamera = false;
                 }
                 else
                 {
                     isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                 }
             }

             Uri selectedImageUri;
             if(isCamera)
             {
                 selectedImageUri = outputFileUri;
             }
             else
             {
                 selectedImageUri = data == null ? null : data.getData();
             }
         }
     }
 }
 
 
 
 
 
 public void showdialog (){
		
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("輸入個人資料");
		//alert.setMessage("o");

		// Set an EditText view to get user input 
	 
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.personallayout3, null);
		
		final EditText nameinput = (EditText) textEntryView.findViewById(R.id.edittext1 );
		final EditText phoneinput = (EditText) textEntryView.findViewById(R.id.edittext3 );

     alert.setView(textEntryView) ;	 

     
		alert.setPositiveButton("確定", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String name = nameinput.getText().toString();
		  String phonenumber = phoneinput.getText().toString()  ;

		  if (name.length() <=0 || phonenumber.length() <=0 ){
			  
			  Toast.makeText(Myselection.this , "請輸入個人資料", Toast.LENGTH_SHORT).show();
		  }
	 
		  else {
			  Context c = App.getContext();
	          SharedPreferences settings = getSharedPreferences ("info", c.MODE_PRIVATE);

              SharedPreferences.Editor PE = settings.edit();
              PE.putInt("usertaken", 1);
	          PE.putString("username", name);
	          PE.putString ("userphone",phonenumber);
	          PE.commit();
	          
	          showdialog2();
		  }
	         
		  
		  }
		});

		alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();	
		 
	}
 
 
 
 public void showdialog2 (){
		
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		Context c = App.getContext();
        SharedPreferences preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);
        
	    String totalstring = preferences.getString("total","0");
        float total = Float.parseFloat(totalstring);
	    String random = getRandomString(8);
        
		SharedPreferences.Editor editor = preferences.edit();

		editor.putString("invoice", random);
		editor.commit();
		
		alert.setTitle("你的帳單已經送出");
	    alert.setMessage("帳單編號: " +random+" 很快有人會聯絡你" );
	    
	    

	 
	    	
	    	
		// Set an EditText view to get user input 
	 
		 
     
		alert.setPositiveButton("確定", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  
			 
			send();
		  //Toast.makeText(MainActivity.this, name+" "+age+ " "+isman+" "+phonenumber, Toast.LENGTH_LONG).show();
			  
		  // Do something with value!
			//Myselection.this.finish();
		  }
		});

	 
		alert.show();	
		 
	}

 private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

 private static String getRandomString(final int sizeOfRandomString)
   {
   final Random random=new Random();
   final StringBuilder sb=new StringBuilder();
   for(int i=0;i<sizeOfRandomString;++i)
     sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
   return sb.toString();
   }

 
 
 
 
}