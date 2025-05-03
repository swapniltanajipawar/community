package com.jaspersoft.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "top_contributors_qa")
public class TopContributorsQa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String contributorName;
    private String teamName;
    private String month;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getContributorName() { return contributorName; }
    public void setContributorName(String contributorName) { this.contributorName = contributorName; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
}
