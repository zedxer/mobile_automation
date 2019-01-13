package com.example.naqi.mobiledatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class GPS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
    }
    public void GoBack(View v) {


        File mytextfile = new File(File_Path.action_path ,"GPS.txt");
        try {
//"/sdcard/com.automation/temp/tempaction"
            if (!mytextfile.exists()) {
                mytextfile.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //  FileOutputStream fout = new FileOutputStream(mytextfile);
            FileWriter myoutwriter = new FileWriter(mytextfile);
            RadioButton gps = (RadioButton) findViewById(R.id.on);
            if (gps.isChecked()) {
                myoutwriter.append("true");
            } else {
                myoutwriter.append("false");
            }

            myoutwriter.close();

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        }

        // get some data befor finishing this activity
        finish();
    }


}
