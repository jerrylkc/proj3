package org.panel;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


public class CustomArrayAdapter<T> extends ArrayAdapter<T>
{
    public CustomArrayAdapter(Context ctx, T [] objects)
    {
        super(ctx, android.R.layout.simple_spinner_item, objects);
    }

    //other constructors

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View view = super.getView(position, convertView, parent);

        //we know that simple_spinner_item has android.R.id.text1 TextView:         

        /* if(isDroidX) {*/
            TextView textview = (TextView)view.findViewById(android.R.id.text1);
            textview.setTextColor(Color.RED);
            //textview.setTextSize(30);
            //text.setText("model");//choose your color :)         
        /*}*/

        return view;

    }
}