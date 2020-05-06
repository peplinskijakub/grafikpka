package pl.grafikpka.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public enum  TypRozkladu {
    POWSZEDNI_SZKOLNY("Powszedni szkolny"),
    SOBOTA("Sobota"),
    NIEDZIELA("Niedziela"),
    POWSZEDNI_FERIE("Powszedni ferie"),
    POWSZDNI_WAKACJE("Powszedni wakacje"),
    SPECJALNY("Specjalny"),
    SOBOTA_WAKACJE("Sobota wakacje"),
    NIEDZIELA_WAKACJE("Niedziela wakacje");

    private String dispayValue;

    private TypRozkladu(String displayValue) {
        this.dispayValue = displayValue;
    }

    public String getDispayValue() {
        return dispayValue;
    }
}
