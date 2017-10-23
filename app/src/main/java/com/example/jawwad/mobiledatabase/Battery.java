package com.example.jawwad.mobiledatabase;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Battery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        final SeekBar seekbar = (SeekBar) findViewById(R.id.battery);
        final TextView percentage = (TextView) findViewById(R.id.percentage);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar bar) {
                int value = bar.getProgress(); // the value of the seekBar progress
            }

            public void onStartTrackingTouch(SeekBar bar) {

            }

            public void onProgressChanged(SeekBar bar,
                                          int paramInt, boolean paramBoolean) {
                percentage.setText("" + paramInt + "%"); // here in textView the percent will be shown
            }
        });


    }

    public void GoBack(View v) {
        // get some data befor finishing this activity
        File mytextfile = new File(File_Path.condition_path, "Battery.txt");
        try {
//"/sdcard/com.automation/temp/tempcondition"
            if (!mytextfile.exists()) {
                mytextfile.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter myoutwriter = new FileWriter(mytextfile);
            SeekBar bat = (SeekBar) findViewById(R.id.battery);
            myoutwriter.append("Battery:" + bat.getProgress() + "\n");
            RadioButton less = (RadioButton) findViewById(R.id.lessthan);
            RadioButton greater = (RadioButton) findViewById(R.id.greaterthan);
            if (less.isChecked()) {
                myoutwriter.append("LessThan");
            } else {
                myoutwriter.append("GreaterThan");
            }

            myoutwriter.close();

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        }


        finish();
    }


}
