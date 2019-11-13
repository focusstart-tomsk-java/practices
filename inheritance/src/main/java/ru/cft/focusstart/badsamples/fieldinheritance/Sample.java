package ru.cft.focusstart.badsamples.fieldinheritance;

public class Sample {

    public static void main(String[] args) {
        ClassB classBInstance = new ClassB();
        classBInstance.setSomeField(123);

        System.out.println(classBInstance.getSomeField());
    }
}
