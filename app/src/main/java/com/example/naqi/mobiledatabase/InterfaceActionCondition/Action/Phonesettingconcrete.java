package com.example.naqi.mobiledatabase.InterfaceActionCondition.Action;

import android.content.Context;
import android.media.AudioManager;

import com.example.naqi.mobiledatabase.InterfaceActionCondition.Interface.IAction;

/**
 * Created by Naqi on 12/12/2016.
 */

public class Phonesettingconcrete implements IAction {

    double volume;
    int brightness;
    boolean vibrateison;
   // String ringpath;
   // String messageringpath;
   // String wallpaperpath;

    public  Phonesettingconcrete( double volume, int brightness, boolean vibrateison){


        this.volume = volume;
        this.brightness = brightness;
        this.vibrateison = vibrateison;
    }

   /* public  Phonesettingconcrete( double volume, int brightness, boolean vibrateison,String ringpath,String messageringpath,String wallpaperpath){


        this.volume = volume;
        this.brightness = brightness;
        this.vibrateison = vibrateison;
        this.ringpath = ringpath;
        this.messageringpath = messageringpath;
        this.wallpaperpath = wallpaperpath;
    }*/

    @Override
    public String getname() {
        return "->  Phone Settings";
    }

    @Override
    public void applyactions(Context c) {

        if (!(volume <= -1))
        {
            AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);

            double vol = am.getStreamMaxVolume(AudioManager.STREAM_RING) * volume / 100;
            am.setStreamVolume(AudioManager.STREAM_RING, (int) vol,0);

        }

        if (!(brightness <= -1))
        {
            android.provider.Settings.System.putInt(c.getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS,brightness);

        }
        if (vibrateison)
        {
            AudioManager   am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
            if(!(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE))
            {
                am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
                        AudioManager.VIBRATE_SETTING_ON);
            }

        }
        else
        {
            AudioManager   am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
            if((am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE))
            {
                am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
                        AudioManager.VIBRATE_SETTING_OFF);
            }

        }

     /*   if (!(ringpath.equals(null)))
        {
            RingtoneManager rtm = new RingtoneManager(c);
            rtm.setActualDefaultRingtoneUri(
                    c.getApplicationContext(),
                    RingtoneManager.TYPE_RINGTONE,
                    Uri.parse(this.ringpath)
            );

        }
        if (!(wallpaperpath.equals(null)))
        {
            WallpaperManager wpm =WallpaperManager.getInstance(c);
            try {
                wpm.setBitmap(BitmapFactory.decodeFile(wallpaperpath));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (!(messageringpath.equals(null)) )
        {
            RingtoneManager rtm = new RingtoneManager(c);
            rtm.setActualDefaultRingtoneUri(
                    c.getApplicationContext(),
                    RingtoneManager.TYPE_NOTIFICATION,
                    Uri.parse(this.messageringpath)
            );
        }*/
    }

    @Override
    public String getactiondetail() {

        String detail = "Volume: "+String.valueOf(this.volume)+"\n"+"Brightness: "+String.valueOf(this.brightness)+"\n";
        if(this.vibrateison)
        {

            detail+= "Vibration: "+"On\n";

        }
        else
        {
            detail+= "Vibration: "+"Off\n";
        }
    //    detail+= "Wallpaper: "+this.wallpaperpath+"\n"+"Ringtone: "+this.ringpath+"\n"+"Messagetone: "+this.messageringpath;


        return detail;
    }
}
