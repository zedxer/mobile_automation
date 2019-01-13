package com.example.naqi.mobiledatabase;

import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static com.example.naqi.mobiledatabase.UpdatePreset.deleteDirectory;

public class FinalPage extends AppCompatActivity {

    EditText presetname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page);
        presetname = (EditText) findViewById(R.id.presetname);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createpreset(View v) {

        //"/sdcard/com.lacalprofile2/temp/"

        File f = new File(File_Path.preset_path, "PresetName.txt");   //"/sdcard/com.automation/temp/"
        if (f.exists() && f.length() != 0) {

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                ObjectBuilder.buildpresetobject(br.readLine().toString());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //"/sdcard/com.lacalprofile2/temp/"
            f = new File(File_Path.preset_path);   //"/sdcard/com.automation/temp/"
            deleteDirectory(f);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

    }

    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();

                }
            }
        }
        return( path.delete() );
    }
}
