package com.fedorvlasov.lazylist;

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
import static com.fedorvlasov.lazylist.Constant.NEEDTYPE;
import static com.fedorvlasov.lazylist.Constant.PHOTONO;

import static com.fedorvlasov.lazylist.Constant.DESCRP;
 

 


import java.util.ArrayList;
import java.util.HashMap;

import org.panel.App;
import org.panel.R;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.sales.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private View view;
    private String[] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
    float textSize;
    private Context c;
    private Context actcontext;
    public ArrayList<HashMap<String,String>> arraylist;
	HashMap<String,String> temp;
    public Tab selecttab;
    public TextView tw;
    SharedPreferences preferences;
    int selectnumber;
    private String type;
    int validatenumber=0;
    int realposition;
      boolean isChecked[] = new boolean [100];  
      ViewHolder holder; 
      
     
    public LazyAdapter(Activity a, String[] Imageurl, ArrayList<HashMap<String,String>> arraylist,String type) {
        activity = a;
        data=Imageurl;
         this.arraylist= arraylist;
        this.type = type;
        initischecked();

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
        
    }
    
    
    
    public LazyAdapter(Context context,String[] Imageurl,ArrayList<HashMap<String,String>> arraylist,String type){
    	
    	c= context;
    	data=Imageurl;
    	this.arraylist= arraylist;
        this.type = type;
         initischecked();
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(c.getApplicationContext());

    }
    
    public LazyAdapter(Context context,ArrayList<HashMap<String,String>> arraylist,String type){
  
    	c= context;
    	this.arraylist= arraylist;
    	
        this.type = type;
        initischecked();

        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    
    
  public void initischecked(){
 
      for (int i = 0; i<isChecked.length;i++ ){
      	isChecked[i] = false;
      }
      
  }
  
 public void setActivity(Activity a){
	 
	 activity = a;
 }   
 
    
    
    public void settab(Tab tab){
    	
    	
    	this.selecttab = tab;
    }
    
    public void setTextView(TextView tw){
    	
    	this.tw = tw;
    }
    
    public int getlistCount(){
    	
    	return arraylist.size();
    	
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
	public Object getlist(int position) {
		// TODO Auto-generated method stub
        return arraylist.get(position);
		
		
	}
	public void setcontext(Context c){
		
		actcontext = c;
	}

	
	
	public Object getlistitem (int position,String key){		
		temp = arraylist.get(position);
		return temp.get(key);
	}
		   

	private class ViewHolder {
		   TextView txtOrdertype;
	       TextView txtFirst;
	       TextView txtSecond;
	       TextView txtThird;
	       TextView txtFourth;
	       TextView txtFifth;
           TextView Needid;	       
	       TextView txtCode;
	       TextView photono,descrp;

	       ImageView image;
	       CheckBox checkbox;
	       Button b1,b2;
	  }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        
    	//Log.i("11position"+position,"checked");
        final int pos = position;              

        if(convertView==null){        
        	
        	/* select xml*/
        	if (type.equals("c")||type.equals("m"))
              convertView = inflater.inflate(R.layout.itemcar, null);
        	if (type.equals("p"))
        	  convertView = inflater.inflate(R.layout.itemparkin, null);
        	if (type.equals("w"))
          	  convertView = inflater.inflate(R.layout.itemwish, null);
  	        if (type.equals("n"))
              convertView = inflater.inflate(R.layout.itemnewcar, null);

  	        if (type.equals("cs"))
                convertView = inflater.inflate(R.layout.itemcustomer, null);
 	              	
            holder = new ViewHolder();     
			holder.txtFirst = (TextView) convertView.findViewById(R.id.itemcol1);
			holder.txtSecond = (TextView) convertView.findViewById(R.id.itemcol2);
			holder.txtThird = (TextView) convertView.findViewById(R.id.itemcol3);
			holder.txtFourth = (TextView) convertView.findViewById(R.id.itemcol4);
			holder.txtCode = (TextView)convertView.findViewById(R.id.itemcode);
			
		 
		   if (type.equals("c")){
			   
			   holder.photono = (TextView)convertView.findViewById(R.id.itemphotono );
			   holder.descrp = (TextView)convertView.findViewById(R.id.itemdescrp  );

		   }	
			
			final TextView textview = holder.txtThird;
			final TextView textview2 = holder.txtFourth;
			
        	if (type.equals("c")||type.equals("m")||type.equals("p")||type.equals("w")){
			  holder.checkbox = (CheckBox)convertView.findViewById(R.id.checkbox);
 
        	}
			
        	if (type.equals("w")||type.equals("cs")){ 
		      holder.txtOrdertype = (TextView)convertView.findViewById(R.id.ordertype);
		      holder.b1 = (Button)convertView.findViewById(R.id.button1); 
		      holder.b2 = (Button)convertView.findViewById(R.id.button2); 

        	}
        	
  	        if (type.equals("n"))
  		      holder.txtFifth = (TextView)convertView.findViewById(R.id.itemdealer);

  	        
 			  Context c = App.getContext();
			  preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);

			    HashMap<String,String> temp = arraylist.get(position);

			    String selectnumberstring = preferences.getString("selectnumber","0");
                int selectnumber = Integer.parseInt(selectnumberstring);
              
 
	        	    
      	        if (type.equals("w")){
	            	  tw.setText("選擇: "+selectnumber );

	            	  
      	        	float total = 0;
	            	  
	            	  if (selectnumber != 0){
	            		  for (int i = 1; i<=selectnumber;i++){
	            			  
	            			  String s []= preferences.getString("productdescrp"+i, "0").split(",");
	            			  
	            			  float price = 0;
	            			  
	            			  if (s[3].length()>0)
	            			    price = Float.parseFloat(s[3]);
	            			  
	            			  total += price * Float.parseFloat(preferences.getString("quantity"+i, "0"));
		            		  }
	      	            
	            		  SharedPreferences.Editor editor = preferences.edit();

	            		     editor.putString("total",total+"") ;
	        	        	 editor.commit();
	        	        		
		            	  tw.setText("選擇: "+selectnumber + "   總共: $"+total);
		            	  
		             
      	        	
      	        }
      	        	 
      	        }

                
             	 //Log.i("finalpos"+pos,"checked");

             	if (type.equals("c")||type.equals("m")||type.equals("p")||type.equals("w")) { 
            		//holder.checkbox.setChecked(false);
            		
             
       			  setcheck(position);

			   //===========================================================
            		/*button */
 
                	if (type.equals("w")||type.equals("cs")){ 
 
            	holder.b1.setOnClickListener(new View.OnClickListener() {
					
 
             		
 					@Override
					public void onClick(View v) {
 						
  						
  						   int quantity = Integer.parseInt(textview.getText().toString()); 
  						   int p = pos+1;
  						   
  						   if (quantity>0)
 						     quantity--;
  						   
    						   String thetext = quantity+"";
  						    textview.setText(thetext );
  						    
 
  			        	    SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("quantity"+p,thetext) ;
        	        		editor.commit();


        	        		String selectnumberstring = preferences.getString("selectnumber","0");
        	                int selectnumber = Integer.parseInt(selectnumberstring);
        	              
    					  //  Toast.makeText(App.getContext(),thetext+"p:"+p+"s:"+selectnumber, Toast.LENGTH_SHORT).show();

    		            		float total = 0;

		            	  
		            	  if (selectnumber != 0){
		            		  for (int i = 1; i<=selectnumber;i++){
		            			  
		            			  String s []= preferences.getString("productdescrp"+i, "0").split(",");
		            			  float price = Float.parseFloat(s[3]);
	    						   //Toast.makeText(App.getContext(),thetext+"p:"+p+"s:"+selectnumber, Toast.LENGTH_SHORT).show();

		            			  total += price * Float.parseFloat(preferences.getString("quantity"+selectnumber, "0"));
  		            		  }
		            		   
			            	  tw.setText("選擇: "+selectnumber + "   總共: $"+total); 
 
			            	  SharedPreferences.Editor editor2 = preferences.edit();

		            		     editor.putString("total",total+"") ;
		        	        	 editor.commit();
		            	  } 
 				 
 
  		 
 					}
				});
            	
            	holder.b2.setOnClickListener(new View.OnClickListener() {
					
 
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						   int quantity = Integer.parseInt(textview.getText().toString()); 
						   int p = pos+1;
 						   quantity++;
   						    String thetext = quantity+"";
  						   textview.setText(thetext ); 
  						   
 
      		  
           	  
 			        	    SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("quantity"+p,thetext) ;
        	        		editor.commit();
        	        		
        	        		String selectnumberstring = preferences.getString("selectnumber","0");
        	                int selectnumber = Integer.parseInt(selectnumberstring);
        	               
 						 //  Toast.makeText(App.getContext(),thetext+"p:"+p+"s:"+selectnumber, Toast.LENGTH_SHORT).show();
 		            		float total = 0;

		            	  if (selectnumber != 0){
		            		  for (int i = 1; i<=selectnumber;i++){
		            			  
		            			  String s []= preferences.getString("productdescrp"+i, "0").split(",");
		            			  float price = Float.parseFloat(s[3]);
		            			  
		            			  total += price * Float.parseFloat(preferences.getString("quantity"+i, "0"));
  		            		  }
		            		  
			            	  tw.setText("選擇: "+selectnumber + "   總共: $"+total);

			            	  SharedPreferences.Editor editor2 = preferences.edit();

		            		     editor.putString("total",total+"") ;
		        	        	 editor.commit();
		        	        	 
		            	  }
        	        		 
        	        		
  						
					}
				});
                	}
            	
                 	holder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
 
                 		  @Override
                		  public void onCheckedChanged(CompoundButton buttonView, boolean  Checked) {
                  			 
                   			 Integer realPosition = (Integer) buttonView.getTag() ;
                 			 //realposition = realPosition;

                  		    Log.i("1pos:"+realPosition+Checked,"checked");

                  		    if (realPosition !=null){
                		      isChecked[realPosition]  =  Checked;
                  			 realposition = realPosition;
  
                  		    }
                 		  }
                		});
                 	
 
                		
            		
 			   holder.checkbox.setOnClickListener( new View.OnClickListener() { 
								
			     public void onClick(View v) {
         		   // Log.i("pos:"+pos ,"checked");

			      CheckBox cb = (CheckBox) v ;  
				  String selectnumberstring = preferences.getString("selectnumber","0");
                  int selectnumber = Integer.parseInt(selectnumberstring);
	  			  
	        	  SharedPreferences.Editor editor = preferences.edit();
                  
                  /*
                  for (int i = 1 ; i <=selectnumber; i++){
                
    	    	  String descrp = preferences.getString("productdescrp"+i,"0" );
    	    	  String order = preferences.getString("ordertype"+i, "0");
    	    	  String type = preferences.getString("producttype"+i,"0");
    	    	    	    	  
	        	  Log.i("in cache: " + i+" " +descrp +" " + order +" " +type,"m");}*/
                  

			      if (cb.isChecked()){
 					  Log.i("onclicked:"+realposition,"checked");

                      if (type.equals("w"))
                    	 if (validatenumber>0){
                    	     validatenumber--;
                             editor.putString("validate", Integer.toString(validatenumber));}
                      
			    	  selectnumber++;                         
			    	  HashMap<String,String> temp = arraylist.get(realposition);	
			    	 //  Log.i("position"+  pos,"pos");
			    	  
			    	    if (!type.equals("w")){
		        		     selecttab.setText( "選擇: " +selectnumber);
		        		    }
		        		
		        		if (type.equals("w")){
		        		    	
		            	   tw.setText("選擇: "+selectnumber);
		        			/*
		            	  float total = 0;
		            	  
		            	  if (selectnumber != 0){
		            		  for (int i = 1; i<=selectnumber;i++){
		            			  
		            			  String s []= preferences.getString("productdescrp"+selectnumber, "0").split(",");
		            			  float price = Float.parseFloat(s[3]);
		            			  
		            			  total += price * Float.parseFloat(preferences.getString("quantity"+selectnumber, "0"));
  		            		  }
		            		  
			            	  tw.setText("選擇: "+selectnumber + "   總共: $"+total);

		            		   
		            	  }
		            	  */
		            		  
		            	  
 		        		    }
	        		
		        		editor.putString("selectnumber", selectnumber+"");	    	        		
		        		editor.putString("productitem" + selectnumber,temp.get(CODE_COLUMN));		        		
		        		editor.putString("producttype"+selectnumber,temp.get(PRODUCTTYPE));
		        		editor.putString("ordertype"+selectnumber,temp.get(ORDERTYPE));
		        		editor.putString("quantity"+selectnumber,temp.get(THIRD_COLUMN));
		        		
 		        		
	        	    	  String mydescrp = preferences.getString("productdescrp"+selectnumber,"0" );
	        	    	  String myorder = preferences.getString("ordertype"+selectnumber, "0");
	        	    	  String mytype = preferences.getString("producttype"+selectnumber,"0"); 
	        	    	  String mycode = preferences.getString("productitem"+selectnumber,"0");
	        	    	  String myquantity = preferences.getString("quantity"+selectnumber, "0");
 
		    	    	//  editor.remove("quantity"+lastindex);

	    	        	   //Log.i("after remove2: " + " "+mycode +mydescrp +" " + myorder +" " +mytype + " " +myquantity,"m");
		        		
		        		
		        		if (type.equals("c")||type.equals("m")||type.equals("n")||type.equals("w")){
			        	  editor.putString("productdescrp" + selectnumber, temp.get(FIRST_COLUMN)+"," + temp.get(SECOND_COLUMN) +"," 
			        				+ temp.get(THIRD_COLUMN) + "," +  temp.get(FOURTH_COLUMN));
			        	  
		        		}
		        		
		        		if (type.equals("p")){
				              
			              if (temp.get(ORDERTYPE).equals("b"))
			        	    editor.putString("productdescrp" + selectnumber, temp.get(FIRST_COLUMN)+"," +temp.get(FIRST_COLUMN2)+"," +temp.get(FIRST_COLUMN3)
			        	    		+"," + temp.get(SECOND_COLUMN));
			              if (temp.get(ORDERTYPE).equals("r"))
			        	    editor.putString("productdescrp" + selectnumber, temp.get(FIRST_COLUMN)+","+temp.get(FIRST_COLUMN2)+","+temp.get(FIRST_COLUMN3)
			        	    		+","+  temp.get(THIRD_COLUMN));			        	  
			    		
			           
		        		}
	        		//Toast.makeText(v.getContext(), "You save "+ preferences.getString("productitem"+selectnumber,"0")+ preferences.getString(dscrp,"0") + " " +  preferences.getString(order,"0") 
			        			//	, Toast.LENGTH_SHORT).show();
	
			                  for (int i = 1 ; i <=selectnumber; i++){
			                      
			                	  String code  = preferences.getString("productitem"+i,"0");
			        	    	  String descrp = preferences.getString("productdescrp"+i,"0" );
			        	    	  String theorder = preferences.getString("ordertype"+i, "0");
			        	    	  String type = preferences.getString("producttype"+i,"0");  	
			        	    	  
			    	        	  Log.i("save in cache: " + i+" "+ code +descrp +" " + theorder +" " +type,"m");

			                      }
		        		
			      }
			      else{
			    	  
                      if (type.equals("w"))
                    	{validatenumber++;
                         editor.putString("validate", Integer.toString(validatenumber));}
			    	  
			    	  HashMap<String,String> temp = arraylist.get(realposition);
					 
		    		  
			    	  int tempselectnumber = selectnumber - 1;

			    	  
			    	  if (tempselectnumber > 0){
				    	    for (int i = 1 ; i<=selectnumber ; i++){

					    	      String productcode = preferences.getString("productitem" + i,"0");
					    	      
				    	    	  String descrp = preferences.getString("productdescrp" + i,"0" );
				    	    	  String order = preferences.getString("ordertype"+i, "0");
				    	    	  String type = preferences.getString("producttype"+i,"0");
					        	  
					        	  //Log.i("before remove:"+i+" " + productcode + " " +descrp +" " + order +" " +type,"m");					      
					        	  if (temp.get(CODE_COLUMN).equals(productcode)){						        					    	    	  
					    	      //Log.i("You remove "+i+" "+  productcode + " " +  descrp + " " + order +" " + type, "m");
					    	    	  
   				    	    	      editor.remove("productitem" + i);
					    	    	  editor.remove("productdescrp" + i);
					    	    	  editor.remove("ordertype"+i);
					    	    	  editor.remove("producttype"+i);
				                      editor.remove("quantity"+i);
					    	    	  
						        	  int deleteindex = i;
						        	  int lastindex = i;
						        	  int nextindex = deleteindex;
						        	  
					    	    	  for (int j=i+1;!(preferences.getString("productitem"+j,"0").equals("0"));j++)
					    	    		  lastindex = j;					    	    		  
					    	          
					    	    	  //Log.i("lastindex "+lastindex,"m");

					    	    	  
					                  /*for (int e = 1 ; e <=lastindex; e++){
					                      
					        	    	  String mydescrp = preferences.getString("productdescrp"+e,"0" );
					        	    	  String myorder = preferences.getString("ordertype"+e, "0");
					        	    	  String mytype = preferences.getString("producttype"+e,"0"); 
					        	    	  String mycode = preferences.getString("productitem"+e,"0");
					    	        	  //Log.i("before remove2: " + e+" "+mycode +mydescrp +" " + myorder +" " +mytype,"m");

					                      }*/
					                  
					    	          for (int currentindex = deleteindex ; nextindex<lastindex ; currentindex++){
					    	        	      
					    	        	      nextindex = currentindex+1;
                                              editor.putString("productitem"+currentindex, preferences.getString("productitem"+nextindex, "0"));
                                              editor.putString("productdescrp"+currentindex, preferences.getString("productdescrp"+nextindex,"0" ));
                                              editor.putString("ordertype"+currentindex,preferences.getString("ordertype"+nextindex, "0"));
                                              editor.putString("producttype"+currentindex,preferences.getString("producttype"+nextindex,"0"));
                                              editor.putString("quantity"+currentindex,preferences.getString("quantity"+nextindex,"0"));
                                              
					    	          }

					    	    	 // Log.i("deleteindex "+deleteindex,"m");

					    	           if (lastindex!=deleteindex){
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
							    	    	//  editor.remove("quantity"+lastindex);

						    	        	  //Log.i("after remove2: " + e+" "+mycode +mydescrp +" " + myorder +" " +mytype,"m");

						                      }
					    	        

					    	    	  
					    	      }
					    	    }
				    	  if (!type.equals("w")){
			    	        selecttab.setText("選擇: "+tempselectnumber);
		            	    //activity.invalidateOptionsMenu(); 
				    	  }

			    	      if (type.equals("w")){
				            	 // tw.setText("選擇: "+tempselectnumber);

			    	      }
			    	    	  /*
			            	  float total = 0;
			            	  
			            	  if (selectnumber != 0){
			            		  for (int i = 1; i<=tempselectnumber;i++){
			            			  
			            			  String s []= preferences.getString("productdescrp"+tempselectnumber, "0").split(",");
			            			  Float price = Float.parseFloat(s[3]);
			            			  
			            			  total += price * Float.parseFloat(preferences.getString("quantity"+tempselectnumber, "0"));
	  		            		  }
			            		  
				            	  tw.setText("選擇: "+selectnumber + "   總共: $"+total);
				            	  
 			    	      }			    	      }
 			    	      */
			    	   
			    	  }
			    	  if (tempselectnumber <= 0)
			    	    {  if (!type.equals("w")){
			    	    	 selecttab.setText("選擇: "+0);
 		            	    //activity.invalidateOptionsMenu(); 
			    	    }
		    	           if (type.equals("w"))
				            	  tw.setText("選擇: "+0 + "   總共: $0 " );
 
			    	    }
			    	  

		        		editor.putString("selectnumber", tempselectnumber+"");		        		
		        		String msg = preferences.getString("selectnumber","0");
		        		//Log.i(msg,"msg");
			    	  
		        	   selectnumber -- ;
			    	  
			      }
	        		editor.commit();

			      
			     }  
			    }); 
 			   
 			   
 			   
 			   
 			 

           		 
 			   
 		 
  			   
 			   
 			   
            	}// end if (type.equals("c")||type.equals("m")||type.equals("p"))

			
			
	        textSize = holder.txtCode.getTextSize();      
	        textSize = 15;
	        
	    	if (type.equals("n")){
		       // textSize = (float)(textSize*0.8);
	    	    settextproperty (holder.txtFifth,textSize);}
	    	else
		        //textSize = (float)(textSize*0.8);

	        settextproperty(holder.txtFirst,textSize);
	        settextproperty(holder.txtSecond,textSize );
	        settextproperty(holder.txtThird,textSize );

	    	if (!type.equals("w")){  
	 
 	        settextproperty(holder.txtFourth,textSize );}
	        
			convertView.setTag(holder);
        }
            
        else{
        	
        	
        	holder = (ViewHolder)convertView.getTag();
        }
        

        
          if(isChecked[position]){
            Log.i("position:"+position,"checked");

            holder.checkbox.setChecked(true);}
           else
           holder.checkbox.setChecked(false);
         
         setcheck(position);
        
		HashMap<String, String> map = arraylist.get(position);
		
		if (!type.equals("w")){	
		holder.txtFirst.setText(map.get(FIRST_COLUMN));
		holder.txtSecond.setText(map.get(SECOND_COLUMN));
		holder.txtThird.setText(map.get(THIRD_COLUMN));
		holder.txtFourth.setText("$"+map.get(FOURTH_COLUMN));
		holder.txtCode.setText(map.get(CODE_COLUMN));
		}
		
		
    	if (type.equals("p")){    		
    		holder.txtFirst.setText(map.get(FIRST_COLUMN)+" ("+map.get(FIRST_COLUMN2)+") "+"  μu: "+map.get(FIRST_COLUMN3));
    		holder.txtSecond.setText("°a: "+ map.get(SECOND_COLUMN));
    		holder.txtThird.setText("¯2: "+ map.get(THIRD_COLUMN) + " / ?e");
    		holder.txtFourth.setText("?e’A: "+ map.get(FOURTH_COLUMN));}
		
		
    	if (type.equals("w")){
    		
    		holder.txtFirst.setText(map.get(FIRST_COLUMN));
    		holder.txtSecond.setText("$"+map.get(FOURTH_COLUMN));
    		holder.txtThird.setText(map.get(THIRD_COLUMN));


    	/* 
    		if (map.get(ORDERTYPE).equals("b"))
            holder.txtOrdertype.setText("?R");
    	  if (map.get(ORDERTYPE).equals("r"))
              holder.txtOrdertype.setText("¯2");
*/
    	  holder.checkbox.setChecked(true);
    	  
    	 
    	
    	}
    	
    	if (type.equals("cs")){
    	  
    	  Log.i("order:"+map.get(ORDERTYPE),"or");
    	  holder.txtOrdertype.setText(map.get(ORDERTYPE));
    	}
    	
    	if (type.equals("n")){
            holder.txtFifth.setText(map.get(FIFTH_COLUMN));}
    	
    	if(type.equals("w")){
			  holder.checkbox.setTag(new Integer(position));

    	}
		
    	
   if (type.equals("c") ){
			   
			   holder.photono.setText(map.get(PHOTONO));  
			   holder.descrp.setText(map.get(DESCRP));  

			  holder.checkbox.setTag(new Integer(position));

		   }
   
    	if (type.equals("c")||type.equals("m"))
    	{
          ImageView image=(ImageView)convertView.findViewById(R.id.image);       
        
         setimage ( data[position],image);
        
         //imageLoader.DisplayImage(data[position], image);
       // imageLoader.clearCache();
    	}

        return convertView;
    }
    
    
    public void  setimage( String url, ImageView img){
         // imageLoader.clearCache();

    	imageLoader.DisplayImage(url , img);
 
    }
    
     
    
    public void settextproperty (TextView textview ,float size){
    	
    	textview.setTextSize(size);
    	
    }
    	
    
    public void setcheck(int position){
    	
    	 Context c = App.getContext();
		  preferences = c.getSharedPreferences("info", c.MODE_PRIVATE);
		    String selectnumberstring = preferences.getString("selectnumber","0");
            int selectnumber = Integer.parseInt(selectnumberstring);		
            
            holder.checkbox.setChecked(false);

            if (selectnumber>0){

    	    for (int i = 1 ; i<=selectnumber ; i++){

    	    	
    	          String temproductcode = preferences.getString("productitem" + i,"0");
    	          //holder.checkbox.setChecked(false);
		    	  HashMap<String,String> temp = arraylist.get(position);

    	           
	    	      if (temp.get(CODE_COLUMN).equals(temproductcode)){
	    	    	  
	    	    	    // Log.i(i+" " + temproductcode    +"  " +temp.get(CODE_COLUMN),"product");
		        		 holder.checkbox.setChecked(true);
		        		}			        	
	      
	    	    }
        	
        } 
    	
    }
    	
    	
    
}