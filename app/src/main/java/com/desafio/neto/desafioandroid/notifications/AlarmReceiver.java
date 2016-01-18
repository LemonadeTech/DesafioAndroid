package com.desafio.neto.desafioandroid.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.desafio.neto.desafioandroid.MainActivity_;
import com.desafio.neto.desafioandroid.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent resultIntent = new Intent(context, MainActivity_.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification builder = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Desafio Android")
                .setContentText("Confira a previsão do tempo!")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        int mNotificationId = 1;
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, builder);
    }
}
