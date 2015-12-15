package com.example.android.camera2basic;

import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by dolevb on 29/11/2015.
 */
public class Utils {

//    public static String GET_ITEMS_LIST = "http://192.168.2.14:8080/FinalProjectServer/newjsp.jsp?action=getUserList&id=1";
//    public static String GET_BARCODE = "http://192.168.2.14:8080/FinalProjectServer/newjsp.jsp?action=getProduct&barcode=%s";
//    public static String POST_ITEM = "http://192.168.2.14:8080/FinalProjectServer/newjsp.jsp";

    private static String SERVER_TO_CONNECT = "10.0.0.6:8080";
    public static String GET_ITEMS_LIST = "http://" +  SERVER_TO_CONNECT +"/FinalProjectServer/newjsp.jsp?action=getUserList&id=1";
    public static String GET_BARCODE = "http://" +  SERVER_TO_CONNECT +"/FinalProjectServer/newjsp.jsp?action=getProduct&barcode=%s";
    public static String POST_ITEM = "http://" +  SERVER_TO_CONNECT +"/FinalProjectServer/newjsp.jsp";

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
        Log.d("AAAAAAAA", urlToGet);
        URL url;
        HttpURLConnection urlConnection = null;
        StringBuffer temp = new StringBuffer();
        try {
            Log.d("AAAAAAAA", "1");
            url = new URL(urlToGet);

            urlConnection = (HttpURLConnection) url
                    .openConnection();
            Log.d("AAAAAAAA", "2");
            InputStream in = urlConnection.getInputStream();
            Log.d("AAAAAAAA", "3");
            InputStreamReader isw = new InputStreamReader(in);
            Log.d("AAAAAAAA", "4");
            int data = isw.read();
            Log.d("AAAAAAAA", "5");
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                temp.append(current);
            }
            Log.d("AAAAAAAA", "7");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }
        Log.d("AAAAAAAA", "8");
        return temp.toString();
        }

    private static String fileToBase64(String filePath) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(filePath);//You can get an inputStream using any IO API
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static Boolean addProduct(String barcode, String sourceFileUri)  {
        //action=addUserProduct&barcode=%s&image

        HttpURLConnection httpUrlConnection = null;
        StringBuffer temp = new StringBuffer();
        Log.d("AAAAAAAbbbbbbb", barcode);
        Log.d("AAAAAAAbbbbbbb", sourceFileUri);
        try {

            URL url = new URL(POST_ITEM);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");

            DataOutputStream wr = new DataOutputStream (
                    httpUrlConnection.getOutputStream ());
            String fileData = fileToBase64(sourceFileUri);
            String DataToSend = "action=addUserProduct&id=1&barcode=" + URLEncoder.encode(barcode, "UTF-8") + "&image=" + URLEncoder.encode(fileData, "UTF-8");

            wr.writeBytes(DataToSend);
            wr.flush();
            wr.close();
            Log.d("AAAAAAAABBBBB", "post return code: " + httpUrlConnection.getResponseCode());
            httpUrlConnection.disconnect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                httpUrlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }

        return false;
    }
}
