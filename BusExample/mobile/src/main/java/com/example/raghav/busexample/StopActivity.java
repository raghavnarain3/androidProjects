package com.example.raghav.busexample;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class StopActivity extends AppCompatActivity {

    String id;
    ListView buses;
    String query;
    ArrayList<String> busR = new ArrayList<String>();
    ArrayList<String> eta = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);
        buses = (ListView) findViewById(R.id.listView2);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getString("id");
        }
        new StopAsyncTask().execute(id);

    }


    private class StopAsyncTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            //chill
        }

        protected String doInBackground(String... strings) {
            StringBuilder builder = new StringBuilder();
            try {
                URL url = new URL(getString(R.string.stopAPI) + strings[0]);
                busR.add(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    return builder.toString();
                } catch (Exception e) {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        protected void onPostExecute(String result) {
            query = result;
            try {
                JSONObject reader = new JSONObject(query);
                JSONArray d = reader.getJSONArray("departures");
                for (int i = 0; i < d.length(); i++) {
                    JSONObject b = d.getJSONObject(i);
                    JSONObject trip = b.getJSONObject("trip");
                    //String theBus = trip.getString("shape_id");
                    String time = b.getString("expected_mins");
                    String headSign = b.getString("headsign");
                    busR.add(headSign + " eta: " + time + " mins");
                    eta.add(time);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, busR) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);
                        return view;
                    }
                };

                buses.setAdapter(arrayAdapter);



            } catch (Exception e) {
                e.printStackTrace();
            }



        }
    }
}
