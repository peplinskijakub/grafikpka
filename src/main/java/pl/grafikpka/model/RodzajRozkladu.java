package pl.grafikpka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class RodzajRozkladu {

    @Id
    private String id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "typ_rozkladu")
    private TypRozkladu typRozkladu;
    @Column(name = "linia")
    private String linia;
    @Column(name = "brygada")
    private String brygada;
    @Column(name = "godzina")
    private String godzina;
    @Column(name = "miejsce_zmiany")
    private String miejsceZmiany;
    @Column(name = "pierwsza_linia")
    private String pierwszaLinia;

//    @Transient
//    private MultipartFile file;
}
