package com.example.android.camera2basic;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        String balbla = Utils.getDataFromUrl("http://10.0.0.1:8080/FinalProjectServer/newjsp.jsp?action=getUserList&id=1");
                //"[   {     \"id\": \"123\",     \"name\": \"קוטג תנובה 5%\",     \"expirationDate\": \"12/12/12\"   },   {     \"id\": \"432\",     \"name\": \"חלב תנובה 3%\",     \"expirationDate\": \"05/12/15\"   },   {     \"id\": \"6812\",     \"name\": \"גבינת עמק 27%\",     \"expirationDate\": \"28/12/15\"   },   {     \"id\": \"1233\",     \"name\": \"נוטלה\",     \"expirationDate\": \"30/12/16\"   },   {     \"id\": \"6812\",     \"name\": \"גבינת עמק 28%\",     \"expirationDate\": \"28/11/15\"   },   {     \"id\": \"434\",     \"name\": \"חלב תנובה 3%\",     \"expirationDate\": \"10/12/15\"   } ] "
Log.d("camera2basic", balbla);
       return Utils.parseItemsList(balbla.replaceAll("(?m)^[ \t]*\r?\n", "").trim());
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

