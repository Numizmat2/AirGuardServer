package com.airguard.airguard.enums;

public enum PollutionTypes {
    PM25("PM2.5"),
    PM10("PM10"),
    PM1("PM1"),
    CO("CO"),
    O3("O3"),
    SO2("SO2"),
    NO2("NO2");

    private final String pollutionName;
    PollutionTypes(String pollutionName) { this.pollutionName = pollutionName;}
    public String pollutionName() {
        return pollutionName;
    }
}
