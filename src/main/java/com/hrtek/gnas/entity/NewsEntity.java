package com.hrtek.gnas.entity;

import java.sql.Date;

/**
 * 新闻实体
 * 470969043@qq.com
 *
 * @author daihui
 * @since 2018-01-04 15:43
 */
public class NewsEntity {

    private Integer newsId;
    private String newsTitle;
    private String newsContent;
    private String newsLevel;
    private String newsAddressName;
    private String newsCategory;
    private Double lgt;
    private Double lat;
    private Date newsDate;
    /**
     * 用来存各地新闻条数（DB无此字段）
     */
    private Integer newsCount;

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsLevel() {
        return newsLevel;
    }

    public void setNewsLevel(String newsLevel) {
        this.newsLevel = newsLevel;
    }

    public String getNewsAddressName() {
        return newsAddressName;
    }

    public void setNewsAddressName(String newsAddressName) {
        this.newsAddressName = newsAddressName;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }

    public Double getLgt() {
        return lgt;
    }

    public void setLgt(Double lgt) {
        this.lgt = lgt;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public Integer getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Integer newsCount) {
        this.newsCount = newsCount;
    }

    @Override
    public String toString() {
        return "NewsEntity{" +
                "newsId=" + newsId +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsContent='" + newsContent + '\'' +
                ", newsLevel='" + newsLevel + '\'' +
                ", newsAddressName='" + newsAddressName + '\'' +
                ", newsCategory='" + newsCategory + '\'' +
                ", lgt=" + lgt +
                ", lat=" + lat +
                ", newsDate=" + newsDate +
                '}';
    }
}
