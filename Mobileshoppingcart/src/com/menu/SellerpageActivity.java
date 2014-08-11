package com.menu;

import org.panel.App;
import org.panel.NothingSelectedSpinnerAdapter;
import org.panel.R;
import org.panel.R.layout;
import org.panel.R.menu;

 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

 
 
 
import android.R.color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class SellerpageActivity extends SherlockFragmentActivity {

	Uri outputFileUri;
    ImageView showImg  = null;
    Bitmap uploadBitmap;
    String imageEncoded;
    Spinner sexspinner,catidspinner,categoryspinner; 
    String sex = "";
    int catid = 0,category = 0; 
    int textSize;
    EditText name,price,quantity,descrp;
    String u_price = "",u_name = "",u_descrp = "",u_quantity = "";
    
    
    String catidtype [] = {"類別","衣服","褲/裙","鞋或其他"};

    String sextype []= {"男/女裝","男","女"};
    
    String categorytype0 [] = {"類型"};
    
    String categorytype1 [] = {"類型","冷衫","T-shirt","外套", "其他"};
    
    String categorytype21 [] = {"類型","休閒褲","運動褲","修身褲", "其他"};
    
    String categorytype22 [] = {"類型","短裙","熱褲","晚裝","牛仔褲","其他"};

    String categorytype31 [] = {"類型","皮靴","波鞋","帆布鞋" ,"涼鞋","其他"};
    String categorytype32 [] = {"類型","皮靴","高跟鞋","涼鞋", "其他" };

   
 
    
    ArrayAdapter sex_adapter,catid_adapter,category_adapter;
    NothingSelectedSpinnerAdapter category_adapter2 ;
    TextView imageDetails;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sellerpage);
		
	    imageDetails = (TextView) findViewById(R.id.sellerdummy);
		Button photo = (Button)findViewById(R.id.capturephoto);
		Button upload = (Button)findViewById(R.id.upload);
		Button back  = (Button)findViewById(R.id.backbutton);

		showImg = (ImageView) findViewById(R.id.showImg);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        
        name = (EditText)findViewById(R.id.edittextname); 
        price = (EditText)findViewById(R.id.edittextprice); 
        quantity = (EditText)findViewById(R.id.edittextquantity); 
        descrp = (EditText)findViewById(R.id.edittextdescrp); 

        
        
        sexspinner = (Spinner)findViewById(R.id.sexspinner);
        catidspinner = (Spinner)findViewById(R.id.catidspinner);
        categoryspinner = (Spinner)findViewById(R.id.categoryspinner);
          
        textSize = (int)(imageDetails.getTextSize()*0.6);      

        catid_adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, catidtype);

 
        sex_adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, sextype);
 
        category_adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, categorytype0);

        
        catidspinner.setPrompt("類別");
        sexspinner.setPrompt("男/女裝");
        categoryspinner.setPrompt("類型");
        
        NothingSelectedSpinnerAdapter catid_adapter2 = new NothingSelectedSpinnerAdapter(catid_adapter,
          		 R.layout.brand,this,(int)(imageDetails.getTextSize()*0.7),"類別");
        
        NothingSelectedSpinnerAdapter sex_adapter2 =  new NothingSelectedSpinnerAdapter(sex_adapter,
       		 R.layout.brand,this,(int)(imageDetails.getTextSize()*0.7),"男/女裝");
        
        
        category_adapter2 =  new NothingSelectedSpinnerAdapter(category_adapter,
       		 R.layout.brand,this,(int)(imageDetails.getTextSize()*0.7),"類型");
        
       
        
        catidspinner.setAdapter(catid_adapter2);
        sexspinner.setAdapter(sex_adapter2);
        categoryspinner.setAdapter(category_adapter2);

    	photo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            	openImageIntent();
            	}
            });
    	

    	back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	SellerpageActivity.this.finish(); 
            	}
            });
    	
    	
    	
		
		upload.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            	//openImageIntent();
            	
            	if (showImg.getDrawable()  == null || catid == 0  
            		|| category == 0 || sex.equals("") 
            		|| name.getText().length() <=0 || price.getText().length() <=0
            		|| quantity.getText().length() <=0 || descrp.getText().length() <=0) 
            	 {
				  //new FetchTask().execute();
                	Toast.makeText(SellerpageActivity.this, "請輸入資料和上傳相片", Toast.LENGTH_SHORT).show();}
            	else{
            		//new FetchTask().execute();
            		u_price =  price.getText().toString(); u_name = name.getText().toString();
            		u_quantity = quantity.getText().toString(); u_descrp = descrp.getText().toString();
            		
            	 /*
            		try {
            		  u_descrp = URLEncoder.encode(u_descrp, "UTF-8");
            	      u_name = URLEncoder.encode(u_name, "UTF-8");}
            		catch( Exception e){
            			
            		}*/
            		 
            		
            		String temp = catid + " "+ category +" "+ sex +" "+u_price +" "+ u_name +" "+ u_quantity +" "+ u_descrp;
            		
                    Toast.makeText(SellerpageActivity.this, temp, Toast.LENGTH_SHORT).show();
                	
                	Context c = App.getContext();
                    SharedPreferences preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);
                    int usertaken = preferences.getInt("usertaken",0);
                    
                     
                    if (usertaken == 0)
                	  showdialog();
                	   
                    else
                      new FetchTask().execute();
                	 

            		
            	}
	
            	}
            });
		
        
        
        
        categoryspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {   
           	     
              
            	
            	if (position != 0){
               	 
            	 
            		 if  (category_adapter.getItem(position-1).toString().equals("冷衫")){
               		  category = 1;
              		  //Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

                	 }
           
               	 
               	 
               	 else if  (category_adapter.getItem(position-1).toString().equals("T-shirt")){ 
               		 
             		  category = 2;
              		 // Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

              	 } 
            		 
             	 else if  (category_adapter.getItem(position-1).toString().equals("外套")){ 
               		 
            		  category = 3;
              		  //Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

             	 } 
             	 else if  (category_adapter.getItem(position-1).toString().equals("休閒褲")){ 
               		 
            		  category = 4;
              		  //Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

             	 } 
             	 else if  (category_adapter.getItem(position-1).toString().equals("運動褲")){ 
               		 
            		  category = 5;
              		  //Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

             	 } 
             	 else if  (category_adapter.getItem(position-1).toString().equals("修身褲")){ 
               		 
            		  category = 6;
              		 // Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

             	 } 
             	 else if  (category_adapter.getItem(position-1).toString().equals("短裙")){ 
               		 
            		  category = 7;
              		 // Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

             	 } 
             	 else if  (category_adapter.getItem(position-1).toString().equals("熱褲")){ 
               		 
            		  category = 8;
              		  //Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

             	 } 
            	 else if  (category_adapter.getItem(position-1).toString().equals("晚裝")){ 
               		 
           		  category = 9;
          		  // Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

            	 } 
            	 else if  (category_adapter.getItem(position-1).toString().equals("牛仔褲")){ 
               		 
           		  category = 10;
          		 // Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

            	 } 
            	 else if  (category_adapter.getItem(position-1).toString().equals("皮靴")){ 
               		 
           		  category = 11;
          		 // Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

            	 } 
            	 else if  (category_adapter.getItem(position-1).toString().equals("波鞋")){ 
               		 
           		  category = 12;
          		 // Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

            	 } 
            	 else if  (category_adapter.getItem(position-1).toString().equals("帆布鞋")){ 
               		 
           		  category = 13;
          		 // Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

            	 } 
            	 else if  (category_adapter.getItem(position-1).toString().equals("高跟鞋")){ 
               		 
           		  category = 14;
          		 // Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

            	 } 
            	 else if  (category_adapter.getItem(position-1).toString().equals("涼鞋")){ 
               		 
           		  category = 15;
          		 // Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

            	 } 
            	 else if  (category_adapter.getItem(position-1).toString().equals("其他")){ 
               		 
           		  category = 16;
          		 // Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();

            	 } 

               		// changeadapterdata(modelspinner,Acuramodel);               	 
               
             	 else{
             		 
              	     category = 0;
             		// Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();
             	 }
             		 
            		 //Toast.makeText(SellerpageActivity.this, "sex: "+sex+" catid"+catid +"category: " + category , Toast.LENGTH_SHORT).show();
             
           	 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
         	     category = 0;
       		 //Toast.makeText(SellerpageActivity.this, "catid"+catid, Toast.LENGTH_SHORT).show();

      
            }
        });
        
        
        
        
        sexspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {   
           	     
              
            	
            	if (position != 0){
               	 
               	 if  (sex_adapter.getItem(position-1).toString().equals("男")){
               		   sex = "M";
               		 
                    if (catid == 1)
               			changeadapterdata(categoryspinner,categorytype1);
               		   
               		if (catid == 2)
               			changeadapterdata(categoryspinner,categorytype21);
               		
               		if (catid == 3)
               			changeadapterdata(categoryspinner,categorytype31);
               		   
          
               		   
               		 //Toast.makeText(SellerpageActivity.this, "sex:"+sex+"catid"+catid, Toast.LENGTH_SHORT).show();
                	 }
               	 else if  (sex_adapter.getItem(position-1).toString().equals("女")){
             		   sex = "F";
             		   
             		   if (catid == 1)
                  			changeadapterdata(categoryspinner,categorytype1);
                  		   
                  		if (catid == 2)
                  			changeadapterdata(categoryspinner,categorytype22);
                  		
                  		if (catid == 3)
                  			changeadapterdata(categoryspinner,categorytype32);
                  		   
               		// Toast.makeText(SellerpageActivity.this, "sex:"+sex+"catid"+catid, Toast.LENGTH_SHORT).show();

               		// changeadapterdata(modelspinner,Acuramodel);               	 
               	 }
               	 
             	 else{
             		 
             		 sex = "";               		 
             		 //Toast.makeText(SellerpageActivity.this, "sex:"+sex+"catid"+catid, Toast.LENGTH_SHORT).show();
             	 }
             		 
               	 
             
           	 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
       		 sex = "";
       		 //Toast.makeText(SellerpageActivity.this, "catid"+catid, Toast.LENGTH_SHORT).show();

      
            }
        });
      

        
        
        
        catidspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {   
           	     
              
            	
            	if (position != 0){
               	 
               	 if  (catid_adapter.getItem(position-1).toString().equals("衣服")){
               		 catid = 1;
               		 
               		 if (sex.equals("M"))
                	   changeadapterdata(categoryspinner,categorytype1);
               		 
               		 if (sex.equals("F"))
         			  changeadapterdata(categoryspinner,categorytype1);
                		   
               		 
             		// Toast.makeText(SellerpageActivity.this, "sex:"+sex+"catid"+catid, Toast.LENGTH_SHORT).show();
               		// changeadapterdata(modelspinner,Audimodel);               	 
               	 }
               	 else if  (catid_adapter.getItem(position-1).toString().equals("褲/裙")){
               		 catid = 2;
             		 Toast.makeText(SellerpageActivity.this, "sex:"+sex+"catid"+catid, Toast.LENGTH_SHORT).show();

               		 if (sex.equals("M"))
                  	   changeadapterdata(categoryspinner,categorytype21);
                 		 
               		 if (sex.equals("F"))
           			  changeadapterdata(categoryspinner,categorytype22);
               		// changeadapterdata(modelspinner,Acuramodel);               	 
               	 }
               	 
             	 else if  (catid_adapter.getItem(position-1).toString().equals("鞋或其他")){
               		 catid = 3;
             		 //Toast.makeText(SellerpageActivity.this, "sex:"+sex+"catid"+catid, Toast.LENGTH_SHORT).show();

               		 if (sex.equals("M"))
                    	   changeadapterdata(categoryspinner,categorytype31);
                   		 
               		 if (sex.equals("F"))
             			  changeadapterdata(categoryspinner,categorytype32);
               		// changeadapterdata(modelspinner,Acuramodel);               	 
               	 }
             	 else{
             		 
             		 catid = 0;
             		 //Toast.makeText(SellerpageActivity.this, "sex:"+sex+"catid"+catid, Toast.LENGTH_SHORT).show();
             	 }
             		 
               	 
             
           	 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
       		 catid = 0;
     		 //Toast.makeText(SellerpageActivity.this, "sex:"+sex+"catid"+catid, Toast.LENGTH_SHORT).show();

                // your code here             
       		// changeadapterdata(modelspinner,year1);


  
            }
        });
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	 
		
 		
	} 
 
	 
	 public void changeadapterdata(Spinner s,String v []){
	    	
		  category_adapter = new ArrayAdapter(this,
	       		 android.R.layout.simple_spinner_item,v);
		  category_adapter2 = new NothingSelectedSpinnerAdapter(category_adapter,
	       		 R.layout.brand,this,(int)(imageDetails.getTextSize()*0.7),"類型");
	        s.setAdapter(category_adapter2);
	    	
	    }
	
	
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
		             BitmapFactory.Options o2 = new BitmapFactory.Options();
                     o2.inSampleSize=2;
                     
		             if(isCamera)
		             {
		                 selectedImageUri = outputFileUri;
		                 try { 
			               //  Bitmap bitmap  = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));
			                 Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri), null, o2);

			                 
			                // Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri), outPadding, o2) 
			                 
			                 Bitmap newBitmap ;
	                         
		                        if (bitmap != null) {
		                             
		                            /********* Creates a new bitmap, scaled from an existing bitmap. ***********/
		 /*
		                        	int orientation = getOrientation(selectedImageUri);
		                        	
		                        	Matrix matrix = new Matrix();
		                        	//set image rotation value to 90 degrees in matrix.
		                        	if (orientation == 1)
		                        	  matrix.postRotate(180);
		                        	 
		                         
		                        	//supply the original width and height, if you don't want to change the height and width of bitmap.
		                            newBitmap = Bitmap.createBitmap(bitmap, 0, 0, 300, 300, matrix, true);
		                        	*/
		                            //newBitmap  = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
		                             
		                            //bitmap .recycle();
		                             
		                            //newBitmap  = Bitmap.createScaledBitmap(bitmap , 300, 300, true);

		                        	int orientation =  getss(selectedImageUri.getPath());
		                        	
		                        	Matrix matrix = new Matrix();

		                        	
		                        	Toast.makeText(this, "orientation:"+orientation, Toast.LENGTH_SHORT).show();
		                        	if (orientation == 0) 
		                        	  matrix.postRotate(360);
		                        	else
			                        	  matrix.postRotate(90);


		                         
		                        	Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,300,300, true);

		                        	uploadBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
		                        	
		                         	 
		                             encodeTobase64(uploadBitmap);

		                        	  //Log.i( imageEncoded,"imageEncoded");

 		                        	
		                            if (  bitmap != null) {
		                                 
		                                //mBitmap = newBitmap;
		                            	showImg.setImageBitmap( uploadBitmap );
		                            }
		                        }
			                 } 
		                        
		                        catch(IOException e){
		                        	
		                        } 
		                 
		             }
		             else
		             {
		                 selectedImageUri = data == null ? null : data.getData();
		                 
		                 try { 
		                	 
		                	 
		                        
			                 Bitmap bitmap2=BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri), null, o2);
		                     Bitmap newBitmap2;
                         
		                 
		                 
	                        if (bitmap2 != null) {
	                             
	                            /********* Creates a new bitmap, scaled from an existing bitmap. ***********/
	 
	                            newBitmap2 = Bitmap.createScaledBitmap(bitmap2, 300, 300, true);
	                             
	                            //bitmap2.recycle();
	                            
	                            
	                            int orientation =  getss(selectedImageUri.getPath());
	                        	
	                        	Matrix matrix = new Matrix();

	                        	
	                        	Toast.makeText(this, "orientation:"+orientation, Toast.LENGTH_SHORT).show();
	                        	if (orientation == 0) 
	                        	  matrix.postRotate(360);
	                        	else
		                        	  matrix.postRotate(90);


	                         
	                        	Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap2,300,300, true);

	                        	uploadBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
	                        	
	                             encodeTobase64(uploadBitmap);
	                              
	                        	Log.i( imageEncoded,"imageEncoded");
	                        	
	                            if (newBitmap2 != null) {
	                            	showImg.setImageBitmap(uploadBitmap);

	                                //mBitmap = newBitmap;
	                            }
	                        }
		                 } 
	                        
	                        catch(IOException e){
	                        	
	                        } 
		             }
		             
		         }
		     }
		 }
		 
		 
		 
		 private int getOrientation(Uri uri){
			    BitmapFactory.Options options = new BitmapFactory.Options();
			    options.inJustDecodeBounds = true;
			    int orientation = 0;
			    try{
			        String image = new File(uri.getPath()).getAbsolutePath();
			        BitmapFactory.decodeFile(image, options);
			        int imageHeight = options.outHeight;
			        int imageWidth = options.outWidth;
			        if (imageHeight > imageWidth){
			            orientation = 1;
			        }
			    }catch (Exception e){
			        //Do nothing
			    }
			    return orientation;
			}
		 
		 
		 
		 private int getss(String url){
			 
			 
			 BitmapFactory.Options bounds = new BitmapFactory.Options();
		        bounds.inJustDecodeBounds = true;
		        Bitmap bm = BitmapFactory.decodeFile(url, bounds);
                //Bitmap bitmap  = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));
		        int rotationAngle = 0;

		        //Bitmap bm = BitmapFactory.decodeFile(url, opts);
		        try{
		        ExifInterface exif = new ExifInterface(url);
		        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
		        
 		        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
 		        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 1;
		        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 2;
		        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 3;

		        }
		        catch (IOException e){}
	 
 
		        return rotationAngle;
		        
		 }
		 
		 
		 
		 
		 
		 
		 
		 
		 public class FetchTask extends AsyncTask<Void, Void, JSONArray> {
		        @Override
		        protected JSONArray doInBackground(Void... params) {
		            try {
		                HttpClient httpclient = new DefaultHttpClient();
		                HttpPost httppost = new HttpPost("http://54.254.179.218/insert.php");

		                
		                // Add your data
		                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		                
		                nameValuePairs.add(new BasicNameValuePair("name", u_name ));
		                nameValuePairs.add(new BasicNameValuePair("price", u_price ));
		                nameValuePairs.add(new BasicNameValuePair("descrp", u_descrp ));
  		                nameValuePairs.add(new BasicNameValuePair("quantity", u_quantity ));

		                nameValuePairs.add(new BasicNameValuePair("catid",Integer.toString(catid)));
		                nameValuePairs.add(new BasicNameValuePair("sex", sex ));

		                nameValuePairs.add(new BasicNameValuePair("category",Integer.toString(category)));
   		                
		                nameValuePairs.add(new BasicNameValuePair("photono", "1" ));





		                
	            		//String temp = catid + " "+ category +" "+ sex +" "+u_price +" "+ u_name +" "+ u_quantity +" "+ u_descrp;

		                
		                if (uploadBitmap !=null ){
		                	
		                	  

		                     
		                	 nameValuePairs.add(new BasicNameValuePair("photo",imageEncoded));
 		                	  Log.i("photostatus","s");
 		        		     Log.i("status", imageEncoded);

 		                	
		                }
		                /*nameValuePairs.add(new BasicNameValuePair("up",mup));
		                nameValuePairs.add(new BasicNameValuePair("low",mlow));
		                nameValuePairs.add(new BasicNameValuePair("pulse",mpluse));*/
		                
		              //  Toast.makeText(DeviceControlActivity.this, "cccc", Toast.LENGTH_LONG).show();
		                
		              //  nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
		                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));

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
		   	              
		   	              
		   	              
		   	       	//String brand[],mode[],year[],price[],code_column[],order_type[],testurl [];

		   	       	
		   	              
		   	           for(int i=0; i<numberList.length(); i++){
		   	            	  
	   	            	     
	   	                    Log.i(numberList.getJSONObject(i).getString("pid"),"status"); 
	   	                   
	   	                    
	   	                     
	      
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
		            	
		            	showdialog2();
		     
		            } else {
		            	
		            	Log.i("fail","status");

		                // error occured
		            }
		        }
		 }
		 
		 
		 public  String encodeTobase64(Bitmap image)
		 {
		     Bitmap immagex=image;
		     ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		     immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		     byte[] b = baos.toByteArray();
		      imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

		     Log.e("LOOK", imageEncoded);
		     return imageEncoded;
		     
		 }
		 
		 
		 //===============================================================================================================
		
		 
		 
		 public void showdialog2 (){
				
				
				AlertDialog.Builder alert = new AlertDialog.Builder(this);

				alert.setTitle("上傳成功");
			    alert.setMessage("你已經上傳了你的貨品");

				// Set an EditText view to get user input 
			 
				 
		        
		 		alert.setPositiveButton("確定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  
 				 
			         
				  //Toast.makeText(MainActivity.this, name+" "+age+ " "+isman+" "+phonenumber, Toast.LENGTH_LONG).show();
					  
				  // Do something with value!
					SellerpageActivity.this.finish();
				  }
				});

			 
				alert.show();	
				 
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
					  
					  Toast.makeText(SellerpageActivity.this , "請輸入個人資料", Toast.LENGTH_SHORT).show();
				  }
			 
				  else {
					  Context c = App.getContext();
			          SharedPreferences settings = getSharedPreferences ("info", c.MODE_PRIVATE);

		              SharedPreferences.Editor PE = settings.edit();
		              PE.putInt("usertaken", 1);
			          PE.putString("username", name);
			          PE.putString ("userphone",phonenumber);
			          PE.commit();
			          
			          new FetchTask().execute();
				  }
			         
				  //Toast.makeText(MainActivity.this, name+" "+age+ " "+isman+" "+phonenumber, Toast.LENGTH_LONG).show();
					  
				  // Do something with value!
				  }
				});

				alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();	
				 
			}
		 
 
		 
		     }
		  
		 

 
