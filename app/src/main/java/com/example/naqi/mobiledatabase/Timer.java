package com.example.naqi.mobiledatabase;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Timer extends AppCompatActivity {

    CheckBox daily;
    String[] Month = {"January","February","March","April","May","June","July","August","September","October","November","December"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        daily = (CheckBox) findViewById(R.id.daily);
        final DatePicker datepicker = (DatePicker) findViewById(R.id.datePicker);
        daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (daily.isChecked()) {

                    datepicker.setEnabled(false);
                } else {
                    datepicker.setEnabled(true);

                }

            }
        });
    }

    public void GoBack(View v) {
        // get some data befor finishing this activity
        File mytextfile = new File(File_Path.condition_path,"Timer.txt");
        try {
//"/sdcard/com.automation/temp/tempcondition"
            if(!mytextfile.exists())
            {
                mytextfile.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();}
        try {
            FileOutputStream fout = new FileOutputStream(mytextfile);
            OutputStreamWriter myoutwriter = new OutputStreamWriter(fout);
            String date;
            if(!daily.isChecked())
            {
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
                int day = (int)datePicker.getDayOfMonth();
                int month = (int)datePicker.getMonth() ;
                int year =  (int)datePicker.getYear();
                date = "Date="+ String.valueOf(day)+":"+Month[month]+":"+String.valueOf(year);

            }
            else
            {
                date = "Date=null";

            }

            TimePicker timepicker = (TimePicker) findViewById(R.id.timePicker);
            final int[] hour = {(int)  timepicker.getCurrentHour()};
            final int[] minut = {(int) timepicker.getCurrentMinute()};

            timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                    hour[0] = hourOfDay;
                    minut[0] = minute;
                }
            });
            myoutwriter.append(date+"\n");
            myoutwriter.append("Time="+String.valueOf(hour[0]+":"+String.valueOf(minut[0])));

            // Toast.makeText(getApplicationContext(),date+" "+String.valueOf(hour[0])+String.valueOf(minut[0]),Toast.LENGTH_LONG).show();


            myoutwriter.close();
            fout.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        }


        finish();
    }






}
