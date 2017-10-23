package com.example.jawwad.mobiledatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {


    static Context contx;
    Switch status;
    public static ListView list;
    Intent i;
    Handler handler;
    public static GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status = (Switch) findViewById(R.id.switch1);
        list = (ListView) findViewById(R.id.listofpresets);

        File Maindir = new File("/sdcard/com.automation/");

        if (!Maindir.exists()) {
            Maindir.mkdir();
        }

        gps = new GPSTracker(getApplicationContext());  //gps

        Toast.makeText(this, String.valueOf(gps.getCurrentSpeed()), Toast.LENGTH_SHORT).show(); //gps

            MainPageAdapter adapter = new MainPageAdapter(this, addpreset());
            list.setAdapter(adapter);
        MainService.cntxt = getApplicationContext();

        contx = getApplicationContext();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                Sensorconcrete.notifypreset();

                Toast.makeText(getApplicationContext(), "service started again", Toast.LENGTH_SHORT).show();
            }
        };
        i = new Intent(getApplicationContext(), MainService.class);
        status.setChecked(false);

        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {

                    MainService.con = true;
                    i.putExtra("messenger", new Messenger(handler));
                    startService(i);
                    Toast.makeText(getApplicationContext(), "service started", Toast.LENGTH_SHORT).show();
                } else {


                    MainService.con = false;
                    stopService(i);

                    Toast.makeText(getApplicationContext(), "service stoped", Toast.LENGTH_SHORT).show();
                }
            }});
    }

      private ArrayList<String> addpreset() {  // list of presets shows on main page

        ArrayList<String> presetnames = new ArrayList<>();

        File yourDir = new File("/sdcard/com.automation/");

        for (File f : yourDir.listFiles()) {
            if (f.isFile()) {
                String name = f.getName();

                presetnames.add(name);

            }
            // make something with the name
        }
        return presetnames;

    }

    public void CreateNewPreset(View v) { //new presets
        Intent newPreset = new Intent(this, NewPresetActivity.class);
        startActivity(newPreset);
        File subdir = new File(File_Path.path);  //"/sdcard/com.automation/temp"
        if (!subdir.exists()) {
            subdir.mkdir();

        }
        File subdir1 = new File(File_Path.condition_path);
        if (!subdir1.exists()) {
            subdir1.mkdir();
//"/sdcard/com.automation/temp/tempcondition"
        }
        File subdir2 = new File(File_Path.action_path);
        if (!subdir2.exists()) {
            subdir2.mkdir();
//"/sdcard/com.automation/temp/tempaction"
        }
    }

    public static Context getcontext() {
        return contx;

    }

    @Override
    public void update(Observable observable, Object data) {
       startVoiceRecognitionActivity();
    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
        startActivityForResult(intent, 1234);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        super.onStop();

    }


}
