package com.example.jawwad.mobiledatabase.InterfaceActionCondition.Condition;

import com.example.jawwad.mobiledatabase.CurrentConditionValues;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface.ICondition;

/**
 * Created by Jawwad on 11/23/2016.
 */

public class Batteryconcrete implements ICondition {

    int level;
    boolean lessthan;
    boolean greaterthan;

    public Batteryconcrete(String choice, int value) {
        if (choice.equals("LessThan")) {
            this.lessthan = true;
        }
        if (choice.equals("GreaterThan")) {
            this.greaterthan = true;
        }
        this.level = value;
    }


    @Override
    public String getname() {
        return "->  Battery";
    }

    @Override
    public Boolean issatisfy() {
        if (lessthan && (level > CurrentConditionValues.battery)) {

            return true;
        }
        if (greaterthan && (level < CurrentConditionValues.battery)) {

            return true;
        }

        return false;

    }

    @Override
    public String getconditiondetail() {
        String detail = "Battery: ";

        if(this.lessthan)
        {

            detail += "LessThan "+ String.valueOf(this.level);


        }
        else
        {

            detail += "GreaterThan "+ String.valueOf(this.level);

        }


        return detail;
    }
}
