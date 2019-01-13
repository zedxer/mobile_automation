package com.example.naqi.mobiledatabase.InterfaceActionCondition.Action;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.example.naqi.mobiledatabase.InterfaceActionCondition.Interface.IAction;

/**
 * Created by naqi on 11/28/2016.
 */

public class Bluetoothconcrete implements IAction {

    boolean ison;


    public  Bluetoothconcrete(boolean isonv)
    {

        this.ison = isonv;


    }
    @Override
    public String getname() {
        return "->  Bluetooth";
    }

    @Override
    public void applyactions(Context c) {
        BluetoothAdapter blue = BluetoothAdapter.getDefaultAdapter();
        if(this.ison)
        {


            if (!blue.isEnabled()) {
                blue.enable();
            }


        }
        else {

            if (blue.isEnabled()) {
                blue.disable();
            }

        }
    }

    @Override
    public String getactiondetail() {
        String detail= "Bluetooth: ";
        if (ison)
        {
            detail+= "On";

        }
        else
        {
            detail+= "Off";
        }
        return detail;

    }


}
