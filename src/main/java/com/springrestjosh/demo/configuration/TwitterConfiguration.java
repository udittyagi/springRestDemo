package com.springrestjosh.demo.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class TwitterConfiguration {
    public static final String TWITTER_NAME = "twitter";
    /**
     * The root directory to which all uploads for the application are uploaded.
     */
    public static final File TWITTER_STORAGE_DIRECTORY = new File(System.getProperty("user.home"),TWITTER_NAME);
    /**
     * Things are first uploaded by the application server to this directory. it's a sort
     * of staging directory
     */
    public static final File TWITTER_STORAGE_UPLOAD_DIRECTORY = new File(TWITTER_STORAGE_DIRECTORY,"uploads");
    /**
     * When a profile photo is uploaded, the resultant, completely uploaded image is
     * stored in this directory
     */
    public static final File TWITTER_PROFILE_UPLOAD_DIRECTORY = new File(TWITTER_STORAGE_DIRECTORY,"profiles");



    @PostConstruct
    public void settingDirectories() throws Throwable{
        File[] files = {TWITTER_STORAGE_DIRECTORY,TWITTER_STORAGE_UPLOAD_DIRECTORY,TWITTER_PROFILE_UPLOAD_DIRECTORY};
        for(File f : files){
            if(!f.exists() && !f.mkdir()){
                String msg = String.format("you must create the profile photos directory ('%s') " +
                        "and make it accessible to this process. Unable to do so from this process.", f.getAbsolutePath());
                throw new RuntimeException(msg);
            }
        }
    }
}
