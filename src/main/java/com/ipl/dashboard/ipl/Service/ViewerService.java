package com.ipl.dashboard.ipl.Service;

import com.ipl.dashboard.ipl.Entity.Viewer;
import com.ipl.dashboard.ipl.Repo.ViewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewerService implements UserDetailsService {


    @Autowired
    private ViewRepo viewRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Viewer viewer=viewRepo.findByEmail(username);

        if(viewer == null)
            throw new UsernameNotFoundException("User Not Found..");

        return User.withUsername(username).password(viewer.getPassword()).authorities(getAuthority(viewer)).build();
    }

    private List<SimpleGrantedAuthority> getAuthority(Viewer viewer) {
        return Arrays.stream(viewer.getRole().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
