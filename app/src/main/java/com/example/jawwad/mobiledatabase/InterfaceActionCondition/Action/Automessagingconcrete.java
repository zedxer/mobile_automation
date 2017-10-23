package com.example.jawwad.mobiledatabase.InterfaceActionCondition.Action;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.example.jawwad.mobiledatabase.InterfaceActionCondition.Interface.IAction;

import java.util.ArrayList;

/**
 * Created by Jawwad on 12/4/2016.
 */

public class Automessagingconcrete implements IAction{

    ArrayList<String> numbers = new ArrayList<String>();
    String message;


    public Automessagingconcrete(ArrayList<String> numbers,String message)
    {
        this.numbers = numbers;
        this.message = message;

    }

    @Override
    public String getname() {
        return "->  Auto messaging";
    }

    @Override
    public void applyactions(Context c) {

        for (String n:this.numbers) {

            sendSMS(n,this.message,c);

        }
    }

    @Override
    public String getactiondetail() {
        String details = "Numbers: \n";
        for (String num: this.numbers
                ) {

            details+= num+"\n";

        }

        details+= "Message: \n\t"+this.message;

        return details;
    }

    private void sendSMS(String phoneNumber, String message, final Context c) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(c, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(c, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        c.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(c, "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(c, "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(c, "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(c, "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(c, "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        c.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(c, "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(c, "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }



}
