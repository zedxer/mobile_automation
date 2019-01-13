package com.example.naqi.mobiledatabase.InterfaceActionCondition.Condition;

import android.util.Log;

import com.example.naqi.mobiledatabase.CurrentConditionValues;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Interface.ICondition;

/**
 * Created by Naqi on 11/5/2016.
 */

public class Timerconcrete implements ICondition {

    String date =  null;
    String time = null;

    public Timerconcrete(String date, String time) {
        this.date = date;
        this.time = time;
    }

    @Override
    public String getname() {
        return "->  Timer";
    }

    @Override
    public Boolean issatisfy() {
        Log.i("wait","time is : "+CurrentConditionValues.time.toString());

        if (CurrentConditionValues.time.equals(this.time))
        {
            if (this.date.equals("null") || CurrentConditionValues.date.equals(this.date))
            {
                return true;
            }

        }

        return false;
    }

    @Override
    public String getconditiondetail() {
        String detail = "Date: ";
        if(this.date.equals("null"))
        {
            detail+= "Daily";

        }
        else
        {

            detail+= this.date;

        }
        detail+="\nTime: "+ this.time;

        return detail;
    }
}
