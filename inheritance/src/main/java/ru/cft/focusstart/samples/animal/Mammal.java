package ru.cft.focusstart.samples.animal;

import ru.cft.focusstart.samples.location.Location;
import ru.cft.focusstart.samples.place.NamedPlace;

public abstract class Mammal extends Animal {

    protected Mammal(String name, Location location, int speed) {
        super(name, location, speed);
    }

    public abstract void say();

    @Override
    public void moveTo(NamedPlace to) {
        say();
        super.moveTo(to);
    }
}
