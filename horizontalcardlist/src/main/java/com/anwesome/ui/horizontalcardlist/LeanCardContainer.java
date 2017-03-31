package com.anwesome.ui.horizontalcardlist;

import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 31/03/17.
 */
public class LeanCardContainer extends ViewGroup{
    private int w,h;
    private List<LeanCard> leanCards = new ArrayList<>();
    private LeanMenuView leanMenuView;
    public LeanCardContainer(Context context) {
        super(context);
        initDimensions(context);
    }
    public LeanCardContainer(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void addLeanCard(LeanCard leanCard) {
        LeanCardView leanCardView = new LeanCardView(getContext());
        leanCardView.setLeanCard(leanCard);
        addView(leanCardView,new LayoutParams(w/4,w/4));
        requestLayout();
    }
    public void initDimensions(Context context) {
        DisplayManager displayManager = (DisplayManager)context.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        Point size = new Point();
        display.getRealSize(size);
        w = size.x;
        h = size.y;
    }
    public void onMeasure(int wSpec,int hSpec) {
        int wSize = 0,hSize = 0;
        for(int i = 0;i<getChildCount();i++) {
            View view = getChildAt(i);
            measureChild(view,wSpec,hSpec);
            if(view instanceof LeanMenuView) {
                continue;
            }
            else if(view instanceof LeanCardView) {
                wSize += view.getMeasuredWidth();
                hSize = Math.max(hSize,view.getMeasuredHeight());
            }
        }
        setMeasuredDimension(Math.max(w,wSize),hSize);
    }
    public void onLayout(boolean reloaded,int a,int b,int w,int h) {
        int x = 0,y=0;
        for(int i = 0;i<getChildCount();i++) {
            View view = getChildAt(i);
            if(view instanceof LeanMenuView) {
                continue;
            }
            else if(view instanceof LeanCardView) {
                int wSize = view.getMeasuredWidth();
                int hSzie = view.getMeasuredHeight();
                view.layout(x,y,x+wSize,y+hSzie);
                x+=wSize;
            }
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(),y = event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            for(int i=0;i<getChildCount();i++) {
                View view = getChildAt(i);
                if(view instanceof LeanCardView) {
                    LeanCardView leanCardView = (LeanCardView)view;
                    if(leanCardView.handleMenuTap(x-leanCardView.getX(),y-leanCardView.getY()));
                }
            }
        }
        return true;
    }

}
