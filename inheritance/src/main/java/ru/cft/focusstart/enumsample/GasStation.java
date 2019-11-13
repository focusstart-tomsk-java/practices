package ru.cft.focusstart.enumsample;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GasStation {

    private final Map<FuelType, Integer> stocks = new HashMap<>();

    public void addToStock(FuelType fuelType, int amount) {
        int newAmount = stocks.getOrDefault(fuelType, 0) + amount;
        stocks.put(fuelType, newAmount);
    }

    public String getStocks() {
        return stocks.entrySet()
                .stream()
                .map(e -> String.format("%s: %d литра", e.getKey().getDisplayableName(), e.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
