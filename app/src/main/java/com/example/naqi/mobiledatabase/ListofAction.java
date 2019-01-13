package com.example.jawwad.mobiledatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ListofAction extends AppCompatActivity {

    ActionPageAdapter adp ;
    String[] tvalues = {"Wifi","Bluetooth","GPS","Auto messaging","Phone Settings"};
    ListView list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_action);

        adp = new ActionPageAdapter(this,tvalues);
        list = (ListView) findViewById(R.id.listView3);
        list.setAdapter(adp);
    }

    public void  GoBack(View v)
    {
        Intent bck = new Intent(this,ConditionEnd.class);
        startActivity(bck);
    }


    public void  GoToNext(View v)
    { if(ActionPageAdapter.checkChecked) {
        Intent go = new Intent(this, FinalPage.class);
        startActivity(go);
    }else Toast.makeText(this, "At-least check on condition to create preset", Toast.LENGTH_SHORT).show();


    }







}
