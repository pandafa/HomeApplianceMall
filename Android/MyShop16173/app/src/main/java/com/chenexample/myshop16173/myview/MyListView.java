package com.chenexample.myshop16173.myview;

import android.view.View;
import android.widget.ListView;

public class MyListView extends ListView {

    public MyListView(android.content.Context context,android.util.AttributeSet attrs){
        super(context, attrs);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}