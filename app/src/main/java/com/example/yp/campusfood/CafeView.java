package com.example.yp.campusfood;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.List;


public class CafeView extends Activity implements OnClickListener{

    private ImageView image;
    private TextView hours, menu;
    private Button btn1, btn2;
    private String strUrl;
    private String cafeName;

    // button for options menu
    final int FAVORITE = Menu.FIRST + 1;
    final int UNFAV = Menu.FIRST + 2;

    static List<String> fav = new ArrayList<String>();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafe_layout);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);

        image = (ImageView) findViewById(R.id.imageView);
        hours = (TextView) findViewById(R.id.TextHours);
        menu = (TextView) findViewById(R.id.TextMenu);
        btn1 = (Button) findViewById(R.id.Button1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.Button2);
        btn2.setOnClickListener(this);

        // get string message of cafe selected
        Bundle b = getIntent().getExtras();
        cafeName =b.getString("CafeName");
        setTitle(cafeName);

        Spanned strHours = null, strMenu = null;

        switch (cafeName) {

            case "Lower Cafe":
                image.setBackgroundResource(R.drawable.lacava);     // show cafe image
                strHours = Html.fromHtml(getString(R.string.hours_lowerCafe));
                strMenu = Html.fromHtml(getString(R.string.menu_lowerCafe));
                strUrl = "https://bentley.sodexomyway.com/dining-choices/lacava.html";
                break;

            case "The 921":
                image.setBackgroundResource(R.drawable.studentcenter);
                strHours = Html.fromHtml(getString(R.string.hours_921));
                strMenu = Html.fromHtml(getString(R.string.menu_921));
                strUrl = "http://bentley.sodexomyway.com/dining-choices/index.html";
                break;

            case "Deloitte Cafe":
                image.setBackgroundResource(R.drawable.einstein);
                strHours = Html.fromHtml(getString(R.string.hours_Deloitte));
                strMenu = Html.fromHtml(getString(R.string.menu_Deloitte));
                strUrl = "http://www.einsteinbros.com/";
                break;

            case "Currito":
                image.setBackgroundResource(R.drawable.currito);
                strHours = Html.fromHtml(getString(R.string.hours_currito));
                strMenu = Html.fromHtml(getString(R.string.menu_currito));
                strUrl = "http://www.currito.com/";
                break;

            case "Dunkin' Donuts":
                image.setBackgroundResource(R.drawable.dunkin);
                strHours = Html.fromHtml(getString(R.string.hours_dunkin));
                strMenu = Html.fromHtml(getString(R.string.menu_dunkin));
                strUrl = "http://www.dunkindonuts.com/en";
                break;

        }
        hours.setText(strHours);
        menu.setText(strMenu);

    }

    // options menu that dynamically changes content
    public boolean onPrepareOptionsMenu(Menu menu){
        menu.clear();
        if (!fav.contains(cafeName)){
            MenuItem item1 = menu.add(0, FAVORITE, Menu.NONE, "Add to Favorite");
            item1.setShortcut('1', 'a');
        } else {
            MenuItem item2 = menu.add(0, UNFAV, Menu.NONE, "Unfavorite");
            item2.setShortcut('2', 'a');
        }
        return true;
    }

    public void onClick(View v) throws SecurityException {

        switch (v.getId()) {

            case R.id.Button1:
                // show location in google maps
                Intent intent0 = new Intent(CafeView.this, Map.class);
                intent0.putExtra("Cafe", cafeName);
                startActivity(intent0);
                break;

            case R.id.Button2:
                // open website in web view
                Intent intent = new Intent(CafeView.this, WebBrowse.class);
                intent.putExtra("URL", strUrl);
                startActivity(intent);
                break;
        }
    }

        public boolean onOptionsItemSelected(MenuItem item) {

            int itemID = item.getItemId();

            switch(itemID){
                case FAVORITE:
                    fav.add(cafeName);
                    return true;

                case UNFAV:
                    fav.remove(cafeName);
                    return true;

                default: super.onOptionsItemSelected(item);
            }
            return false;
    }

}
