package com.nero.demo.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程锁的测试
 * <p>
 * date : 2018/3/28
 * time : 11:32
 * </p>
 *
 * @author Nero
 */
@Component
public class ThreadLockTest {

    @Autowired
    private ItemCountMapper itemCountMapper;


    private ExecutorService executorService = Executors.newFixedThreadPool(5);


    public void toStartReadAndWriteData() {

        for (int i = 0; i < 100; ++i) {
            executorService.execute(() -> readAndWriteData(1L));
        }
    }

    public void toStartReadAndWriteDataWithLock() {

        for (int i = 0; i < 100; ++i) {
            executorService.execute(() -> readAndWriteDataWithLock(2L));
        }
    }

    public void toStartReadAndWriteDataWithDistributedLock() throws Exception {


//        for (int i = 0; i < 100; ++i) {
//
//            final int       index = i;
//            Callable<Void> task = new Callable<Void>()
//            {
//                @Override
//                public Void call() throws Exception
//                {
//                    try
//                    {
//                        readAndWriteDataWithDistributedLock(3L, index);
////                            }
//                    }
//                    catch ( InterruptedException e )
//                    {
//                        Thread.currentThread().interrupt();
//                    }
//                    catch ( Exception e )
//                    {
//                        e.printStackTrace();
//                        // log or do something
//                    }
//                    return null;
//                }
//            };
//            executorService.submit(task);
//        }
//
//        executorService.shutdown();
//        executorService.awaitTermination(10, TimeUnit.MINUTES);
        for (int i = 0; i < 100; ++i) {
            executorService.execute(() -> {
                try {
                    readAndWriteDataWithDistributedLock(3L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
//            Thread.sleep(1);
//        }
    }


    private void readAndWriteData(Long id) {

        Integer count = itemCountMapper.findCountById(id);
        itemCountMapper.updateCountById(id, ++count);
    }

    private ReentrantLock reentrantLock = new ReentrantLock();

    private void readAndWriteDataWithLock(Long id) {

        reentrantLock.lock();
        try {
            Integer count = itemCountMapper.findCountById(id);
            itemCountMapper.updateCountById(id, ++count);
        } finally {
            reentrantLock.unlock();
        }
    }

    private void readAndWriteDataWithDistributedLock(Long id) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:32791", 60000, 60000, new ExponentialBackoffRetry(1000, 3));
        client.start();
        String path = "/nero/locks";
        InterProcessMutex lock = new InterProcessMutex(client, path);

        if (!lock.acquire(10000, TimeUnit.MILLISECONDS)) {
            throw new IllegalStateException(Thread.currentThread().getName() + " could not acquire the lock");
        }
        try {
            Integer count = itemCountMapper.findCountById(id);
            itemCountMapper.updateCountById(id, ++count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.release();
        }
    }
}
