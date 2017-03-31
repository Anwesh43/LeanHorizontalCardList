package com.anwesome.ui.horizontalcardlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by anweshmishra on 31/03/17.
 */
public class LeanCardContainerGroup extends ViewGroup {
    private int w,h;
    public LeanCardContainerGroup(Context context) {
        super(context);
        initDimensions(context);
    }
    public LeanCardContainerGroup(Context context, AttributeSet attrs) {
        super(context,attrs);
        initDimensions(context);
    }
    public void addLeanCardContainerSection(LeanCardContainer cardContainer,String title) {
        TextView textView = new TextView(getContext());
        textView.setText(title);
        textView.setTextSize(h/100);
        textView.setTextColor(Color.BLACK);
        addView(textView,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getContext());
        horizontalScrollView.addView(cardContainer,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        addView(horizontalScrollView,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        requestLayout();
    }
    public void initDimensions(Context context) {
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        Point dimension = new Point();
        display.getRealSize(dimension);
        w = dimension.x;
        h = dimension.y;
    }
    public void onMeasure(int wSpec,int hSpec) {
        int hSize = 0;
        for(int i=0;i<getChildCount();i++) {
            View view = getChildAt(i);
            measureChild(view,wSpec,hSpec);
            hSize+=view.getMeasuredHeight()+h/40;
        }
        setMeasuredDimension(w,hSize);
    }
    public void onLayout(boolean reloaded,int a,int b,int w,int h) {
        int x = 0,y = 0;
        for(int i=0;i<getChildCount();i++) {
            View view = getChildAt(i);
            view.layout(x,y,x+view.getMeasuredWidth(),y+view.getMeasuredHeight());
            y+=view.getMeasuredHeight()+h/40;
        }
    }
    public void show() {
        Activity activity = (Activity)getContext();
        ScrollView scrollView = new ScrollView(activity);
        scrollView.addView(this,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        activity.addContentView(scrollView,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
    }
}
