package org.panel;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.sales.car;
import com.sales.motor;
import com.sales.parkin;
import com.sales.wish;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
 
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
 
    // Declare the number of ViewPager pages
    final int PAGE_COUNT = 4;
    Tab tab;
    Context c;
    car fragmentcar;
    motor fragmentmotor;
    parkin fragmentparkin;
    wish fragmentwish;
    Activity a;
    private Fragment mCurrentPrimaryItem = null;

 
    public ViewPagerAdapter(FragmentManager fm,Tab tab,Context c,Activity a) {
        super(fm);
        this.tab = tab;
        this.c = c;
        this.a = a;
    }
 
    @Override
    public Fragment getItem(int arg0) {
        switch (arg0) {
 
        // Open FragmentTab1.java
        case 0:{
            fragmentcar = new car();
            fragmentcar.settab(tab);
            fragmentcar.setActivity(a);
            return fragmentcar;}
 
        // Open FragmentTab2.java
        case 1:{
            fragmentmotor = new motor();
            fragmentmotor.settab(tab);
            return fragmentmotor;
        }
 
        // Open FragmentTab3.java
        case 2: {
            fragmentparkin = new parkin();
            fragmentparkin.settab(tab);
            return fragmentparkin;}
        
    
                   
        
        case 3:{
            fragmentwish = new wish();
            return fragmentwish;}
            
        
        }
        return null;
    }
 
/*
    @Override
    public void startUpdate(View v){
    	
    	super.startUpdate(v);
    	
    }
    */
    
    


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return PAGE_COUNT;
    }
 
}