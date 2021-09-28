package com.ipl.dashboard.ipl.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private long totalMatch;

    private long totalWins;

    public Team(String name, long totalMatch) {
        this.name = name;
        this.totalMatch = totalMatch;
    }

    @Transient
    private List<Match> matches;
}
