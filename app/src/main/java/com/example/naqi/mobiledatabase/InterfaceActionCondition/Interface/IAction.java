package com.example.naqi.mobiledatabase.InterfaceActionCondition.Interface;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Naqi on 11/4/2016.
 */

public interface IAction  extends Serializable
{
    String getname();
    void applyactions(Context c);
    String getactiondetail();
}
