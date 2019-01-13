package com.example.naqi.mobiledatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Naqi on 12/11/2016.
 */

public class SelectedContactAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;

    public SelectedContactAdapter(Context context, String[] values) {
        super(context, R.layout.simplerow, values);
        this.context = context;
        this.values = values;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.simplerow, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.numname);  //contactlist view numbers
        textView.setText(values[position]);


        return rowView;
    }
}
