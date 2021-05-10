package com.mad2021.classapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class reminder_alarm extends AppCompatActivity {
    TimePicker alarmTime;
    TextClock currentTime;
    private Button set_reminder;
    private ImageView back_Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_alarm);
        back_Home = (ImageView)findViewById(R.id.back_home);
        alarmTime = findViewById(R.id.timePicker);
        currentTime = findViewById(R.id.textClock);
        set_reminder = (Button)findViewById(R.id.set_reminder);
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        Timer t = new Timer();
        // Setting the Reminder
        set_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (currentTime.getText().toString().equals(AlarmTime())){
                            r.play();
                        }else{
                            r.stop();
                        }
                    }
                },0,5000);
                Toast.makeText(reminder_alarm.this, "Reminder set", Toast.LENGTH_SHORT).show();
            }
        });


        // Redirecting  user to the dashboard
        back_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(reminder_alarm.this,Student_DashBorad.class));
            }
        });

    }
    public String AlarmTime(){
        Integer alarmHours = alarmTime.getCurrentHour();
        Integer alarmMinutes = alarmTime.getCurrentMinute();
        String stringAlarmMinutes;
        if(alarmMinutes < 10){
            stringAlarmMinutes = "0";
            stringAlarmMinutes = stringAlarmMinutes.concat(alarmMinutes.toString());
        }else{
            stringAlarmMinutes = alarmMinutes.toString();
        }

        String stringAlarmTime;
        if (alarmHours > 12){
            alarmHours =   alarmHours - 12;
            stringAlarmTime = alarmHours.toString().concat(":").concat(stringAlarmMinutes).concat(" PM");
        }else {
            stringAlarmTime = alarmHours.toString().concat(":").concat(stringAlarmMinutes).concat(" AM");
        }

        return stringAlarmTime;
    }
}