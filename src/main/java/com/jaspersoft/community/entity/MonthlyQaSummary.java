package com.jaspersoft.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "monthly_qa_summary")
public class MonthlyQaSummary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String month;
    private Integer totalQuestionsAssigned;
    private Integer totalQuestionsAnswered;
    private Integer totalQuestionsEngaged;
    private Integer totalQuestionsPending;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Integer getTotalQuestionsAssigned() { return totalQuestionsAssigned; }
    public void setTotalQuestionsAssigned(Integer totalQuestionsAssigned) { this.totalQuestionsAssigned = totalQuestionsAssigned; }

    public Integer getTotalQuestionsAnswered() { return totalQuestionsAnswered; }
    public void setTotalQuestionsAnswered(Integer totalQuestionsAnswered) { this.totalQuestionsAnswered = totalQuestionsAnswered; }

    public Integer getTotalQuestionsEngaged() { return totalQuestionsEngaged; }
    public void setTotalQuestionsEngaged(Integer totalQuestionsEngaged) { this.totalQuestionsEngaged = totalQuestionsEngaged; }

    public Integer getTotalQuestionsPending() { return totalQuestionsPending; }
    public void setTotalQuestionsPending(Integer totalQuestionsPending) { this.totalQuestionsPending = totalQuestionsPending; }
}
