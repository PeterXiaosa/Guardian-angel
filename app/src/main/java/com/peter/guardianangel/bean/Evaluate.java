package com.peter.guardianangel.bean;


import java.sql.Date;

/**
 *  评论类
 */
public class Evaluate {
    //    evaluate_account`, `be_evaluated_account`, `content`, `star_rank`, `evaluate_date`, `evaluate_start`, `evaluate_end
    private String evaluateAccount;
    private String beEvaluatedAccount;
    private String content;
    private int starRank;    // 5星好评为10. 3.5星为7
    private String evaluateDate;
    private String evaluateStart;
    private String evaluateEnd;

    public String getEvaluateAccount() {
        return evaluateAccount;
    }

    public void setEvaluateAccount(String evaluateAccount) {
        this.evaluateAccount = evaluateAccount;
    }

    public String getBeEvaluatedAccount() {
        return beEvaluatedAccount;
    }

    public void setBeEvaluatedAccount(String beEvaluatedAccount) {
        this.beEvaluatedAccount = beEvaluatedAccount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStarRank() {
        return starRank;
    }

    public void setStarRank(int starRank) {
        this.starRank = starRank;
    }

    public String getEvaluateDate() {
        return evaluateDate;
    }

    public void setEvaluateDate(String evaluateDate) {
        this.evaluateDate = evaluateDate;
    }

    public String getEvaluateStart() {
        return evaluateStart;
    }

    public void setEvaluateStart(String evaluateStart) {
        this.evaluateStart = evaluateStart;
    }

    public String getEvaluateEnd() {
        return evaluateEnd;
    }

    public void setEvaluateEnd(String evaluateEnd) {
        this.evaluateEnd = evaluateEnd;
    }
}