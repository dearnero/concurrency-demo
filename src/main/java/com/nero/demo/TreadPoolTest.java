package com.nero.demo;

import java.util.concurrent.*;

/**
 * 线程池测试
 * <p>
 * date : 2017/12/14
 * time : 19:30
 * </p>
 *
 * @author Nero
 */
public class TreadPoolTest {

    private void test(){
        ExecutorService executorService = Executors.newCachedThreadPool();

        Callable<User> callable = () -> {
            User user = new User();
            user.setName("Bob");
            return user;
        };
        Future<User> future = executorService.submit(callable);
        try {
            User user = future.get();
            System.out.println(user.name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void test1(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Callable<User> callable = () -> {

            Thread.sleep(5000);

            User user = new User();
            user.setName("Smith");
            return user;
        };
        FutureTask<User> futureTask = new FutureTask<>(callable);
        executorService.submit(futureTask);
        try {

            futureTask.cancel(true);

            User user = futureTask.get();
            System.out.println(user.name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new TreadPoolTest().test();

        new TreadPoolTest().test1();
    }

    class User{

        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
