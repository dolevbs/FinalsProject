package com.example.android.camera2basic;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

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
        ListView bla =(ListView) view.findViewById(R.id.itemsListView);
        // 1. pass context and data to the custom adapter
        MyArrayAdapter adapter = new MyArrayAdapter(this.getContext(), generateData());

        bla.setAdapter(adapter);
        view.findViewById(R.id.AddItemButton).setOnClickListener(this);
    }

    private ArrayList<Item> generateData(){
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Item 1","First Item on the list"));
        items.add(new Item("Item 2", "Second Item on the list"));
        items.add(new Item("Item 3", "Third Item on the list"));

        return items;
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
