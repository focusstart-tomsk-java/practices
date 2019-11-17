package ru.cft.focusstart.badsamples;

public class ReorderSample {
    private int a = 2;
    private volatile boolean flg = false;

    private void method1() {
        a = 1;
        flg = true;
    }

    private void method2() {
        if (flg && a == 2) {
            System.out.println("a = " + a);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            ReorderSample reorderSample = new ReorderSample();
            new Thread(reorderSample::method1).start();
            new Thread(reorderSample::method2).start();
        }
    }
}
