package com.ipl.dashboard.ipl.Repo;

import com.ipl.dashboard.ipl.Entity.Viewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewRepo extends JpaRepository<Viewer,Integer> {

    public Viewer findByEmail(String email);

}
