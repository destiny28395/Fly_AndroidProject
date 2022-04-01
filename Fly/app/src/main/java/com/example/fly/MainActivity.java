package com.example.fly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private boolean isMute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this,MyService.class));


        findViewById(R.id.txtPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,GameActivity.class));

            }
        });
        findViewById(R.id.txtQuit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(MainActivity.this,MyService.class));
                Intent intent = new Intent(MainActivity.this,Reminder.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,
                        intent,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                long timeClick = System.currentTimeMillis();
                long click = 1000*5;
                alarmManager.set(AlarmManager.RTC_WAKEUP,click+timeClick,pendingIntent);
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            }
        });
        
        TextView highScoreTxt = findViewById(R.id.txtHighScore);
        SharedPreferences prefs = getSharedPreferences("game",MODE_PRIVATE);
        highScoreTxt.setText("HighScore: "+prefs.getInt("highscore",0));
        isMute = prefs.getBoolean("isMute",false);
        ImageView volumeCtrl = findViewById(R.id.volumeCtrl);
        if(isMute)
        {
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
        }
        else {
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
        }
        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMute = !isMute;
                if(isMute)
                {
                    volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    startService(new Intent(MainActivity.this,MyService.class));
                }
                else {
                    volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    stopService(new Intent(MainActivity.this,MyService.class));

                }
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute",isMute);
                editor.apply();
            }
        });
    }
}