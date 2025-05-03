package com.jaspersoft.community.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wiki")
public class Wiki {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "contributor_name", length = 100)
    private String contributorName;

    @Column(name = "articles_assigned")
    private int articlesAssigned;

    @Column(name = "articles_updated")
    private int articlesUpdated;

    @Column(name = "month", length = 3)
    private String month;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getContributorName() { return contributorName; }
    public void setContributorName(String contributorName) { this.contributorName = contributorName; }

    public int getArticlesAssigned() { return articlesAssigned; }
    public void setArticlesAssigned(int articlesAssigned) { this.articlesAssigned = articlesAssigned; }

    public int getArticlesUpdated() { return articlesUpdated; }
    public void setArticlesUpdated(int articlesUpdated) { this.articlesUpdated = articlesUpdated; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
}
