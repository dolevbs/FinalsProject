package com.example.android.camera2basic;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by dolevb on 29/11/2015.
 */
public class Utils {
    private static Date stringToDate(String dateToConvert) {
        String dtStart = "2010-10-15T09:27:37Z";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        try {
            return format.parse(dateToConvert);

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return Calendar.getInstance().getTime();
    }
    public static ArrayList<Item> parseItemsList(String data) {
        ArrayList<Item> returnList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i=0; i< jsonArray.length(); i++ ){
                JSONObject tempObj = jsonArray.getJSONObject(i);
                Item temp = new Item(tempObj.getInt("id"),
                                     tempObj.getString("name"),
                        stringToDate(tempObj.getString("expirationDate")));
                returnList.add(temp);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Utils", e.getStackTrace().toString());
        }

        return returnList;
    }

    public static String getDataFromUrl(String urlToGet) {
        URL url;
        HttpURLConnection urlConnection = null;
        StringBuffer temp = new StringBuffer();
        try {
            url = new URL("http://www.mysite.se/index.asp?data=99");

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();

            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                temp.append(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }
        return temp.toString();
        }
}
