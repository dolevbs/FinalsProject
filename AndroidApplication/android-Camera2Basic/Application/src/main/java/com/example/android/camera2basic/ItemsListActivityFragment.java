package com.example.android.camera2basic;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemsListActivityFragment extends Fragment  implements View.OnClickListener {
    ArrayList<Item> mItemsFromNetwork;
    ListView mItemsListView;
    public ItemsListActivityFragment() {
        mItemsFromNetwork = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_items_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        this.generateData();
        ListView bla =(ListView) view.findViewById(R.id.itemsListView);
        mItemsListView = bla;
        // 1. pass context and data to the custom adapter
        MyArrayAdapter adapter = new MyArrayAdapter(this.getActivity().getApplicationContext(), mItemsFromNetwork);

        bla.setAdapter(adapter);
        view.findViewById(R.id.AddItemButton).setOnClickListener(this);
    }

    public void generateData(){
        new DownloadItemsList().execute("");
       ///return Utils.parseItemsList(balbla.replaceAll("(?m)^[ \t]*\r?\n", "").trim());
    }

    private class DownloadItemsList extends AsyncTask {

        protected Object doInBackground(Object[] urls) {
            Log.d("AAAAAAAAAAAAAAAAAAAA", "doInBackground");
            String balbla = Utils.getDataFromUrl(Utils.GET_ITEMS_LIST);
            //"[   {     \"id\": \"123\",     \"name\": \"קוטג תנובה 5%\",     \"expirationDate\": \"12/12/12\"   },   {     \"id\": \"432\",     \"name\": \"חלב תנובה 3%\",     \"expirationDate\": \"05/12/15\"   },   {     \"id\": \"6812\",     \"name\": \"גבינת עמק 27%\",     \"expirationDate\": \"28/12/15\"   },   {     \"id\": \"1233\",     \"name\": \"נוטלה\",     \"expirationDate\": \"30/12/16\"   },   {     \"id\": \"6812\",     \"name\": \"גבינת עמק 28%\",     \"expirationDate\": \"28/11/15\"   },   {     \"id\": \"434\",     \"name\": \"חלב תנובה 3%\",     \"expirationDate\": \"10/12/15\"   } ] "
            Log.d("AAAAAAAAAAAAAAA", balbla);
                    return balbla;
        }

        protected void onPostExecute(Object result) {
            String bla = (String) result;
            Log.d("AAAAAAAAA", "postExcute");
            Log.d("AAAAAAAAA", bla);
            mItemsFromNetwork = Utils.parseItemsList(bla.replaceAll("(?m)^[ \t]*\r?\n", "").trim());
            mItemsListView.setAdapter(new MyArrayAdapter(getActivity().getApplicationContext(), mItemsFromNetwork));

        }

    }
    private ItemRegister mItemToRegister;
    private class AddProductTask extends AsyncTask {

        protected Object doInBackground(Object[] barcodes) {

            return Utils.addProduct(mItemToRegister.getBarcode(), mItemToRegister.getExpirationDateFile().getAbsolutePath());

        }
        protected void onPostExecute(Object result) {

            Log.d("AAAAAAAAA", "postExcute");
            Log.d("AAAAAAAAA", "" + result);
            generateData();

        }
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        Log.d("asdasdasd", "onActivityResult() called with: " + "requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], intent = [" + intent + "]");
        if (requestCode == 3 ) {


            final String barcode = intent.getStringExtra("barcode");
            final String filePath = intent.getStringExtra("date");
            mItemToRegister = new ItemRegister();
            mItemToRegister.setBarcode(barcode);

            if ( filePath != null ) {
                mItemToRegister.setExpirationDateFile(new File(filePath));
            }
            new AddProductTask().execute();
        }

    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.AddItemButton: {
                try {
                    //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                    Intent intent;
                    intent = new Intent(this.getActivity(), AddItemActicity.class);
                    //startActivityForResult();
                    startActivityForResult(intent,3);
                } catch (ActivityNotFoundException anfe) {}

//                getFragmentManager().beginTransaction()
//                        .replace(R.id.fragmentHolder, AddItemFragment.newInstance())
//                        .commit();
            }
        }
    }
}


