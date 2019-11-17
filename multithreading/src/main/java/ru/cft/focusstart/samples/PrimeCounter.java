package ru.cft.focusstart.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PrimeCounter {

    private static final int START_NUMBER = 0;
    private static final int END_NUMBER = 300_000;
    private static final int THREAD_COUNT = 16;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        count();
        countUsingThreads();
        countUsingThreadPool();
    }

    private static void count() {
        long startTime = System.currentTimeMillis();
        System.out.println(PrimeCounterUtils.countPrimes(START_NUMBER, END_NUMBER));
        System.out.println("Computed in " + (System.currentTimeMillis() - startTime) / 1000 + "s");
    }

    private static void countUsingThreads() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        List<PrimeCounterTask> primeCounters = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * (END_NUMBER - START_NUMBER) / THREAD_COUNT;
            int end = (i + 1) * (END_NUMBER - START_NUMBER) / THREAD_COUNT;
            PrimeCounterTask thread = new PrimeCounterTask(start, end);
            primeCounters.add(thread);
            thread.start();
        }

        int result = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            primeCounters.get(i).join();
            result += primeCounters.get(i).getResult();
        }
        System.out.println(result);
        System.out.println("Computed in " + (System.currentTimeMillis() - startTime) / 1000 + "s");
    }

    private static void countUsingThreadPool() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<Integer>> results = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * (END_NUMBER - START_NUMBER) / THREAD_COUNT;
            int end = (i + 1) * (END_NUMBER - START_NUMBER) / THREAD_COUNT;
            Future<Integer> task = executorService.submit(() -> PrimeCounterUtils.countPrimes(start, end));
            results.add(task);
        }

        int result = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            result += results.get(i).get();
        }
        executorService.shutdown();
        System.out.println(result);
        System.out.println("Computed in " + (System.currentTimeMillis() - startTime) / 1000 + "s");
    }
}

class PrimeCounterUtils {

    static int countPrimes(int from, int to) {
        int result = 0;
        for (int i = from; i < to; i++) {
            if (isPrime(i)) {
                result++;
            }
        }
        return result;
    }

    private static boolean isPrime(long number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}

class PrimeCounterTask extends Thread {
    private final int from;
    private final int to;

    private int result = 0;

    PrimeCounterTask(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        result = PrimeCounterUtils.countPrimes(from, to);
    }

    int getResult() {
        return result;
    }
}
