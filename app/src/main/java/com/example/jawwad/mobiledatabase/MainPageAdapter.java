package com.example.jawwad.mobiledatabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Naqi on 11/11/2016.
 */

public class MainPageAdapter extends ArrayAdapter<String>{

    private final Context context;
    private ArrayList<String> presetnames = new ArrayList<>();
    private boolean[] stars;

    public MainPageAdapter(Context context, ArrayList<String> pname) {
        super(context, R.layout.mainlistlayout, pname);
        this.context = context;
        this.presetnames = pname;
        this.stars = new boolean[pname.size()];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.mainlistlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(presetnames.get(position));
        // Change the icon for Windows and iPhone
        String s = presetnames.get(position);
        if (!Sensorconcrete.isActive(s)) {
            imageView.setImageResource(R.drawable.no);
            imageView.setTag("no");

        } else {

            imageView.setImageResource(R.drawable.yes);
            imageView.setTag("yes");
        }

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // String dd = (String)imageView.getTag().toString();
                //   String path = Environment.getExternalStorageDirectory().getPath() + "com.automation/";
                String x = v.getTag().toString();

                if (x.equals("no")) {

                    try {    //"/sdcard/com.lacalprofile2/"
                        FileInputStream fileIn = new FileInputStream("/sdcard/com.automation/"+ presetnames.get(position));
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        Presetconcrete p = (Presetconcrete) in.readObject();
                        p.setPresetname(presetnames.get(position));
                        Sensorconcrete.addPreset(p);     //14/11/16
                        in.close();
                        fileIn.close();
                    } catch (IOException i) {
                        i.printStackTrace();

                    } catch (ClassNotFoundException c) {
                        c.printStackTrace();

                    }


                } else if (x.equals("yes")) {
                   Sensorconcrete.removePreset(presetnames.get(position));  //14-11-16
                }
            //    notifyDataSetInvalidated();  // comment
                notifyDataSetChanged();
            }
        });

        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Do something here.

                PopupMenu pm = new PopupMenu(context, rowView);
                pm.getMenuInflater().inflate(R.menu.mainmenu, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        if (imageView.getTag().toString().equals("yes") && item.getTitle().toString().equals("Update"))

                        {

                            Toast.makeText(context, "Please Deactivate Preset For Update", Toast.LENGTH_SHORT).show();


                        } else {


                            switch (item.getTitle().toString()) {
                                case "Update": {

                                    //update code

                                    //before update creating temp folder                       //"/sdcard/com.automation/temp"
                                    File subdir = new File(File_Path.path);   //"/sdcard/com.lacalprofile2/com.lacalprofile2/temp"
                                    if (!subdir.exists()) {
                                        subdir.mkdir();

                                    }                                                                     //"/sdcard/com.automation/temp/tempcondition"
                                    File subdir1 = new File(File_Path.condition_path);  //"/sdcard/com.lacalprofile2/temp/tempcondition"
                                    if (!subdir1.exists()) {
                                        subdir1.mkdir();

                                    }                                                                         //"/sdcard/com.automation/temp/tempaction"
                                    File subdir2 = new File(File_Path.action_path);  // "/sdcard/com.lacalprofile2/temp/temptask"
                                    if (!subdir2.exists()) {
                                        subdir2.mkdir();

                                    }

                                    Intent i = new Intent(context,UpdatePreset.class);
                                    i.putExtra("preset name",presetnames.get(position));
                                    context.startActivity(i);
                                    break;
                                }
                                case "Delete": {

                                    //delete code
                                    if (imageView.getTag().toString().equals("yes")) {

                                        Toast.makeText(context, "Please Deactivate Preset For Deletion", Toast.LENGTH_SHORT).show();

                                    } else {
                                        //deleting the serelize object from sd card


                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                                        // Setting Dialog Title
                                        alertDialog.setTitle("Confirm Delete...");

                                        // Setting Dialog Message
                                        alertDialog.setMessage("Are you sure you want to delete this Preset?");

                                        // Setting Icon to Dialog
                                        // alertDialog.setIcon(R.drawable.delete);

                                        // Setting Positive "Yes" Button
                                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // Write your code here to invoke YES event
                                                // Toast.makeText(context, "You clicked on YES", Toast.LENGTH_SHORT).show();

                                                //"/sdcard/com.lacalprofile2/"
                                        //           String path1 = Environment.getExternalStorageDirectory().getPath() + "com.automation/";


                                                File file = new File("/sdcard/com.automation/" + presetnames.get(position));
                                                boolean deleted = file.delete();

                                                presetnames.remove(position);

                                                notifyDataSetChanged();


                                            }
                                        });

                                        // Setting Negative "NO" Button
                                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Write your code here to invoke NO event
                                                //  Toast.makeText(context, "You clicked on NO", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                            }
                                        });

                                        // Showing Alert Message
                                        alertDialog.show();


                                    }


                                    break;
                                }
                                case "Detail": {
                                    String detail = "Details: ";
                                       //   String path2 = Environment.getExternalStorageDirectory().getPath() + "com.automation/";
                                    //detail code
                                    try {
                                        //"/sdcard/com.lacalprofile2/"

                                        FileInputStream fileIn = new FileInputStream("/sdcard/com.automation/" + presetnames.get(position));
                                        ObjectInputStream in = new ObjectInputStream(fileIn);
                                        Presetconcrete p = (Presetconcrete) in.readObject();
                                       // p.setPresetname(presetnames.get(position));   // comment
                                        detail = p.getPresetdetail();
                                        in.close();
                                        fileIn.close();
                                    } catch (IOException i) {
                                        i.printStackTrace();

                                    } catch (ClassNotFoundException c) {
                                        c.printStackTrace();

                                    }
                                    Intent i = new Intent(context, DetailActivity.class);
                                    i.putExtra("details", detail);
                                    context.startActivity(i);


                                    break;
                                }

                            }


                        }
                        return true;
                    }
                });

                pm.show();
                return true;
            }
        });


        return rowView;
    }




}
