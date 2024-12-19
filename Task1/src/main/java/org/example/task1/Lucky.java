package org.example.task1;

import java.util.concurrent.atomic.AtomicInteger;

public class Lucky {
    static AtomicInteger count = new AtomicInteger(0);


    static class LuckyThread extends Thread {
        private final int start;
        private final int end;


        LuckyThread(int start, int end){
            this.start = start;
            this.end = end;
        }


        @Override
        public void run() {
            for (int x = start; x < end; x++) {
                x++;
                if ((x % 10) + (x / 10) % 10 + (x / 100) % 10 == (x / 1000)
                        % 10 + (x / 10000) % 10 + (x / 100000) % 10) {
                    System.out.printf("%s %d\n",Thread.currentThread().getName(),x);
                    count.incrementAndGet();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int range = 999_999 / 3;
        Thread t1 = new LuckyThread(0, range);
        Thread t2 = new LuckyThread(range, 2 * range);
        Thread t3 = new LuckyThread(2 * range, 3 * range + 1);
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + count);
    }
}