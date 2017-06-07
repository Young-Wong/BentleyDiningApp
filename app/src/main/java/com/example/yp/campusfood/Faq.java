package com.example.yp.campusfood;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

public class Faq extends Activity {
    TextView text;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_layout);

        text = (TextView) findViewById(R.id.Faq);
        Spanned faqText = null;

        faqText = Html.fromHtml(getString(R.string.FAQ));

        text.setText(faqText);
    }
}
