package com.cst2335.lamo0241;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        String url2 = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";
        String url1 = "https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
        ForecastQuery weather = new ForecastQuery();
        weather.execute(url1,url2);


    }



    private class ForecastQuery extends AsyncTask<String, Integer, String>{
        public String UV, min, max, temp, icon;
        Bitmap picture;


        public void getPicture() throws IOException {
            Bitmap image = null;
            Log.i("Looking for file: ", icon +".png");
            if (!fileExistance(icon+".png")){
                Log.i("FILE NOT FOUND, DOWNLOADING","type: ICON");
            try {
                URL url = new URL("https://openweathermap.org/img/w/" + icon + ".png");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    image = BitmapFactory.decodeStream(connection.getInputStream());
                }
                FileOutputStream outputStream = openFileOutput( icon + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
                this.picture=image;
            }
            catch (Exception e){Log.e("IMAGE ERROR", String.valueOf(e)); }


            }
            else {
                Log.i("FILE ALREADY DOWNLOADED","type: ICON");
                FileInputStream fis = null;
                try {    fis = openFileInput(icon+".png");   }
                catch (FileNotFoundException e) {    e.printStackTrace();  }
                this.picture = BitmapFactory.decodeStream(fis);

            }

            publishProgress(100);

        }
        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }


        @Override
        protected String doInBackground(String... args) {
              if(!args[1].isEmpty()){
                try {
                    URL jsonurl = new URL(args[1]);
                    HttpURLConnection jsonConnection = (HttpURLConnection) jsonurl.openConnection();
                    InputStream response1 = jsonConnection.getInputStream();
                    BufferedReader jsonreader = new BufferedReader(new InputStreamReader(response1));
                    String jsonString = jsonreader.readLine();
                    JSONObject jsondata = new JSONObject(jsonString);
                    double value = jsondata.getDouble("value");
                    this.UV = String.valueOf(value);
                }
                catch(Exception e){}
              }
              try {

              //create a URL object of what server to contact:
              URL url = new URL(args[0]);

              //open the connection
              HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

              publishProgress(25);
              //wait for data:
              InputStream response = urlConnection.getInputStream();

              //From part 3
              XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
              factory.setNamespaceAware(false);
              XmlPullParser xpp = factory.newPullParser();
              xpp.setInput( response  , "UTF-8");

              //From part 3,
              String parameter = null;

              int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT
              publishProgress(50);
              while(eventType != XmlPullParser.END_DOCUMENT)
              {

                  if(eventType == XmlPullParser.START_TAG)
                  {
                      //If you get here, then you are pointing at a start tag
                      if(xpp.getName().equals("temperature"))
                      {

                          //If you get here, then you are pointing to a <temperature> start tag
                          this.temp = xpp.getAttributeValue(null,    "value");
                          this.min = xpp.getAttributeValue(null, "min");
                          this.max = xpp.getAttributeValue(null, "max");
                      }

                      else if(xpp.getName().equals("weather"))
                      {

                          icon = xpp.getAttributeValue(null, "icon"); // this will run for <AMessage message="parameter" >
                      }


                  }
                  eventType = xpp.next(); //move to the next xml event and store it in a variable
              }



          }
          catch (Exception e)
          {
            Log.e("ERROR", "error "+e);
          }

            publishProgress(75);

              try {
                getPicture();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Done";
        }

        //Type 2
        @Override
        public void onProgressUpdate(Integer ... args)
        {
            Integer[] progressive = args;
            ProgressBar bar = findViewById(R.id.progressbar);
            bar.setProgress(progressive[0]);
        }
        //Type3
        public void onPostExecute(String fromDoInBackground)
        {
            ProgressBar bar = findViewById(R.id.progressbar);
            bar.setVisibility(View.INVISIBLE);
            ImageView pic = findViewById(R.id.weatherpicture);
            pic.setImageBitmap(picture);
            TextView min = findViewById(R.id.mintemp);
            TextView temp = findViewById(R.id.currenttemp);
            TextView max = findViewById(R.id.maxtemp);
            TextView UV = findViewById(R.id.uvrating);
            temp.setText("Current temperature: " + this.temp+"\u2103");
            min.setText("Minimum: "+ this.min+"\u2103");
            max.setText("Maximum: " + this.max+"\u2103");
            UV.setText("UV Rating: "+ this.UV);
            Log.i("HTTP", fromDoInBackground);
        }


    }
}