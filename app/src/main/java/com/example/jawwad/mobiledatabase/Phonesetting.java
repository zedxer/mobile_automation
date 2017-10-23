package com.example.jawwad.mobiledatabase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Phonesetting extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonesetting);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return false;
    }

    public void GoBack(View v) {
        File mytextfile = new File(File_Path.action_path, "Phone Setting.txt");
        try {
//"/sdcard/com.automation/temp/tempaction"
            if (!mytextfile.exists()) {
                mytextfile.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            FileWriter myoutwriter = new FileWriter(mytextfile);
            SeekBar vol = (SeekBar) findViewById(R.id.volumeseekBar);


            SeekBar bri = (SeekBar) findViewById(R.id.brightnessseekBar);

            Switch vib = (Switch) findViewById(R.id.vibrationswitch);

            myoutwriter.append("Volume:" + vol.getProgress() + "\n");
            myoutwriter.append("Brightness:" + bri.getProgress() + "\n");
            myoutwriter.append("Vibration:" + vib.isChecked() + "\n");

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
