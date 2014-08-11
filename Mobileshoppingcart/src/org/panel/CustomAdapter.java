package org.panel;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends ArrayAdapter<String>
{

	
	TextView label;
	String objects[];
	Context context;
	
     public CustomAdapter(Context context, int resourceId,
                 String[] objects) {
           super(context, resourceId, objects);
    	   this.objects = objects;
    	   this.context = context;

           // TODO Auto-generated constructor stub
     }
     @Override
  public View getDropDownView(int position, View convertView,ViewGroup parent) {
     return getCustomView(position, convertView, parent);
  }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
     return getCustomView(position, convertView, parent);
 }

 public View getCustomView(int position, View convertView, ViewGroup parent) {

     LayoutInflater inflater = LayoutInflater.from(context);
     View row=inflater.inflate(R.layout.spinner_item, parent, false);
     label =(TextView)row.findViewById(R.id.spinner_textView);
     label.setText(objects[position]);


     RadioButton radioButton=(RadioButton)row.findViewById(R.id.spinner_radio_button);
    radioButton.setOnClickListener(new OnClickListener(){

public void onClick(View v){

	String text = "clicked:"+label.getText();
	Toast.makeText(v.getContext(),text,Toast.LENGTH_LONG).show();


}
 });

     return row;
     }

}
