package ru.cft.focusstart.enumsample;

import java.util.Arrays;

public class Sample {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));

        GasStation gasStation = new GasStation();
        gasStation.addToStock(FuelType.DIESEL, 23);
        gasStation.addToStock(FuelType.PETROL, 165);
        System.out.println(gasStation.getStocks());
        System.out.println("--------------------------");

        FuelType diesel = FuelType.valueOf("DIESEL");
        gasStation.addToStock(diesel, 200);
        System.out.println(gasStation.getStocks());
        System.out.println("--------------------------");

        FuelType gas = FuelType.fromDisplayableName("Газ");
        gasStation.addToStock(gas, 100);
        System.out.println(gasStation.getStocks());
        System.out.println("--------------------------");
    }
}
