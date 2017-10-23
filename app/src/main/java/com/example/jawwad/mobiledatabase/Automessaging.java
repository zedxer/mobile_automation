package com.example.jawwad.mobiledatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Automessaging extends AppCompatActivity {


    ListView v;
    String[] contacts = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 1:
                if (resultCode == RESULT_OK) {

                    contacts = (String[]) data.getStringArrayExtra("selectedcontact");
                    SelectedContactAdapter adapter = new SelectedContactAdapter(getApplicationContext(), contacts);
                    v.setAdapter(adapter);
                }
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automessaging);
        v = (ListView) findViewById(R.id.contactlist);
    }

    public void SelectContactClick(View v) {
        Intent selectContact = new Intent(this, Contactlist.class);
        startActivityForResult(selectContact, 1);


    }


    public void GoBack(View v) {
        // get some data befor finishing this activity
        File mytextfile = new File(File_Path.action_path,"Auto messaging.txt");
        try {
//"/sdcard/com.automation/temp/tempaction"
            if(! mytextfile.exists())
            {
                mytextfile.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();}
        try {
            //  FileOutputStream fout = new FileOutputStream(mytextfile);
            FileWriter myoutwriter = new FileWriter(mytextfile);
            myoutwriter.append("Numbers:" + "\n");

            if(contacts == null)
            {
                Toast.makeText(this,"Select Atleast One contact",Toast.LENGTH_SHORT).show();

            }
            else {
                for (int i = 0; i < contacts.length; i++) {
                    myoutwriter.append(contacts[i].substring((contacts[i].indexOf("\n") + 1), (contacts[i].length())) + "\n");
                }
                myoutwriter.append("----------------------" + "\n");
                myoutwriter.append("Message:" + "\n");
                EditText msg = (EditText) findViewById(R.id.message);
                myoutwriter.append(msg.getText().toString());


                myoutwriter.close();
                finish();
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException ee) {
            ee.printStackTrace();
        }


    }




}
