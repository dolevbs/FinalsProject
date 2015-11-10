package com.example.android.camera2basic;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;

public class ItemsListActivity extends Activity implements AddItemFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( savedInstanceState ==null ) {
            setContentView(R.layout.activity_items_list);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}