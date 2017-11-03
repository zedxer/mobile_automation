package com.example.jawwad.mobiledatabase;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
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

public class ActionPageAdapter  extends ArrayAdapter<String>{

    private final Context context;
    private final String[] values;
    static boolean checkChecked;
    public ActionPageAdapter(Context context, String[] values) {
        super(context, R.layout.commonlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.commonlayout, parent, false);
        final TextView textView = (TextView) rowView.findViewById(R.id.name);
        final ImageView icon = (ImageView) rowView.findViewById(R.id.con_icon);
        final CheckBox ch = (CheckBox) rowView.findViewById(R.id.IsToRun);

        boolean checked = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("checkBox1", false);
        ch.setChecked(checked);

        textView.setText(values[position]);

        if (values[position] == "Wifi") {
            icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.wifiico));
            icon.invalidate();
        }

        else  if (values[position] == "Bluetooth")
        {
            icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.blutoothico));
            icon.invalidate();
        }

        else  if (values[position] == "GPS")
        {
            icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.locationico));
            icon.invalidate();
        }
        else if (values[position] == "Auto messaging")
        {
            icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.messageico));
        }
        else if (values[position] == "Phone Settings")
        {
            icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.settingico));
            icon.invalidate();
        }

        ch.setChecked(false);
        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                        checkChecked = true;
                    File mytextfile = new File(File_Path.action_path, textView.getText().toString() + ".txt");
                    try {

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
                    //"/sdcard/com.lacalprofile2/temp/temptask"
                    checkChecked = false;

               //     String path = Environment.getExternalStorageDirectory().getPath() + "com.automation/temp/tempaction";
                    File mytextfile = new File(File_Path.action_path, textView.getText().toString() + ".txt");

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
                    f = new Intent(context, Wifi.class);
                //    context.startActivity(f);

                }
                else  if (textView.getText() == values[1]) {
                    ch.setChecked(true);
                    f = new Intent(context, BlueTooth.class);
               //     context.startActivity(f);

                }
                else  if (textView.getText() == values[2]) {
                    ch.setChecked(true);
                    f = new Intent(context, GPS.class);
               //     context.startActivity(f);

                }
                else  if (textView.getText() == values[3]) {
                    ch.setChecked(true);
                    f = new Intent(context, Automessaging.class);
               //     context.startActivity(f);

                }
                else  if (textView.getText() == values[4]) {
                    ch.setChecked(true);
                    f = new Intent(context, Phonesetting.class);
                //    context.startActivity(f);

                }

                else {
                    Toast.makeText(context, "Something goes wrong!",
                            Toast.LENGTH_LONG).show();
                }
                context.startActivity(f);
            }


        });

        return rowView;

    }


}
