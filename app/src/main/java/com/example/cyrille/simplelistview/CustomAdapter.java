package com.example.cyrille.simplelistview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
    public ArrayList<String> Id = new ArrayList<String>();
    public ArrayList<String> Word_en = new ArrayList<String>();
    public ArrayList<String> Word_fr = new ArrayList<String>();
    public ArrayList<String> Category = new ArrayList<String>();
    public CustomAdapter(Context  context, ArrayList<String> Id, ArrayList<String> Word_en, ArrayList<String> Word_fr, ArrayList<String> Category
    )
    {
        this.mContext = context;
        this.Id = Id;
        this.Word_en = Word_en;
        this.Word_fr = Word_fr;
        this.Category = Category;
    }
    @Override
    public int getCount() {
        return Id.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
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
            // holder.id = (TextView) convertView.findViewById(R.id.tvid);
            holder.word_en = (TextView) convertView.findViewById(R.id.tvname);
            // holder.word_fr = (TextView) convertView.findViewById(R.id.tvmailid);
            // holder.category = (TextView) convertView.findViewById(R.id.tvage);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        // holder.id.setText(Id.get(position));
        holder.word_en.setText(Word_en.get(position));
        // holder.word_fr.setText(Word_fr.get(position));
        // holder.category.setText(Category.get(position));
        return convertView;
    }
    public class viewHolder {
        // TextView id;
        TextView word_en;
        // TextView word_fr;
        // TextView category;
    }
}
