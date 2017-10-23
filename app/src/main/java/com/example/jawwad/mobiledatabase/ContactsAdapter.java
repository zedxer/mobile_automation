package com.example.jawwad.mobiledatabase;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jawwad on 12/11/2016.
 */

public class ContactsAdapter  extends ArrayAdapter<String> {

    private final Context context;
    private final String[][] values;
    private boolean[] stars;
    Bitmap img;
    List<String> selectedname;

    public ContactsAdapter(Context context, String[][] values , Bitmap img) {
        super(context, R.layout.contactlayout, getNameArray(values));
        this.context = context;
        this.values = values;
         this.img = img;
        this.stars = new boolean[values.length];
        selectedname = new ArrayList<>();


    }

    public static String[] getNameArray(String[][] ctct) {
        String[] arr = new String[ctct[0].length];
        for (int i = 0; i < ctct[0].length; i++) {
            arr[i] = ctct[0][i];
        }
        return arr;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.contactlayout, parent, false);
        final TextView textView = (TextView) rowView.findViewById(R.id.name);
        final TextView textView1 = (TextView) rowView.findViewById(R.id.num);
        final CheckBox ch = (CheckBox) rowView.findViewById(R.id.IsToRun);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.app_icon);
        textView.setText(values[0][position]);
        textView1.setText(values[1][position]);
        imageView.setImageBitmap(img);
        ch.setChecked(false);
        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ch.isChecked()) {
                    // Toast.makeText(context, textView.getText().toString(), Toast.LENGTH_LONG).show();
                    selectedname.add(textView.getText().toString() + "\n" + textView1.getText().toString());
                } else {
                    selectedname.remove(textView.getText().toString() + "\n" + textView1.getText().toString());

                }

            }
        });
        // Change the icon for Windows and iPhone
        // String s = values[position];
        //  if (s.startsWith("Windows7") || s.startsWith("iPhone")
        //   || s.startsWith("Solaris")) {


        return rowView;
    }

    public List<String> getSelectedname() {

        return selectedname;
    }

}
