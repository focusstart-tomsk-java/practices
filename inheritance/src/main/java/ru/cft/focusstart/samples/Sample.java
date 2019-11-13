package ru.cft.focusstart.samples;

import ru.cft.focusstart.samples.animal.Animal;
import ru.cft.focusstart.samples.animal.Cow;
import ru.cft.focusstart.samples.animal.Mammal;
import ru.cft.focusstart.samples.location.Location;
import ru.cft.focusstart.samples.place.NamedPlace;
import ru.cft.focusstart.samples.place.Sight;

import java.util.ArrayList;
import java.util.List;

public class Sample {

    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("Liza", new Location(0, 0), 1));
        animals.add(new Cow("Burenka", new Location(1, 1), 3));

        NamedPlace sherwoodForest = new Sight("Sherwood Forest", new Location(2, 3));

        moveAnimalsTo(animals, sherwoodForest);
        System.out.println("--------------------------");

        for (Animal animal : animals) {
            if (animal instanceof Mammal) {
                Mammal mammal = (Mammal) animal;
                mammal.say();
            }
        }
    }

    private static void moveAnimalsTo(List<Animal> animals, NamedPlace newLocation) {
        animals.forEach(animal -> animal.moveTo(newLocation));
    }
}
