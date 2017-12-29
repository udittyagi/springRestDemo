package com.springrestjosh.demo.controller;

import com.springrestjosh.demo.domain.ProfilePhoto;
import com.springrestjosh.demo.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class PhotoUploadController {
    @Autowired
    TwitterService twitterService;
    @PostMapping("/upload")
    ResponseEntity<String> uploadPhoto(@RequestParam Long accountId, @RequestBody MultipartFile file){

        if(file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")) {
            try {
                byte[] imageBytes = file.getBytes();
                twitterService.writeUserProfilePhoto(accountId, MediaType.parseMediaType(file.getContentType()),imageBytes);
                System.out.println(file.getContentType());
                return new ResponseEntity<String>("File Uploaded", HttpStatus.OK);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<String>("File Uploaded ", HttpStatus.OK);
        }

        return new ResponseEntity<String>("Upload Only png or jpeg image" , HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    }

    @GetMapping("/photo/{accountId}")
    ResponseEntity<byte[]> readPhoto(@PathVariable Long accountId){
        HttpHeaders headers = new HttpHeaders();
        ProfilePhoto photo = twitterService.readUserProfilePhoto(accountId);
        if(photo != null){
            headers.setContentType(photo.getMediaType());
            return new ResponseEntity<byte[]>(photo.getPhoto(),headers,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
