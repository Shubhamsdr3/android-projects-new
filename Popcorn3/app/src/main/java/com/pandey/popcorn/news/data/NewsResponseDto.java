package com.pandey.popcorn.news.data;

import java.io.Serializable;
import java.util.List;

public class NewsResponseDto implements Serializable {

    private String status;

    private int totalResult;

    private List<Article> articleList;

    public NewsResponseDto(String status, int totalResult, List<Article> articleList) {
        this.status = status;
        this.totalResult = totalResult;
        this.articleList = articleList;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
