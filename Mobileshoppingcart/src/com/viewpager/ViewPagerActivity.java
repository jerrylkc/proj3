/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.viewpager;

import com.fedorvlasov.lazylist.ImageLoader;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class ViewPagerActivity extends Activity {

	private ViewPager mViewPager;
	Context c;
	String url[];
	int selectpage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    
		Bundle bundle = this.getIntent().getExtras();
        url = bundle.getStringArray("url");
        selectpage = bundle.getInt("selectpage");
        
		mViewPager = new HackyViewPager(this);
		setContentView(mViewPager);
		
		c = this.getApplicationContext();

		mViewPager.setAdapter(new SamplePagerAdapter(url));
		mViewPager.setCurrentItem(selectpage);

	}

	static class SamplePagerAdapter extends PagerAdapter {

		
		private String url[];
		
		@Override
		public int getCount() {
			return url.length;
		}
        public SamplePagerAdapter (String url []){
        	this.url = url;
        };

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			//photoView.seti
			
		       ImageLoader imageLoader=new ImageLoader(container.getContext());
		       imageLoader.DisplayImage(url[position], photoView);
		       
			   //photoView.setImageResource(sDrawables[position]);

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

}
