package com.folautech.cleanupdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.folautech.cleanupdata.service.CSVFileService;
import com.folautech.cleanupdata.service.FileProcessorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CleanUpDataApplication implements ApplicationRunner {

    @Autowired
    private FileProcessorService fileProcessorService;

    @Autowired
    private CSVFileService       csvFileService;

    public static void main(String[] args) {
        SpringApplication.run(CleanUpDataApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String slug = fileProcessorService.cleanUp("novi-be-assignment.csv");

        String filename = csvFileService.generateFile(slug);

        log.info("filename={}", filename);
    }

}
