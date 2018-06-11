package com.kedian.domain;


import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author happy
 * @version 0
 * @Package com.kedian.domain
 * @date 2018/6/11 11:16
 * @Description:用来存储已经下载的页面的地址信息
 */
@Entity
public class DownloadInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private String baseUrl;
    private String title;
    private  String filePath;
    private Timestamp createTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public DownloadInfo() {
    }

    public DownloadInfo(String baseUrl, String title, String filePath) {
        this.baseUrl = baseUrl;
        this.title = title;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "id=" + id +
                ", baseUrl='" + baseUrl + '\'' +
                ", title='" + title + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
