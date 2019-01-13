package com.example.naqi.mobiledatabase;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naqi.mobiledatabase.InterfaceActionCondition.Action.Automessagingconcrete;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Action.Bluetoothconcrete;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Action.Gpsconcrete;

import com.example.naqi.mobiledatabase.InterfaceActionCondition.Action.Phonesettingconcrete;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Action.Wificoncrete;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Condition.Batteryconcrete;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Condition.Chargingconcrete;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Condition.Locationconcrete;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Condition.Timerconcrete;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Interface.IAction;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Interface.ICondition;
import com.google.android.gms.maps.model.LatLng;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UpdatePreset extends AppCompatActivity {

    ExpendAbleListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Presetconcrete p = null;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_preset);

        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // preparing list data
        prepareListData();
        name = (TextView) findViewById(R.id.name);
        Intent i = getIntent();

        name.setText("Preset Name: " + i.getStringExtra("preset name"));
        getobject(i.getStringExtra("preset name"));
        if (p != null) {
            listAdapter = new ExpendAbleListAdapter(this, listDataHeader, listDataChild, p);
        } else {
            Toast.makeText(MainActivity.getcontext(), "Preset Not Found", Toast.LENGTH_SHORT).show();
            finish();
        }
        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    void getobject(String name) {

        try {
            FileInputStream fileIn = new FileInputStream("/sdcard/com.automation/"+ name);
            ObjectInputStream in = new ObjectInputStream(fileIn); // deserilization
            p = (Presetconcrete) in.readObject(); //return object
            in.close();
            fileIn.close();


        } catch (IOException i) {
            i.printStackTrace();

        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
    }



    public void ok(View v) {

        remove();
        updatepreset();
        serializeobject();

        File f = new File(File_Path.preset_path); //"/sdcard/com.automation/temp/"
        deleteDirectory(f);

        finish();

    }


    public void serializeobject() {

        try {
            String s =  name.getText().toString();
            s = s.replace("Preset Name: ","");

            FileOutputStream fileOut =
                    new FileOutputStream("/sdcard/com.automation/"  +s);
            ObjectOutputStream out = new ObjectOutputStream(fileOut); //serialization
            out.writeObject(p);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void remove() {  //expandablelist remove cond and act

        ArrayList<ICondition> cond = (ArrayList<ICondition>) p.condition.clone();
        for (ICondition c : cond
                ) {


            if (!(listAdapter.condcheckbox.get(c.getname()))) {
                p.condition.remove(p.condition.indexOf(c));
            }

        }
        ArrayList<IAction> act = (ArrayList<IAction>) p.action.clone();
        for (IAction a : act
                ) {


            if (!(listAdapter.actcheckbox.get(a.getname()))) {
                p.action.remove(a);
            }
        }
    }


    public IAction getaction(String name)
    {
        for (IAction a : p.action
                ) {
            if (a.getname().equals(name)) {
                return a;
            }

        }
        return null;

    }
    public ICondition getcondition(String name) {

        for (ICondition c : p.condition
                ) {
            if (c.getname().equals(name)) {
                return c;
            }

        }
        return null;
    }

    static String MainFolderPath = File_Path.path;   //"/sdcard/com.automation/temp"
    static String ActionFolderPath = MainFolderPath + "/tempaction/";
    static String ConditionFolderPath = MainFolderPath + "/tempcondition/";

    public void updatepreset() {
        for (String s : listAdapter.condcheckbox.keySet()) {
            String news = s.replace("->  ", "");

            if (listAdapter.condcheckbox.get(s)) {
                File f = new File(ConditionFolderPath, news + ".txt");
                if (f.exists() && f.length() != 0) {

                    ICondition con = getcondition(s);

                    if (con != null) {
                        p.condition.remove(con);
                    }
                    addnewobject(news, "c");
                }
            }
        }
        for (String s : listAdapter.actcheckbox.keySet()
                ) {
            String news = s.replace("->  ", "");

            if (listAdapter.actcheckbox.get(s)) {
                File f = new File(ActionFolderPath, news + ".txt");
                if (f.exists() && f.length() != 0) {

                    IAction act = getaction(s);

                    if (act != null) {
                        p.action.remove(act);

                    }
                    addnewobject(news, "a");
                }
            }
        }
    }



    public void addnewobject(String nameofpreset, String yourchoice) {
        if (yourchoice == "c") {
            switch (nameofpreset) {
                case "Timer": {


                    File f = new File(ConditionFolderPath, "Timer.txt");
                    if (f.exists() && f.length() != 0) {


                        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                            String date = br.readLine();
                            String dat = date.substring(date.indexOf("=") + 1, date.length());
                            String time = br.readLine();
                            String tim = time.substring(time.indexOf("=") + 1, time.length());


                            p.condition.add(new Timerconcrete(dat, tim));


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }


                    break;
                }

                case "Battery": {


                    File f = new File(ConditionFolderPath, "Battery.txt");
                    if (f.exists() && f.length() != 0) {
                        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                            String level = br.readLine();
                            String lvl = level.substring(level.indexOf(":") + 1, level.length());
                            String choicee = br.readLine();


                            p.condition.add(new Batteryconcrete(choicee, Integer.parseInt(lvl)));


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    break;
                }

                case "Charging": {
                    File f = new File(ConditionFolderPath, "Charging.txt");
                    if (f.exists() && f.length() != 0) {
                        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                            String ch = br.readLine();
                            if (ch.equals("WhileCharging")) {

                                p.condition.add(new Chargingconcrete(true));
                            } else if (ch.equals("WhileNotCharging")) {

                                p.condition.add(new Chargingconcrete(false));

                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }

                case "Location": {
                    File f = new File(ConditionFolderPath, "Location.txt");
                    if (f.exists() && f.length() != 0) {
                        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                            String name = br.readLine();
                            String nam = name.substring(name.indexOf(":") + 1, name.length());
                            String latitude = br.readLine();
                            String lat = latitude.substring(latitude.indexOf(":") + 1, latitude.length());
                            String longitude = br.readLine();
                            String longi = longitude.substring(longitude.indexOf(":") + 1, longitude.length());
                            String radius = br.readLine();
                            double rad = Double.valueOf(radius.substring(radius.indexOf(":") + 1, radius.length()));
                            LatLng latlang = new LatLng(Double.valueOf(lat), Double.valueOf(longi));

                            p.condition.add(new Locationconcrete(rad, nam, Double.valueOf(lat), Double.valueOf(longi)));


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }

            }
        }

         else {
            switch (nameofpreset) {

                case "Wifi": {


                    File   f = new File(ActionFolderPath, "Wifi.txt");
                    if (f.exists() && f.length() != 0) {
                        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

                            String volstring = br.readLine();
                            if (volstring.matches("true")) {
                                p.action.add(new Wificoncrete(true));
                            } else {
                                p.action.add(new Wificoncrete(false));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    break;
                }

                case "Bluetooth": {

                    File  f = new File(ActionFolderPath, "Bluetooth.txt");
                    if (f.exists() && f.length() != 0) {
                        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

                            String volstring = br.readLine();
                            if (volstring.matches("true")) {
                                p.action.add(new Bluetoothconcrete(true));
                            } else {
                                p.action.add(new Bluetoothconcrete(false));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                }

                case "GPS": {

                    File  f = new File(ActionFolderPath, "GPS.txt");
                    if (f.exists() && f.length() != 0) {
                        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

                            String volstring = br.readLine();
                            if (volstring.matches("true")) {
                                p.action.add(new Gpsconcrete(true));
                            } else {
                                p.action.add(new Gpsconcrete(false));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                }

                case "Auto messaging": {
                    File f = new File(ActionFolderPath, "Auto messaging.txt");
                    if (f.exists() && f.length() != 0) {


                        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

                            ArrayList<String> num = new ArrayList<String>();
                            String message;
                            br.readLine();
                            String number = br.readLine();
                            while (!(number.equals("----------------------"))) {
                                num.add(number);
                                number = br.readLine();
                            }
                            br.readLine();
                            message = br.readLine();

                            p.action.add(new Automessagingconcrete(num, message));

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                    break;
                }

                case "Phone Settings": {

                    File f = new File(ActionFolderPath, "Phone Setting.txt");
                    if (f.exists() && f.length() != 0) {

                        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

                            String volstring = br.readLine();
                            double vol = Double.valueOf(volstring.substring(volstring.indexOf(":") + 1, volstring.length()));
                            String bristring = br.readLine();
                            int brightness = Integer.valueOf(bristring.substring(bristring.indexOf(":") + 1, bristring.length()));
                            String vibration = br.readLine();
                            boolean vibrate = false;
                            if (vibration.substring(vibration.indexOf(":") + 1, vibration.length()).equals("true")) {
                                vibrate = true;
                            }
                            p.action.add(new Phonesettingconcrete(vol, brightness, vibrate));

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        }
    }


    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    private void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("    Conditions");
        listDataHeader.add("    Actions");

        // Adding child data
        List<String> conditions = new ArrayList<String>();
        conditions.add("->  Timer");
        conditions.add("->  Battery");
        conditions.add("->  Charging");
        conditions.add("->  Location");

        List<String> actions = new ArrayList<String>();
        actions.add("->  Wifi");
        actions.add("->  Bluetooth");
        actions.add("->  GPS");
        actions.add("->  Auto messaging");
        actions.add("->  Phone Settings");

        listDataChild.put(listDataHeader.get(0), conditions); // Header, Child data
        listDataChild.put(listDataHeader.get(1), actions);

    }
}
