package com.sales;

import static com.fedorvlasov.lazylist.Constant.FIRST_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FIRST_COLUMN2;
import static com.fedorvlasov.lazylist.Constant.FIRST_COLUMN3;

import static com.fedorvlasov.lazylist.Constant.SECOND_COLUMN;
import static com.fedorvlasov.lazylist.Constant.THIRD_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FOURTH_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FIFTH_COLUMN;
import static com.fedorvlasov.lazylist.Constant.CODE_COLUMN;
import static com.fedorvlasov.lazylist.Constant.SELECTNUMBER;
import static com.fedorvlasov.lazylist.Constant.ORDERTYPE;
import static com.fedorvlasov.lazylist.Constant.PRODUCTTYPE;


import java.util.ArrayList;
import java.util.HashMap;

import org.panel.App;
import org.panel.R;
import org.panel.R.id;
import org.panel.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.fedorvlasov.lazylist.LazyAdapter;
 
public class wish extends SherlockFragment {
 	
    int textSize;
   // LazyAdapter adapter;
    Tab tab;
    ArrayList<HashMap<String,String>> arraylist;
    int selectnumber;
    View v;
    ListView wishlist;
    
    @Override
    public SherlockFragmentActivity getSherlockActivity() {
       
    	return super.getSherlockActivity();
        
    }
    

    
    
public void onStart(){
	
	super.onStart();
	Log.i("start","deleted");
}
@Override
public void onStop(){
	
	
	super.onStop();
    Log.i("stop", "deleted");  

}

    @Override  
    public void onAttach(Activity activity) {  
        super.onAttach(activity);  
        Log.i("Fragment 1", "deleted");  
    }  
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	Log.i("callonviewcreated","deleted");
        super.onViewCreated(view, savedInstanceState);
    }
 

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Get the view from fragmenttab2.xml    	
        
    	
        View view = inflater.inflate(R.layout.wish, container, false);
        v = view;

		
  	    //Log.i("fuck","m3");

    	//Toast.makeText(view.getContext(), "on created view", Toast.LENGTH_SHORT).show();

        
    
        TextView dummytext = (TextView)view.findViewById(R.id.wishdummy);   
        TextView wishnumber = (TextView)view.findViewById(R.id.wishnumber);
        Button send = (Button)view.findViewById(R.id.sendbutton);
        Button update = (Button)view.findViewById(R.id.updatebutton);
        wishlist = (ListView)view.findViewById(R.id.wishlist);
        
        Context c = App.getContext();
        SharedPreferences preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);
        
	    String selectnumberstring = preferences.getString("selectnumber","0");
        selectnumber = Integer.parseInt(selectnumberstring);
  	    Log.i("selectnumber" + selectnumber,"m3");

        for (int i = 1 ; i <=selectnumber; i++){

      	      String code  = preferences.getString("productitem"+i,"0");
	    	  String descrp = preferences.getString("productdescrp"+i,"0" );
	    	  String theorder = preferences.getString("ordertype"+i, "0");
	    	  String type = preferences.getString("producttype"+i,"0");  	    	  
      	  Log.i("in wishopen: " + i+" "+ code +descrp +" " + theorder +" " +type,"m");

            }
        
        DisplayMetrics metrics = view.getContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        		
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height/5);
      	params.setMargins(0, 0, 0, 0);
         

        textSize = (int)(dummytext.getTextSize()*0.6); 
     
        settextproperty(wishnumber);
        
       
        
        if (selectnumber == 0){
        	wishnumber.setVisibility(View.GONE);
        	wishlist.setVisibility(View.GONE);
        	update.setVisibility(View.GONE);
 
        }
        else{
        	
        	wishnumber.setVisibility(View.VISIBLE);
        	update.setVisibility(View.VISIBLE);
        	wishlist.setVisibility(View.VISIBLE);

        	wishnumber.setText("選擇項目: "+selectnumber);
         	
        }
        
        populateList(selectnumber);
        String testurl [] = new String[selectnumber];
        
        
        /*
        adapter = new LazyAdapter(view.getContext(),testurl,arraylist,"w");
        adapter.settab(tab);
        adapter.notifyDataSetChanged();
        
        wishlist.setAdapter(adapter);*/
      	
        view.setVisibility(View.GONE);
        return view;
    }
 
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	
    	
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
    
    

    
    @Override
    public void onDestroy()
    {
    	wishlist.setAdapter(null);
    	
        super.onDestroy();
    }
    
	public void settab (Tab tab){
		this.tab = tab;
		
	}
	
private void populateList(int value) {

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

//    		Toast.makeText(v.getContext(), "In wish "+ preferences.getString("productitem"+i,"0")+ preferences.getString("productdescrp"+i,"0") + " " +  preferences.getString("ordertype"+i,"0") 
  //  				, Toast.LENGTH_SHORT).show();
    		
	       productcode[i] = preferences.getString("productitem" + i,"0");
	       
  	       String dscrp[] = preferences.getString("productdescrp" + i, "0").split(",");
  	       
  	       for (int j = 0; j < dscrp.length ; j++){
  	       if (j==0) firstcol [i] = dscrp[j]; 
  	       if (j==1)secondcol [i] = dscrp[j]; 
  	       if (j==2)thirdcol[i] = dscrp[j];  if (j==3)forthcol[i] = dscrp[j];}
  	      
  	       
  	       ordertypecol[i] = preferences.getString("ordertype"+i, "0");
  	       producttype_array[i] = preferences.getString("producttype"+i,"0");
  	 

	    }

	
    	//for (String elements:forthcol)
    		//elements = "$" + elements;
    	
    	for (int i = 1 ; i<=selectnumber ; i++){
    		if (ordertypecol[i].equals("b"))
    			ordertypecol[i] = "買";
    	    if (ordertypecol[i].equals("r"))
    	    	ordertypecol[i] = "租";
    	    }
    	     
	    
    	arraylist = new ArrayList<HashMap<String,String>>();
    	HashMap<String,String> temp;
    	
    	for (int i = 1 ; i<=selectnumber ; i++)
    	{
    		
    		Log.i("wish" + productcode[i]+" "+firstcol[i]+" "+
    				secondcol[i]+" "+thirdcol[i]+" "+forthcol[i]+" "+ordertypecol[i] , "m");
    	temp = new HashMap<String,String>();
    	temp.put(CODE_COLUMN, productcode[i]);
	    temp.put(FIRST_COLUMN,firstcol[i]);
		temp.put(SECOND_COLUMN, secondcol[i]);
		temp.put(THIRD_COLUMN, thirdcol[i]);
		temp.put(FOURTH_COLUMN, forthcol[i]);
		temp.put(ORDERTYPE, ordertypecol[i]);
		arraylist.add(temp);
    	}
	  }
	  
	 
    }
    
    


 public void settextproperty (TextView textview){
    	
    	textview.setTextSize(textSize);
    	textview.setTextColor(Color.WHITE);
    	
    }
 
}