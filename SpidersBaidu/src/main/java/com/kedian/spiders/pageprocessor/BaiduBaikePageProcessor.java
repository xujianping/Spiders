package com.kedian.spiders.pageprocessor;

import com.kedian.utils.Base64Img;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.kedian.utils.ReadFileToString.readToString;

/**
 * @author happy
 * @version 0
 * @Package com.kedian.spiders.pageprocessor
 * @date 2018/6/11 10:56
 * @Description:
 */
@Component("BaiduBaikePageProcessor")
public class BaiduBaikePageProcessor implements PageProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //对站点本身的一些配置信息，例如编码、HTTP头、超时时间、重试策略等、代理等，都可以通过设置Site对象来进行配置。
    private Site site = Site.me()
            .setDisableCookieManagement(true)
            .setCharset("UTF-8")
            .setTimeOut(30000)
            .setRetryTimes(3)
            .setSleepTime(new Random().nextInt(30) * 100);

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        Map myExtras = page.getRequest().getExtras();


        if ((int) page.getRequest().getExtra("_level") == 1) {
            page.getHtml().getDocument().getElementsByTag("a").forEach(lk -> {
                if (lk.attr("href").contains("item") && !lk.attr("href").contains("百度百科")) {
                    myExtras.remove("_level");
                    myExtras.put("_level", 2);
                    Request request = new Request("https://baike.baidu.com" + lk.attr("href"));
                    request.setExtras(myExtras);
                    page.addTargetRequest(request);
                }
            });

        }
        logger.info("获取到的页面地址:" + page.getUrl());

        page.getHtml().getDocument().getElementsByTag("script").remove();
        page.getHtml().getDocument().getElementsByTag("link").remove();
        page.getHtml().getDocument().getElementsByClass("header-wrapper").remove();
        page.getHtml().getDocument().getElementsByClass("navbar-wrapper").remove();
        page.getHtml().getDocument().getElementsByClass("wgt-footer-main").remove();
        page.getHtml().getDocument().getElementsByClass("lemmaWgt-searchHeader").remove();
        page.getHtml().getDocument().getElementById("side-share").remove();
        page.getHtml().getDocument().getElementById("layer").remove();
        page.getHtml().getDocument().getElementsByClass("tashuo-bottom").remove();
        page.getHtml().getDocument().getElementsByClass("edit-icon").remove();
        page.getHtml().getDocument().getElementsByClass("before-content").remove();
        page.getHtml().getDocument().getElementsByClass("after-content").remove();
        page.getHtml().getDocument().getElementsByClass("side-content").remove();
        page.getHtml().getDocument().getElementsByClass("top-tool").remove();
        page.getHtml().getDocument().getElementsByClass("lock-lemma").remove();
        page.getHtml().getDocument().getElementsByClass("edit-lemma").remove();

        page.getHtml().getDocument().getElementsByTag("a").forEach(lk -> {
            if (lk.attr("href").contains("item") || lk.attr("href").contains("pic") || lk.attr("href").contains("redirect")) {
                lk.after(lk.html());
                lk.remove();

            }
        });
        //图片处理
        page.getHtml().getDocument().select("img").forEach(img -> {
            String base64Str = "";
            if (img.attr("data-src").length() > 0) {
                base64Str = Base64Img.GetImageStrFromUrl(img.attr("data-src"));
            } else {
                base64Str = Base64Img.GetImageStrFromUrl(img.attr("src"));
            }
            img.attr("src", "data:image/png;base64," + base64Str);
            img.attr("data-src", "");
        });


        //添加样式文件
        page.getHtml().getDocument().getElementsByTag("head").append((String) page.getRequest().getExtras().get("cssStr"));

        //将结果给pipline 进行后续的存储处理
        Map pageMap = new HashMap();
        pageMap.put("pages", page.getHtml());
        pageMap.put("baseUrl", page.getUrl().toString());
        pageMap.put("title", page.getHtml().xpath("//dd[@class='lemmaWgt-lemmaTitle-title']/h1/text()").toString());
        pageMap.put("filePath", page.getRequest().getExtras().get("savePath"));

        page.putField("pageMap", pageMap);

    }
}
