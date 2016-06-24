package com.hqgj.mylibrary.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import com.hqgj.mylibrary.R;
import com.hqgj.mylibrary.utils.DensityUtil;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: ly
 * data: 2016/6/15
 */
public class AppUtil {

    public int NUM_PAGE = 0;
    public int NUM = 20;
    private Map<String, Integer> mFaceMap = new LinkedHashMap<String, Integer>();
    private Map<String, Integer> mFaceMapHX = new LinkedHashMap<String, Integer>();


    private static AppUtil application;
    private BitmapFactory.Options options;

    public AppUtil() {
        application = this;
    }

    public static synchronized AppUtil getInstance() {

        if (application == null) {
            application = new AppUtil();
        }
        return application;
    }

    public Map<String, Integer> getFaceMap() {
        if (!mFaceMap.isEmpty())
            return mFaceMap;
        init();
        return mFaceMap;
    }

    public void init(){
        initFaceMap();
        NUM_PAGE = (int) Math.ceil(mFaceMap.size() / 20.0);

    }


    public Map<String, Integer> getFaceMapHX() {
        if (!mFaceMapHX.isEmpty())
            return mFaceMapHX;
        initHX();
        return mFaceMapHX;
    }

    public void initHX(){
        initFaceMapHX();
        NUM_PAGE = (int) Math.ceil(mFaceMapHX.size() / 20.0);

    }

    private void initFaceMapHX() {
        mFaceMapHX.put("[):]", R.drawable.hx_1);
        mFaceMapHX.put("[:D]", R.drawable.hx_2);
        mFaceMapHX.put("[;)]", R.drawable.hx_3);
        mFaceMapHX.put("[:-o]", R.drawable.hx_4);
        mFaceMapHX.put("[:p]", R.drawable.hx_5);
        mFaceMapHX.put("[(H)]", R.drawable.hx_6);
        mFaceMapHX.put("[:@]", R.drawable.hx_7);
        mFaceMapHX.put("[:s]", R.drawable.hx_8);
        mFaceMapHX.put("[:$]", R.drawable.hx_9);
        mFaceMapHX.put("[:(]", R.drawable.hx_10);
        mFaceMapHX.put("[:'(]", R.drawable.hx_11);
        mFaceMapHX.put("[:|]", R.drawable.hx_12);
        mFaceMapHX.put("[(a)]", R.drawable.hx_13);
        mFaceMapHX.put("[8o|]", R.drawable.hx_14);
        mFaceMapHX.put("[8-|]", R.drawable.hx_15);
        mFaceMapHX.put("[+o(]", R.drawable.hx_16);
        mFaceMapHX.put("[<o)]", R.drawable.hx_17);
        mFaceMapHX.put("[|-)]", R.drawable.hx_18);
        mFaceMapHX.put("[*-)]", R.drawable.hx_19);
        mFaceMapHX.put("[:-#]", R.drawable.hx_20);
        mFaceMapHX.put("[:-*]", R.drawable.hx_21);
        mFaceMapHX.put("[^o)]", R.drawable.hx_22);
        mFaceMapHX.put("[8-)]", R.drawable.hx_23);
        mFaceMapHX.put("[(|)]", R.drawable.hx_24);
        mFaceMapHX.put("[(u)]", R.drawable.hx_25);
        mFaceMapHX.put("[(S)]", R.drawable.hx_26);
        mFaceMapHX.put("[(*)]", R.drawable.hx_27);
        mFaceMapHX.put("[(#)]", R.drawable.hx_28);
        mFaceMapHX.put("[(R)]", R.drawable.hx_29);
        mFaceMapHX.put("[({)]", R.drawable.hx_30);
        mFaceMapHX.put("[(})]", R.drawable.hx_31);
        mFaceMapHX.put("[(k)]", R.drawable.hx_32);
        mFaceMapHX.put("[(F)]", R.drawable.hx_33);
        mFaceMapHX.put("[(W)]", R.drawable.hx_34);
        mFaceMapHX.put("[(D)]", R.drawable.hx_35);
    }


    private void initFaceMap() {
        mFaceMap.put("[呲牙]", R.drawable.emoji_1);
        mFaceMap.put("[调皮]", R.drawable.emoji_2);
        mFaceMap.put("[流汗]", R.drawable.emoji_3);
        mFaceMap.put("[偷笑]", R.drawable.emoji_4);
        mFaceMap.put("[再见]", R.drawable.emoji_5);
        mFaceMap.put("[敲打]", R.drawable.emoji_6);
        mFaceMap.put("[擦汗]", R.drawable.emoji_7);
        mFaceMap.put("[猪头]", R.drawable.emoji_8);
        mFaceMap.put("[玫瑰]", R.drawable.emoji_9);
        mFaceMap.put("[流泪]", R.drawable.emoji_10);
        mFaceMap.put("[大哭]", R.drawable.emoji_11);
        mFaceMap.put("[嘘]", R.drawable.emoji_12);
        mFaceMap.put("[酷]", R.drawable.emoji_13);
        mFaceMap.put("[抓狂]", R.drawable.emoji_14);
        mFaceMap.put("[委屈]", R.drawable.emoji_15);
        mFaceMap.put("[便便]", R.drawable.emoji_16);
        mFaceMap.put("[炸弹]", R.drawable.emoji_17);
        mFaceMap.put("[菜刀]", R.drawable.emoji_18);
        mFaceMap.put("[可爱]", R.drawable.emoji_19);
        mFaceMap.put("[色]", R.drawable.emoji_20);
        mFaceMap.put("[害羞]", R.drawable.emoji_21);
        mFaceMap.put("[得意]", R.drawable.emoji_22);
        mFaceMap.put("[吐]", R.drawable.emoji_23);
        mFaceMap.put("[微笑]", R.drawable.emoji_24);
        mFaceMap.put("[发怒]", R.drawable.emoji_25);
        mFaceMap.put("[尴尬]", R.drawable.emoji_26);
        mFaceMap.put("[惊恐]", R.drawable.emoji_27);
        mFaceMap.put("[冷汗]", R.drawable.emoji_28);
        mFaceMap.put("[爱心]", R.drawable.emoji_29);
        mFaceMap.put("[示爱]", R.drawable.emoji_30);
        mFaceMap.put("[白眼]", R.drawable.emoji_31);
        mFaceMap.put("[傲慢]", R.drawable.emoji_32);
        mFaceMap.put("[难过]", R.drawable.emoji_33);
        mFaceMap.put("[惊讶]", R.drawable.emoji_34);
        mFaceMap.put("[疑问]", R.drawable.emoji_35);
        mFaceMap.put("[睡]", R.drawable.emoji_36);
        mFaceMap.put("[亲亲]", R.drawable.emoji_37);
        mFaceMap.put("[憨笑]", R.drawable.emoji_38);
        mFaceMap.put("[爱情]", R.drawable.emoji_39);
        mFaceMap.put("[衰]", R.drawable.emoji_40);
        mFaceMap.put("[撇嘴]", R.drawable.emoji_41);
        mFaceMap.put("[阴险]", R.drawable.emoji_42);
        mFaceMap.put("[奋斗]", R.drawable.emoji_43);
        mFaceMap.put("[发呆]", R.drawable.emoji_44);
        mFaceMap.put("[右哼哼]", R.drawable.emoji_45);
        mFaceMap.put("[拥抱]", R.drawable.emoji_46);
        mFaceMap.put("[坏笑]", R.drawable.emoji_47);
        mFaceMap.put("[飞吻]", R.drawable.emoji_48);
        mFaceMap.put("[鄙视]", R.drawable.emoji_49);
        mFaceMap.put("[晕]", R.drawable.emoji_50);
        mFaceMap.put("[大兵]", R.drawable.emoji_51);
        mFaceMap.put("[可怜]", R.drawable.emoji_52);
        mFaceMap.put("[强]", R.drawable.emoji_53);
        mFaceMap.put("[弱]", R.drawable.emoji_54);
        mFaceMap.put("[握手]", R.drawable.emoji_55);
        mFaceMap.put("[胜利]", R.drawable.emoji_56);
        mFaceMap.put("[抱拳]", R.drawable.emoji_57);
        mFaceMap.put("[凋谢]", R.drawable.emoji_58);
        mFaceMap.put("[饭]", R.drawable.emoji_59);
        mFaceMap.put("[蛋糕]", R.drawable.emoji_60);
        mFaceMap.put("[西瓜]", R.drawable.emoji_61);
        mFaceMap.put("[啤酒]", R.drawable.emoji_62);
        mFaceMap.put("[飘虫]", R.drawable.emoji_63);
        mFaceMap.put("[勾引]", R.drawable.emoji_64);
        mFaceMap.put("[OK]", R.drawable.emoji_65);
        mFaceMap.put("[爱你]", R.drawable.emoji_66);
        mFaceMap.put("[咖啡]", R.drawable.emoji_67);
        mFaceMap.put("[钱]", R.drawable.emoji_68);
        mFaceMap.put("[月亮]", R.drawable.emoji_69);
        mFaceMap.put("[美女]", R.drawable.emoji_70);
        mFaceMap.put("[刀]", R.drawable.emoji_71);
        mFaceMap.put("[发抖]", R.drawable.emoji_72);
        mFaceMap.put("[差劲]", R.drawable.emoji_73);
        mFaceMap.put("[拳头]", R.drawable.emoji_74);
        mFaceMap.put("[心碎]", R.drawable.emoji_75);
        mFaceMap.put("[太阳]", R.drawable.emoji_76);
        mFaceMap.put("[礼物]", R.drawable.emoji_77);
        mFaceMap.put("[足球]", R.drawable.emoji_78);
        mFaceMap.put("[骷髅]", R.drawable.emoji_79);
        mFaceMap.put("[挥手]", R.drawable.emoji_80);
        mFaceMap.put("[闪电]", R.drawable.emoji_81);
        mFaceMap.put("[饥饿]", R.drawable.emoji_82);
        mFaceMap.put("[困]", R.drawable.emoji_83);
        mFaceMap.put("[咒骂]", R.drawable.emoji_84);
        mFaceMap.put("[折磨]", R.drawable.emoji_85);
        mFaceMap.put("[抠鼻]", R.drawable.emoji_86);
        mFaceMap.put("[鼓掌]", R.drawable.emoji_87);
        mFaceMap.put("[糗大了]", R.drawable.emoji_88);
        mFaceMap.put("[左哼哼]", R.drawable.emoji_89);
        mFaceMap.put("[哈欠]", R.drawable.emoji_90);
        mFaceMap.put("[快哭了]", R.drawable.emoji_91);
        mFaceMap.put("[吓]", R.drawable.emoji_92);
        mFaceMap.put("[篮球]", R.drawable.emoji_93);
        mFaceMap.put("[乒乓球]", R.drawable.emoji_94);
        mFaceMap.put("[NO]", R.drawable.emoji_95);
        mFaceMap.put("[跳跳]", R.drawable.emoji_96);
        mFaceMap.put("[怄火]", R.drawable.emoji_97);
        mFaceMap.put("[转圈]", R.drawable.emoji_98);
        mFaceMap.put("[磕头]", R.drawable.emoji_99);
        mFaceMap.put("[回头]", R.drawable.emoji_100);
        mFaceMap.put("[跳绳]", R.drawable.emoji_101);
        mFaceMap.put("[激动]", R.drawable.emoji_102);
        mFaceMap.put("[街舞]", R.drawable.emoji_103);
        mFaceMap.put("[献吻]", R.drawable.emoji_104);
        mFaceMap.put("[左太极]", R.drawable.emoji_105);
        mFaceMap.put("[右太极]", R.drawable.emoji_106);
        mFaceMap.put("[闭嘴]", R.drawable.emoji_107);


    }



    public void  initBitmapOption(Context context){
        if(options==null){
            options= new BitmapFactory.Options();
            options.inPreferredConfig= Bitmap.Config.RGB_565;
            options.inSampleSize=1;
            options.inSampleSize=calculateInSampleSize(context,options, DensityUtil.dpToPx(context.getResources(), 18), DensityUtil.dpToPx(context.getResources(),18));
            options.inJustDecodeBounds=false;
        }
    }


    private int calculateInSampleSize(Context context,BitmapFactory.Options options, int width, int height) {
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

    public SpannableString getSpannableString(Context context,String str){
        initBitmapOption(context);
        SpannableString spannableString=new SpannableString(str);
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        String zhengze = "\\[[^\\]]+\\]";
        // 通过传入的正则表达式来生成一个pattern
        Pattern pattern = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression(context, spannableString, pattern, 0,false);
        } catch (Exception e) {
            Log.e("dealExpression", e.getMessage());
        }
        return spannableString;
    }

    public SpannableString getSpannableStringHX(Context context,String str,boolean showHX){
        initBitmapOption(context);
        SpannableString spannableString=new SpannableString(str);
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        String zhengze = "\\[[^\\]]+\\]";
        // 通过传入的正则表达式来生成一个pattern
        Pattern pattern = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression(context, spannableString, pattern, 0,showHX);
        } catch (Exception e) {
            Log.e("dealExpression", e.getMessage());
        }
        return spannableString;
    }



    private void dealExpression(Context context, SpannableString spannableString, Pattern pattern, int start,boolean showHX) {
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }

            if(showHX){
                if(AppUtil.getInstance().getFaceMapHX().containsKey(key)){
                    int resId = AppUtil.getInstance().getFaceMapHX().get(key);
                    if (resId != 0) {
                        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
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
                                dealExpression(context, spannableString, pattern, end, showHX);
                            }
                        }
                        break;
                    }
                }
            }else{
                if(AppUtil.getInstance().getFaceMap().containsKey(key)){
                    int resId = AppUtil.getInstance().getFaceMap().get(key);
                    if (resId != 0) {
                        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
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
                                dealExpression(context, spannableString, pattern, end, showHX);
                            }
                        }
                        break;
                    }
                }
            }

        }
    }


}
