package com.hqgj.facedemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hqgj.facedemo.R;
import com.hqgj.facedemo.utils.DensityUtil;
import com.hqgj.mylibrary.common.AppContext;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: ly
 * data: 2016/6/15
 */
public class ListViewAdapter extends BaseAdapter {

    private ArrayList<String> lists;
    private Context context;
    private BitmapFactory.Options options;

    public ListViewAdapter(Context context, ArrayList<String> lists) {
        this.context = context;
        this.lists = lists;
        options= new BitmapFactory.Options();
        options.inPreferredConfig= Bitmap.Config.RGB_565;
        options.inSampleSize=1;
        options.inSampleSize=calculateInSampleSize(options, DensityUtil.dpToPx(context.getResources(),18), DensityUtil.dpToPx(context.getResources(),18));
        options.inJustDecodeBounds=false;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int width, int height) {
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(context.getResources(), R.drawable.emoji_1, options);
        int heightRatio= (int) Math.ceil((options.outHeight*1.0) / width);
        int widthRatio= (int) Math.ceil((options.outWidth*1.0) / height);
        int inSampleSize=1;
        if(heightRatio>widthRatio){
            inSampleSize=heightRatio;
        }else{
            inSampleSize=widthRatio;
        }
        return inSampleSize;
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

        SpannableString spannableString=getSpannableString(str);
        holder.textView.setText(spannableString);
        return convertView;
    }

    private SpannableString getSpannableString(String str) {
        SpannableString spannableString=new SpannableString(str);
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        String zhengze = "\\[[^\\]]+\\]";
        // 通过传入的正则表达式来生成一个pattern
        Pattern pattern = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression(context, spannableString, pattern, 0);
        } catch (Exception e) {
            Log.e("dealExpression", e.getMessage());
        }
        return spannableString;
    }

    private void dealExpression(Context context, SpannableString spannableString, Pattern pattern, int start) {
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            int resId = AppContext.getInstance().getFaceMap().get(key);
            if (resId != 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId,options);
                if(bitmap!=null){
                    WeakReference<Bitmap> weakReference=new WeakReference<>(bitmap);
                    // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
                    ImageSpan imageSpan = new ImageSpan(context,weakReference.get());
                    // 计算该图片名字的长度，也就是要替换的字符串的长度
                    int end = matcher.start() + key.length();
                    // 将该图片替换字符串中规定的位置中
                    spannableString.setSpan(imageSpan, matcher.start(), end,
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    if (end < spannableString.length()) {
                        // 如果整个字符串还未验证完，则继续。。
                        dealExpression(context, spannableString, pattern, end);
                    }
                }
                /*
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId,options);
                if(bitmap!=null){
                    int rawHeigh = bitmap.getHeight();
                    int rawWidth = bitmap.getWidth();
                    // 设置表情的大小===
                    int newHeight = DensityUtil.dip2px(context, 24);
                    int newWidth = DensityUtil.dip2px(context, 24);
                    // 计算缩放因子
                    float heightScale = ((float) newHeight) / rawHeigh;
                    float widthScale = ((float) newWidth) / rawWidth;
                    // 新建立矩阵
                    Matrix matrix = new Matrix();
                    matrix.postScale(heightScale, widthScale);
                    // 设置图片的旋转角度
                    // matrix.postRotate(-30);
                    // 设置图片的倾斜
                    // matrix.postSkew(0.1f, 0.1f);
                    // 将图片大小压缩
                    // 压缩后图片的宽和高以及kB大小均会变化
                    Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            rawWidth, rawHeigh, matrix, true);
                    WeakReference<Bitmap> weakReference=new WeakReference<>(newBitmap);
                    // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
                    ImageSpan imageSpan = new ImageSpan(context,weakReference.get());
                    // 计算该图片名字的长度，也就是要替换的字符串的长度
                    int end = matcher.start() + key.length();
                    // 将该图片替换字符串中规定的位置中
                    spannableString.setSpan(imageSpan, matcher.start(), end,
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    if (end < spannableString.length()) {
                        // 如果整个字符串还未验证完，则继续。。
                        dealExpression(context, spannableString, pattern, end);
                    }
                    bitmap.recycle();
                }
                */
                break;
            }
        }
    }

    public static class ViewHolder{
        TextView textView;
    }
}
