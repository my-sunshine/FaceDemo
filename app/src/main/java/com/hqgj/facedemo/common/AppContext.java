package com.hqgj.facedemo.common;

import android.app.Application;
import android.graphics.Bitmap;

import com.hqgj.facedemo.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * author: ly
 * data: 2016/6/15
 */
public class AppContext extends Application {

    public int NUM_PAGE = 0;// 总共有多少页
    public int NUM = 20;// 每页20个表情,还有最后一个删除button
    private Map<String, Integer> mFaceMap = new LinkedHashMap<String, Integer>();
    private static AppContext application;

    public AppContext() {
        application = this;
    }

    public static synchronized AppContext getInstance() {

        if (application == null) {
            application = new AppContext();
        }

        return application;
    }


    @Override
    public void onCreate() {
        super.onCreate();
       // initImageLoader();
       // initFaceMap();
       // NUM_PAGE = (int) Math.ceil(mFaceMap.size() / 20.0);
    }

    private void initImageLoader() {
        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.emoji_1)
                .showImageOnLoading(R.drawable.emoji_1)
                .showImageOnFail(R.drawable.emoji_1).cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(imageOptions).
                threadPoolSize(5).memoryCache(new WeakMemoryCache()).threadPriority(Thread.MIN_PRIORITY).build();
        ImageLoader.getInstance().init(configuration);

    }

    public Map<String, Integer> getFaceMap() {
        if (!mFaceMap.isEmpty())
            return mFaceMap;
        return null;
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


}
