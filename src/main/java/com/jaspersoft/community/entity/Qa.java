package com.jaspersoft.community.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "qa")
public class Qa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "contributor_name")
    private String contributorName;
    
    @Column(name = "questions_assigned")
    private Integer questionsAssigned;
    
    @Column(name = "questions_pending_on_community_user")
    private Integer questionsPendingOnCommunityUser;
    
    @Column(name = "questions_pending_contribution")
    private Integer questionsPendingContribution; // ✅ New field
    
    @Column(name = "questions_answered")
    private Integer questionsAnswered;
    
    @Column(name = "month", length = 3)
    private String month;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContributorName() {
		return contributorName;
	}

	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}

	public Integer getQuestionsAssigned() {
		return questionsAssigned;
	}

	public void setQuestionsAssigned(Integer questionsAssigned) {
		this.questionsAssigned = questionsAssigned;
	}

	public Integer getQuestionsPendingOnCommunityUser() {
		return questionsPendingOnCommunityUser;
	}

	public void setQuestionsPendingOnCommunityUser(Integer questionsPendingOnCommunityUser) {
		this.questionsPendingOnCommunityUser = questionsPendingOnCommunityUser;
	}

	public Integer getQuestionsPendingContribution() {
		return questionsPendingContribution;
	}

	public void setQuestionsPendingContribution(Integer questionsPendingContribution) {
		this.questionsPendingContribution = questionsPendingContribution;
	}

	public Integer getQuestionsAnswered() {
		return questionsAnswered;
	}

	public void setQuestionsAnswered(Integer questionsAnswered) {
		this.questionsAnswered = questionsAnswered;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
