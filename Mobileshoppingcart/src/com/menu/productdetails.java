package com.menu;

import static com.fedorvlasov.lazylist.Constant.CODE_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FIRST_COLUMN;
import static com.fedorvlasov.lazylist.Constant.FOURTH_COLUMN;
import static com.fedorvlasov.lazylist.Constant.ORDERTYPE;
import static com.fedorvlasov.lazylist.Constant.PRODUCTTYPE;
import static com.fedorvlasov.lazylist.Constant.SECOND_COLUMN;
import static com.fedorvlasov.lazylist.Constant.THIRD_COLUMN;

import java.io.File;

import org.panel.App;
import org.panel.R;
import org.panel.R.id;
import org.panel.R.layout;


import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
 import com.fedorvlasov.lazylist.ImageLoader;
import com.sales.Common;
import com.sales.MainActivity;
import com.viewpager.ViewPagerActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;

public class productdetails extends SherlockFragmentActivity  {
	
	
	private int monW,monH;		 
	String[] mStrings;
	ImageView img[] = new ImageView[5];
	int imgnumber = 0;
	Bitmap bitmap,bitmap2;
	ImageView tempimg;
	LinearLayout myGallery;
	int sn = 0;
    int car_photoindex[],new_carphotoindex[],motor_photoindex[];
    String temphotoc [],temphotom[],temphotonc[];
    String cartype;
    CheckBox c;
    int checked = 0;
    String pid,name,photono,descrp,price;
    String realdescp,realphotono;
    int photonumber;
 
    
    public void onCreate(Bundle savedInstanceState) {
    	
       super.onCreate(savedInstanceState);
       setContentView(R.layout.productdetails);
       
       
       ActionBar mActionBar;
       mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
       mActionBar.setDisplayShowTitleEnabled(false);
       mActionBar.setDisplayShowHomeEnabled(false);
       
       
       Bundle bundle = this.getIntent().getExtras();
       checked = bundle.getInt("checkbox");
       cartype = bundle.getString("cartype");
       pid = bundle.getString("code");  //pid
       name = bundle.getString("brand");
       photono = bundle.getString("model");
       descrp = bundle.getString("year");
       price = bundle.getString("price");
       String photo = bundle.getString("photo");
       realphotono = bundle.getString("photono");
       realdescp = bundle.getString("descrp");

       photonumber = Integer.parseInt(realphotono);
        //String dealer = bundle.getString("dealer");
       
    
       //TextView producttext = (TextView)findViewById(R.id.);
       //producttext.setText(code+" "+brand+" "+model+" "+year+" "+price);
    
       myGallery = (LinearLayout)findViewById(R.id.mygallery);
       
       String ExternalStorageDirectoryPath = Environment
       		.getExternalStorageDirectory()
       		.getAbsolutePath();
       
       
       monW = getWindowManager().getDefaultDisplay().getWidth();
       monH  = getWindowManager().getDefaultDisplay().getHeight();
      // Log.i("W:"+monW+"H:"+ monH,"size");
       
       TextView dummytext = (TextView)findViewById(R.id.productdummy);      
       int textSize = (int)(dummytext.getTextSize()); 
       int textSize2 = (int)(textSize*0.6);
       
        TextView brandtext = (TextView)findViewById(R.id.brand);
       TextView modeltext = (TextView)findViewById(R.id.model);
        TextView yeartext = (TextView)findViewById(R.id.year);
 
       //c.setTextSize(textSize2*3);
        
       brandtext.setText("名稱: "+name);
       modeltext.setText("價錢: "+ price);
       yeartext.setText("描述: "+realdescp);
        
       
  
       if (cartype.equals("nc")){
           c = (CheckBox)findViewById(R.id.checkneed);      
           c.setVisibility(View.GONE);
        }   
       
       if (cartype.equals("c")||cartype.equals("m")){
         c = (CheckBox)findViewById(R.id.checkneed);      
         
         if (checked == 1)
    	   c.setChecked(true);
         else
    	   c.setChecked(false);
       
       c.setOnClickListener(onclicklistener);}
       
       if (cartype.equals("c")){
     	   
    	   
       }
       
       
       
         settextproperty(brandtext,textSize2); 
       settextproperty(modeltext,textSize2);
        settextproperty(yeartext,textSize2);
       

     

       //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(monW/6,monH/6);
       //c.setLayoutParams(params);        
       
       String targetPath = ExternalStorageDirectoryPath + "/Pictures/PIP_CAMERA";
       Log.i("path"+targetPath,"targetPath");
       
     //  Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
       File targetDirector = new File(targetPath);
       	      
        mStrings = new String [photonumber];
         
        for (int i = 0 ; i<photonumber ; i++){
        	int j = i+1;
        	mStrings[i] =  "http://54.254.179.218/image/"+pid+"_"+j +".jpg"; 
        }
        
        /*
        temphotonc = new String []{
        photo,"http://54.254.179.218/image/1_2.jpg",
        "http://54.254.179.218/image/1_3.jpg",       
        };
        
        
        temphotom = new String []{
                photo,"http://54.254.179.218/image/1_2.jpg",
                "http://54.254.179.218/image/1_3.jpg",       
                };
*/
        		
   
        
        
       

       
        car_photoindex = new int [photonumber ];
        //new_carphotoindex = new int [temphotonc.length];
        //motor_photoindex = new int [temphotom.length];
       
        
        
     //  File[] files = targetDirector.listFiles();
       if (cartype.equals("c"))
    	for (int i = 0 ; i < mStrings.length ; i++){
    		car_photoindex[i] = i; 	
       	  myGallery.addView(insertPhoto(mStrings[i],img[i],car_photoindex[i]));}
       
       /*
       if (cartype.equals("nc"))
    	   for (int i = 0 ; i < temphotonc.length ; i++){
    		   new_carphotoindex[i] = i;   	   
    		   myGallery.addView(insertPhoto(temphotonc[i],img[i],new_carphotoindex[i]));}
       
       
       if (cartype.equals("m"))
    	   for (int i = 0 ; i < temphotom.length ; i++){   		   
    	       motor_photoindex[i] = i;
    		   myGallery.addView(insertPhoto(temphotom[i],img[i],motor_photoindex[i]));}
       
       */
       //LinearLayout productpage= (LinearLayout)findViewById(R.id.productpage);
       //productpage.setBackgroundColor(Color.BLACK);
    
       
       
       HorizontalScrollView scrollview = (HorizontalScrollView)findViewById(R.id.scrollview);

       
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
           
       
       /*String title = brand+" "+model+" "+year; 
        Tab tab = mActionBar.newTab().setTabListener(tabListener);
        tab.setCustomView(common.gettitletext(t,title,(int)(textSize*0.7)));
        mActionBar.addTab(tab);*/
       
   }
   
    
    OnClickListener onclicklistener = new OnClickListener(){
    	
	     public void onClick(View v) {

   		     Context context = App.getContext();
   		     SharedPreferences preferences = context.getSharedPreferences("info", context.MODE_PRIVATE);
             int selectnumber = Integer.parseInt(preferences.getString("selectnumber","0"));
        	 SharedPreferences.Editor editor = preferences.edit();

   		       if (c.isChecked()){
	    		    selectnumber++;
	        		editor.putString("selectnumber", selectnumber+"");	    	        		
	        		editor.putString("productitem" + selectnumber,pid);		        		
	        		editor.putString("producttype"+selectnumber,cartype);
	        		editor.putString("ordertype"+selectnumber,"b");	        		
		        	editor.putString("productdescrp" + selectnumber, name+"," + photono +"," 
		        				+ "123" + "," +  price);
	        		editor.putString("quantity"+selectnumber,"1");

		        	  
	        		
	    	 }
	    	   else{
			    	  int tempselectnumber = selectnumber - 1;
		    	      if (tempselectnumber > 0){
				    	    for (int i = 1 ; i<=selectnumber ; i++){

					    	      String productcode = preferences.getString("productitem" + i,"0");					    	      
				    	    	  
					        	  					      
					        	  if (pid.equals(productcode)){
						        					    	    	  	    	  
				    	    	      editor.remove("productitem" + i);
					    	    	  editor.remove("productdescrp" + i);
					    	    	  editor.remove("ordertype"+i);
					    	    	  editor.remove("producttype"+i);
					    	    	  editor.remove("quantity"+i);
						        	  editor.commit();
				                  
						        	  int deleteindex = i;
						        	  int lastindex = i;
						        	  int nextindex = deleteindex;
						        	  
					    	    	  for (int j=i+1;!(preferences.getString("productitem"+j,"0").equals("0"));j++)
					    	    		  lastindex = j;					    	    		  
					    	          
					                  
					    	          for (int currentindex = deleteindex ; nextindex<lastindex ; currentindex++){
					    	        	      
					    	        	      nextindex = currentindex+1;
					    	        							    	    	  
                                           editor.putString("productitem"+currentindex, preferences.getString("productitem"+nextindex, "0"));
                                           editor.putString("productdescrp"+currentindex, preferences.getString("productdescrp"+nextindex,"0" ));
                                           editor.putString("ordertype"+currentindex,preferences.getString("ordertype"+nextindex, "0"));
                                           editor.putString("producttype"+currentindex,preferences.getString("producttype"+nextindex,"0"));
                                           editor.putString("quantity"+currentindex,preferences.getString("quantity"+nextindex,"0"));

					    	          }

					    	    	  Log.i("deleteindex "+deleteindex,"m");

					    	           if (lastindex!=deleteindex){
					    	        	   
							    	    	  Log.i("did","m");


							    	    	  editor.remove("productitem"+lastindex);
							    	    	  editor.remove("productdescrp"+lastindex);
							    	    	  editor.remove("ordertype"+lastindex);
							    	    	  editor.remove("producttype"+lastindex);
							    	    	  editor.remove("quantity"+lastindex);
							    	    	  
							    	    	 
					    	           }
					    	            
					    	           
					    	              /* check cache*/
						                  for (int e = 1 ; e <=lastindex; e++){
						                      
						        	    	  String mydescrp = preferences.getString("productdescrp"+e,"0" );
						        	    	  String myorder = preferences.getString("ordertype"+e, "0");
						        	    	  String mytype = preferences.getString("producttype"+e,"0"); 
						        	    	  String mycode = preferences.getString("productitem"+e,"0");
						    	        	  Log.i("after remove2: " + e+" "+mycode +mydescrp +" " + myorder +" " +mytype,"m");

						                      }
					    	        

					    	    	  
					    	      
					    	    }
				    	    }

		        		editor.putString("selectnumber", tempselectnumber+"");			    	  
		        	    selectnumber -- ;
	             } 
	    	   }//end else
               editor.commit();
    	
	     }
    };
    
    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) 
            bgDrawable.draw(canvas);
        else 
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
    
    
   public void settextproperty (TextView v,int textsize){
   	
   	v.setTextSize(textsize);
   	v.setTextColor(Color.BLACK);
   	
   }
   

   View insertPhoto(String path,ImageView imageView,int number){
   	
   	int w,h;
   	
   	w = (int)(monW/2); h = (int)(monH/3);
   	//w= 400; h= 300;
   	
   	Bitmap bm;
   	
   	LinearLayout layout = new LinearLayout(getApplicationContext());
   	layout.setLayoutParams(new LayoutParams(w,h));
   	//layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
   	layout.setGravity(Gravity.CENTER);
   	
   	imageView = new ImageView(getApplicationContext());
   	imageView.setLayoutParams(new LayoutParams(w,h));
       //imageView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
   	imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
   	//Log.i("ImageView id"+imageView.getId(),"im");
   	/*  */
   	
   	/*
   	URL myfileurl =null;
       try
       {
           myfileurl= new URL(path);

       }
       catch (MalformedURLException e)
       {

           e.printStackTrace();
       }

   	
   	try
       {
           HttpURLConnection conn= (HttpURLConnection)myfileurl.openConnection();
           conn.setDoInput(true);
           conn.connect();
           int length = conn.getContentLength();
           int[] bitmapData =new int[length];
           byte[] bitmapData2 =new byte[length];
           InputStream is = conn.getInputStream();
           BitmapFactory.Options options = new BitmapFactory.Options();

           bm = BitmapFactory.decodeStream(is,null,options);

           imageView.setImageBitmap(bm);

           //dialog.dismiss();
           } 
       catch(IOException e)
       {
           // TODO Auto-generated catch block
           e.printStackTrace();
//         Toast.makeText(PhotoRating.this, "Connection Problem. Try Again.", Toast.LENGTH_SHORT).show();
       }
   	
   	*/
   	
   	
   	
   	
   	/*
   	imageView.setImageBitmap(bm);    	
       ImageView image=(ImageView)findViewById(R.id.image);
   	
   	DownloadImageTask a = new DownloadImageTask(imageView);
   	imageView.setImageBitmap(a.doInBackground(path));//
   	
   	a.execute(path);*/
   	
       ImageLoader imageLoader=new ImageLoader(getApplicationContext());
       imageLoader.DisplayImage(path, imageView);
       //img[imgnumber] = imageView;
       //Log.i("imagenumber"+imgnumber,"img");
       
       final int number2 = number;
       imageView.setOnClickListener(new OnClickListener() {
           public void onClick(View v)
           {
            //   Toast.makeText(App.getContext(), "i"+number2, Toast.LENGTH_SHORT).show();
               
               Bundle b=new Bundle();
               b.putInt("selectpage", number2);
               
               if (cartype.equals("c"))
                 b.putStringArray("url", mStrings);
               
              // if (cartype.equals("nc"))
                //   b.putStringArray("url", temphotonc);
               
               //if (cartype.equals("m"))
                 //  b.putStringArray("url", temphotom);

               Intent intent=new Intent();
               intent.putExtras(b);
               intent.setClass(productdetails.this,ViewPagerActivity.class);
       		   startActivity(intent);
       		   //productdetails.this.finish();
           } 

       });
       
       //data[position] url of image
       
       //imageView.setImageBitmap(imageLoader.DisplayImage(path, imageView));*/
   	
   	layout.addView(imageView);
   	//imgnumber++;
   	return layout;
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
    	Intent intent = new Intent();
    	intent.setClass(this,SellerpageActivity.class);
		startActivity(intent);
		this.finish();
		break;
    }
    
    case R.id.customermenu:
    {
    	
    	Intent intent = new Intent();
    	intent.setClass(this,productdetails.class);
		startActivity(intent);
		this.finish();
		break;
    }
    
    case R.id.carmenu:
    {
    	Intent intent = new Intent();
    	intent.setClass(this,MainActivity.class);
		startActivity(intent);
		this.finish();
		break;
    }
    
    case R.id.exitmenu:
    {

    	Common common = new Common();
    	common.setcontext(productdetails.this);
    	common.showalert(); 
        break;
        
    }
		
    default:
        return super.onOptionsItemSelected(item);
    }
    
    return true;
}    

}
