package com.anwesome.ui.horizontalcardlist;

import android.graphics.Point;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by anweshmishra on 02/04/17.
 */
public class LeanMenuController {
    private LeanCardContainerGroup cardContainerGroup;
    private LeanMenuView leanMenuView;
    private LeanMenuController(LeanCardContainerGroup leanCardContainerGroup) {
        this.cardContainerGroup = leanCardContainerGroup;
    }
    public static LeanMenuController getInstance(LeanCardContainerGroup leanCardContainerGroup) {
        return new LeanMenuController(leanCardContainerGroup);
    }
    public void show(List<LeanMenu> leanMenus,float x,float y,int w) {
        if(leanMenuView!=null) {
            this.cardContainerGroup.removeView(leanMenuView);
        }
        leanMenuView = new LeanMenuView(cardContainerGroup.getContext());
        leanMenuView.setLeanMenus(leanMenus);
        leanMenuView.setX(x);
        leanMenuView.setY(y);
        leanMenuView.setElevation(20);
        this.cardContainerGroup.addView(leanMenuView,new ViewGroup.LayoutParams(w,leanMenuView.getMenuHeight()));
        this.cardContainerGroup.requestLayout();
    }
    public void hide() {
        if(leanMenuView!=null) {
            leanMenuView.setVisibility(View.INVISIBLE);
        }
    }
}
