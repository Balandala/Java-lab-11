package org.example.task2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PoolExample {

    public static void main(String[] args) throws InterruptedException {

        // создаем пул для выполнения наших задач
        //   максимальное количество созданных задач - 3
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                // не изменяйте эти параметры
                3, 3, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3));

        // сколько задач выполнилось
        AtomicInteger count = new AtomicInteger(0);

        // сколько задач выполняется
        AtomicInteger inProgress = new AtomicInteger(0);

        int taskCount = 30;

        // отправляем задачи на выполнение
        for (int i = 0; i < taskCount; i++) {
            final int number = i;
            Thread.sleep(10);

            while (true) {
                if (inProgress.intValue() < 3 || number == taskCount - 1)
                    break;
                Thread.sleep(10);
            }

            System.out.println("creating #" + number);
            executor.submit(() -> {
                int working = inProgress.intValue();
                System.out.println("start #" + number + ", in progress: " + working);
                try {
                    // тут какая-то полезная работа
                    Thread.sleep(Math.round(1000 + Math.random() * 2000));
                } catch (InterruptedException e) {
                    // ignore
                }
                working = inProgress.decrementAndGet();
                System.out.println("end #" + number + ", in progress: " + working + ", done tasks: " + count.incrementAndGet());

                return null;
            });
            inProgress.incrementAndGet();
        }
        executor.shutdown();
    }
}
