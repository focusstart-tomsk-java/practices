package ru.cft.focusstart.badsamples.staticinheritance;

import java.util.ArrayList;
import java.util.List;

public class Sample {

    public static void main(String[] args) {
        ClassB classBInstance = new ClassB();
        classBInstance.printGreeting();

        List<ClassA> instances = new ArrayList<>();
        instances.add(classBInstance);
        instances.forEach(i -> i.printGreeting());
    }
}
