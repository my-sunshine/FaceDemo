package com.hqgj.facedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hqgj.facedemo.R;
import com.hqgj.facedemo.common.AppContext;
import com.hqgj.facedemo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.Map;

/**
 * author: ly
 * data: 2016/6/15
 */
public class FaceAdapter extends BaseAdapter {

    private Context context;
    private int currentPage;
    private Map<String, Integer> faceMap ;
    private LayoutInflater inflate;
    private ArrayList<Integer> faceInCurrentPage=new ArrayList<>();

    public FaceAdapter(Context context, int currentPage) {
        this.context=context;
        this.currentPage=currentPage;
        inflate=LayoutInflater.from(context);
        faceMap= AppContext.getInstance().getFaceMap();
        initDate();
    }

    private void initDate() {
        if(faceMap!=null){
            for(Map.Entry<String ,Integer> entry:faceMap.entrySet()){
                faceInCurrentPage.add(entry.getValue());
            }
        }
    }

    @Override
    public int getCount() {
        return AppContext.getInstance().NUM + 1;
    }

    @Override
    public Object getItem(int position) {
        return faceInCurrentPage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflate.inflate(R.layout.item_chat_face, null, false);
            viewHolder.faceIV = (ImageView) convertView.findViewById(R.id.face_iv);
            viewHolder.faceIV.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context, 36)));
            viewHolder.faceIV.setPadding(DensityUtil.dip2px(context, 0),DensityUtil.dip2px(context, 6),DensityUtil.dip2px(context, 0),DensityUtil.dip2px(context, 6));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == AppContext.getInstance().NUM) {
            viewHolder.faceIV.setImageResource(R.drawable.emotion_del_selector);
            viewHolder.faceIV.setBackgroundDrawable(null);
            viewHolder.faceIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFaceItemClickListener.onFaceItemDeleteClickListener(position);
                }
            });
        } else {
            int count = AppContext.getInstance().NUM * currentPage + position;
            // 总共107个表情==
            if (faceMap!=null && count < faceMap.size()) {
                viewHolder.faceIV.setImageResource(faceInCurrentPage.get(count));
                viewHolder.faceIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onFaceItemClickListener.onFaceItemClickListener(position);
                    }
                });
            } else {
                viewHolder.faceIV.setImageDrawable(null);
                viewHolder.faceIV.setBackgroundDrawable(null);
                viewHolder.faceIV.setEnabled(false);
            }
        }
        return convertView;
    }

    public static class ViewHolder {
        ImageView faceIV;
    }

    public void setOnFaceItemClickListener(OnFaceItemClickListener onFaceItemClickListener){
        this.onFaceItemClickListener=onFaceItemClickListener;
    }

    private OnFaceItemClickListener onFaceItemClickListener;
    public interface OnFaceItemClickListener{

        void onFaceItemClickListener(int position);

        void onFaceItemDeleteClickListener(int position);
    }
}
