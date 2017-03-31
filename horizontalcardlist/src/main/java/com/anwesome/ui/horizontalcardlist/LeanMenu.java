package com.anwesome.ui.horizontalcardlist;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by anweshmishra on 31/03/17.
 */
public class LeanMenu {
    private String title;
    private OnMenuClickListener onMenuClickListener;
    private float x,y,w,h;
    private LeanMenu(String title,OnMenuClickListener onMenuClickListener) {
        this.title = title;
        this.onMenuClickListener = onMenuClickListener;
    }
    public void setDimension(float x,float y,float w,float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    public interface OnMenuClickListener {
        void onClick();
    }
    public int hashCode() {
        return title.hashCode()+(int)(y);
    }
    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.translate(x+w/2,y+h/2);
        paint.setColor(Color.parseColor("#FAFAFA"));
        canvas.drawRect(new RectF(-w/2,-h/2,w/2,h/2),paint);
        paint.setTextSize(h/4);
        canvas.drawText(title,-paint.measureText(title)/2,0,paint);
        canvas.restore();
    }
    public boolean handleTap(float x,float y) {
        boolean condition = x>=this.x && x<=this.x+w && y>=this.y && y<=this.y+h;
        if(condition && onMenuClickListener!=null) {
            onMenuClickListener.onClick();
        }
        return condition;
    }
}
