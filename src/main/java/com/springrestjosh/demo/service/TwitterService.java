package com.springrestjosh.demo.service;

import com.springrestjosh.demo.domain.ProfilePhoto;
import com.springrestjosh.demo.domain.TwitterAccount;
import com.springrestjosh.demo.domain.User;
import org.springframework.http.MediaType;

import java.awt.*;
import java.util.Date;
import java.util.List;

public interface TwitterService {
    List<User> getAllUsers();
    List<TwitterAccount> getAllTwitterAccounts(Long userId);
    TwitterAccount getTwitterAccountByAccountName(String accountName);
    User findUserById(Long userId);
    User findUserByUserName(String userName);
    //List<TwitterAccount> getAllFollowedAccount(Long userId);
    List<User> getAllFollowers(Long accountId);
    void startFollowing(Long userId , Long followingAccountId);
    void stopFollowing(Long userId, Long followingAccountId);
    User createNewUser(User user);
    TwitterAccount createNewAccount(String accountName , Long userId);
    void writeUserProfilePhoto(Long accountId, MediaType mediaType, byte[] profilePhotoBytes);
    ProfilePhoto readUserProfilePhoto(Long accountId);

}
