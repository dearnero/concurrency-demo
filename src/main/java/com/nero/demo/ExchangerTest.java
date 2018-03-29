package com.nero.demo;

import java.util.UUID;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger
 * <p>
 * date : 2017/12/23
 * time : 20:37
 * </p>
 *
 * @author Nero
 */
public class ExchangerTest {

    private static final Exchanger<String> exgr = new Exchanger<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

//        threadPool.execute(() -> {
//            try {
//                String A = "银行流水A";    // A录入银行流水数据
//                String B = exgr.exchange(A);
//                System.out.println("线程1打印：A和B数据是否一致：" + A.equals(B) + "，A录入的是："+ A + "，B录入是：" + B);
//            } catch (InterruptedException e) {
//            }
//        });
//
//        threadPool.execute(() -> {
//            try {
//                String B = "银行流水B";	// B录入银行流水数据
//                Thread.sleep(3000); // 线程1会堵塞在exchange方法
//                String A = exgr.exchange(B);
//                System.out.println("线程2打印：A和B数据是否一致：" + A.equals(B) + "，A录入的是："+ A + "，B录入是：" + B);
//            } catch (InterruptedException e) {
//            }
//        });
//        threadPool.shutdown();


        System.out.println(UUID.randomUUID().toString());
    }
}
