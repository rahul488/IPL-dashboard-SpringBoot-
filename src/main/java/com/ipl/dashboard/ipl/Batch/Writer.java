package com.ipl.dashboard.ipl.Batch;

import com.ipl.dashboard.ipl.Entity.Match;
import com.ipl.dashboard.ipl.Repo.MatchRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Writer implements ItemWriter<Match> {


    @Autowired
    private MatchRepo matchRepo;


    @Override
    public void write(List<? extends Match> matches) throws Exception {
        matchRepo.saveAll(matches);
    }
}
