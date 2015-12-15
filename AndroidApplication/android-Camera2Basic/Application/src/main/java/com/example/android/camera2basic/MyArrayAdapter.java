package com.example.android.camera2basic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by dolevb on 27/11/2015.
 */
public class MyArrayAdapter extends ArrayAdapter<Item> {

        private final Context context;
        private final ArrayList<Item> itemsArrayList;

        public MyArrayAdapter(Context context, ArrayList<Item> itemsArrayList) {

            super(context, R.layout.item_row, itemsArrayList);

            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.item_row, parent, false);

            // 3. Get the two text view from the rowView
            TextView labelView = (TextView) rowView.findViewById(R.id.label);
            TextView valueView = (TextView) rowView.findViewById(R.id.value);

            // 4. Set the text for textView
            Item curItem = itemsArrayList.get(position);
            Calendar today = Calendar.getInstance();
            today.add(Calendar.DAY_OF_YEAR, -5);
            Log.i("as", today.toString());
            Date bla = today.getTime();

            if ( curItem.getExpirationDate().before(today.getTime()  )) {
                rowView.setBackgroundColor(0xFFDD0029);
            }
            labelView.setText(curItem.getTitle());
            valueView.setText(curItem.getDescription());

            // 5. retrn rowView
            return rowView;
        }



}
