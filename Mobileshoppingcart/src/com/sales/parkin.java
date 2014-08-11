package com.sales;

import static com.fedorvlasov.lazylist.Constant.FIRST_COLUMN;
import static com.fedorvlasov.lazylist.Constant.SECOND_COLUMN;
import static com.fedorvlasov.lazylist.Constant.THIRD_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FOURTH_COLUMN;
import static com.fedorvlasov.lazylist.Constant.CODE_COLUMN;
import static com.fedorvlasov.lazylist.Constant.ORDERTYPE;
import static com.fedorvlasov.lazylist.Constant.PRODUCTTYPE;
import static com.fedorvlasov.lazylist.Constant.PHOTONO;

import static com.fedorvlasov.lazylist.Constant.DESCRP;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import org.panel.NothingSelectedSpinnerAdapter;
import org.panel.R;
import org.panel.R.id;
import org.panel.R.layout;

import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.fedorvlasov.lazylist.LazyAdapter;
 
import com.menu.productdetails;
import com.menu.Myselection.FetchTask;
import com.actionbarsherlock.app.ActionBar.Tab;
 

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;


public class parkin extends SherlockFragment {
 
    ListView list;
    private ArrayList<HashMap<String,String>> arraylist;
    LazyAdapter adapter;
    LinearLayout carlayout;
    TextView brandtext,modeltext,dummytext;
    Spinner sexspinner,typespinner,latestspinner,pricespinner,apricespinner;
    View v;
    View view;
    Tab tab;
    Context context;
     ArrayAdapter sex_adapter,type_adapter,latest_adapter,price_adapter;
    int textSize;
    Activity a;
	private PullToRefreshListView mPullRefreshListView;
     
    
  
    
	String brand[],model[],year[],price[],code_column[],order_type[],testurl [],photono[],sex[],descrp[];
    JSONArray numberList = new JSONArray();   

    
    @Override
    public SherlockFragmentActivity getSherlockActivity() {
        return super.getSherlockActivity();       
    }
 
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
    }
 
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
    	Log.i("created","created");
    	view = inflater.inflate(R.layout.car, container, false);
        context = view.getContext();
    	
    	carlayout = (LinearLayout)view.findViewById(R.id.carlayout);
    	
		mPullRefreshListView = (PullToRefreshListView)view.findViewById(R.id.list);

        //list=(ListView)view.findViewById(R.id.list);
        
		// Set a listener to be invoked when the list should be refreshed.
				mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(App.getContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new FetchTask().execute();
					}
				});

				// Add an end-of-list listener
				mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
 					}
				});

				list = mPullRefreshListView.getRefreshableView();

				// Need to use the Actual ListView when registering for Context Menu
				registerForContextMenu(list);
		
		
		
		
		
		
		
        IntentFilter filter = new IntentFilter();
        filter.addAction("update");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(App.getContext());
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
           	 retain();
            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver, filter);
        
        
        update();
        
        list.setOnItemClickListener(new OnItemClickListener() 
        {
        	
        	
        	public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) 
        	{
        		
        		CheckBox c = (CheckBox)arg1.findViewById(R.id.checkbox);
        		int check = 0;
        		  if (c.isChecked())
        		    check = 1;
        		  else
        			check = 0;
        		
        		
        		Intent intent = new Intent();
				intent.setClass(view.getContext(),productdetails.class);
				Bundle bundle =new Bundle();
				bundle.putInt("checkbox", check);
				bundle.putString("code",adapter.getlistitem(position-1, CODE_COLUMN)+"");
		        bundle.putString("brand",adapter.getlistitem(position-1, FIRST_COLUMN)+"");
		        bundle.putString("model", adapter.getlistitem(position-1,SECOND_COLUMN)+"");
		        bundle.putString("year", adapter.getlistitem(position-1,THIRD_COLUMN)+"");
		        bundle.putString("price", adapter.getlistitem(position-1,FOURTH_COLUMN)+"");
		        bundle.putString("photono", adapter.getlistitem(position-1,PHOTONO)+"");
		        bundle.putString("descrp", adapter.getlistitem(position-1,DESCRP)+"");

		        //bundle.putString("photo",mStrings[position]);
		        bundle.putString("cartype", "c");
     
		        intent.putExtras(bundle);
				startActivity(intent);
				
        		
    		}
    	});
        

        
         sexspinner = (Spinner)view.findViewById(R.id.brandspinner);
         typespinner = (Spinner)view.findViewById(R.id.modelspinner);
         latestspinner = (Spinner)view.findViewById(R.id.yearspinner);
         pricespinner = (Spinner)view.findViewById(R.id.pricespinner);
         
         dummytext = (TextView)view.findViewById(R.id.cardummy);      
         textSize = (int)(dummytext.getTextSize()*0.6);      
         
        
                  
        
         
        
         String sex []= {"男/女裝","男","女","全部"};
         String type [] = {"類型","冷衫","T-shirt","外套", "其他","全部"};
         String price [] = {"價格","由便至貴","由貴至便"};
         String latest [] = {"日期","由新至舊","由舊至新"};
        // CustomAdapter adapter = new CustomAdapter(view.getContext(),R.layout.spinner_item,model);
         
        sex_adapter = new ArrayAdapter(view.getContext(),
        		 android.R.layout.simple_spinner_item, sex);
         type_adapter = new ArrayAdapter(view.getContext(),
        		 android.R.layout.simple_spinner_item,type);
         
         latest_adapter = new ArrayAdapter(view.getContext(),
        		 android.R.layout.simple_spinner_item,latest);
         
         price_adapter = new ArrayAdapter(view.getContext(),
        		 android.R.layout.simple_spinner_item,price);
         
  

        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         //model_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

         sexspinner.setPrompt("男/女裝");
         typespinner.setPrompt("類別");
         pricespinner.setPrompt("價格");
         latestspinner.setPrompt("日期");
         
         NothingSelectedSpinnerAdapter sex_adapter2 =  new NothingSelectedSpinnerAdapter(sex_adapter,
        		 R.layout.brand,view.getContext(),(int)(dummytext.getTextSize()*0.7),"男/女裝");
         
         NothingSelectedSpinnerAdapter type_adapter2 = new NothingSelectedSpinnerAdapter(type_adapter,
        		 R.layout.brand,view.getContext(),(int)(dummytext.getTextSize()*0.7),"類型");
         
        
         
         NothingSelectedSpinnerAdapter price_adapter2= new NothingSelectedSpinnerAdapter(price_adapter,
        		 R.layout.brand,view.getContext(),(int)(dummytext.getTextSize()*0.7),"價格");
         
         
         NothingSelectedSpinnerAdapter latest_adapter2= new NothingSelectedSpinnerAdapter(latest_adapter,
        		 R.layout.brand,view.getContext(),(int)(dummytext.getTextSize()*0.7),"日期");

 
        // setHeight(sexspinner,2,pricespinner);

         sexspinner.setAdapter(sex_adapter2);
         
         typespinner.setAdapter(type_adapter2);
         
         latestspinner.setAdapter(latest_adapter2);
         
         pricespinner.setAdapter(price_adapter2);

         
         
         sexspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {   
            	     
                 if (position != 0){
                	 
                	 if  (sex_adapter.getItem(position-1).toString().equals("Audi")){
                		// changeadapterdata(modelspinner,Audimodel);               	 
                	 }
                	 else if  (sex_adapter.getItem(position-1).toString().equals("Acura")){
                		// changeadapterdata(modelspinner,Acuramodel);               	 
                	 }
                	 
                	 /*
                	 else if  (brand_adapter.getItem(position-1).toString().equals("Aston Martin")){
                		 changeadapterdata(modelspinner,Astonmodel);               	 
                	 }
                	 
                	 else if  (brand_adapter.getItem(position-1).toString().equals("BMW")){
                		 changeadapterdata(modelspinner,bmwmodel);               	 
                	 }
                	 
                	 else if  (brand_adapter.getItem(position-1).toString().equals("Ford")){
                	 changeadapterdata(modelspinner,fordmodel);                	 
                	 }
                	 else if  (brand_adapter.getItem(position-1).toString().equals("Mitsubishi")){
                    	 changeadapterdata(modelspinner,mitsumodel);                	 
                    	 }
                	 
                	 else if  (brand_adapter.getItem(position-1).toString().equals("Hoda")){
                    	 changeadapterdata(modelspinner,hondamodel);                	 
                    	 }
                	 
                	 else if  (brand_adapter.getItem(position-1).toString().equals("Toyota")){
                    	 changeadapterdata(modelspinner,toyotamodel);                	 
                    	 }
                	 else if (brand_adapter.getItem(position-1).toString().equals("Porsche")){
                    	 changeadapterdata(modelspinner,porschemodel); 
                    	 }   
                    	 */
                	 
 	 
     
                	 
                 
            	 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
                 // your code here             
        		// changeadapterdata(modelspinner,year1);


   
             }
         });
         
         
      
         
         
         
         
        return view;
    }
    

    
    public void update(){
    	
    	
        new FetchTask().execute();

        
    	 //populateList(4);
    	 
     	 
    	 
          
        
      
       
    	
    	
    }
    
    
    
    public void changeadapterdata(Spinner s,String v []){
    	/*
    	model_adapter = new ArrayAdapter(view.getContext(),
       		 android.R.layout.simple_spinner_item,v);
    	model_adapter2 = new NothingSelectedSpinnerAdapter(model_adapter,
       		 R.layout.brand,view.getContext(),(int)(dummytext.getTextSize()*0.63),"«¬¸¹");
        s.setAdapter(model_adapter2);
        */
    	
    }
    
    
    @Override
    public void onDestroy()
    {
        list.setAdapter(null);
        //clearcache();
        super.onDestroy();
    }

    
    public OnClickListener listener=new OnClickListener(){
        @Override
        public void onClick(View arg0) {
            adapter.imageLoader.clearCache();
            adapter.notifyDataSetChanged();
        }
    };
    
    
    public void clearcache(){    	
    	adapter.imageLoader.clearCache();
        adapter.notifyDataSetChanged();    	
    }
    

    
public void setHeight(final View h,final int type,final View target){
		
		final View v = h;
//		final TextView tv = (TextView)view.findViewById(R.id.brandtext);
	    int finalHeight,finalWidth;
		
	    
	      ViewTreeObserver vto = h.getViewTreeObserver();
	      
	        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
	            public boolean onPreDraw() {
	            	
	            	//tv.getMeasuredHeight();
	            	//Log.i(tv.getMeasuredHeight()+"","textheigh2");
	                //Tv.setText(finalHeight);
	                //Log.i("Height: " +  v.getMeasuredHeight() + " Width: " + v.getMeasuredWidth(),"h");
	                
	            	if (type == 2){
	            		//Log.i(v.getMeasuredHeight()+"","textheigh2");
	            	    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(v.getMeasuredWidth()), LayoutParams.WRAP_CONTENT);
	            	    target.setLayoutParams(lp);
	            	    //modelspinner.setLayoutParams(lp);
	            	}
	            	
	            	if (type == 1){
	            	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	            	h.setLayoutParams(lp);}
	            	//lp.setMargins(0, v.getMeasuredHeight(), 0, 0);
	                //carlayout.setLayoutParams(lp);}
	                
	                return true;
	            }
	        });
		
	};
    
	
	public void setActivity(Activity a){
		
		this.a = a;
	}
	
	public void settab (Tab tab){
		this.tab = tab;
		
	}
	
    public void settextproperty (TextView textview){
    	
    	textview.setTextSize(textSize);
    	textview.setTextColor(Color.WHITE);
    	
    }
    
    private void populateList(int value) {
		
    	
        //testurl = new String[value];

        
    	for (int i = 0 ; i<price.length ; i++){
    		//price[i] = "$" +price[i];
    	}
    	
    	arraylist = new ArrayList<HashMap<String,String>>();
    	HashMap<String,String> temp;
    	
    	for (int i = 0 ; i<brand.length ; i++)
    	{
    	temp = new HashMap<String,String>();
    	temp.put(CODE_COLUMN, code_column[i]);
	    temp.put(FIRST_COLUMN,brand[i]);
		temp.put(SECOND_COLUMN, "1");
		temp.put(THIRD_COLUMN, "1");
		temp.put(FOURTH_COLUMN, price[i]);
		temp.put(DESCRP, descrp[i]);
		temp.put(PHOTONO, photono[i]);

		temp.put(ORDERTYPE, "b");
        temp.put(PRODUCTTYPE, "c");
		arraylist.add(temp);
    	}
    }
  
 
    
   //========================================================================
    public class FetchTask extends AsyncTask<Void, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(Void... params) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://54.254.179.218/try.php");

                
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("catid","3"));
               
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

                JSONArray numberList = new JSONArray();   
                
                
                //Log.i(""+response.getStatusLine().getStatusCode(),"status");
                
                if(response.getStatusLine().getStatusCode() == 200)  
   	          { 
   	            /*�?�出回應字串*/
   	        	  
   		        	 Log.i("fuck1","fuck1");

   	                String strResult = EntityUtils.toString(response.getEntity(), "utf-8");
   	        	  
   	         

   			 
   	              JSONObject demoJson = new JSONObject(strResult);
   	              numberList = demoJson.getJSONArray("success");
   	              
   	              
   	              
 
   	       	
   	              brand = new String[numberList.length()]; model = new String[numberList.length()];
   	              order_type = new String[numberList.length()]; year = new String[numberList.length()];
   	              price = new String[numberList.length()]; code_column = new String[numberList.length()];
   	              testurl = new String [numberList.length()]; photono = new String[numberList.length()];
   	              descrp = new String[numberList.length()];
   	              sex = new String [numberList.length()];
   	              
   	              
   	              for(int i=0; i<numberList.length(); i++){
   	            	  
   	            	    int j = i+1;
   	                 /*   Log.i(numberList.getJSONObject(i).getString("pid"),"status");
   	                    Log.i(numberList.getJSONObject(i).getString("name"),"status");
   	                    Log.i(numberList.getJSONObject(i).getString("price"),"status");
   	                    
   	                    Log.i(numberList.getJSONObject(i).getString("photono"),"status");

   	                    Log.i(numberList.getJSONObject(i).getString("descrp"),"status");
   	                    Log.i(numberList.getJSONObject(i).getString("quantity"),"status");
   	                    Log.i(numberList.getJSONObject(i).getString("catid"),"status");*/
   	                    
   	                    code_column [i] = numberList.getJSONObject(i).getString("pid"); 
   	                    brand[i] = numberList.getJSONObject(i).getString("name");
   	                    sex[i] = numberList.getJSONObject(i).getString("type");
   	                    photono[i] = numberList.getJSONObject(i).getString("photono");
   	                    descrp[i] = numberList.getJSONObject(i).getString("descrp");

   	                    
   	                    price[i] = numberList.getJSONObject(i).getString("price");
   	                    testurl[i] = "http://54.254.179.218/image/"+ code_column[i]+"_1.jpg";
      
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
            	
            	 retain();
            } else {
            	
            	Log.i("fail","status");

                // error occured
            }
        }
    }
    
    public void retain(){
        populateList(numberList.length());
        adapter = new LazyAdapter(view.getContext(),testurl,arraylist,"c");
        adapter.settab(tab);
        adapter.setActivity(a);
        
        if (adapter!=null)
        adapter.notifyDataSetChanged();

   		//mAdapter.notifyDataSetChanged();

   	// Call onRefreshComplete when the list has been refreshed.
   	    mPullRefreshListView.onRefreshComplete();
        list.setAdapter(adapter);
      	
      }
 
}
