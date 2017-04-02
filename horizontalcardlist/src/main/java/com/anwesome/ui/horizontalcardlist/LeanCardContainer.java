package com.anwesome.ui.horizontalcardlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.util.AttributeSet;
import android.view.*;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 31/03/17.
 */
public class LeanCardContainer extends ViewGroup{
    private int w,h,origW;
    private List<LeanCard> leanCards = new ArrayList<>();
    private boolean calledExplicitly = false;
    private HorizontalScrollView horizontalScrollView;
    private LeanMenuController leanMenuController;
    public LeanCardContainer(Context context) {
        super(context);
        initDimensions(context);
        calledExplicitly = true;
    }
    public void setParentHorizontalScrollView(HorizontalScrollView horizontalScrollView) {
        this.horizontalScrollView = horizontalScrollView;
    }
    public void setLeanMenuController(LeanMenuController leanMenuController) {
        this.leanMenuController = leanMenuController;
    }
    public LeanCardContainer(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void addLeanCard(LeanCard leanCard,OnClickListener onClickListener) {
        LeanCardView leanCardView = new LeanCardView(getContext());
        leanCardView.setLeanCard(leanCard);
        leanCardView.setOnClickListener(onClickListener);
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
        int wSize = w/20,hSize = 0;
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
        origW = wSize;
        setMeasuredDimension(Math.max(w,wSize),hSize+h/20);
    }
    public void onLayout(boolean reloaded,int a,int b,int w,int h) {
        int x = w/80,y=0;
        for(int i = 0;i<getChildCount();i++) {
            View view = getChildAt(i);
            if(view instanceof LeanMenuView) {
                int mx = (int)view.getX(),my = (int)view.getY(),mw = view.getMeasuredWidth(),mh = view.getMeasuredHeight();
                view.layout(0,0,mw,mh);
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
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX(),y = event.getY();
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                handleTapForView( view, x, y);
            }
        }
        return true;
    }
    private void handleTapForView(View view,float x,float y) {
        if(view instanceof LeanCardView && leanMenuController!=null && horizontalScrollView!=null) {
            LeanCardView leanCardView = (LeanCardView)view;
            x-=leanCardView.getX();
            y-=leanCardView.getY();
            if(leanCardView.handleMenuTap(x,y)) {
                List<LeanMenu> leanMenus = leanCardView.getMenus();
                if(leanMenus!=null) {
                    float xMenu = leanCardView.getX()+leanCardView.getMeasuredWidth()*0.4f-horizontalScrollView.getScrollX();
                    if(xMenu > w*0.8f) {
                        xMenu = (leanCardView.getX())-horizontalScrollView.getScrollX();
                    }
                    float yMenu = (leanCardView.getY()+leanCardView.getMeasuredHeight()*0.2f);
                    int wMenu = leanCardView.getMeasuredWidth();
                    leanMenuController.show(leanMenus,horizontalScrollView.getX()+xMenu,horizontalScrollView.getY()+yMenu,wMenu);
                }
            }
            else if(leanCardView.handleCardTap(x,y)) {
                //TO IMPLEMENT
            }
            horizontalScrollView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                        leanMenuController.hide();
                    }
                    return false;
                }
            });
        }
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
