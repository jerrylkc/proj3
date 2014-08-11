package com.menu;

import static com.fedorvlasov.lazylist.Constant.FIRST_COLUMN;
import static com.fedorvlasov.lazylist.Constant.SECOND_COLUMN;
import static com.fedorvlasov.lazylist.Constant.THIRD_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FOURTH_COLUMN;
import static com.fedorvlasov.lazylist.Constant.CODE_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FIFTH_COLUMN;



import java.util.ArrayList;
import java.util.HashMap;

import org.panel.App;
import org.panel.NothingSelectedSpinnerAdapter;
import org.panel.R;
import org.panel.R.id;
import org.panel.R.layout;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.Display;
import android.view.Gravity;
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
import android.graphics.Typeface;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.fedorvlasov.lazylist.LazyAdapter;
import com.sales.Common;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
 

public class Newcar extends SherlockFragmentActivity {
 
    ListView list;
    private ArrayList<HashMap<String,String>> arraylist;
    LazyAdapter adapter;
    LinearLayout carlayout;
    TextView brandtext,modeltext,dummytext;
    Spinner brandspinner,modelspinner,yearspinner,pricespinner;
    //ArrayList<String,String>news = new ArrayList<String,String>();
    Tab tab;
    Context context;
    ArrayAdapter brand_adapter,model_adapter,year_adapter,price_adapter;    
    int textSize;
    NothingSelectedSpinnerAdapter brand_adapter2,model_adapter2,year_adapter2,price_adapter2;
    
    String model[] ={"©Ò¦³«¬¸¹"};
    String brand[] ={"©Ò¦³µP¤l","Acura","Audi","Aston Martin","BMW","Ford","Mercedes Benz","Mitsubishi","Honda","Toyota","Porsche","¨ä¥L"};
    
    String Audimodel [] = {"©Ò¦³«¬¸¹","A1-A8","Q3-Q7","TT","R8","¨ä¥L"};
    String Acuramodel [] = {"©Ò¦³«¬¸¹","RLX","TL","TSX","ILX","ZDX","MDX","RDX","¨ä¥L"};
    String Astonmodel [] = {"©Ò¦³«¬¸¹","Vantage","DB9","Rapide S","VANQUISH","ZAGATO","CC1OO","¨ä¥L"};
    String bmwmodel [] = {"©Ò¦³«¬¸¹","1 series","3 series","5 series","6 series","7 sereis","X series","Z4","M series","¨ä¥L"};
    String fordmodel [] = {"©Ò¦³«¬¸¹","C-MAX","E-Series","Edge","Escape","Expedition", "Explorer" ,"Fiesta" ,"Flex","Focus","Fusion"
    		               ,"Mustang","Shelby GT500","Taurus","Transit Connect Wagon","¨ä¥L"};
    String benzmodel [] = {"©Ò¦³«¬¸¹","A-Class","B-Class","C-Class","E-Class","S-Class","CLA-Class","CLS-Class","SL-Class","SLK-Class"
    		              ,"G-Class","GL-Class","M-Class","SLS AMG","¨ä¥L"};
    
    String mitsumodel [] = {"©Ò¦³«¬¸¹","Pajero","Triton","OutLander","Delica",
    		"Lancer","Galant","Colt","Mirage","i","ek","TOPPO","Minicab","L300","¨ä¥L"};
    
    String toyotamodel[] = {"©Ò¦³«¬¸¹","86","Alphard","Camry","Corolla","Land Cruiser Prado","Marl X","Previa","Prius","Noah","Ractis"
    		              ,"RAV4","Vellfire","Wish","Hlace","Coaster","Public Light Bus","LPG Taxi","¨ä¥L"};

    String hondamodel [] = {"©Ò¦³«¬¸¹","Stream","CR-Z","Freed","Jazz","Odyssey","Stepwgn","Stepwgn Spade","¨ä¥L"};
    		
    String porschemodel [] = {"©Ò¦³«¬¸¹","Boxter","Cayman","911","Panamera","Cayenne","¨ä¥L"};
    String alfaromeomodel [] = {"©Ò¦³«¬¸¹","MITO","GIULIETTA","159","159SW","¨ä¥L"};
    

 
    @Override
public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car);
        App.getInstance().addActivity(this);

    	
        ActionBar mActionBar;
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        
    	carlayout = (LinearLayout)findViewById(R.id.carlayout);
        list=(ListView)findViewById(R.id.list);
        
        Display display = getWindowManager().getDefaultDisplay(); 
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();
        
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
        list.setLayoutParams(lp);
        
        
        populateList(4);
        String testurl [] = new String[4];
   
        adapter = new LazyAdapter(this,testurl,arraylist,"n");
        adapter.settab(tab);
        
        list.setAdapter(adapter);
        
        list.setOnItemClickListener(new OnItemClickListener() 
        {
        	
        	
        	public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) 
        	{
        		
        		
        		Toast.makeText(App.getContext(), "You click on " +adapter.getlistitem(position,FIRST_COLUMN) + " "+
        				adapter.getlistitem(position,SECOND_COLUMN) + " "+ adapter.getlistitem(position,THIRD_COLUMN)
        				
        				, Toast.LENGTH_SHORT).show();
        		
        		
        		Intent intent = new Intent();
				intent.setClass(Newcar.this,productdetails.class);
				Bundle bundle =new Bundle();
				
				bundle.putString("code",adapter.getlistitem(position, CODE_COLUMN)+"");
		        bundle.putString("brand",adapter.getlistitem(position, FIRST_COLUMN)+"");
		        bundle.putString("model", adapter.getlistitem(position,SECOND_COLUMN)+"");
		        bundle.putString("year", adapter.getlistitem(position,THIRD_COLUMN)+"");
		        bundle.putString("price", adapter.getlistitem(position,FOURTH_COLUMN)+"");
		        bundle.putString("photo",mStrings[position]);
		        bundle.putString("dealer",adapter.getlistitem(position,FIFTH_COLUMN)+"");
		        bundle.putString("cartype", "nc");
	        
		        intent.putExtras(bundle);
				startActivity(intent);
        		    		
    		}
    	});
        

        
         brandspinner = (Spinner)findViewById(R.id.brandspinner);
         modelspinner = (Spinner)findViewById(R.id.modelspinner);
         yearspinner = (Spinner)findViewById(R.id.yearspinner);
         pricespinner = (Spinner)findViewById(R.id.pricespinner);
         
         dummytext = (TextView)findViewById(R.id.cardummy);      
         textSize = (int)(dummytext.getTextSize()*0.6);      
         
        
         
        
         String []year = {"©Ò¦³¦~¥÷" , "2013","2012","2011","2010","2009","2008-2004","2003-1999","1998 ©Î ¥H«e"};
         String []price = {"©Ò¦³»ù®æ","2¸U¥H¤U","2¸U-4¸U","4¸U-6¸U","6¸U-8¸U","8¸U-10¸U","10¸U©Î¥H¤W"};
        // CustomAdapter adapter = new CustomAdapter(view.getContext(),R.layout.spinner_item,model);
         
         brand_adapter = new ArrayAdapter(App.getContext(),
        		 android.R.layout.simple_spinner_item, brand);
          model_adapter = new ArrayAdapter(App.getContext(),
        		 android.R.layout.simple_spinner_item,model);
          year_adapter = new ArrayAdapter(App.getContext(),
        		 android.R.layout.simple_spinner_item,year);
          price_adapter = new ArrayAdapter(App.getContext(),
        		 android.R.layout.simple_spinner_item,price);

        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         //model_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

         brandspinner.setPrompt("¿ï¾ÜµP¤l");
         modelspinner.setPrompt("¿ï¾Ü«¬¸¹");
         yearspinner.setPrompt("¿ï¾Ü¦~¥÷");
         pricespinner.setPrompt("¿ï¾Ü»ù®æ");
         
         NothingSelectedSpinnerAdapter brand_adapter2= new NothingSelectedSpinnerAdapter(brand_adapter,
        		 R.layout.brand,App.getContext(),(int)(dummytext.getTextSize()*0.63),"µP¤l");
         NothingSelectedSpinnerAdapter model_adapter2= new NothingSelectedSpinnerAdapter(model_adapter,
        		 R.layout.brand,App.getContext(),(int)(dummytext.getTextSize()*0.63),"«¬¸¹");
         NothingSelectedSpinnerAdapter year_adapter2= new NothingSelectedSpinnerAdapter(year_adapter,
        		 R.layout.brand,App.getContext(),(int)(dummytext.getTextSize()*0.63),"¦~¥÷");
         NothingSelectedSpinnerAdapter price_adapter2= new NothingSelectedSpinnerAdapter(price_adapter,
        		 R.layout.brand,App.getContext(),(int)(dummytext.getTextSize()*0.63),"»ù®æ");

         
         
         brandspinner.setAdapter(brand_adapter2);
         brandspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {   
            	     
                 if (position != 0){
                	 
                	 if  (brand_adapter.getItem(position-1).toString().equals("Audi")){
                		 changeadapterdata(modelspinner,Audimodel);               	 
                	 }
                	 else if  (brand_adapter.getItem(position-1).toString().equals("Acura")){
                		 changeadapterdata(modelspinner,Acuramodel);               	 
                	 }
                	 
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
                	 else{
            		 changeadapterdata(modelspinner,model);
                	 
                 }
                 
            	 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parentView) {
                 // your code here             
        		 changeadapterdata(modelspinner,model);
             }
         });
         
         modelspinner.setAdapter(model_adapter2);
         yearspinner.setAdapter(year_adapter2);
         pricespinner.setAdapter(price_adapter2);
         
         setHeight(brandspinner,2,pricespinner);
         
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
             
         Tab tab = mActionBar.newTab().setTabListener(tabListener);
         tab.setCustomView(common.gettitletext(t,"·s¨®¸ê®Æ",textSize));
         mActionBar.addTab(tab);
    }
    
    
    public void changeadapterdata(Spinner s,String v []){
    	
    	model_adapter = new ArrayAdapter(App.getContext(),
       		 android.R.layout.simple_spinner_item,v);
    	model_adapter2 = new NothingSelectedSpinnerAdapter(model_adapter,
       		 R.layout.brand,App.getContext(),(int)(dummytext.getTextSize()*0.63),"«¬¸¹");
        s.setAdapter(model_adapter2);
    	
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
    	
    	Toast.makeText(App.getContext(),"§A¤w¸g¦b¦¹­¶",Toast.LENGTH_SHORT).show();
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
    	common.setcontext(Newcar.this);
    	common.showalert(); 
        break;
        
    }
		
    default:
        return super.onOptionsItemSelected(item);
    }
    
    return true;
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
	
    public void clearcache(){    	
    	adapter.imageLoader.clearCache();
        adapter.notifyDataSetChanged();    	
    }
	
	public void settab (Tab tab){
		this.tab = tab;
		
	}
	
    public void settextproperty (TextView textview){
    	
    	textview.setTextSize(textSize);
    	textview.setTextColor(Color.WHITE);
    	
    	
    }
    
    private String[] mStrings={
    		"http://www.autotribute.com/wp-content/uploads/2012/05/Alfa-Romeo-12C-GTS-Concept-front-end.jpg",
    		"http://clefpalette.files.wordpress.com/2012/11/lamborghini_gallardo-gallery.jpg",
            "http://cdn.caradvice.com.au/wp-content/uploads/2008/10/aus2008100782343_hir.jpg",
            "http://mazdago.com/wp-content/uploads/2013/03/2013-Mazda-RX-9-Photo1.jpg"};
    
    private void populateList(int value) {
		
    	
    	String brand_column[] ={"Alfa Romeo","Mitsubishi","Porsche","Ford"};
    	String model_column[] = {"159","Triton","Boxter","C-MAX"};
    	String year_column[] = {"86","13","13","08"};
    	String price_column[] = {"300K", "600K","800K","1.3M"};
    	String dealer_column[] = {"Swire Motors","Universal Cars Limited","Jebsen Motors Limited","Ford Hong Kong"};
    	String code_column[] = {"nca1", "nca2","nca3","nca4"};
        
    	for (int i = 0 ; i<price_column.length ; i++)
    		price_column[i] = "$" +price_column[i];
    	
    	arraylist = new ArrayList<HashMap<String,String>>();
    	HashMap<String,String> temp;
    	
    	for (int i = 0 ; i<value ; i++)
    	{
    	temp = new HashMap<String,String>();
    	temp.put(CODE_COLUMN, code_column[i]);
	    temp.put(FIRST_COLUMN,brand_column[i]);
		temp.put(SECOND_COLUMN, model_column[i]);
		temp.put(THIRD_COLUMN, year_column[i]);
		temp.put(FOURTH_COLUMN, price_column[i]);
		temp.put(FIFTH_COLUMN, dealer_column[i]);
		arraylist.add(temp);
    	}
    }
 
   
    
 
}
