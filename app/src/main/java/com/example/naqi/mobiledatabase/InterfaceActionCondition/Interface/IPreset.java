package com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface;

import java.io.Serializable;

/**
 * Created by Naqi on 11/4/2016.
 */

public interface IPreset  extends Serializable // Observer
{

void setPresetname(String s);
 String getPresetname();
  String getPresetdetail();
    void updatePreset();

}
