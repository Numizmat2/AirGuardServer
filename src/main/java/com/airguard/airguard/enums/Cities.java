package com.airguard.airguard.enums;

public enum Cities {
    GDANSK("gdańsk"),
    WROCLAW("wrocław"),
    KRAKOW("kraków"),
    WARSZAWA("warszawa"),
    POZNAN("poznań"),
    LODZ("łódź"),
    BYDGOSZCZ("bydgoszcz"),
    SZCZECIN("szczecin"),
    BIALYSTOK("białystok"),
    KIELCE("kielce"),
    LUBLIN("lublin"),
    KATOWICE("katowice"),
    RZESZOW("rzeszów"),
    OPOLE("opole"),
    ZIELONA_GORA("zielona góra"),
    KOSZALIN("koszalin");

    private final String cityName;
    Cities(String cityName) { this.cityName = cityName;}
    public String cityName() {
        return cityName;
    }
}
