package com.oztz.hackinglabmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.adapter.TeamMembersAdapter;
import com.oztz.hackinglabmobile.businessclasses.Participant;
import com.oztz.hackinglabmobile.businessclasses.Team;
import com.oztz.hackinglabmobile.helper.App;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.RequestTask;

import java.util.ArrayList;
import java.util.List;

public class TeamDetailActivity extends ActionBarActivity implements JsonResult {

    Team team;
    ListView teamsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        team = loadTeam();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        teamsListView = (ListView) findViewById(R.id.Team_Detail_List_View);
        new RequestTask(this).execute(getResources().getString(R.string.hackingLabUrl) +
                "WebService/GetUsers/" + String.valueOf(App.eventId), "teamMembers");
        Log.d("DEBUG", getResources().getString(R.string.hackingLabUrl) +
                "WebService/GetUsers/" + String.valueOf(App.eventId));
    }

    private Team loadTeam(){
        Intent intent = getIntent();
        return new Gson().fromJson(intent.getStringExtra("team"), Team.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_team_detail, menu);
        restoreActionBar();
        return true;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(team.groupname);
    }

    private Participant[] getTeamMembers(Participant[] participants){
        List<Participant> teamMembers = new ArrayList<Participant>();
        for(int i=0; i<participants.length; i++){
            if(participants[i].groupID == team.groupID){
                teamMembers.add(participants[i]);
            }
        }
        return teamMembers.toArray(new Participant[teamMembers.size()]);
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {
        try {
            Participant[] p;
            p = new Gson().fromJson(JsonString, Participant[].class);
            final Participant[] participants = getTeamMembers(p);
            teamsListView.setAdapter(new TeamMembersAdapter(getApplicationContext(), R.layout.item_teams, participants));
        } catch(Exception e){
            Toast.makeText(this, "Error getting data", Toast.LENGTH_SHORT);
        }
    }
}
