package com.anwesome.ui.horizontalcardlist;

import android.content.Context;
import android.graphics.*;
import android.view.View;

import java.util.List;

/**
 * Created by anweshmishra on 31/03/17.
 */
public class LeanCardView extends View{
    private LeanCard leanCard;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public LeanCardView(Context context) {
        super(context);
    }
    public List<LeanMenu> getMenus() {
        if(leanCard!=null) {
            return leanCard.getLeanMenus();
        }
        return null;
    }
    public void setLeanCard(LeanCard leanCard) {
        this.leanCard = leanCard;
    }
    public void onDraw(Canvas canvas) {
        if(leanCard!=null) {
            leanCard.draw(canvas,paint);
        }
    }
    public boolean handleMenuTap(float x,float y) {
        return leanCard.handleMenuTap(x,y);
    }
}
