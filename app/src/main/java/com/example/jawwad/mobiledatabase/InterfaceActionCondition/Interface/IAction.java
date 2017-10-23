package com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Jawwad on 11/4/2016.
 */

public interface IAction  extends Serializable
{
    String getname();
    void applyactions(Context c);
    String getactiondetail();
}
