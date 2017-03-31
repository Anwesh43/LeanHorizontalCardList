package com.anwesome.ui.horizontalcardlist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 31/03/17.
 */
public class LeanMenuView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<LeanMenu> leanMenus = new ArrayList<>();
    public LeanMenuView(Context context) {
        super(context);
    }
    public void setLeanMenus(List<LeanMenu> leanMenus) {
        this.leanMenus = leanMenus;
    }
    public int getMenuHeight() {
        int y = 0;
        for(LeanMenu leanMenu:leanMenus) {
            PointF dimension = leanMenu.getDimension();
            y+=dimension.y;
        }
        return y;
    }
    public void onDraw(Canvas canvas) {
        for(LeanMenu leanMenu:leanMenus) {
            leanMenu.draw(canvas,paint);
            PointF dimension = leanMenu.getDimension();
            float y = leanMenu.getY();
            canvas.drawLine(dimension.x/10,y+dimension.y,9*dimension.x/10,y+dimension.y,paint);
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            for(LeanMenu leanMenu:leanMenus) {
                if(leanMenu.handleTap(event.getX(),event.getY())) {
                    setVisibility(INVISIBLE);
                }
            }
        }
        return true;
    }
    public int hashCode() {
        return super.hashCode()+leanMenus.hashCode();
    }
}
