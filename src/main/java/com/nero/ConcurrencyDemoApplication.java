package com.nero;

import com.nero.demo.lock.ThreadLockTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConcurrencyDemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ConcurrencyDemoApplication.class, args);
	}

	@Autowired
	private ThreadLockTest threadLockTest;

	@Override
	public void run(String... strings) throws Exception {

//		// 执行单线程更新
		threadLockTest.toStartReadAndWriteData();
////		// 执行多线程lock更新
		threadLockTest.toStartReadAndWriteDataWithLock();
		// 执行多线程分布式lock更新
		threadLockTest.toStartReadAndWriteDataWithDistributedLock();
	}
}
