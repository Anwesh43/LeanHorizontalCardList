package com.anwesome.ui.horizontalcardlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 31/03/17.
 */
public class LeanCardContainer extends ViewGroup{
    private int w,h;
    private List<LeanCard> leanCards = new ArrayList<>();
    private LeanMenuView leanMenuView;
    private boolean calledExplicitly = false;
    public LeanCardContainer(Context context) {
        super(context);
        initDimensions(context);
        calledExplicitly = true;
    }
    public LeanCardContainer(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void addLeanCard(LeanCard leanCard) {
        LeanCardView leanCardView = new LeanCardView(getContext());
        leanCardView.setLeanCard(leanCard);
        addView(leanCardView,new LayoutParams(w/4,w/4+h/20));
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
        int wSize = w/10,hSize = 0;
        for(int i = 0;i<getChildCount();i++) {
            View view = getChildAt(i);
            measureChild(view,wSpec,hSpec);
            if(view instanceof LeanMenuView) {
                continue;
            }
            else if(view instanceof LeanCardView) {
                wSize += (view.getMeasuredWidth()*12)/10;
                hSize = Math.max(hSize,view.getMeasuredHeight());
            }
        }
        setMeasuredDimension(Math.max(w,wSize),hSize+h/10);
    }
    public void onLayout(boolean reloaded,int a,int b,int w,int h) {
        int x = w/10,y=0;
        for(int i = 0;i<getChildCount();i++) {
            View view = getChildAt(i);
            if(view instanceof LeanMenuView) {
                int mx = (int)view.getX(),my = (int)view.getY(),mw = view.getMeasuredWidth(),mh = view.getMeasuredHeight();
                view.layout(mx,my,mx+mw,my+mh);
                continue;
            }
            else if(view instanceof LeanCardView) {
                int wSize = view.getMeasuredWidth();
                int hSize = view.getMeasuredHeight();
                view.layout(x,y+h/20,x+wSize,y+hSize+h/10);
                x+=(wSize*12)/10;
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
                    if(leanCardView.handleMenuTap(x-leanCardView.getX(),y-leanCardView.getY())) {
                        if(leanMenuView!=null) {
                            removeView(leanMenuView);
                        }
                        List<LeanMenu> leanMenus = leanCardView.getMenus();
                        if(leanMenus!=null) {
                            leanMenuView = new LeanMenuView(getContext());
                            leanMenuView.setLeanMenus(leanMenus);
                            int menuHeight = leanMenuView.getMenuHeight();
                            leanMenuView.setX(x);
                            leanMenuView.setY(y);
                            addView(leanMenuView,new LayoutParams(leanCardView.getMeasuredWidth(),menuHeight));
                            requestLayout();
                        }
                    }
                }
            }
        }
        return true;
    }
    public void show() {
        if(calledExplicitly) {
            Activity activity = (Activity)getContext();
            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(activity);
            activity.addContentView(horizontalScrollView,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            horizontalScrollView.addView(this,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        }
    }

}
