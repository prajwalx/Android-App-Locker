package com.example.nishant.practiceappmarch;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nishant on 15-Mar-17.
 */

public class PdfListAdapter extends ArrayAdapter<Pdf> {

    Activity activity;
    int layoutResourceId;
    ArrayList<Pdf> data;
    Pdf pdf;

    PdfListAdapter(Activity activity, int layoutResourceId, ArrayList<Pdf> data)
    {
        super(activity,layoutResourceId,data);
        this.activity=activity;
        this.layoutResourceId=layoutResourceId;
        this.data=data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        PdfHolder holder=null;
        if(row==null)
        {
            LayoutInflater inflater=LayoutInflater.from(activity);
            row=inflater.inflate(layoutResourceId,parent,false);
            holder=new PdfHolder();
            holder.textViewName=(TextView) row.findViewById(R.id.listName);
            holder.textViewUrl=(TextView) row.findViewById(R.id.listUrl);
            row.setTag(holder);

        }
        else
        {
            holder=(PdfHolder) row.getTag();
        }
        pdf=data.get(position);
        holder.textViewName.setText(pdf.getName());
        holder.textViewUrl.setText(pdf.getUrl());
        return row;
        //return super.getView(position, convertView, parent);
    }

    class PdfHolder
    {
        TextView textViewName,textViewUrl;
    }
}
