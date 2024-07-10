package org.example.test4;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;
import sun.security.timestamp.TSRequest;

/**
 * @ClassNAME TestBiased
 * @Description 多个线程竞争使用我们的偏向锁，导致偏向锁的升级
 * @Author zhaoweishan
 * @Date 2024/7/8 16:53
 * @Version 1.0
 */


@Slf4j
public class TestBiased {
    public static void main(String[] args) {
        dog d = new dog();
        new Thread(() -> {
            log.info(ClassLayout.parseInstance(d).toPrintable());
            synchronized (d) {
                log.info(ClassLayout.parseInstance(d).toPrintable());
            }
            log.info(ClassLayout.parseInstance(d).toPrintable());
            synchronized (TestBiased.class) {
                TestBiased.class.notify();
            }
        }, "t1").start();
        new Thread(() -> {
            synchronized (TestBiased.class) {
                try {
                    TestBiased.class.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };
            log.info(ClassLayout.parseInstance(d).toPrintable());
            synchronized (d) {
                log.info(ClassLayout.parseInstance(d).toPrintable());
            }
            log.info(ClassLayout.parseInstance(d).toPrintable());
        }, "t2").start();
    }
}

class  dog{

}
