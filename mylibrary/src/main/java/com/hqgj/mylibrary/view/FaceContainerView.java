package com.hqgj.mylibrary.view;

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
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hqgj.mylibrary.R;
import com.hqgj.mylibrary.adapter.FaceAdapter;
import com.hqgj.mylibrary.adapter.FacePagerAdapter;
import com.hqgj.mylibrary.common.AppUtil;
import com.hqgj.mylibrary.utils.DensityUtil;
import com.hqgj.mylibrary.view.indicator.CirclePageIndicator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Set;

/**
 * author: ly
 * data: 2016/6/15
 */
public class FaceContainerView extends LinearLayout implements View.OnClickListener {

    private ViewPager viewPager;
    private ImageView showFace;
    private CirclePageIndicator indicator;
    private LinearLayout viewPagerInfo ;
    private Button send;
    private EditText editText;
    private ImageView addPhoto;
    private LinearLayout photoInfo;
    private TextView takeCamera;
    private TextView takePhoto;


    private ArrayList<View> faceViews;

    private InputMethodManager mInputMethodManager;

    private boolean isFaceShow = false;

    private boolean isTakePhotoShow = false;
    private int currentPage = 0;

    private View root;
    private Context context;
    private BitmapFactory.Options option;

    private ArrayList<String> keyList;



    public FaceContainerView(Context context) {
        this(context,null);
    }

    public FaceContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        AppUtil.getInstance().init();
        root= LayoutInflater.from(context).inflate(R.layout.layout_base_face_view,null);
        this.setOrientation(LinearLayout.VERTICAL);
        LayoutParams linearLayout= new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        this.removeAllViews();
        this.addView(root, linearLayout);
        keyList=new ArrayList<>();
        if(AppUtil.getInstance().getFaceMap()!=null){
            Set<String > keySet= AppUtil.getInstance().getFaceMap().keySet();
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
        for(int index=0;index< AppUtil.getInstance().NUM_PAGE;index++){
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
        gridView.setVerticalSpacing(DensityUtil.dpToPx(context.getResources(), 12));
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setCacheColorHint(0);
        gridView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        gridView.setPadding(0,DensityUtil.dpToPx(context.getResources(), 12),0,0);
        gridView.setGravity(Gravity.CENTER);
        FaceAdapter faceAdapter=new FaceAdapter(context, page);
        gridView.setAdapter(faceAdapter);

        faceAdapter.setOnFaceItemClickListener(new FaceAdapter.OnFaceItemClickListener() {
            @Override
            public void onFaceItemClickListener(int position) {
                int count = currentPage * AppUtil.getInstance().NUM + position;
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), (Integer) AppUtil.getInstance().getFaceMap().values().toArray()[count], option);
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
                if (position == AppUtil.getInstance().NUM) {
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
        takePhoto= (TextView) root.findViewById(R.id.takePhoto);
        takeCamera= (TextView) root.findViewById(R.id.takeCamera);
        photoInfo= (LinearLayout) root.findViewById(R.id.photoInfo);
        addPhoto= (ImageView) root.findViewById(R.id.addPhoto);
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
        addPhoto.setOnClickListener(this);

        takePhoto.setOnClickListener(this);
        takeCamera.setOnClickListener(this);

        editText.requestFocus();

        if(!isTakePhotoShow){
            addPhoto.setVisibility(View.GONE);
            send.setVisibility(View.VISIBLE);
        }

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (isFaceShow) {
                        return;
                    }
                    if (viewPagerInfo.getVisibility() == View.VISIBLE) {
                        viewPagerInfo.setVisibility(View.GONE);
                        isFaceShow = false;
                    }
                    if (View.VISIBLE == photoInfo.getVisibility()) {
                        photoInfo.setVisibility(View.GONE);
                    }
                }
            }
        });



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!isTakePhotoShow) {
                    return;
                }
                if (s.length() > 0) {

                    addPhoto.setVisibility(View.GONE);

                    send.setVisibility(View.VISIBLE);

                } else {

                    addPhoto.setVisibility(View.VISIBLE);

                    send.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.showFace){
            if(View.VISIBLE==photoInfo.getVisibility()){
                photoInfo.setVisibility(View.GONE);
            }
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
                    editText.requestFocus();
                }

            }
        }else if(v.getId()==R.id.send){
            if(viewPagerInfo.getVisibility()==View.VISIBLE){
                viewPagerInfo.setVisibility(View.GONE);
                isFaceShow=false;
            }else{
                mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }

            String text=editText.getText().toString();

            onSendMessageListener.onSendMessageListener(text);

            editText.setText("");
        }else if(v.getId()==R.id.editText){
            if(viewPagerInfo.getVisibility() ==View.VISIBLE){
                viewPagerInfo.setVisibility(View.GONE);
                isFaceShow=false;
            }
            if(View.VISIBLE==photoInfo.getVisibility()){
                photoInfo.setVisibility(View.GONE);
            }
        }


        else if(v.getId()==R.id.addPhoto){

            if(!isTakePhotoShow){
                return;
            }

            editText.clearFocus();

            if(viewPagerInfo.getVisibility()==View.VISIBLE){
                viewPagerInfo.setVisibility(View.GONE);
                isFaceShow=false;
            }

            if(View.VISIBLE==photoInfo.getVisibility()){
                photoInfo.setVisibility(View.GONE);

            }else{
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
                        photoInfo.setVisibility(View.VISIBLE);
                    }
                });

                if(!flag){
                    photoInfo.setVisibility(View.VISIBLE);
                }

            }

        }else if(v.getId()==R.id.takePhoto){
            if (View.VISIBLE == photoInfo.getVisibility()) {
                photoInfo.setVisibility(View.GONE);
            }
            onTakePhotoListener.onTakePhotoListener();
        }else if(v.getId()==R.id.takeCamera){
            if (View.VISIBLE == photoInfo.getVisibility()) {
                photoInfo.setVisibility(View.GONE);
            }
            onTakePhotoListener.onTakeCameraListener();
        }

    }


    public void showKeyBoard(){
        if(viewPagerInfo.getVisibility()==View.VISIBLE){
            viewPagerInfo.setVisibility(View.GONE);
            isFaceShow=false;
        }
        editText.requestFocus();
        mInputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }


    public void hiddenKeyBoard(){
        if(viewPagerInfo.getVisibility()==View.VISIBLE){
            viewPagerInfo.setVisibility(View.GONE);
            isFaceShow=false;
        }else{
            mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }


    public void setTakePhotoShow(boolean isTakePhotoShow){
        this.isTakePhotoShow=isTakePhotoShow;
        if(isTakePhotoShow){
            addPhoto.setVisibility(View.VISIBLE);
            send.setVisibility(View.GONE);
        }else{
            addPhoto.setVisibility(View.GONE);
            send.setVisibility(View.VISIBLE);
        }

    }

    public void setOnSendMessageListener(OnSendMessageListener onSendMessageListener){
        this.onSendMessageListener=onSendMessageListener;
    }
    private OnSendMessageListener onSendMessageListener;

    public interface OnSendMessageListener{
        void onSendMessageListener(String message);
    }


    public void setOnTakePhotoListener(OnTakePhotoListener onTakePhotoListener){
        this.onTakePhotoListener=onTakePhotoListener;
    }

    private OnTakePhotoListener onTakePhotoListener;

    public interface OnTakePhotoListener{
        void onTakePhotoListener();
        void onTakeCameraListener();
    }


}
