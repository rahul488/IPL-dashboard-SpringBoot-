package com.ipl.dashboard.ipl.Oauth2;

import com.ipl.dashboard.ipl.Entity.Viewer;
import com.ipl.dashboard.ipl.Repo.ViewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Oauth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ViewRepo viewerRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomOauth2User oauth2User=(CustomOauth2User)authentication.getPrincipal();
        System.out.println("user is"+oauth2User.getEmail());
        System.out.println("client is"+oauth2User.getClientName());
        System.out.println("name is"+oauth2User.getName());
        Viewer viewer= viewerRepo.findByEmail(oauth2User.getEmail());

        if(viewer != null){
            System.out.println("User account already exist...");
        }else{
            System.out.println(("New User........."));
            Viewer viewer1=new Viewer();
            viewer1.setEmail(oauth2User.getEmail());
            viewer1.setRole("ROLE_USER");
            viewer1.setClientName(oauth2User.getClientName());
            viewerRepo.save(viewer1);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
