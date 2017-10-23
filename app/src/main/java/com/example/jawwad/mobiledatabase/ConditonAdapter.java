package com.example.jawwad.mobiledatabase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jawwad.mobiledatabase.model.ConditionDetails;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Naqi on 10/15/2017.
 */

public class ConditonAdapter extends RecyclerView.Adapter<ConditonAdapter.ViewHolder> {
    private ArrayList<ConditionDetails> conditionDetailsArrayList;
    private Context context;
    static CheckBox checkBox;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imgViewCon;


        public ViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.name);
            imgViewCon = (ImageView) view.findViewById(R.id.con_icon);
            checkBox = (CheckBox) view.findViewById(R.id.IsToRun);
        }
    }

    public ConditonAdapter(Context context, ArrayList<ConditionDetails> conditionDetailsArrayList) {
        this.context = context;
        this.conditionDetailsArrayList = conditionDetailsArrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commonlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        ConditionDetails detail = conditionDetailsArrayList.get(i);

        viewHolder.tvName.setText(detail.getName());

        viewHolder.imgViewCon.setImageDrawable(detail.getImage());
//        Glide.with(context).load(detail.getImage()).into(viewHolder.imgViewAdmin);


        checkBox.setChecked(false);

       checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// create directory
                //  String path = Environment.getExternalStorageDirectory().getPath() + "/temp/tempcondition";
                if (isChecked) {


                    File mytextfile = new File(File_Path.condition_path, viewHolder.tvName.getText().toString() + ".txt");
                    try {
//"/sdcard/com.automation/temp/tempcondition"
                        if (!mytextfile.exists()) {
                            mytextfile.createNewFile();
                        } else {
                            mytextfile.delete();
                            mytextfile.createNewFile();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    File mytextfile = new File(File_Path.condition_path, viewHolder.tvName.getText().toString() + ".txt");

                    if (mytextfile.exists()) {
                        mytextfile.delete();
                    }

                }

            }
        });
        if(detail.ischecked()){
            checkBox.setChecked(true);
        }








    }

    @Override
    public int getItemCount() {
        return conditionDetailsArrayList.size();
    }


}