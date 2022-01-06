package com.github.kaklakariada.fritzbox;

public abstract class AbstractTestHelper {

    protected String getAin(final String identifier) {
        return identifier.replaceAll("\\s*", "");
    }

    protected double getCelsius(int code) {
        return code / 2D;
    }

    protected int getDegreeCode(double celsius) {
        return (int) (celsius * 2D);
    }

}
