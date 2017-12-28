package com.springrestjosh.demo.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Temporal(TemporalType.DATE)
    private Date signUpDate;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private Set<TwitterAccount> twitterAccounts = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "followers",fetch = FetchType.LAZY)
    private Set<TwitterAccount> followedAccounts = new HashSet<>();

    public User() {
    }


    public User(String firstName, String lastName, String userName, Date dateOfBirth, Date signUpDate, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.signUpDate = signUpDate;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<TwitterAccount> getTwitterAccounts() {
        return twitterAccounts;
    }

    public void setTwitterAccount(TwitterAccount twitterAccount) {
        this.twitterAccounts.add(twitterAccount);
        twitterAccount.setUser(this);
    }

    public Set<TwitterAccount> getFollowedAccounts() {
        return followedAccounts;
    }

    public void setFollowedAccount(TwitterAccount followedAccount) {
        this.followedAccounts.add(followedAccount);
        followedAccount.getFollowers().add(this);
    }

    public void removeFollowedAccount (TwitterAccount followedAccount){
        if(!followedAccounts.contains(followedAccount)){
            return;
        }
        followedAccounts.remove(followedAccount);
        followedAccount.removeFollower(this);
    }

   public void removeAccount(TwitterAccount account){
       if(!twitterAccounts.contains(account))
           return;
       twitterAccounts.remove(account);
       account.setUser(null);
   }

   public void removeUser(){
       TwitterAccount[] accounts = new TwitterAccount[twitterAccounts.size()];
       TwitterAccount[] accounts1 = followedAccounts.toArray(accounts);
       for(TwitterAccount account : accounts1){
           System.out.println(account.getAccountName());
           removeFollowedAccount(account);
       }
   }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", signUpDate=" + signUpDate +
                ", twitterAccounts=" + twitterAccounts +
                ", followedAccounts=" + followedAccounts +
                '}';
    }
}
