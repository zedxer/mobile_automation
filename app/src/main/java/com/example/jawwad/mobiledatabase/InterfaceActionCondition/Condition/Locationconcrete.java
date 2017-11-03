package com.example.jawwad.mobiledatabase.InterfaceActionCondition.Condition;

import com.example.jawwad.mobiledatabase.CurrentConditionValues;
import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface.ICondition;

/**
 * Created by Naqi on 2/19/2017.
 */

public class Locationconcrete implements ICondition {

    // LatLng latlang;
    double lat;
    double lang;
    double radius;
    String name;

    public  Locationconcrete(double radiusv,String nam, double l, double lg) {
        this.lat = l;
        this.lang = lg;
        this.radius = radiusv;
        this.name = nam;
    }

    @Override
    public String getname() {
        return "->  Location";
    }

    @Override
    public Boolean issatisfy() {
        float[] length = new float[1];
        android.location.Location.distanceBetween(this.lat, this.lang,
                CurrentConditionValues.location.latitude, CurrentConditionValues.location.longitude, length);
        if (Double.valueOf(length[0]) <= Double.valueOf(this.radius)) {


            return true;
        }

        return false;
    }

    @Override
    public String getconditiondetail() {
        String detail = "Location: "+this.lat+" , "+this.lang +"\nName: "+this.name  +"\n"+"Radius: "+this.radius+" meter";
        return detail;
    }
}
