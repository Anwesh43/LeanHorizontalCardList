package com.anwesome.ui.leanhorizontalcardlistdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anwesome.ui.horizontalcardlist.LeanCard;
import com.anwesome.ui.horizontalcardlist.LeanCardContainer;
import com.anwesome.ui.horizontalcardlist.LeanCardContainerGroup;
import com.anwesome.ui.horizontalcardlist.LeanMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int drawables[] = {R.drawable.gojira,R.drawable.kol,R.drawable.penguin,R.drawable.profile_img};
    private String titles[] = {"Gojira","Kings of Leno","Penguin","ProfileImg"};
    private String subtitles[] = {"music band","music band","bird","person"};
    private String menus[] ={"add","delete","track","download","order","share","save"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LeanCardContainerGroup leanCardContainerGroup = new LeanCardContainerGroup(this);
        Bitmap bitmaps[] = new Bitmap[4];
        for(int i=0;i<drawables.length;i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(),drawables[i]);
        }
        for(int k = 0;k<5;k++) {
            LeanCardContainer leanCardContainer = new LeanCardContainer(this);
            for (int i = 0; i < drawables.length * 4; i++) {
                int e = drawables.length;
                final String title = titles[i%e];
                LeanCard leanCard = new LeanCard(bitmaps[i % e], titles[i % e], subtitles[i % e]);
                List<LeanMenu> leanMenus = new ArrayList<>();
                for (int j = 0; j < menus.length; j++) {
                    final String menuText = menus[j];
                    LeanMenu leanMenu = new LeanMenu(menus[j], new LeanMenu.OnMenuClickListener() {
                        @Override
                        public void onClick() {
                            Toast.makeText(MainActivity.this, menuText, Toast.LENGTH_SHORT).show();
                        }
                    });
                    leanMenus.add(leanMenu);
                }
                leanCard.setLeanMenus(leanMenus);
                leanCardContainer.addLeanCard(leanCard,new View.OnClickListener(){
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            leanCardContainerGroup.addLeanCardContainerSection(leanCardContainer, "Section "+(k+1));
        }
        leanCardContainerGroup.show();
        //leanCardContainer.show();
    }
}
