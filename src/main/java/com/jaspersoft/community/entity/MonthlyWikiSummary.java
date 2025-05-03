package com.jaspersoft.community.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "monthly_wiki_summary")
public class MonthlyWikiSummary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String month;
    private Integer totalArticlesAssigned;
    private Integer totalArticlesUpdated;
    private Integer totalArticlesPending;
    private BigDecimal updatedArticlePercent;
    private BigDecimal pendingArticlePercent;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Integer getTotalArticlesAssigned() { return totalArticlesAssigned; }
    public void setTotalArticlesAssigned(Integer totalArticlesAssigned) { this.totalArticlesAssigned = totalArticlesAssigned; }

    public Integer getTotalArticlesUpdated() { return totalArticlesUpdated; }
    public void setTotalArticlesUpdated(Integer totalArticlesUpdated) { this.totalArticlesUpdated = totalArticlesUpdated; }

    public Integer getTotalArticlesPending() { return totalArticlesPending; }
    public void setTotalArticlesPending(Integer totalArticlesPending) { this.totalArticlesPending = totalArticlesPending; }

    public BigDecimal getUpdatedArticlePercent() { return updatedArticlePercent; }
    public void setUpdatedArticlePercent(BigDecimal updatedArticlePercent) { this.updatedArticlePercent = updatedArticlePercent; }

    public BigDecimal getPendingArticlePercent() { return pendingArticlePercent; }
    public void setPendingArticlePercent(BigDecimal pendingArticlePercent) { this.pendingArticlePercent = pendingArticlePercent; }
}
