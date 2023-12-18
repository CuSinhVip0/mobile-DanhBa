package vn.edu.stu.do_an_mobile;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class BirthdayAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

            // Prepare the notification channel (required for Android 8.0 Oreo and above)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Birthday Reminder Channel";
                String description = "Channel for Birthday Reminder";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("birthdayChannelId", name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            // Create the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "birthdayChannelId")
                    .setSmallIcon(R.drawable.jake) // replace with your own drawable icon
                    .setContentTitle("Sinh nhật vui vẻ!")
                    .setContentText("Chúc mừng sinh nhật! Chúc bạn một ngày tuyệt vời!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // Show the notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, builder.build()); // 1 is the notification ID

    }
}
