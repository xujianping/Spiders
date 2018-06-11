package com.kedian.spiders;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author happy
 * @version 0
 * @Package com.xujp.spiders
 * @date 2018/5/22 22:20
 * @Description:用于记录spider,用于后期的展示和控制
 */
@Component("SpiderPools")
@Scope("singleton")
public class SpiderPools {
    protected Map<String,Spider> spiderPool = new HashMap<>();

    public Map<String, Spider> getSpiderPool() {
        return spiderPool;
    }

    public void setSpiderPool(Map<String, Spider> spiderPool) {
        this.spiderPool = spiderPool;
    }

    public Spider getByUUID(String UUID){
       return spiderPool.get(UUID);
    }

    public Boolean stopByUUID(String UUID){
        spiderPool.get(UUID).stop();
        return true;
    }
    public Boolean removeByUUID(String UUID){
        spiderPool.remove(UUID);
        return true;
    }

    public void addSpider(String UUID,Spider spider){
        spiderPool.put(UUID,spider);

    }

}
