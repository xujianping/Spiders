package com.kedian.service;

import com.kedian.domain.DownloadInfo;
import us.codecraft.webmagic.Spider;

/**
 * @author happy
 * @version 0
 * @Package com.kedian.service
 * @date 2018/6/11 11:48
 * @Description:
 */
public interface DownloadInfoService {
    /**
     * 保存并返回数据
     * @param downloadInfo
     * @return
     */
    public DownloadInfo saveDownloadInfo(DownloadInfo downloadInfo);

    /***
     * 创建下载任务
     * @param baseUrl
     * @param savePath
     * @return
     */
    public Spider createSpider(String baseUrl,String savePath);

    /***
     * 关键字查询
     * @param keywords
     * @param savePath
     * @return
     */
    public Spider searchPages(String keywords,String savePath);
}
