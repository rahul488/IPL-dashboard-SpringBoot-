package com.ipl.dashboard.ipl.Service;

import com.ipl.dashboard.ipl.Entity.Match;
import com.ipl.dashboard.ipl.Entity.Team;
import com.ipl.dashboard.ipl.Repo.MatchRepo;
import com.ipl.dashboard.ipl.Repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamService {

    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private TeamRepo teamRepo;


    public void setTeamDetails(){

        Map<String, Team> teamData=new HashMap<>();

        List<Object[]> team1=matchRepo.getTeam1Details();
        List<Object[]> team2=matchRepo.getTeam2Details();
        List<Object[]> totalWin= matchRepo.countTotalWinsByTeam();

        team1.stream().map(t->new Team((String)t[0],(long) t[1]))
                .forEach(e->teamData.put(e.getName(),e));
        team2.stream().forEach(e->{
            String teamName=(String)e[0];
            Team team=teamData.get(teamName);
            team.setTotalMatch((long)e[1]+team.getTotalMatch());
           teamData.put(teamName,team);
        });
        totalWin.stream().forEach(e->{
            Team team=teamData.get((String) e[0]);
            if(team!=null)team.setTotalWins((long) e[1]);
        });

        for(Team t:teamData.values()){
            teamRepo.save(t);
        }
    }

}
