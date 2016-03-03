package com.example.raghav.quotebook;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

public class Quotebook extends ActionBarActivity {
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotebook);

        RelativeLayout touch = (RelativeLayout) findViewById(R.id.touch);
        final TextView quoteText = (TextView) findViewById(R.id.quote);
        final TextView personText = (TextView) findViewById(R.id.person);
        final ArrayList<Quote> quoteList = new ArrayList<Quote>();
        Quote quote4 = new Quote("I wish I was more like Raghav", "Stephen Guevara");
                quoteList.add(quote4);
        Quote quote1 = new Quote("I love Raghav Narain", "Daniel F.");
        quoteList.add(quote1);
        Quote quote2 = new Quote("42-14",
                "Raghav Narain");
        quoteList.add(quote2);
        Quote quote3 = new Quote("I suck at Madden", "Steve");
                quoteList.add(quote3);
        Quote quote5 = new Quote("I wish I was as good as Raghav at fifa", "Dan");
                quoteList.add(quote5);


        quoteList.add(quote5);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count < quoteList.size()) {
                    Quote q = quoteList.get(count);
                    quoteText.setText(q.getQuote());
                    personText.setText(q.getPerson());
                    count = count + 1;
                } else{
                    count = 0;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quotebook, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
