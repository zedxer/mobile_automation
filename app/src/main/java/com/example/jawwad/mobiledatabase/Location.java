package com.example.jawwad.mobiledatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static com.example.jawwad.mobiledatabase.File_Path.condition_path;

public class Location extends AppCompatActivity {

    TextView lat;
    TextView lang;
    TextView rad;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        lat = (TextView) findViewById(R.id.latval);
        lang = (TextView) findViewById(R.id.langval);
        rad = (TextView) findViewById(R.id.radval);
        name = (TextView) findViewById(R.id.namval);
    }

    public void GoBack(View v) {

        if(!(name.getText().toString().matches("")))
        {


            File mytextfile = new File(condition_path, "Location.txt");
            try {

                if (!mytextfile.exists()) {

                    mytextfile.createNewFile();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                //  FileOutputStream fout = new FileOutputStream(mytextfile);
                FileWriter myoutwriter = new FileWriter(mytextfile);


                myoutwriter.append("Name:" + name.getText() + "\n");
                myoutwriter.append("Latitude:" + lat.getText() + "\n");
                myoutwriter.append("Longitude:" + lang.getText() + "\n");
                myoutwriter.append("Radius:" + rad.getText());


                myoutwriter.close();



            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }
        finish();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 1:
                if (resultCode == RESULT_OK) {

                    lang.setText(data.getStringExtra("Longitude"));
                    lat.setText(data.getStringExtra("Latitude"));
                    rad.setText(data.getStringExtra("Radius"));
                    name.setText(data.getStringExtra("Name"));


                }
                break;

        }
    }

        public void CreatCLick(View v) {
            Intent newL = new Intent(this, Createnewlocation.class);
            startActivityForResult(newL, 1);
        }


    }



