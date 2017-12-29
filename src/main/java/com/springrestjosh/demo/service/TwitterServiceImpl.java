package com.springrestjosh.demo.service;

import com.springrestjosh.demo.configuration.TwitterConfiguration;
import com.springrestjosh.demo.domain.ProfilePhoto;
import com.springrestjosh.demo.domain.TwitterAccount;
import com.springrestjosh.demo.domain.User;
import com.springrestjosh.demo.repository.TwitterRepository;
import com.springrestjosh.demo.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
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

    @Override
    public void writeUserProfilePhoto(Long accountId, MediaType mediaType, byte[] profilePhotoBytes) {
        TwitterAccount account = twitterRepository.findOne(accountId);
        account.setProfilePhotoUploaded(true);
        account.setProfilePhotoMediaType(mediaType.toString());
        twitterRepository.save(account);

        ByteArrayInputStream byteArrayInputStream = null;
        FileOutputStream fileOutputStream = null;
        try{
            byteArrayInputStream = new ByteArrayInputStream(profilePhotoBytes);
            fileOutputStream = new FileOutputStream(fileForPhoto(accountId));
            IOUtils.copy(byteArrayInputStream,fileOutputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(byteArrayInputStream);
            IOUtils.closeQuietly(fileOutputStream);
        }

    }

    @Override
    public ProfilePhoto readUserProfilePhoto(Long accountId) {
        if(!twitterRepository.findOne(accountId).isProfilePhotoUploaded())
            return null;
        FileInputStream inputStream = null;
        ProfilePhoto photo = new ProfilePhoto();
        try{
            inputStream = new FileInputStream(fileForPhoto(accountId));
            photo.setAccountId(accountId);
            photo.setMediaType(MediaType.parseMediaType(twitterRepository.findOne(accountId).getProfilePhotoMediaType()));
            photo.setPhoto(FileCopyUtils.copyToByteArray(inputStream));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
        return photo;
    }

    public File fileForPhoto(Long accountId){
        return new File(TwitterConfiguration.TWITTER_PROFILE_UPLOAD_DIRECTORY,Long.toString(accountId));
    }
}
