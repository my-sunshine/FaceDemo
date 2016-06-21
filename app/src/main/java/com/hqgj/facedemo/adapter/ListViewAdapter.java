package com.hqgj.facedemo.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hqgj.facedemo.R;
import com.hqgj.mylibrary.common.AppUtil;

import java.util.ArrayList;

/**
 * author: ly
 * data: 2016/6/15
 */
public class ListViewAdapter extends BaseAdapter {

    private ArrayList<String> lists;
    private Context context;

    public ListViewAdapter(Context context, ArrayList<String> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_list_view,null);
            holder.textView= (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        String str=lists.get(position);

        SpannableString spannableString=AppUtil.getInstance().getSpannableString(context,str);
        holder.textView.setText(spannableString);
        return convertView;
    }

    public static class ViewHolder{
        TextView textView;
    }
}
