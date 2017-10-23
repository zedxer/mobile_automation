package com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface;

import java.io.Serializable;

/**
 * Created by Jawwad on 11/4/2016.
 */

public interface ICondition extends Serializable
{
    String getname();
    Boolean issatisfy();
    String getconditiondetail();
}
