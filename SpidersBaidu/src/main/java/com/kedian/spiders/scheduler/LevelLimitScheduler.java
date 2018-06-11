package com.kedian.spiders.scheduler;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

/**
 * @author happy
 * @version 0
 * @Package com.kedian.spiders.scheduler
 * @date 2018/6/11 17:11
 * @Description:
 */
public class LevelLimitScheduler extends PriorityScheduler {
    private int levelLimit = 2;

    public LevelLimitScheduler(int levelLimit) {
        this.levelLimit = levelLimit;
    }

    @Override
    public synchronized void push(Request request, Task task) {
        if (((Integer) request.getExtra("_level")) <= levelLimit) {
            super.push(request, task);
        }
    }

}
