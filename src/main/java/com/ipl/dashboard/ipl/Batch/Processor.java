package com.ipl.dashboard.ipl.Batch;

import com.ipl.dashboard.ipl.DTO.MatchInput;
import com.ipl.dashboard.ipl.Entity.Match;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Processor implements ItemProcessor<MatchInput, Match> {

    @Override
    public Match process(MatchInput matchInput) throws Exception {

        Match match=new Match();

        match.setCity(matchInput.getCity());
        match.setVenue(matchInput.getVenue());

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("d-MM-yyy");

        LocalDate dateObject = LocalDate.parse(matchInput.getDate(),formatter);

        match.setDate(dateObject);

        match.setTossDecision(matchInput.getToss_decision());

        String firstInningsTeam,secondInningsTeam;

        //order the team 1 and team2 according to firstInnings and secondInnings respectively.

        if("bat".equals(matchInput.getToss_decision())){
            firstInningsTeam=matchInput.getToss_winner();
            secondInningsTeam=matchInput.getToss_winner().equals(matchInput.getTeam1())?matchInput.getTeam2():matchInput.getTeam1();
        }
        else{
            secondInningsTeam=matchInput.getToss_winner();
            firstInningsTeam=matchInput.getToss_winner().equals(matchInput.getTeam1())?matchInput.getTeam2():matchInput.getTeam1();
        }

        match.setTeam1(firstInningsTeam);
        match.setTeam2(secondInningsTeam);

        match.setVenue(matchInput.getVenue());
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setTossWinner(matchInput.getToss_winner());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());
        match.setWinner(matchInput.getWinner());


        return match;
    }
}
