package ru.cft.focusstart;

import java.util.Objects;

public class Tuple {

    private final int first;
    private final int second;

    public Tuple(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple tuple = (Tuple) o;
        return first == tuple.first &&
                second == tuple.second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
