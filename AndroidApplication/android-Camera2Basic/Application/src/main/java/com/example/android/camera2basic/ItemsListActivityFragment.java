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
import java.util.Date;
import java.util.GregorianCalendar;

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
        MyArrayAdapter adapter = new MyArrayAdapter(this.getActivity().getApplicationContext(), generateData());

        bla.setAdapter(adapter);
        view.findViewById(R.id.AddItemButton).setOnClickListener(this);
    }

    private ArrayList<Item> generateData(){
       return Utils.parseItemsList("[\n" +
                "  {\n" +
                "    \"id\": 3337,\n" +
                "    \"name\": \"enim\",\n" +
                "    \"expirationDate\": \"31/12/15\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2212,\n" +
                "    \"name\": \"eiusmod\",\n" +
                "    \"expirationDate\": \"03/08/16\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3068,\n" +
                "    \"name\": \"reprehenderit\",\n" +
                "    \"expirationDate\": \"07/03/16\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1290,\n" +
                "    \"name\": \"commodo\",\n" +
                "    \"expirationDate\": \"16/07/15\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3422,\n" +
                "    \"name\": \"aliquip\",\n" +
                "    \"expirationDate\": \"09/05/15\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1514,\n" +
                "    \"name\": \"sint\",\n" +
                "    \"expirationDate\": \"12/05/16\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3565,\n" +
                "    \"name\": \"consequat\",\n" +
                "    \"expirationDate\": \"17/07/16\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1938,\n" +
                "    \"name\": \"quis\",\n" +
                "    \"expirationDate\": \"01/12/15\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1334,\n" +
                "    \"name\": \"consectetur\",\n" +
                "    \"expirationDate\": \"26/11/15\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1038,\n" +
                "    \"name\": \"reprehenderit\",\n" +
                "    \"expirationDate\": \"29/12/15\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2561,\n" +
                "    \"name\": \"culpa\",\n" +
                "    \"expirationDate\": \"24/11/15\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2440,\n" +
                "    \"name\": \"sunt\",\n" +
                "    \"expirationDate\": \"19/02/15\"\n" +
                "  },\n" +
                "]");
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.AddItemButton: {
                try {
                    //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                    Intent intent;
                    intent = new Intent(this.getActivity(), AddItemActicity.class);
                    startActivity(intent);
                } catch (ActivityNotFoundException anfe) {}

//                getFragmentManager().beginTransaction()
//                        .replace(R.id.fragmentHolder, AddItemFragment.newInstance())
//                        .commit();
            }
        }
    }
}

