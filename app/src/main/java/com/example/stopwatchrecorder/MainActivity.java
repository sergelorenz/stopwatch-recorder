package com.example.stopwatchrecorder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import hallianinc.opensource.timecounter.StopWatch;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "sessions.json";

    private EditText sessionNameEdit;

    private StopWatch stopWatch;
    private TextView stopWatchView;

    private Button btnStart;
    private Button btnPause;
    private Button btnSave;
    private Button btnSavedSessions;
    private Button btnReset;

    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopWatchView = findViewById(R.id.stopWatchView);
        stopWatch = new StopWatch(stopWatchView);
        sessionNameEdit = findViewById(R.id.sessionName);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnSave = findViewById(R.id.btnNewSession);
        btnReset = findViewById(R.id.btnReset);
        btnSavedSessions = findViewById(R.id.btnSavedSessions);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int SAVE_SESSION = 2;
                String sessionName = sessionNameEdit.getText().toString();
                Intent saveIntent = new Intent(MainActivity.this, Pop.class);
                saveIntent.putExtra("Session Name", sessionName);
                startActivityForResult(saveIntent, SAVE_SESSION);
            }
        });

        ArrayList<Session> sessionList = getSavedSessions();
        String latestSessionNum = Integer.toString(sessionList.size() + 1);
        String newSessionName = "Session" + latestSessionNum;
        sessionNameEdit.setText(newSessionName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String sessionName = data.getStringExtra("Session Name");
        if (resultCode == 2) {
            String sessionDescription = data.getStringExtra("Session Description");
            String sessionDate = data.getStringExtra("Session Date");
            sessionNameEdit.setText(sessionName);
            openSavedSessions(sessionDescription, sessionDate);

        } else {
            sessionNameEdit.setText(sessionName);

        }
    }
    public void openSaved(View v) {
        openSavedSessions(null, null);
    }


    public void openSavedSessions(@Nullable String sessionDescription, @Nullable String sessionDate) {
        Intent intent = new Intent(MainActivity.this, SavedSessions.class);
        if (sessionDescription != null) {
            intent.putExtra("Session Name", sessionNameEdit.getText().toString());
            intent.putExtra("Session Description", sessionDescription);
            intent.putExtra("Session Date", sessionDate);
            intent.putExtra("Time Record", stopWatchView.getText().toString());
        }
        this.finish();
        startActivity(intent);
    }

    public void newSession(View v) {
        finish();
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }

    public void startStopWatch(View v) {
        if (!running) {
            stopWatch.start();
            running = true;

            btnPause.setEnabled(true);
            btnStart.setEnabled(false);
            btnSave.setEnabled(false);
            btnSavedSessions.setEnabled(false);
            btnReset.setEnabled(true);
        }
    }

    public void pauseStopWatch(View v) {
        if (running) {
            running = false;
            stopWatch.pause();

            btnSavedSessions.setEnabled(true);
            btnSave.setEnabled(true);
            btnPause.setText("Resume");
        }
        else {
            running = true;
            stopWatch.resume();
            btnPause.setEnabled(true);
            btnStart.setEnabled(false);
            btnSave.setEnabled(false);
            btnSavedSessions.setEnabled(false);
            btnPause.setText("Pause/Stop");
        }
    }

    public void resetStopWatch(View v) {
        stopWatch.stop();
        running = false;
        stopWatchView.setText("0:0");
        btnSave.setEnabled(false);
        btnStart.setEnabled(true);
        btnReset.setEnabled(false);
        btnPause.setText("Pause/Stop");
        btnPause.setEnabled(false);
        btnSavedSessions.setEnabled(true);
    }


    public ArrayList<Session> getSavedSessions() {
        ArrayList<Session> savedSessionList = new ArrayList<>();
        String jsonString = "";
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }

            jsonString = sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    Gson gson = new Gson();
                    Type sessionType = new TypeToken<ArrayList<Session>>() {
                    }.getType();
                    savedSessionList = gson.fromJson(jsonString, sessionType);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return savedSessionList;
    }
}
