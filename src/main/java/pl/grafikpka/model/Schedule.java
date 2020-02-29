package pl.grafikpka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    private String id;
    @Column(name = "date")
    private String date;
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "typ_rozkladu")
    private TypRozkladu typRozkladu;
    @Column(name = "nr_sluzbowy")
    private String nrSluzbowy;
    @Column(name = "linia")
    private String linia;
    @Column(name = "poczatekPracy")
    private String poczatekPracy;
    @Column(name = "koniecPracy")
    private String koniecPracy;
    @Column(name = "miejsce_zmiany")
    private String miejsceZmiany;

    @Transient
    private MultipartFile file;
}
