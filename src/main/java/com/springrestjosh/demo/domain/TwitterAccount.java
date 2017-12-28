package com.springrestjosh.demo.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TwitterAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String accountName;

    private boolean profilePhotoUploaded;


    @ManyToOne(cascade = CascadeType.ALL)
    private User user;


    @ManyToMany
    private Set<User> followers = new HashSet<>();

    public TwitterAccount() {
    }

    public TwitterAccount(String accountName,boolean profilePhotoUploaded) {
        this.accountName = accountName;
        this.profilePhotoUploaded = profilePhotoUploaded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public boolean isProfilePhotoUploaded() {
        return profilePhotoUploaded;
    }

    public void setProfilePhotoUploaded(boolean profilePhotoUploaded) {
        this.profilePhotoUploaded = profilePhotoUploaded;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if(user==null){
            this.user = user;
            return;
        }
        this.user = user;
        user.getTwitterAccounts().add(this);
    }


    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollower(User follower) {
        this.followers.add(follower);
        follower.getFollowedAccounts().add(this);
    }

    public void removeFollower(User follower){
        if(!followers.contains(follower))
            return;
        followers.remove(follower);
        follower.getFollowedAccounts().remove(this);
    }

    @Override
    public String toString() {
        return "TwitterAccount{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", profilePhotoUploaded=" + profilePhotoUploaded +
                '}';
    }
}
