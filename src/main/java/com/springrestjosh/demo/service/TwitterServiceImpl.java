package com.springrestjosh.demo.service;

import com.springrestjosh.demo.domain.TwitterAccount;
import com.springrestjosh.demo.domain.User;
import com.springrestjosh.demo.repository.TwitterRepository;
import com.springrestjosh.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TwitterServiceImpl implements TwitterService {

    @Autowired
    TwitterRepository twitterRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<TwitterAccount> getAllTwitterAccounts(Long userId) {
        return twitterRepository.findByUser(userRepository.findOne(userId));
    }

    @Override
    public TwitterAccount getTwitterAccountByAccountName(String accountName) {
        Optional<TwitterAccount> account = twitterRepository.findByAccountName(accountName);
        if(account.isPresent()) {
            //System.out.println(account.get());
            return account.get();
        }
        return null;
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User findUserByUserName(String userName) {
        Optional<User> account = userRepository.findByUserName(userName);
        if(account.isPresent()) {
            //System.out.println(account.get());
            return account.get();
        }
        return null;
    }

    @Override
    public List<User> getAllFollowers(Long accountId) {
        TwitterAccount account = twitterRepository.findOne(accountId);
        Set<User> followers =  account.getFollowers();
        return new ArrayList<>(followers);
    }

    @Override
    public void startFollowing(Long userId, Long followingAccountId) {
        TwitterAccount account = twitterRepository.findOne(followingAccountId);
        User user =  userRepository.findOne(userId);
        user.setFollowedAccount(account);
        userRepository.save(user);
    }

    @Override
    public void stopFollowing(Long userId, Long followingAccountId) {
        TwitterAccount account = twitterRepository.findOne(followingAccountId);
        User user = userRepository.findOne(userId);
        user.removeFollowedAccount(account);
        userRepository.save(user);

    }

    @Override
    public User createNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public TwitterAccount createNewAccount(String accountName, Long userId) {
        TwitterAccount account = new TwitterAccount(accountName,false);
        User user = userRepository.findOne(userId);
        user.setTwitterAccount(account);
        userRepository.save(user);
        return twitterRepository.findByAccountName(accountName).get();
    }
}
