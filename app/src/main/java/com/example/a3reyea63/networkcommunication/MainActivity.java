package com.example.a3reyea63.networkcommunication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;

public class MainActivity extends Activity implements View.OnClickListener{

    //class MyTask extends AsyncTask<Void,Void,String>
    class MyTask extends AsyncTask<String,Void,String>
    {
        //public String doInBackground(Void... unused)
        public String doInBackground(String... artists)
        {
            HttpURLConnection conn = null;
            try
            {
                //URL url = new URL("http://www.free-map.org.uk/course/mad/ws/hits.php?artist=Oasis");
               String urlstring = "http://www.free-map.org.uk/course/mad/ws/hits.php?artist=" + artists[0];
               URL url = new URL(urlstring.replace(" ", "%20"));

                conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                if(conn.getResponseCode() == 200)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String result = "", line;
                    while((line = br.readLine()) !=null)
                        result += line + "\n";
                    return result;
                }
                else
                    return "HTTP ERROR: " + conn.getResponseCode();


            }
            catch(IOException e)
            {
                return e.toString();
            }
            finally
            {
                if(conn!=null)
                    conn.disconnect();
            }
        }

        public void onPostExecute(String result)
        {
            TextView response = (TextView)findViewById(R.id.response);
            response.setText(result);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button go = (Button)findViewById(R.id.btngo);
        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MyTask task = new MyTask();

        EditText artist = (EditText)findViewById(R.id.artist);
        task.execute(artist.getText().toString());
    }
}

//task.execute(arg1, arg2, arg3);