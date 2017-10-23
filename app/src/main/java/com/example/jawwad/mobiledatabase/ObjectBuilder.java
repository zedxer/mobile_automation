package com.example.jawwad.mobiledatabase;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Action.Automessagingconcrete;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Action.Bluetoothconcrete;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Action.Gpsconcrete;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Action.Phonesettingconcrete;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Action.Wificoncrete;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Condition.Batteryconcrete;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Condition.Chargingconcrete;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Condition.Locationconcrete;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Condition.Timerconcrete;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface.IAction;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface.ICondition;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Jawwad on 11/11/2016.
 */

public class ObjectBuilder {

    private static String MainFolderPath = File_Path.path;  //"/sdcard/com.automation/temp"
    private static String ActionFolderPath = MainFolderPath + "/tempaction/";
    private static String ConditionFolderPath = MainFolderPath + "/tempcondition/";
    private static ArrayList<ICondition> condition = new ArrayList<>();
    private static ArrayList<IAction> action = new ArrayList<>();
    private static Presetconcrete p;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void buildconditionsobjects() {

       File f = new File(ConditionFolderPath, "Timer.txt");
        if (f.exists() && f.length() != 0) {


            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String date = br.readLine();
                String dat = date.substring(date.indexOf("=") + 1, date.length());
                String time = br.readLine();
                String tim = time.substring(time.indexOf("=") + 1, time.length());


                condition.add(new Timerconcrete(dat, tim));


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        f = new File(ConditionFolderPath, "Battery.txt");
        if (f.exists() && f.length() != 0) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String level = br.readLine();
                String lvl = level.substring(level.indexOf(":") + 1, level.length());
                String choice = br.readLine();


                condition.add(new Batteryconcrete(choice, Integer.parseInt(lvl)));


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        f = new File(ConditionFolderPath, "Charging.txt");
        if (f.exists() && f.length() != 0) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String ch = br.readLine();
                if (ch.equals("WhileCharging")) {

                    condition.add(new Chargingconcrete(true));
                } else if (ch.equals("WhileNotCharging")) {

                    condition.add(new Chargingconcrete(false));

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //for location
        f = new File(ConditionFolderPath, "Location.txt");
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

                condition.add(new Locationconcrete(rad, nam, Double.valueOf(lat), Double.valueOf(longi)));


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void buildactionobjects() {

       File f = new File(ActionFolderPath, "Wifi.txt");
        if (f.exists() && f.length() != 0) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {

                String volstring = br.readLine();
                if (volstring.matches("true")) {
                    action.add(new Wificoncrete(true));
                } else {
                    action.add(new Wificoncrete(false));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        f = new File(ActionFolderPath, "Bluetooth.txt");
        if (f.exists() && f.length() != 0) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {

                String volstring = br.readLine();
                if (volstring.matches("true")) {
                    action.add(new Bluetoothconcrete(true));
                } else {
                    action.add(new Bluetoothconcrete(false));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        f = new File(ActionFolderPath, "GPS.txt");
        if (f.exists() && f.length() != 0) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {

                String volstring = br.readLine();
                if (volstring.matches("true")) {
                    action.add(new Gpsconcrete(true));
                } else {
                    action.add(new Gpsconcrete(false));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

         f = new File(ActionFolderPath, "Auto messaging.txt");
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
                action.add(new Automessagingconcrete(num, message));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        f = new File(ActionFolderPath, "Phone Setting.txt");
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

                action.add(new Phonesettingconcrete(vol, brightness, vibrate));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }





    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void buildpresetobject(String name) {

        buildactionobjects();
        buildconditionsobjects();

        p = new Presetconcrete(condition, action, name);

        condition = new ArrayList<ICondition>();
        action = new ArrayList<IAction>();
        try {

            FileOutputStream fileOut = new FileOutputStream("/sdcard/com.automation/" + name);  //.ser
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(p);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
