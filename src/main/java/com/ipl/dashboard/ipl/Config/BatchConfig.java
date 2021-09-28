package com.ipl.dashboard.ipl.Config;

import com.ipl.dashboard.ipl.Batch.Processor;
import com.ipl.dashboard.ipl.Batch.Writer;
import com.ipl.dashboard.ipl.DTO.MatchInput;
import com.ipl.dashboard.ipl.Entity.Match;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private Processor processor;

    @Autowired
    private Writer writer;


    @Bean
    public Job job(){

        Step step=stepBuilderFactory.get("MATCH_DATA")
                .<MatchInput, Match>chunk(10)
                .reader(reader())
                .processor(processor)
                .writer(writer)
                .build();
        return jobBuilderFactory.get("IPL_DATA").incrementer(new RunIdIncrementer()).start(step).build();

    }

    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("matchItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("match-data.csv"))
                .delimited()
                .names(new String[]{"city","date","player_of_match","venue","neutral_venue","team1","team2","toss_winner","toss_decision",
                        "winner","result","result_margin","eliminator","umpire1","umpire2"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {{
                    setTargetType(MatchInput.class);
                }})
                .build();
    }



}
