package com.ipl.dashboard.ipl.Controller;

import com.ipl.dashboard.ipl.Entity.Match;
import com.ipl.dashboard.ipl.Entity.Team;
import com.ipl.dashboard.ipl.Repo.MatchRepo;
import com.ipl.dashboard.ipl.Repo.TeamRepo;
import com.ipl.dashboard.ipl.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class MatchController {

   @Autowired
   private TeamService matchService;
   @Autowired
   private MatchRepo matchRepo;

   @Autowired
   private TeamRepo teamRepo;

    @GetMapping("/teams/{teamName}")
    public Team getMatches(@PathVariable String teamName){

        Team team=teamRepo.findByName(teamName);

        team.setMatches(matchRepo.findLatestMatchesByTeam(teamName,4));

        return team;

    }

    @GetMapping("/teams/{teamName}/matches")
    public List<Match> getByYear(@PathVariable String teamName, @RequestParam int year) throws ParseException {

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("d-MM-yyy");

        LocalDate date1=LocalDate.parse("01-01-"+year,formatter);
        year+=1;
        LocalDate date2=LocalDate.parse("01-01-"+year,formatter);

//        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
//
//        Date date1 = dateFormatter.parse("01-01-"+year);
//        year+=1;
//        Date date2 = dateFormatter.parse("01-01-"+year);

        return matchRepo.getMatchOfTeamByYear(teamName,date1,date2);
    }
    @GetMapping("/getAllTeams")
    public List<Team> getAllTeams(){
        return teamRepo.findAll();
    }

}
