package com.example.jawwad.mobiledatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jawwad.mobiledatabase.model.ConditionDetails;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class NewPresetActivity  extends AppCompatActivity {
    private ArrayList<ConditionDetails> conditionDetailsArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ConditonAdapter mAdapter;
    private EditText presetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_new);
//        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.listView2);
        presetName = (EditText) findViewById(R.id.presetname);

        mAdapter = new ConditonAdapter(this, conditionDetailsArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareAdminData();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ConditionDetails conditionDetails = conditionDetailsArrayList.get(position);
//                Toast.makeText(getApplicationContext(), adminDetails.getAdmin_name() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent f = null;
                    if(conditionDetails.getName() == "Timer"){
//                        ConditonAdapter.checkBox.setChecked(true);

                        conditionDetails.setIschecked(true);
                        f = new Intent(NewPresetActivity.this, Timer.class);
                    }
                    else if (conditionDetails.getName() == "Battery") {
                        conditionDetails.setIschecked(true);
                        f = new Intent(NewPresetActivity.this, Battery.class);
                        //   context.startActivity(f);
                        //  ch.setChecked(true);
                    } else if (conditionDetails.getName() == "Charging") {
                        conditionDetails.setIschecked(true);
                        f = new Intent(NewPresetActivity.this, Charging.class);
                        // context.startActivity(f);
                        //   ch.setChecked(true);

                    } else if (conditionDetails.getName() == "Location") {
                        conditionDetails.setIschecked(true);
                        f = new Intent(NewPresetActivity.this, Location.class);
                    } else {
                        Toast.makeText(NewPresetActivity.this, "Something goes wrong!",
                                Toast.LENGTH_LONG).show();
                    }
                NewPresetActivity.this.startActivity(f);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
    public void  GoToNext(View v) {

        if (presetName.getText().toString().matches("")) {

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
                myoutwriter.append(presetName.getText());
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







    private void prepareAdminData() {
        ConditionDetails conditionDetails = new ConditionDetails("Timer",this.getResources().getDrawable(R.mipmap.ic_launcher1));
        conditionDetailsArrayList.add(conditionDetails);
        conditionDetails = new ConditionDetails("Battery",this.getResources().getDrawable(R.mipmap.batteryico));
        conditionDetailsArrayList.add(conditionDetails);
        conditionDetails = new ConditionDetails("Charging",this.getResources().getDrawable(R.mipmap.chargingico));
        conditionDetailsArrayList.add(conditionDetails);
        conditionDetails = new ConditionDetails("Location",this.getResources().getDrawable(R.mipmap.locationico));
        conditionDetailsArrayList.add(conditionDetails);


        mAdapter.notifyDataSetChanged();
    }
}