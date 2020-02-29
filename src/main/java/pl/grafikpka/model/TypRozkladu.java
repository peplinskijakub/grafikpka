package pl.grafikpka.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public enum  TypRozkladu {
    POWSZEDNI_SZKOLNY("Powszedni szkolny"),
    SOBOTA("Sobota"),
    NIEDZIELA("Niedziela"),
    POWSZEDNI_FERIE("Powszedni ferie"),
    POWSZDNI_WAKACJE("Powszedni wakacje"),
    SPECJALNY("Specjalny");

    private final String dispayValue;

    TypRozkladu(String dispayValue) {
        this.dispayValue = dispayValue;
    }

    public String getDispayValue() {
        return dispayValue;
    }
}
