package com.example.jawwad.mobiledatabase;

import android.widget.ArrayAdapter;

import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface.IPreset;

import java.util.ArrayList;

/**
 * Created by Naqi on 11/11/2016.
 */

public class Sensorconcrete{

    static ArrayList<IPreset> Presets = new ArrayList<>();

    public Sensorconcrete() {
    }


    private static ArrayList<IPreset> GetPresetClone()
    {
        ArrayList<IPreset> p = (ArrayList<IPreset>) Presets.clone();
        return  p;

    }

    public static boolean isActive(String name) {
        ArrayList<IPreset> p = GetPresetClone();
        for (IPreset nam : p) {
            if (nam.getPresetname().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void addPreset(IPreset preset) {

        if (!Presets.contains(preset)) {
            Presets.add(preset);
        }
    }


    public static void removePreset(String name) {

        for (IPreset p : Presets
                ) {
            if (p.getPresetname().equals(name)) {
                Presets.remove(p);
                return;
            }
        }

    }


    public static void notifypreset() {

        ArrayList<IPreset> Presetsclone = GetPresetClone();
        for (IPreset p : Presetsclone
                ) {
            p.updatePreset();
        }
        ((ArrayAdapter) MainActivity.list.getAdapter()).notifyDataSetInvalidated();
    }




   /* @Override
    public void addPreset(IPreset preset) {

    }

    @Override
    public void removePreset(String name) {

    }

    @Override
    public void notifyAllPreset() {

    }*/
}
