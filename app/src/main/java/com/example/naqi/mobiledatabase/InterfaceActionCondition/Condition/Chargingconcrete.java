package com.example.naqi.mobiledatabase.InterfaceActionCondition.Condition;

import com.example.naqi.mobiledatabase.CurrentConditionValues;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Interface.ICondition;

/**
 * Created by Naqi on 11/23/2016.
 */

public class Chargingconcrete implements ICondition {


    boolean WhileCharging;
    public Chargingconcrete(boolean c)
    {
        this.WhileCharging = c;
    }


    @Override
    public String getname() {
        return "->  Charging";
    }
    @Override
    public Boolean issatisfy() {

        if (CurrentConditionValues.charging == this.WhileCharging) {
            return true ;
        }
        return  false;
    }

    @Override
    public String getconditiondetail() {
        String detail = "Charging";
        if(this.WhileCharging)
        {
            detail= "While "+detail;



        }
        else
        {
            detail= "While Not "+detail;

        }

        return detail;
    }

}
