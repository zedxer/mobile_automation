package com.example.jawwad.mobiledatabase.InterfaceActionCondition.Action;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface.IAction;

/**
 * Created by Jawwad on 11/7/2016.
 */

public class Wificoncrete implements IAction{

    boolean ison;


    public  Wificoncrete(boolean isonv) {

        this.ison = isonv;


    }


    @Override
    public String getname() {
        return "->  Wifi";
    }

    @Override
    public void applyactions(Context c) {

        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        if (ison) {


            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
        }

        else
        {

            if (wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);
            }


        }
    }

    @Override
    public String getactiondetail() {
        String detail = "Wifi: ";
        if(this.ison)
        {

            detail+="On";

        }
        else
        {
            detail+="Off";
        }

        return detail;
    }

}
