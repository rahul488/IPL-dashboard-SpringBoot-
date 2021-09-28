package com.ipl.dashboard.ipl.Repo;

import com.ipl.dashboard.ipl.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepo extends JpaRepository<Team,Integer> {

    public Team findByName(String teamName);
}
