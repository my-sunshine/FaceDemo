package com.hqgj.facedemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ly on 2016/1/6.
 */
public class ExtendListView extends ListView {

    public ExtendListView(Context context) {
        super(context);
    }

    public ExtendListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

    }




}
