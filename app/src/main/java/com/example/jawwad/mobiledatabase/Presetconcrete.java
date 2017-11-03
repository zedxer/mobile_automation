package com.example.jawwad.mobiledatabase;

import android.content.Context;

import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface.IAction;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface.ICondition;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface.IPreset;

import java.util.ArrayList;

/**
 * Created by Naqi on 11/11/2016.
 */

class Presetconcrete implements IPreset , java.io.Serializable {

    ArrayList<ICondition> condition;
    ArrayList<IAction> action;
    private String presetename;


    public Presetconcrete(ArrayList<ICondition> con, ArrayList<IAction> action, String name) {

        this.condition = con;
        this.action = action;

    }

    public void applyaction(Context context) {
        for (IAction a : action ) {
            a.applyactions(context);

        }

        Sensorconcrete.removePreset(this.getPresetname());

    }

    @Override
    public void setPresetname(String s) {
        this.presetename =  s;
    }

    @Override
    public String getPresetname() {
        return presetename;
    }

    @Override
    public String getPresetdetail() {

        String detail = ("Conditions: \n");
        for (ICondition c : this.condition
                ) {
            detail += c.getconditiondetail() + "\n";
        }

        detail += "Actions: \n";

        for (IAction a : this.action
                ) {
            detail += a.getactiondetail() + "\n";
        }

        return detail;
    }

    @Override
    public void updatePreset() {

        for (ICondition con : condition)
        {
            if (!con.issatisfy())
            { return;}
        }

        applyaction(MainService.cntxt);
    }

}
