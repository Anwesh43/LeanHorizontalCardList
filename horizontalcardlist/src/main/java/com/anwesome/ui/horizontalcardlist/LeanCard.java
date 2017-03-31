package com.anwesome.ui.horizontalcardlist;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 31/03/17.
 */
public class LeanCard {
    private Bitmap bitmap;
    private String title,subtitle;
    private int w,h,menuH;
    private MenuButton menuButton;
    private List<LeanMenu> leanMenus = new ArrayList<>();
    private int time = 0;
    public List<LeanMenu> getLeanMenus() {
        return leanMenus;
    }
    public LeanCard(Bitmap bitmap,String title,String subtitle) {
        this.bitmap = bitmap;
        this.title = title;
        this.subtitle = subtitle;
    }
    public void setLeanMenus(List<LeanMenu> leanMenus) {
        this.leanMenus = leanMenus;
    }
    public void draw(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            bitmap = Bitmap.createScaledBitmap(bitmap,w,2*h/3,true);
            menuButton = new MenuButton();
        }
        paint.setColor(Color.parseColor("#FAFAFA"));
        canvas.drawRect(new RectF(0,0,w,h),paint);
        canvas.drawBitmap(bitmap,0,0,paint);
        paint.setColor(Color.parseColor("#212121"));
        paint.setTextSize(h/8);
        canvas.drawText(title,w/10,2*h/3+h/15,paint);
        paint.setTextSize(h/16);
        canvas.drawText(subtitle,w/10,4*h/5,paint);
        if(leanMenus.size()>0) {
            menuButton.draw(canvas,paint);
        }
        time++;
    }
    public void setMenuDimensions() {
        int y = 0,hSize = h/4;
        for(LeanMenu menu:leanMenus) {
            menu.setDimension(0,y,w,h/4);
            y+=h/4;
        }
        menuH = y;
    }
    public boolean handleMenuTap(float x,float y) {
        return menuButton.handleTap(x,y);
    }
    private List<LeanMenu> getMenus() {
        return leanMenus;
    }
    public int hashCode() {
        return bitmap.hashCode()+title.hashCode()+subtitle.hashCode();
    }
    private class MenuButton {
        float r,x,y;
        public MenuButton() {
            r = w/30;
            x = 4*w/5;
            y = 4*h/5;
        }
        public void draw(Canvas canvas,Paint paint){
            paint.setColor(Color.parseColor("#212121"));
            for(int i=0;i<3;i++) {
                canvas.save();
                canvas.translate(x,y);
                canvas.drawCircle(0,(i-1)*2*r,r,paint);
                canvas.restore();
            }
        }
        public boolean handleTap(float x,float y) {
            boolean condition = x>=this.x-r && x<=this.x+r && y>=this.y-3*r && y<=this.y+3*r;
            return condition;
        }
    }
}
