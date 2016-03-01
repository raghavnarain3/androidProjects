package com.example.raghav.busexample;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtStopSearch;
    ListView lstStops;
    ArrayList<String> results = new ArrayList<String>();
    ArrayList<String> ids = new ArrayList<String>();
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtStopSearch = (EditText) findViewById(R.id.editText);
        lstStops = (ListView) findViewById(R.id.listView);
        edtStopSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                //chill
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //chill
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String userText = edtStopSearch.getText().toString();
                new MyAsyncTask().execute(userText);
            }

        });
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            //chill
        }

        protected String doInBackground(String... strings) {
            StringBuilder builder = new StringBuilder();
            try {
                URL url = new URL(getString(R.string.autocompleteAPI) + strings[0]);
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
            results.clear();
            ids.clear();
            query = result;

            try {
                JSONArray jsonArray = new JSONArray(query);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String name = jsonArray.getJSONObject(i).getString("n");
                    String ide = jsonArray.getJSONObject(i). getString("i");
                    String val = name + " " + ide;
                    results.add(val);
                    ids.add(ide);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, results) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);
                        return view;
                    }
                };

                lstStops.setAdapter(arrayAdapter);

                lstStops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    // argument position gives the index of item which is clicked
                    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                        Toast.makeText(getApplicationContext(), ids.get(position), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), StopActivity.class);
                        i.putExtra("id", ids.get(position));
                        startActivity(i);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
