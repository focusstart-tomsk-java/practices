package ru.cft.focusstart.badsamples;

import java.util.*;

public class MapConcurrentModificationSample {

    private final Map<String, String> map = new HashMap<>();

    private MapConcurrentModificationSample() {
        map.put("key1", "value1");
    }

    private void start() {
        startMutatingThread();
        startCheckingThread();
    }

    private void startMutatingThread() {
        new Thread(() -> {
            int counter = 0;
            long startTime = System.currentTimeMillis();
            while (counter++ < 1000) {
                List<String> keys = new ArrayList<>();
                for (int i = 0; i < 1000; i++) {
                    String key = UUID.randomUUID().toString();
                    map.put(key, UUID.randomUUID().toString());
                    keys.add(key);
                }
                for (String key : keys) {
                    map.remove(key);
                }
            }
            System.out.println(String.format("Executed %s iterations over %d ms", counter, System.currentTimeMillis() - startTime));
        }).start();
    }

    private void startCheckingThread() {
        new Thread(() -> {
            while (true) {
                String value = map.get("key1");
                if (!"value1".equals(value)) {
                    System.out.println("Value for key1 is not value1, actual value: " + value);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new MapConcurrentModificationSample().start();
    }
}
