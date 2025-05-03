package com.jaspersoft.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "qa")
public class Qa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String contributorName;
    private Integer questionsAssigned;
    private Integer pendingCommunityUser;
    private Integer answered;
    private Integer month;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getContributorName() { return contributorName; }
    public void setContributorName(String contributorName) { this.contributorName = contributorName; }

    public Integer getQuestionsAssigned() { return questionsAssigned; }
    public void setQuestionsAssigned(Integer questionsAssigned) { this.questionsAssigned = questionsAssigned; }

    public Integer getPendingCommunityUser() { return pendingCommunityUser; }
    public void setPendingCommunityUser(Integer pendingCommunityUser) { this.pendingCommunityUser = pendingCommunityUser; }

    public Integer getAnswered() { return answered; }
    public void setAnswered(Integer answered) { this.answered = answered; }

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }
}
