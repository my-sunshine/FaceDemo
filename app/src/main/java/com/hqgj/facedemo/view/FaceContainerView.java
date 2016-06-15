package com.hqgj.facedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hqgj.facedemo.R;
import com.hqgj.facedemo.adapter.FaceAdapter;
import com.hqgj.facedemo.adapter.FacePagerAdapter;
import com.hqgj.facedemo.common.AppContext;
import com.hqgj.facedemo.utils.DensityUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * author: ly
 * data: 2016/6/15
 */
public class FaceContainerView extends LinearLayout implements View.OnClickListener {

    /** 显示表情页的viewpager */
    private ViewPager viewPager;
    private ImageView showFace;
    private CirclePageIndicator indicator;
    private LinearLayout viewPagerInfo ;
    private Button send;
    private EditText editText;

    /** 表情页界面集合 */
    private ArrayList<View> faceViews;

    private InputMethodManager mInputMethodManager;
    private WindowManager.LayoutParams mParams;

    private boolean isFaceShow = false;
    private int currentPage = 0;// 表情页数
    private List<String> faceKeyList;// 表情list

    private View root;
    private Context context;
    private BitmapFactory.Options option;

    private ArrayList<String> keyList;// 表情list



    public FaceContainerView(Context context) {
        this(context,null);
    }

    public FaceContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        root= LayoutInflater.from(context).inflate(R.layout.layout_base_face_view,null);
        this.setOrientation(LinearLayout.VERTICAL);
        LayoutParams linearLayout= new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        this.removeAllViews();
        this.addView(root, linearLayout);
        keyList=new ArrayList<>();
        if(AppContext.getInstance().getFaceMap()!=null){
            Set<String > keySet=AppContext.getInstance().getFaceMap().keySet();
            if(!keySet.isEmpty()){
                keyList.addAll(keySet);
            }

        }

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mInputMethodManager= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        initView();
        initFaceView();
        option=new BitmapFactory.Options();
        option.inSampleSize=1;
        option.inPreferredConfig= Bitmap.Config.RGB_565;

    }

    private void initFaceView() {
        faceViews=new ArrayList<>();
        for(int index=0;index< AppContext.getInstance().NUM_PAGE;index++){
            faceViews.add(getGridView(index));
        }
        FacePagerAdapter facePagerAdapter =new FacePagerAdapter(faceViews);
        viewPager.setAdapter(facePagerAdapter);
        viewPager.setCurrentItem(currentPage);
        indicator.setViewPager(viewPager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(context, "page:" + position, Toast.LENGTH_LONG).show();
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPagerInfo.setVisibility(View.GONE);
    }

    private View getGridView( int page) {
        GridView gridView=new GridView(context);
        gridView.setNumColumns(7);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setBackgroundColor(Color.TRANSPARENT);
        gridView.setHorizontalSpacing(DensityUtil.dpToPx(context.getResources(), 1));
        gridView.setVerticalSpacing(DensityUtil.dpToPx(context.getResources(), 1));
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setCacheColorHint(0);
        gridView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        gridView.setGravity(Gravity.CENTER);
        FaceAdapter faceAdapter=new FaceAdapter(context, page);
        gridView.setAdapter(faceAdapter);

        faceAdapter.setOnFaceItemClickListener(new FaceAdapter.OnFaceItemClickListener() {
            @Override
            public void onFaceItemClickListener(int position) {
                Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
                int count = currentPage * AppContext.getInstance().NUM + position;
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), (Integer) AppContext.getInstance().getFaceMap().values().toArray()[count], option);
                if (bitmap != null) {
                    String emojiStr = keyList.get(count);
                    WeakReference<Bitmap> weakReference = new WeakReference<>(bitmap);
                    ImageSpan imageSpan = new ImageSpan(context, weakReference.get());
                    SpannableString spannableString = new SpannableString(emojiStr);
                    spannableString.setSpan(imageSpan, emojiStr.indexOf("["), emojiStr.indexOf("]") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    editText.append(spannableString);
                } else {
                    String emojiStr = keyList.get(count);
                    int index = editText.getSelectionStart();
                    StringBuilder s = new StringBuilder(editText.getText().toString());
                    s.insert(index, emojiStr);
                    editText.setText(s.toString());
                    editText.setSelection(index + emojiStr.length());
                }

            }

            @Override
            public void onFaceItemDeleteClickListener(int position) {
                Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
                if (position == AppContext.getInstance().NUM) {
                    int selection = editText.getSelectionStart();
                    String text = editText.getText().toString();
                    if (selection > 0) {
                        String text2 = text.substring(selection - 1);
                        if ("]".equals(text2)) {
                            int start = text.lastIndexOf("[");
                            int end = selection;
                            editText.getText().delete(start, end);
                            return;
                        }
                        editText.getText().delete(selection - 1, selection);
                    }
                }
            }
        });

        gridView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });
        return gridView;
    }

    private void initView() {
        showFace= (ImageView) root.findViewById(R.id.showFace);
        editText= (EditText) root.findViewById(R.id.editText);
        send= (Button) root.findViewById(R.id.send);
        viewPagerInfo= (LinearLayout) root.findViewById(R.id.viewPagerInfo);
        indicator= (CirclePageIndicator) root.findViewById(R.id.indicator);
        viewPager= (ViewPager) root.findViewById(R.id.viewPager);
        send.setClickable(true);
        send.setEnabled(true);
        send.setOnClickListener(this);
        showFace.setOnClickListener(this);
        editText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.showFace:

                if(!isFaceShow){

                   boolean flag= mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0, new ResultReceiver(new Handler(){
                        @Override
                        public void handleMessage(Message msg) {

                        }
                    }){
                        @Override
                        protected void onReceiveResult(int resultCode, Bundle resultData) {

                            try {
                                Thread.sleep(80);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            viewPagerInfo.setVisibility(View.VISIBLE);
                            isFaceShow=true;
                        }
                    });

                    if(!flag){
                        try {
                            Thread.sleep(80);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        viewPagerInfo.setVisibility(View.VISIBLE);
                        isFaceShow=true;
                    }

                }
                break;
            case R.id.send:

                if(viewPagerInfo.getVisibility()==View.VISIBLE){
                    viewPagerInfo.setVisibility(View.GONE);
                    isFaceShow=false;
                }else{
                    mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }

                String text=editText.getText().toString();

                onSendMessageListener.onSendMessageListener(text);

                editText.setText("");

                break;

            case R.id.editText:

                if(viewPagerInfo.getVisibility() ==View.VISIBLE){
                    viewPagerInfo.setVisibility(View.GONE);
                    isFaceShow=false;
                }


                break;
        }
    }

    public void setOnSendMessageListener(OnSendMessageListener onSendMessageListener){
        this.onSendMessageListener=onSendMessageListener;
    }
    private OnSendMessageListener onSendMessageListener;
    public interface OnSendMessageListener{
        void onSendMessageListener(String message);
    }
}
