package org.panel;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.menu.Myselection;
import com.sales.MainActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends SherlockFragmentActivity {

	
	ActionBar mActionBar;
    Activity a;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		 App.getInstance().addActivity(this);
	        App.setContext(this);
	        
	        a = this;
	        // Activate Navigation Mode Tabs
	        mActionBar = getSupportActionBar();
	        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        mActionBar.setDisplayShowTitleEnabled(false);
	        mActionBar.setDisplayShowHomeEnabled(false);
	        
	        Context c = App.getContext();
	        SharedPreferences settings = getSharedPreferences ("info", c.MODE_PRIVATE);
	        int register = settings.getInt("userregister", 0);
	        
	       // Toast.makeText(this, "register"+register, Toast.LENGTH_SHORT).show();
 	        
	        if (register == 0)
	        	showdialog();
	        
	        if (register == 1) // 1 = when logout
	        	showdialog2();
	        
	        if (register == 2 || register == 3 || register == 4  ){  // 2 = registered , 3 = cancel 
	        Intent intent = new Intent();
        	intent.setClass( Registration.this,MainActivity.class);
            startActivityForResult(intent, 1); } 
	        
	         
	}

 
 
	
public void showdialog (){
		
		
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
			  
			  Toast.makeText(Registration.this , "請輸入個人資料", Toast.LENGTH_SHORT).show();
		  }
	 
		  else {
			  Toast.makeText(Registration.this, "註冊成功!", Toast.LENGTH_SHORT).show();
			  Context c = App.getContext();
	          SharedPreferences settings = getSharedPreferences ("info", c.MODE_PRIVATE);

              SharedPreferences.Editor PE = settings.edit();
              PE.putInt("userregister", 2);
	          PE.putString("email",email );
	          PE.putString("password",password );
 
	          PE.commit();
	          
             	Intent intent = new Intent();
            	intent.setClass( Registration.this,MainActivity.class);
                startActivityForResult(intent, 1);  
                
	          //new FetchTask().execute();
		  }
	         
		  //Toast.makeText(MainActivity.this, name+" "+age+ " "+isman+" "+phonenumber, Toast.LENGTH_LONG).show();
			  
		  // Do something with value!
		  }
		});

		 
 		 alert.setNegativeButton("略過", new DialogInterface.OnClickListener() {
 			  public void onClick(DialogInterface dialog, int whichButton) {
 			    // Canceled.
 				  Context c = App.getContext();
 		          SharedPreferences settings = getSharedPreferences ("info", c.MODE_PRIVATE);
 		          SharedPreferences.Editor PE = settings.edit();
 		          PE.putInt("userregister", 3);
 		          PE.commit();

 		         Intent intent = new Intent();
                 intent.setClass( Registration.this,MainActivity.class);
                 startActivityForResult(intent, 1);  
 			  }
 			});
 		 
		alert.show();	
		 
		
	}


public void showdialog2 (){
	
	
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
		  
		  Toast.makeText(Registration.this , "請輸入個人資料", Toast.LENGTH_SHORT).show();
	  }
	  
	  
 
	  if (useremail.equals(email) && password.equals(userpassword)) {
		   
		 
          SharedPreferences.Editor PE = settings.edit();
          PE.putInt("userregister", 2);
           

          PE.commit();
          
          
         	Intent intent = new Intent();
        	intent.setClass( Registration.this,MainActivity.class);
            startActivityForResult(intent, 1);  
            
 	  }
	  
	  else {
		  
		  Toast.makeText(Registration.this , "密碼不正確", Toast.LENGTH_SHORT).show();
		  showdialog2();
		  
	  }
         
	  
	  }
	});

 
	 alert.setNegativeButton("略過", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
			  Context c = App.getContext();
		          SharedPreferences settings = getSharedPreferences ("info", c.MODE_PRIVATE);
		          SharedPreferences.Editor PE = settings.edit();
		          PE.putInt("userregister", 4);
		          PE.commit();

	         Intent intent = new Intent();
        	 intent.setClass( Registration.this,MainActivity.class);
             startActivityForResult(intent, 1);  
		  }
		});

	alert.show();	
	 
}
	
	
	

}
