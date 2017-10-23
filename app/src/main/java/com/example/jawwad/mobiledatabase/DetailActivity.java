package com.example.jawwad.mobiledatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        t = (TextView) findViewById(R.id.detail);

        Intent intent = getIntent();
        String details = intent.getExtras().getString("details");
        t.setText(details);
    }

    public  void Close(View v)
    {

        finish();

    }
}
