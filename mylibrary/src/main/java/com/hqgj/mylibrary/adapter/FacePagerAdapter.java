package com.hqgj.mylibrary.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * author: ly
 * data: 2016/6/15
 */
public class FacePagerAdapter extends PagerAdapter {


    private ArrayList<View> faceViews;

    @Override
    public int getCount() {
        if(faceViews!=null){
            return faceViews.size();
        }
        return 0;
    }

    public FacePagerAdapter(ArrayList<View> faceViews) {
        this.faceViews=faceViews;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(faceViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=faceViews.get(position);
        container.addView(view,0);
        return view;
    }
}
