package ru.cft.focusstart.samples.place;

import ru.cft.focusstart.samples.location.Location;

public class Sight implements NamedPlace {

    private final String name;
    private final Location location;

    public Sight(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
