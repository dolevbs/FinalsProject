package com.example.android.camera2basic;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;

public class AddItemActicity extends Activity implements AddItemFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_acticity);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, AddItemFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
