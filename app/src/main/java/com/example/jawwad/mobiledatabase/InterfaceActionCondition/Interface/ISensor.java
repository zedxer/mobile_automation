package com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface;

/**
 * Created by Jawwad on 11/5/2016.
 */

public interface ISensor { // Subject

    void addPreset(IPreset preset);  // add observer
    void removePreset(String name);   // remove observer
    void notifyAllPreset();  // notify all observers
}
