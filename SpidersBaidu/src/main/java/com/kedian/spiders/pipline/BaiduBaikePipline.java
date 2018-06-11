package com.kedian.spiders.pipline;

import com.kedian.domain.DownloadInfo;
import com.kedian.service.DownloadInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

import java.io.*;
import java.util.Map;

/**
 * @author happy
 * @version 0
 * @Package com.kedian.spiders.pipline
 * @date 2018/6/11 11:03
 * @Description:
 */
@Component("BaiduPagePipline")
public class BaiduBaikePipline extends FilePipeline {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DownloadInfoService downloadInfoService;

    /**8
     *
     * @param resultItems
     * @param task
     */
    public void process(ResultItems resultItems, Task task) {
        Map pageMap;
        pageMap = resultItems.get("pageMap");

        String basePath = (String) pageMap.get("filePath");
        String baseUrl = (String) pageMap.get("baseUrl");
        String fileName = (String) pageMap.get("title");
        File file = createAndSaveFile(basePath, fileName);

        try {
            PrintWriter e = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            e.println(pageMap.get("pages"));
            e.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DownloadInfo downloadInfo = new DownloadInfo(baseUrl,fileName,file.getAbsolutePath());

        downloadInfoService.saveDownloadInfo(downloadInfo);
        logger.info("页面"+baseUrl+"保存成功");

    }

    /***
     *
     * @param basePath
     * @param fileName
     * @return
     */
    public File createAndSaveFile(String basePath, String fileName) {
        File dir = new File(basePath + "/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName + ".html");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
