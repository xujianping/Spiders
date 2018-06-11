package com.kedian.service.serviceImpl;

import com.kedian.domain.DownloadInfo;
import com.kedian.repository.DownloadInfoRepository;
import com.kedian.service.DownloadInfoService;
import com.kedian.spiders.SpiderPools;
import com.kedian.spiders.pageprocessor.BaiduBaikePageProcessor;
import com.kedian.spiders.pageprocessor.BaiduBaikeSearchPageProcessor;
import com.kedian.spiders.pipline.BaiduBaikePipline;
import com.kedian.spiders.scheduler.LevelLimitScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.util.HashMap;
import java.util.Map;

import static com.kedian.utils.ReadFileToString.readToString;

/**
 * @author happy
 * @version 0
 * @Package com.kedian.service.serviceImpl
 * @date 2018/6/11 11:53
 * @Description:
 */

@Service
public class DownloadInfoServiceImpl implements DownloadInfoService {

    @Autowired
    DownloadInfoRepository downloadInfoRepository;

    @Autowired
    BaiduBaikePageProcessor baikePageProcessor;

    @Autowired
    BaiduBaikeSearchPageProcessor searchPageProcessor;

    @Autowired
    BaiduBaikePipline baikePipline;

    @Autowired
    SpiderPools  spiderPools;
    @Autowired
    private Environment env;

    /**
     * 保存并返回数据
     * @param downloadInfo
     * @return
     */
    @Override
    public DownloadInfo saveDownloadInfo(DownloadInfo downloadInfo) {
        DownloadInfo downloadInfo1 = downloadInfoRepository.saveAndFlush(downloadInfo);
        return downloadInfo1;
    }

    /***
     * 创建下载任务
     * @param baseUrl
     * @param savePath
     * @return
     */
    @Override
    public Spider createSpider(String baseUrl, String savePath) {

        Spider spider = null;

        Request request = new Request(baseUrl);
        Map myExtras = new HashMap();
        myExtras.put("savePath", savePath);
        myExtras.put("cssStr", readToString(env.getProperty("bdcss.url")));
        myExtras.put("_level", 1);
        request.setExtras(myExtras);
        if (spiderPools.getByUUID(env.getProperty("spider.uuid")) == null){

             spider = Spider.create(baikePageProcessor);
             spider.addPipeline(baikePipline)
                    .setUUID(env.getProperty("spider.uuid"))
                    .addRequest(request)
                     .scheduler(new LevelLimitScheduler(2))
//                    .setScheduler(new RedisScheduler(env.getProperty("spring.redis.host")))
                    .thread(20);
            spiderPools.addSpider(env.getProperty("spider.uuid"),spider);
        }else {
            spider =   spiderPools.getByUUID(env.getProperty("spider.uuid"));
            spider.addRequest(request);
        }
        return spider;

    }

    @Override
    public Spider searchPages(String keywords, String savePath) {
        String baseUrl = "https://baike.baidu.com/search?word="+keywords;
        Spider spider = null;
        Request request = new Request(baseUrl);
        Map myExtras = new HashMap();
        myExtras.put("savePath", savePath);
        myExtras.put("cssStr", readToString(env.getProperty("bdcss.url")));
        request.setExtras(myExtras);
        if (spiderPools.getByUUID(env.getProperty("spider.uuid")) == null){

            spider = Spider.create(searchPageProcessor);
            spider.addPipeline(baikePipline)
                    .setUUID(env.getProperty("spider.uuid"))
                    .addRequest(request)
//                    .setScheduler(new RedisScheduler(env.getProperty("spring.redis.host")))
                    .thread(10);
            spiderPools.addSpider(env.getProperty("spider.uuid"),spider);
        }else {
            spider =   spiderPools.getByUUID(env.getProperty("spider.uuid"));
            spider.addRequest(request);
        }
        return spider;
    }


}
