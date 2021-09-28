package com.ipl.dashboard.ipl.Repo;

import com.ipl.dashboard.ipl.Entity.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepo extends JpaRepository<Match,Integer> {

    @Query("select m.team1,count(*) from Match m group by m.team1")
    public List<Object[]> getTeam1Details();
    @Query("select m.team2,count(*) from Match m group by m.team2")
    public List<Object[]> getTeam2Details();

    @Query("select m.winner,count(*) from Match m group by m.winner")
    public List<Object[]> countTotalWinsByTeam();

//    @Query("select m from Match m where m.team1 =:teamName1 or m.team2 =:teamName2")
//    public List<Match> getMatchesByTeam(@Param("teamName1") String teamName1,@Param("teamName2")String teamName2);


    @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) and m.date between :date1 and :date2 order by date desc ")
    public List<Match> getMatchOfTeamByYear(@Param("teamName")String teamName, @Param("date1") LocalDate date1,@Param("date2") LocalDate date2);

    public List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1,String team2,Pageable pageable);

    default List<Match> findLatestMatchesByTeam(String teamName,int count){
        return getByTeam1OrTeam2OrderByDateDesc(teamName,teamName, PageRequest.of(0,count));
    }

}
