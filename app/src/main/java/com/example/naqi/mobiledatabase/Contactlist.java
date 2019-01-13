package com.example.jawwad.mobiledatabase;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Contactlist extends AppCompatActivity {

    Bitmap img;
    ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);
        ListView list = (ListView) findViewById(R.id.Fulllist);
        adapter = new ContactsAdapter(this, this.getContats(),img);
        list.setAdapter(adapter);
    }

    public String[][] getContats() {
        ContentResolver cr = getContentResolver();
        String[][] Contacts;
        String image_uri = "";
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        String id = null, name = null, phone = null, note = null, orgName = null, title = null;
        String Phone1 = "unknown", Phone2 = "unknown", Phone3 = "unknown", type1 = "unknown", type2 = "unknown", type3 = "unknown";
        int size = cur.getCount();
        Contacts = new String[2][size];
        if (cur.getCount() > 0) {
            int cnt = 0;
            while (cur.moveToNext()) {
                name = "";

                id = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts._ID));
                name = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Contacts[0][cnt] = name;
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[]{id},
                            null);

                    Phone1 = " ";
                    Phone2 = " ";
                    Phone3 = " ";
                    assert pCur != null;
                    while ( pCur.moveToNext()) {
                        String phonetype = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        String MainNumber = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (phonetype.equalsIgnoreCase("1")) {
                            Phone1 = MainNumber;
                            type1 = "home";
                        } else if (phonetype.equalsIgnoreCase("2")) {
                            Phone2 = MainNumber;
                            type2 = "mobile";
                        } else {
                            Phone3 = MainNumber;
                            type3 = "work";
                        }
                    }
                    pCur.close();
                    if (Phone1 != " ") {
                        Contacts[1][cnt] = Phone1;
                    } else if (Phone2 != " ") {
                        Contacts[1][cnt] = Phone2;
                    } else if (Phone3 != " ") {
                        Contacts[1][cnt] = Phone3;
                    } else {
                        Contacts[1][cnt] = "No Number";
                    }


                }
                image_uri = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                if (image_uri != null) {
                    try {
                        img = MediaStore.Images.Media
                                .getBitmap(getContentResolver(),
                                        Uri.parse(image_uri));
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
                cnt++;
            }

        }
        return Contacts;
    }


    public void GoToBack(View v) {


        List<String> selectedname = adapter.getSelectedname();

        String[] arr =  getcontact(selectedname);
        Intent i = getIntent();
        i.putExtra("selectedcontact",arr);

        setResult(RESULT_OK, i);
        //save some data
        finish();
    }
    public  String[] getcontact(List<String> n)
    {

        String[]  contacts = new  String[n.size()];
        int count = 0;
        for (String s: n) {
            contacts[count] = s;
            count++;
        }
        return contacts;
      /*  String[][]  contacts = new  String[n.size()][2];
        int count = 0;
        for (String s: n) {
            contacts[count][0] = s.substring(0,(s.indexOf(':') - 1));
            contacts[count][1] = s.substring((s.indexOf(':') + 1),(s.length() - 1));
            count++;
        }
        return contacts;*/
    }

}
