package com.example.naqi.mobiledatabase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naqi.mobiledatabase.InterfaceActionCondition.Interface.IAction;
import com.example.naqi.mobiledatabase.InterfaceActionCondition.Interface.ICondition;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Naqi on 11/11/2016.
 */

public class ExpendAbleListAdapter extends BaseExpandableListAdapter{


    private Context _context;
    Presetconcrete tobeupdate;
    public Map<String, Boolean> condcheckbox;
    public Map<String, Boolean> actcheckbox;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpendAbleListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData,Presetconcrete p) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;

        this.tobeupdate  = p;
        actcheckbox= new HashMap<String, Boolean>();
        condcheckbox = new HashMap<String, Boolean>();
        SetInitialCheckBox();

    }

    public void SetInitialCheckBox() {

        condcheckbox.put("->  Timer", false);
        condcheckbox.put("->  Battery", false);
        condcheckbox.put("->  Charging", false);
        condcheckbox.put("->  Location", false);
        actcheckbox.put("->  Wifi", false);
        actcheckbox.put("->  Bluetooth", false);
        actcheckbox.put("->  GPS", false);
        actcheckbox.put("->  Auto messaging", false);
        actcheckbox.put("->  Phone Settings", false);

        for (ICondition c:tobeupdate.condition)
        {
            if (c.getname().equals("->  Timer"))
            {
                condcheckbox.put("->  Timer", true);
            }
            else  if (c.getname().equals("->  Battery"))
            {
                condcheckbox.put("->  Battery", true);
            }
            else  if (c.getname().equals("->  Charging"))
            {
            condcheckbox.put("->  Charging", true);
            }
            else if (c.getname().equals("->  Location"))
            {
                condcheckbox.put("->  Location", true);
            }
        }

        for (IAction a:tobeupdate.action)
        {
            if (a.getname().equals("->  Wifi"))
            {
                actcheckbox.put("->  Wifi", true);
            }

           else  if (a.getname().equals("->  Bluetooth"))
            {
                actcheckbox.put("->  Bluetooth", true);
            }
            else  if (a.getname().equals("->  GPS"))
            {
                actcheckbox.put("->  GPS", true);
            }
            else  if (a.getname().equals("->  Auto messaging"))
            {
                actcheckbox.put("->  Auto messaging", true);
            }
            else  if (a.getname().equals("->  Phone Settings"))
            {
                actcheckbox.put("->  Phone Settings", true);
            }

        }
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.updateparent, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.updateparenttext);
        lblListHeader.setTypeface(null, Typeface.BOLD_ITALIC);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null);
        {


            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.updatechild,null);
        }
        int itemType = getChildType(groupPosition,childPosition);

        final TextView txtListChild = (TextView) convertView
                .findViewById(R.id.updatechildtext);

        final CheckBox childcheckbox = (CheckBox) convertView.findViewById(R.id.checkBox);
        if (groupPosition == 0)
        {
            childcheckbox.setChecked(condcheckbox.get(_listDataChild.get("    Conditions").get(childPosition)));
        }
        else
        {
            childcheckbox.setChecked(actcheckbox.get(_listDataChild.get("    Actions").get(childPosition)));
        }
        childcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                         if (groupPosition == 0) {  condcheckbox.put(_listDataChild.get("    Conditions").get(childPosition), childcheckbox.isChecked());

                                                             if (!isChecked)
                                                             {

                                                                 Toast.makeText(_context,"removing", Toast.LENGTH_SHORT).show();
                                                             }
                                                             else
                                                             {


                                                                 createtxtfile(txtListChild,"c");

                                                                 Toast.makeText(_context,"adding, opening " + txtListChild.getText() + " activity", Toast.LENGTH_SHORT).show();
                                                                 openactivity(groupPosition,txtListChild);

                                                             }
                                                         }

                                                         else
                                                         {
                                                             actcheckbox.put(_listDataChild.get("    Actions").get(childPosition), childcheckbox.isChecked());

                                                             if (!isChecked)
                                                             {

                                                                 Toast.makeText(_context,"removing", Toast.LENGTH_SHORT).show();




                                                             }
                                                             else
                                                             {

                                                                 createtxtfile(txtListChild,"a");
                                                                 Toast.makeText(_context, "adding, opening " + txtListChild.getText() + " activity", Toast.LENGTH_SHORT).show();

                                                                 openactivity(groupPosition, txtListChild);



                                                             }

                                                         }

                                                     }
                                                 }
        );


        txtListChild.setText(childText);
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (childcheckbox.isChecked())
                {
                    Toast.makeText(_context, "updating, opening " + txtListChild.getText() + " activity", Toast.LENGTH_SHORT).show();
                    if(groupPosition == 0)
                    {
                        createtxtfile(txtListChild,"c");
                        openactivity(groupPosition,txtListChild);

                    }
                    else
                    {
                        createtxtfile(txtListChild,"a");
                        openactivity(groupPosition,txtListChild);

                    }


                }
                else
                {

                    if(groupPosition == 0)
                    {
                        createtxtfile(txtListChild,"c");
                        openactivity(groupPosition,txtListChild);
                        condcheckbox.put(_listDataChild.get("    Conditions").get(childPosition), true);


                    }
                    else
                    {
                        createtxtfile(txtListChild,"a");
                        openactivity(groupPosition,txtListChild);
                        actcheckbox.put(_listDataChild.get("    Actions").get(childPosition), true);

                    }
                    notifyDataSetInvalidated();



                }

            }
        });

        return convertView;
    }


    public void createtxtfile(TextView textview, String choice) {
        if(choice == "c"){

            String s = textview.getText().toString().replace("->  ","");

            File mytextfile = new File(File_Path.condition_path,s + ".txt");
            try {
                if (!mytextfile.exists()) {
                    mytextfile.createNewFile();
                } else {
                    mytextfile.delete();
                    mytextfile.createNewFile();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }}
        else if(choice == "a"){

            String s = textview.getText().toString().replace("->  ","");
            File mytextfile = new File(File_Path.action_path,s + ".txt");
            try {

                if (!mytextfile.exists()) {
                    mytextfile.createNewFile();
                } else {
                    mytextfile.delete();
                    mytextfile.createNewFile();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void openactivity(int groupposition ,TextView txtListChild)
    {
        Intent f = null;
        if(groupposition == 0)
        {
          //  Intent f = null;
            if (txtListChild.getText() == "->  Timer") {
                f = new Intent(_context, Timer.class);
            }
            else if (txtListChild.getText() == "->  Battery") {
                f = new Intent(_context, Battery.class);


            }

            else if (txtListChild.getText() == "->  Charging") {
            f = new Intent(_context, Charging.class);


        }
           else if (txtListChild.getText() == "->  Location") {
                f = new Intent(_context, Location.class);

            }


            else {
                Toast.makeText(_context, "Something goes wrong!",
                        Toast.LENGTH_LONG).show();
            }


            _context.startActivity(f);

        }
        else
        {

           // Intent f = null;
            if (txtListChild.getText() == "->  Wifi") {
                f = new Intent(_context, Wifi.class);

                _context.startActivity(f);
            }
            else if (txtListChild.getText() == "->  Bluetooth") {
                f = new Intent(_context, BlueTooth.class);
                _context.startActivity(f);

            }
            else if (txtListChild.getText() == "->  GPS") {
                f = new Intent(_context, GPS.class);
                _context.startActivity(f);

            }
            else if (txtListChild.getText() == "->  Auto messaging") {
                f = new Intent(_context, Automessaging.class);
                _context.startActivity(f);
            }

            else if (txtListChild.getText() == "->  Phone Settings") {
                f = new Intent(_context, Phonesetting.class);

                _context.startActivity(f);
            }

            else {
                Toast.makeText(_context, "Something goes wrong!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
