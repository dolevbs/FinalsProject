package com.example.android.camera2basic;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemsListActivityFragment extends Fragment  implements View.OnClickListener {

    public ItemsListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_items_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        view.findViewById(R.id.AddItemButton).setOnClickListener(this);
    }
    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.AddItemButton: {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentHolder, AddItemFragment.newInstance())
                        .commit();
            }
        }
    }
}
