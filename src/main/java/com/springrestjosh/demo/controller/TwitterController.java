package com.springrestjosh.demo.controller;

import com.springrestjosh.demo.domain.TwitterAccount;
import com.springrestjosh.demo.domain.User;
import com.springrestjosh.demo.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/twitter")
public class TwitterController {
    TwitterService twitterService;

    @Autowired
    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping("/users")
    ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(twitterService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    User getUser(@PathVariable Long userId){
        return twitterService.findUserById(userId);
    }

    @GetMapping("/user/{userId}/twitteraccounts")
    List<TwitterAccount> getAccounts(@PathVariable Long userId){
        return twitterService.getAllTwitterAccounts(userId);
    }

    @GetMapping("/account/{accountName}")
    ResponseEntity<TwitterAccount> getAccountByAccountname(@PathVariable String accountName){
        TwitterAccount account = twitterService.getTwitterAccountByAccountName(accountName);
        if(account != null)
           return new ResponseEntity<TwitterAccount>(account,HttpStatus.OK);
        return new ResponseEntity<TwitterAccount>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/username/{userName}")
    ResponseEntity<User> getUserByUserName(@PathVariable String userName){
        User user = twitterService.findUserByUserName(userName);
        if(user != null)
            return  new ResponseEntity<User>(user,HttpStatus.OK);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/followers/{accountId}")
    List<User> getAllFollowers(@PathVariable Long accountId){
        return twitterService.getAllFollowers(accountId);
    }

    @PostMapping("/follow/{userId}/{accountId}")
    ResponseEntity startFollowing(@PathVariable Long userId, @PathVariable Long accountId){
        twitterService.startFollowing(userId,accountId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/stopfollow/{userId}/{accountId}")
    ResponseEntity stopFollowing(@PathVariable Long userId, @PathVariable Long accountId){
        twitterService.stopFollowing(userId,accountId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/create")
    User createNewUser(@RequestBody User user){
        return twitterService.createNewUser(user);
    }

    @PostMapping("/createAccount")
    TwitterAccount createAccount(@RequestParam String accountName, @RequestParam Long userId){
        return twitterService.createNewAccount(accountName,userId);
    }
}
