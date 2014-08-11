package com.sales;

import org.panel.App;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Common extends Object{
	
   private TextView t;
   private AlertDialog dialog;
   private boolean status;
   Activity a;
   Context c;
   
   public Common(){
	   
	  

	   
   }
	
   public void setcontext(Context c){
	   this.c = c;
	   
   }
   public void setactivity(Activity a){
	   
	   this.a = a;
   }
	
   public boolean showalert(){
	   
	   AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);
       
	   alertDialog.setTitle("雜開");
       alertDialog.setMessage("確定要離開?");

       alertDialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog,int which) {

        	   status = false;
               dialog.cancel();
        	   //App.getInstance().exit(0);
           }
       });

       alertDialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
           
           
           status = true;
           App.getInstance().exit();
           }
       });
       
       alertDialog.show();
	   return status;
   }
   
   
	public TextView gettitletext(TextView t,String title,int textSize){
	   
		t.setTextSize((int)(textSize*1.2));
        t.setTextColor(Color.WHITE);
        t.setTypeface(Typeface.DEFAULT_BOLD);
        t.setText(title);
        t.setGravity(Gravity.CENTER);
        
	    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 0);        
        t.setLayoutParams(lp);
	    return t;
		
	}

}
