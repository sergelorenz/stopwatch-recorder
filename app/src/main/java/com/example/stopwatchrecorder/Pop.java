package com.example.stopwatchrecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Pop extends Activity {
    private EditText editTextSessionName;
    private EditText editTextDescription;
    private Intent responseIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupsave);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.5));

        editTextSessionName = findViewById(R.id.editTextSessionName);
        editTextDescription = findViewById(R.id.editTextDescription);


        Intent saveIntent = getIntent();
        String sessionName = saveIntent.getStringExtra("Session Name");
        editTextSessionName.setText(sessionName);

        responseIntent = new Intent(Pop.this, MainActivity.class);
    }

    public void cancelSave(View v) {
        String sessionName = editTextSessionName.getText().toString();
        responseIntent.putExtra("Session Name", sessionName);

        setResult(0, responseIntent);
        this.finish();
    }

    public void saveSession(View v) {
        String sessionName = editTextSessionName.getText().toString();
        String sessionDescription = editTextDescription.getText().toString();
        String sessionDate = getDate();
        responseIntent.putExtra("Session Name", sessionName);
        responseIntent.putExtra("Session Description", sessionDescription);
        responseIntent.putExtra("Session Date", sessionDate);
        setResult(2, responseIntent);
        this.finish();
    }

    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String currentTime = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date());
        return dateFormat.format(calendar.getTime()) + "   " + currentTime;
    }
}
