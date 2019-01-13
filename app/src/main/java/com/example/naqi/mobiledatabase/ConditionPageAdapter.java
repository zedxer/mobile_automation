package com.example.jawwad.mobiledatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Naqi on 11/11/2016.
 */

public class ConditionPageAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;
    private CheckBox ch;

    public ConditionPageAdapter(Context context, String[] values) {
        super(context, R.layout.commonlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.commonlayout, parent, false);

        final TextView textView = (TextView) rowView.findViewById(R.id.name);
        final ImageView icon = (ImageView) rowView.findViewById(R.id.con_icon);
        ch = (CheckBox) rowView.findViewById(R.id.IsToRun);

       /* SharedPreferences settings = context.getSharedPreferences("mysettings", 0);
        SharedPreferences.Editor editor = settings.edit();

// Save
        boolean checkBoxValue = ch.isChecked();
        editor.putBoolean("ch", checkBoxValue);
        editor.commit();

// Load
        ch.setChecked(settings.getBoolean("ch", true));*/


        //final ImageView imageView = (ImageView) rowView.findViewById(R.id.app_icon);
        textView.setText(values[position]);
        if (textView.getText() == "Timer") {
            icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher1));
        } else if (textView.getText() == "Battery") {
            icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.batteryico));
        } else if (textView.getText() == "Charging") {
            icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.chargingico));
        } else if (textView.getText() == "Location") {
            icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.locationico));
        }

        ch.setChecked(false);
        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// create directory
                //  String path = Environment.getExternalStorageDirectory().getPath() + "/temp/tempcondition";
                if (isChecked) {


                    File mytextfile = new File(File_Path.condition_path, textView.getText().toString() + ".txt");
                    try {
//"/sdcard/com.automation/temp/tempcondition"
                        if (!mytextfile.exists()) {
                            mytextfile.createNewFile();
                        } else {
                            mytextfile.delete();
                            mytextfile.createNewFile();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    File mytextfile = new File(File_Path.condition_path, textView.getText().toString() + ".txt");

                    if (mytextfile.exists()) {
                        mytextfile.delete();
                    }

                }

            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent f = null;
                if (textView.getText() == values[0]) {
                    ch.setChecked(true);
                    f = new Intent(context, Timer.class);
                    //   context.startActivity(f);
                    //  ch.setChecked(true);
                } else if (textView.getText() == values[1]) {
                    ch.setChecked(true);
                    f = new Intent(context, Battery.class);
                    //   context.startActivity(f);
                    //  ch.setChecked(true);
                } else if (textView.getText() == values[2]) {
                    ch.setChecked(true);
                    f = new Intent(context, Charging.class);
                    // context.startActivity(f);
                    //   ch.setChecked(true);

                } else if (textView.getText() == values[3]) {
                    ch.setChecked(true);
                    f = new Intent(context, Location.class);
                } else {
                    Toast.makeText(context, "Something goes wrong!",
                            Toast.LENGTH_LONG).show();
                }
                context.startActivity(f);
            }


        });

        return rowView;
    }


}
