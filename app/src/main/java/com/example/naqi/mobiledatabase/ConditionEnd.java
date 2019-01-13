package com.example.naqi.mobiledatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConditionEnd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_end);
    }

    public  void GotoAction(View v)
    {
        Intent action = new Intent(this, ListofAction.class);
        startActivity(action);
    }

}
