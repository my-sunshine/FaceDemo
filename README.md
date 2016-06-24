 FaceDemo 
==== 

####1、README文件使用教程
 
 http://blog.csdn.net/kaitiren/article/details/38513715
 
####2、如何使用Android Studio把自己的Android library分享到jCenter和Maven Central
 
 http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0623/3097.html

####3、使用

######1、
compile 'com.hqgj:mylibrary:0.3.0'

######2、
    <com.hqgj.mylibrary.view.FaceContainerView
        android:id="@+id/faceContainerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />

######3、
faceContainerView.setOnSendMessageListener(

	new FaceContainerView.OnSendMessageListener() 

	{
            @Override
            public void onSendMessageListener(String message) {

                Toast.makeText(MainActivity.this, "text:" + message, Toast.LENGTH_LONG).show();

                lists.add(message);

                adapter.notifyDataSetInvalidated();

            }

        }

     );

![](https://github.com/my-sunshine/FaceDemo/raw/master/app/img/demo1.jpg)  
