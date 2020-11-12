package com.example.stopwatchrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class SavedSessions extends AppCompatActivity {
    private static final String FILE_NAME = "sessions.json";

    private RecyclerView savedSessionView;
    private RecyclerView.Adapter savedSessionAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Session> sessionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_sessions);

        savedSessionView = findViewById(R.id.savedSessionView);

        // load saved file if it exists
        sessionList = getSavedSessions();

        Intent sessionIntent = getIntent();
        Bundle extras = sessionIntent.getExtras();
        if (extras != null) {
            String newSessionName = extras.getString("Session Name");
            String newSessionDescription = extras.getString("Session Description");
            String newSessionDate = extras.getString("Session Date");
            String timeRecord = extras.getString("Time Record");
            sessionList.add(0, new Session(newSessionName, newSessionDescription, newSessionDate, timeRecord));

            // overwrite to file
            saveSessions(sessionList);
        }


        savedSessionView = findViewById(R.id.savedSessionView);
        savedSessionView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        savedSessionAdapter = new SessionsAdapter(sessionList);

        savedSessionView.setLayoutManager(layoutManager);
        savedSessionView.setAdapter(savedSessionAdapter);

    }

    public void newSession(View v) {
        Intent intent = new Intent(SavedSessions.this, MainActivity.class);
        this.finish();
        startActivity(intent);
    }

    public void clearSessions(View v) {
        deleteFile(FILE_NAME);
        sessionList.clear();
        savedSessionView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        savedSessionAdapter = new SessionsAdapter(sessionList);
        savedSessionView.setLayoutManager(layoutManager);
        savedSessionView.setAdapter(savedSessionAdapter);
    }

    public void saveSessions(ArrayList<Session> sessionList) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(sessionList);

        // save file
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(jsonString.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
