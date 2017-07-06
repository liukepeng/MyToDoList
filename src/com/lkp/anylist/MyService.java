package com.lkp.anylist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

import com.lkp.todolist.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LKP on 2015/4/13.
 */
public class MyService extends Service{

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
     Notification.Builder myBuilder;
    public NotificationManager myNotificationManager;

    private void initNotify(){
        myNotificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        myBuilder=new Notification.Builder(this);
        myBuilder.setContentTitle("标题")
                .setContentText("内容")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                .setTicker("通知来了")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.yes);
    }
//    public void showNotify(){
//        myBuilder.setContentTitle("未完成任务")
//                .setContentText("")
//                .setTicker("通知来啦");
//
//        myNotificationManager.notify(notifyId,myBuilder.build());
//    }
    //表示即将发生的意图，
public PendingIntent getDefalutIntent(int flags){
    PendingIntent pendingIntent=PendingIntent.getActivity(this,1,new Intent(),flags);
    return  pendingIntent;
}
    public  void helloService(){
    DBHelper helper = new DBHelper(MyService.this);
    SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd HH:mm");
    while (true){
        try {
        Cursor cursor = helper.queryNotification();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do{
                    int isNotifyColumn = cursor.getColumnIndex("isNotifiy");
                    String isNotifyData = cursor.getString(isNotifyColumn);

                    int timeColumn = cursor.getColumnIndex("time");
                    String timeData = cursor.getString(timeColumn);

                    int numIdColumn =cursor.getColumnIndex("_id");
                    int idData = cursor.getInt(numIdColumn);

                    int numContentColumn = cursor.getColumnIndex("content");
                    String contentData = cursor.getString(numContentColumn);

                    Date date=new Date();


                    if (isNotifyData.equals("true")&&timeData.equals( df.format(date)) ){
                        showIntentActivityNotify(idData,contentData);

                    }
                } while (cursor.moveToNext());
            }
        }
        cursor.close();

            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


    public int onStartCommand (Intent intent,int flags,int startId){
        initNotify();
        new Thread(new Runnable() {
            @Override
            public void run() {
                helloService();
            }
        }).start();
        return super.onStartCommand(intent,flags,startId);
    }

public void showIntentActivityNotify(int idData,String content){
    myBuilder.setAutoCancel(true)
            .setContentTitle("您有未完成任务")
            //内容
            .setContentText(content)
            .setTicker("It's time for AnyList Moment");
    Intent resultIntent = new Intent(this,FirstActivity.class);
    resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_CANCEL_CURRENT);
    myBuilder.setContentIntent(pendingIntent);
    myNotificationManager.notify(idData,myBuilder.build());
}


}
