package com.kedian.controller;

import com.kedian.domain.DownloadInfo;
import com.kedian.domain.ResultModel;
import com.kedian.repository.DownloadInfoRepository;
import com.kedian.service.DownloadInfoService;
import com.kedian.utils.ResultTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author happy
 * @version 0
 * @Package com.kedian.controller
 * @date 2018/6/11 11:25
 * @Description:
 */
@RestController
@RequestMapping("/baikebd")
public class BaiduController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
   private DownloadInfoRepository downloadInfoRepository;

    @Autowired
    DownloadInfoService downloadInfoService;

    @PostMapping("/getPage")
    public String getPage(@RequestParam("baseUrl") String baseUrl,@RequestParam("savePath") String savePath) {
        long st =System.currentTimeMillis();
        Spider spider = downloadInfoService.createSpider(baseUrl,savePath);
        spider.run();
        logger.info(spider.getStatus().toString());
        spider.stop();
        logger.info(spider.getStatus().toString());
        DownloadInfo downloadInfo =   downloadInfoRepository.findByBaseUrl(baseUrl);

        long end = System.currentTimeMillis();
        logger.info("下载完毕,总共耗时"+(end-st)/1000+"秒");
        return downloadInfo.getFilePath();
    }

}
