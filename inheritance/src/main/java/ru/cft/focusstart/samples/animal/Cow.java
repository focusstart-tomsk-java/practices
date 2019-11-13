package ru.cft.focusstart.samples.animal;

import ru.cft.focusstart.samples.location.Location;

public class Cow extends Mammal {

    public Cow(String name, Location location, int speed) {
        super(name, location, speed);
    }

    @Override
    public void say() {
        System.out.println(String.format("%s said moooooooo!", name));
    }
}
