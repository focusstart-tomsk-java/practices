package ru.cft.focusstart.samples.animal;

import ru.cft.focusstart.samples.location.Location;
import ru.cft.focusstart.samples.place.NamedPlace;

public class Animal {

    protected final String name;
    private Location location;
    private int speed;

    public Animal(String name, Location location, int speed) {
        this.name = name;
        this.location = location;
        this.speed = speed;
    }

    public void moveTo(NamedPlace to) {
        int distance = getDistance(location, to.getLocation());
        System.out.println(String.format("%s started to go to %s", name, to.getName()));
        try {
            Thread.sleep(distance / speed * 1000);
        } catch (Exception ignore) {
        }
        location = to.getLocation();
        System.out.println(String.format("%s came to %s", name, to.getName()));
    }

    private int getDistance(Location p1, Location p2) {
        return (int) Math.hypot(p1.getX() - p2.getX(), p1.getY() - p2.getY());
    }
}
