package com.example.stopwatchrecorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.SessionViewHolder> {
    private ArrayList<Session> sessionList;

    public static class SessionViewHolder extends RecyclerView.ViewHolder {

        public TextView sessionName;
        public TextView sessionDescription;
        public TextView sessionDate;
        public TextView timeRecord;

        public SessionViewHolder(View itemView) {
            super(itemView);
            sessionName = itemView.findViewById(R.id.sessionNameTextView);
            sessionDescription = itemView.findViewById(R.id.sessionDescriptionTextView);
            sessionDate = itemView.findViewById(R.id.sessionDateTextView);
            timeRecord = itemView.findViewById(R.id.timeRecordTextView);

        }
    }

    public SessionsAdapter(ArrayList<Session> sessionList) {
        this.sessionList = sessionList;

    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_sessions_template, parent, false);
        return new SessionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        Session currentSession = this.sessionList.get(position);

        holder.sessionName.setText(currentSession.getSessionName());
        holder.sessionDate.setText(currentSession.getSessionDate());
        holder.sessionDescription.setText(currentSession.getSessionDescription());
        holder.timeRecord.setText(currentSession.getTimeRecord());
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }
}
