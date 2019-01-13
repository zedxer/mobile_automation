package com.example.naqi.mobiledatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class NewPreset extends AppCompatActivity {

    String[] Conditions = {"Timer","Battery","Charging","Location"};
    ConditionPageAdapter adp;
    ListView list;
    EditText presetname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_preset);

        adp = new ConditionPageAdapter(this, Conditions);
        list = (ListView)findViewById(R.id.listView2);

        list.isInEditMode();
        list.setAdapter(adp);

        presetname = (EditText) findViewById(R.id.presetname);

    }

    public void  GoToNext(View v) {

        if (presetname.getText().toString().matches("")) {

            Toast.makeText(this, "Please Enter Preset Name", Toast.LENGTH_SHORT).show();

        } else {
            File mytextfile = new File(File_Path.preset_path,"PresetName.txt"); //"/sdcard/com.automation/temp/"
            try {

                if (!mytextfile.exists()) {
                    mytextfile.createNewFile();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileWriter myoutwriter = new FileWriter(mytextfile);
                myoutwriter.append(presetname.getText());
                myoutwriter.close();
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            catch (IOException ee) {
                ee.printStackTrace();
            }

            File delsubdir = new File("/sdcard/com.automation/temp/tempcondition/");
            deleteDirectory(delsubdir,true);
            Intent go = new Intent(this, ConditionEnd.class);

            startActivity(go);

        }
    }

    public static boolean deleteDirectory(File path, boolean OnlyEmpty) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i],OnlyEmpty);
                }
                else {
                    if (OnlyEmpty) {
                        if (files[i].length() == 0) {
                            files[i].delete();
                        }
                    }
                    else
                    {
                        files[i].delete();
                    }
                }
            }
        }
        return( path.delete() );
    }

    public void  GoToMainPage(View v)
    {
        File delsubdir = new File(File_Path.preset_path);  //"/sdcard/com.automation/temp/"
        deleteDirectory(delsubdir,false);
        finish();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                File delsubdir = new File(File_Path.preset_path);  //"/sdcard/com.automation/temp/"
                deleteDirectory(delsubdir,false);
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
