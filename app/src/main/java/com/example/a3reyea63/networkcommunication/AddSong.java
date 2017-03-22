package com.example.a3reyea63.networkcommunication;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddSong extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        Button addButton = (Button)findViewById(R.id.addsongButton);
        addButton.setOnClickListener(this);
    }


    //class MyTask extends AsyncTask<Void,Void,String>
   private class MyTask extends AsyncTask<String,Void,String> {
        //public String doInBackground(Void... unused)
        public String doInBackground(String... params) {
            HttpURLConnection conn = null;
            try {
                //URL url = new URL("http://www.free-map.org.uk/course/mad/ws/hits.php?artist=Oasis");
                String urlstring = "http://www.free-map.org.uk/course/mad/ws/addhit.php";
                URL url = new URL(urlstring);

                String postData = "song=" + params[0] + "&artist=" + params[1] + "&year=" + params[2];
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(postData.length());
                OutputStream out = null;
                out = conn.getOutputStream();
                out.write(postData.getBytes());

                InputStream in = conn.getInputStream();
                if (conn.getResponseCode() == 200) {
                    //   new AlertDialog.Builder(this).setMessage("Song has been added sucessfully").
                    //           setPositiveButton("OK", null).show();
                    return "Song has been added sucessfully"; //dirty code
                } else
                    return "HTTP ERROR: " + conn.getResponseCode();


            } catch (IOException e) {
                return e.toString();
            } finally {
                if (conn != null)
                    conn.disconnect();
            }
        }

        public void onPostExecute(String result) {
            TextView response = (TextView) findViewById(R.id.response);
            response.setText(result);
        }
    }



    @Override
    public void onClick(View view) {

        MyTask task = new MyTask();

        EditText song = (EditText)findViewById(R.id.songadd);
        EditText artist = (EditText)findViewById(R.id.artistadd);
        EditText year = (EditText)findViewById(R.id.yearadd);

        task.execute(song.getText().toString(), artist.getText().toString(), year.getText().toString());



    }
}
