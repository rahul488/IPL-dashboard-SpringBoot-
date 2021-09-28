package com.ipl.dashboard.ipl.Controller;

import com.ipl.dashboard.ipl.DTO.AuthRequest;
import com.ipl.dashboard.ipl.Entity.Viewer;
import com.ipl.dashboard.ipl.Repo.ViewRepo;
import com.ipl.dashboard.ipl.Service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ViewRepo viewRepo;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/generateToken")
    public List<String> generateToken(@RequestBody AuthRequest request){

        String username=request.getEmail();
        String password=request.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

        Viewer viewer=viewRepo.findByEmail(username);

        String name=viewer.getName();
        String role=viewer.getRole();
        String token=jwtService.generateToken(username);

        List<String> details=new ArrayList<>();
        details.add(name);
        details.add(role);
        details.add(token);

        return details;

    }
    @PostMapping("/register")
    public String signup(@RequestBody Viewer viewer) throws  Exception{

        Viewer viewer1=viewRepo.findByEmail(viewer.getEmail());

        if(viewer1 != null)
            throw new Exception("User already exist");

        viewer.setClientName("Local-Login");

        viewer.setPassword(passwordEncoder.encode(viewer.getPassword()));

        viewer.setRole("ROLE_USER");

        viewRepo.save(viewer);

        return "Success";
    }

}
