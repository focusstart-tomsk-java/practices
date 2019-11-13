package ru.cft.focusstart.enumsample;

public enum FuelType {
    DIESEL("Дизельное топливо"),
    PETROL("Бензин"),
    GAS("Газ");

    private final String displayableName;

    FuelType(String displayableName) {
        this.displayableName = displayableName;
    }

    public String getDisplayableName() {
        return displayableName;
    }

    public static FuelType fromDisplayableName(String name) {
        for (FuelType type : FuelType.values()) {
            if (type.getDisplayableName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown FuelType name: " + name);
    }
}
