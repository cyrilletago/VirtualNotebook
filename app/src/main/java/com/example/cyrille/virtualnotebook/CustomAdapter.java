package com.example.cyrille.virtualnotebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by cyrille on 29/10/17.
 */

public class CustomAdapter extends BaseAdapter
{
    private Context mContext;
    ControllerDatabase controldb;
    SQLiteDatabase db;
    public ArrayList<String> englishWordList = new ArrayList<String>();


    public CustomAdapter(Context  context, ArrayList<String> englishWordList)
    {
        this.mContext = context;
        this.englishWordList = englishWordList;

    }
    @Override
    public int getCount() {
        return englishWordList.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class viewHolder {

        TextView word_en;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final    viewHolder holder;
        controldb =new ControllerDatabase(mContext);
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout, null);
            holder = new viewHolder();
            holder.word_en = (TextView) convertView.findViewById(R.id.tvname);
            // holder.word_fr = (TextView) convertView.findViewById(R.id.tvmailid);
            // holder.category = (TextView) convertView.findViewById(R.id.tvage);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        holder.word_en.setText(englishWordList.get(position));


        convertView.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.GRAY);
        return convertView;
    }

}
