package com.example.yp.campusfood;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.view.View;
import android.webkit.WebView;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import android.speech.tts.TextToSpeech.OnInitListener;


public class MainActivity extends Activity implements OnClickListener, AdapterView.OnItemClickListener, OnInitListener {

    private WebView web;
    private TabHost tabHost;
    private ListView listview;
    private ArrayAdapter<String> adapter = null;
    private TextToSpeech speaker;

    final int FAQ = Menu.FIRST + 1;
    final int ABOUT = Menu.FIRST + 2;
    final int CLOSE = Menu.FIRST + 3;

    private ImageButton image1, image2, image3, image4, image5;
    private TextView text1, text2, text3, text4, text5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);

        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        image1 = (ImageButton) findViewById(R.id.image1);
        image2 = (ImageButton) findViewById(R.id.image2);
        image3 = (ImageButton) findViewById(R.id.image3);
        image4 = (ImageButton) findViewById(R.id.image4);
        image5 = (ImageButton) findViewById(R.id.image5);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);

        text1 = (TextView) findViewById(R.id.textView1);
        text2 = (TextView) findViewById(R.id.textView2);
        text3 = (TextView) findViewById(R.id.textView3);
        text4 = (TextView) findViewById(R.id.textView4);
        text5 = (TextView) findViewById(R.id.textView5);

        listview = (ListView) findViewById(R.id.list);
        listview.setOnItemClickListener(this);
        adapter = new ArrayAdapter<String>(this, R.layout.item, CafeView.fav);
        listview.setAdapter(adapter);

        speaker = new TextToSpeech(this, this);

        TabHost.TabSpec spec;
        // Initialize a TabSpec for tab1 and add it to the TabHost
        spec=tabHost.newTabSpec("tag1");	//create new tab specification
        spec.setContent(R.id.tab1);    //add tab view content
        spec.setIndicator("Cafe");    //put text on tab
        tabHost.addTab(spec);             //put tab in TabHost container

        ImageView img = (ImageView) findViewById(R.id.simple_anim);
        img.setBackgroundResource(R.drawable.simple_animation);

        AnimationRoutine1 task1 = new AnimationRoutine1();

        Timer t = new Timer();
        t.schedule(task1, 1000);


        //-------------------------------------------------------------------------------------

        // Initialize a TabSpec for tab2 and add it to the TabHost
        spec=tabHost.newTabSpec("tag2");		//create new tab specification
        spec.setContent(R.id.tab2);			//add view tab content
        spec.setIndicator("Favorites");
        tabHost.addTab(spec);					//put tab in TabHost container


        //-------------------------------------------------------------------------------------

        // Initialize a TabSpec for tab3 and add it to the TabHost
        spec=tabHost.newTabSpec("tag3");		//create new tab specification
        spec.setContent(R.id.tab3);			//add tab view content
        spec.setIndicator("Twitter");			//put text on tab
        tabHost.addTab(spec); 					//put tab in TabHost container

        // set up custom fonts
        // custom fonts for TtlAmt
        Typeface oj = Typeface.createFromAsset(getAssets(), "fonts/orange juice.ttf");
        text1.setTypeface(oj);
        text2.setTypeface(oj);
        text3.setTypeface(oj);
        text4.setTypeface(oj);
        text5.setTypeface(oj);

        // set up web view for tab3
        web = (WebView) findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("https://twitter.com/BentleyDining/with_replies");

        //intercept URL loading and load in widget
        web.setWebViewClient(new WebViewClient(){

            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }

        });
    }

    // animation start
    class AnimationRoutine1 extends TimerTask {

        @Override
        public void run() {
            ImageView img = (ImageView) findViewById(R.id.simple_anim);
            AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
            frameAnimation.start();
        }
    }

    // animation stop
    class AnimationRoutine2 extends TimerTask {

        @Override
        public void run() {
            ImageView img = (ImageView) findViewById(R.id.simple_anim);
            AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
            frameAnimation.stop();
        }
    }

    // stop animation when on pause
    public void onPause(){
        super.onPause();
        AnimationRoutine2 task2 = new AnimationRoutine2();
        Timer t2 = new Timer();
        t2.schedule(task2, 0);
    }

    // option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item1 = menu.add(0, FAQ, Menu.NONE, "FAQs");
        MenuItem item2 = menu.add(0, ABOUT, Menu.NONE, "About this app");
        MenuItem item3 = menu.add(0, CLOSE, Menu.NONE, "Close App");
        item1.setShortcut('1', 'f');
        item2.setShortcut('2', 'a');
        item3.setShortcut('3', 'c');
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int ItemID = item.getItemId();


        switch (ItemID) {

            case FAQ:
            Intent intentFaq = new Intent(MainActivity.this, Faq.class);
            startActivity(intentFaq);
            return true;

            case ABOUT:
                speak("Welcome to Bentley Dining Services, we hope you enjoy your meal.");
                return true;

            case CLOSE:
                finish();

            default: super.onOptionsItemSelected(item);
        }
        return false;
    }

    public void speak(String output) {
        speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            // Set preferred language to US english.
            speaker.setLanguage(Locale.US);
        }
    }
    public void onClick(View v) throws SecurityException {
        // intent to start a new activity
        Intent intent = new Intent(MainActivity.this, CafeView.class);

        // intent to send broadcast
        Intent broadcast = new Intent("TRIGGER_RECEIVER");

        // find image buttons by id and run respective code
        switch (v.getId()) {

            case R.id.image1:
                intent.putExtra("CafeName", "Lower Cafe");
                broadcast.putExtra("Cafe", "Lower Cafe");
                sendBroadcast(broadcast);
                startActivity(intent);
                break;

            case R.id.image2:
                intent.putExtra("CafeName", "The 921");
                broadcast.putExtra("Cafe", "The 921");
                sendBroadcast(broadcast);
                startActivity(intent);
                break;

            case R.id.image3:
                intent.putExtra("CafeName", "Deloitte Cafe");
                broadcast.putExtra("Cafe", "Deloitte Cafe");
                sendBroadcast(broadcast);
                startActivity(intent);
                break;

            case R.id.image4:
                intent.putExtra("CafeName", "Currito");
                broadcast.putExtra("Cafe", "Currito");
                sendBroadcast(broadcast);
                startActivity(intent);
                break;

            case R.id.image5:
                intent.putExtra("CafeName", "Dunkin' Donuts");
                broadcast.putExtra("Cafe", "Dunkin' Donuts");
                sendBroadcast(broadcast);
                startActivity(intent);
                break;
        }
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        String cafe = CafeView.fav.get(position);

        Intent intent = new Intent(MainActivity.this, CafeView.class);

        intent.putExtra("CafeName", cafe);
        startActivity(intent);
    }
}
