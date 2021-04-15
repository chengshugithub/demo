package com.example.demo.util;

import org.redisson.api.RBucket;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;
/**
 * 2 * @Author: ZhangShuai
 * 3 * @Date: 2020/6/17 10:11
 * 4  创建redis中常用的方法
 */
@Component
public class RedissonUtil {

    @Autowired
    private RedissonClient redissonClient;

    //获取一个 key
    public String getString(String key) {
        //RExpirable RExpirable.remainTimeToLive(), RExpirable.remainTimeToLiveAsync(), RExpirableReactive.remainTimeToLive();
        RBucket<Object> result = this.redissonClient.getBucket(key);
        if (StringUtils.isEmpty(result.get())) {
            return null;
        }
        return result.get().toString();
    }

    //获取一个key的过期时间
    public long getTime(String key) {
        long result = redissonClient.getPermitExpirableSemaphore(key).remainTimeToLive();
        ;
        return result;
    }


    //设置一个 key vlaue的值
    public void setString(String key, Object value) {
        RBucket<Object> result = this.redissonClient.getBucket(key);
        if (!result.isExists()) {
            result.set(value);
        }
    }


    //设置key的过期时间 time为秒单位
    public void setStringTime(String key, Object value, Long time) {
        RBucket<Object> result = this.redissonClient.getBucket(key);
        if (!result.isExists()) {
            result.set(value, time, TimeUnit.SECONDS);
        }
    }

    //判断这个key是否存在
    public boolean hasString(String key) {
        RBucket<Object> result = this.redissonClient.getBucket(key);
        if (result.isExists()) {
            return true;
        } else {
            return false;
        }
    }

    //删除一个key
    public boolean delString(String key) {
        RBucket<Object> result = this.redissonClient.getBucket(key);
        if (result.delete()) {
            return true;
        } else {
            return false;
        }
    }

    public long incr(String key, long delta) {
        return this.redissonClient.getAtomicLong(key).addAndGet(delta);
    }
    // -----------------------------------------------------------------------

    public void lock() {
        RCountDownLatch countDown = redissonClient.getCountDownLatch("aa");
        countDown.trySetCount(1);
        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RCountDownLatch latch = redissonClient.getCountDownLatch("countDownLatchName");
        latch.countDown();
        RReadWriteLock rwlock = redissonClient.getReadWriteLock("lockName");
        rwlock.readLock().lock();
        rwlock.writeLock().lock();
        rwlock.readLock().lock(10, TimeUnit.SECONDS);
        rwlock.writeLock().lock(10, TimeUnit.SECONDS);
        try {
            boolean res = rwlock.readLock().tryLock(100, 10, TimeUnit.SECONDS);
            boolean res1 = rwlock.writeLock().tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

