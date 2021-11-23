package com.github.kaklakariada.fritzbox.model.homeautomation;

public enum MEASUREMENT_UNIT {
    TEMPERATURE("C", 0.1, Temperature.class), 
    VOLTAGE("V", 0.001, Voltage.class), 
    POWER("W", 0.01, Power.class), 
    ENERGY("Wh", 1D, Energy.class), 
    HUMIDITY("%", 1D, Humidity.class);

    private final String unit;
    private final Double precision;
    private final Class mapper;

    MEASUREMENT_UNIT(String unit, Double precision, Class mapper) {
        this.unit = unit;
        this.precision = precision;
        this.mapper = mapper;
    }

    public String getUnit() {
        return unit;
    }

    public Double getPrescision() {
        return precision;
    }

    public Class getMapper() {
        return mapper;
    }

    public static MEASUREMENT_UNIT getMatchingMeasurementUnit(Class caller) {
        for (MEASUREMENT_UNIT iterator : MEASUREMENT_UNIT.values()) {
            if (caller.getSimpleName().equals(iterator.getMapper().getSimpleName())) {
                return iterator;
            }
        }
        return null;

    }


}
