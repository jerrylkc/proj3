package org.panel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech.OnInitListener;

public class App extends Application implements OnInitListener {

    private static Context mContext;
    private List<Activity> mList = new LinkedList<Activity>();   
    private static App instance;       
    private Activity current;

     
    public synchronized static App getInstance() {    
        if (null == instance) {     
            instance = new App();     
        }     
        return instance;     
    }     
     
    public void addActivity(Activity activity) {     
        mList.add(activity);     
    }
    
    public void currentactivity(Activity activity){
    	
    	current = activity;
    }
     
    public void exit() {     
        try {     
            for (Activity activity : mList) {     
                if (activity != null)     
                          activity.finish();     
            }     
        } catch (Exception e) {     
            e.printStackTrace();     
        } finally {     
            System.exit(0);     
        }     
    }     
     
    @Override     
    public void onLowMemory() {     
        super.onLowMemory();         
        System.gc();   
    }     
    
    
    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context Context) {
        mContext = Context;
    }

    
    
    public void onInit (int status){

        //what you want to do just after the completion of the TextToSpeech engine initialization
        }
   

}