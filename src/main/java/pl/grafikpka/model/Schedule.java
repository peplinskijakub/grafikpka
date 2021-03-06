package pl.grafikpka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.Set;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    private String id;
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "typ_rozkladu")
    private TypRozkladu typRozkladu;
    @Column(name = "username")
    @Indexed
    private String username;
    @Column(name = "linia")
    private String linia;
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "poczatekPracy")
    private String poczatekPracy;
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "koniecPracy")
    private String koniecPracy;
    @Column(name = "miejsce_zmiany")
    private String miejsceZmiany;
    @Column(name = "rodzaj_rozkladu")
    private Set<RodzajRozkladu>rodzajRozkladu;

    @Transient
    private MultipartFile file;
}
