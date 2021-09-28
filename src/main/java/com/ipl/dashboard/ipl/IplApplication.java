package com.ipl.dashboard.ipl;

import com.ipl.dashboard.ipl.Service.TeamService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.UUID;

@SpringBootApplication
public class IplApplication  {

	public static void main(String[] args) {
		SpringApplication.run(IplApplication.class, args);
	}


}
