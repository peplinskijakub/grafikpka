package pl.grafikpka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import javax.persistence.Transient;
@Repository
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RodzajRozkladu {

    @Id
    private String id;
    private String typRozkladu;
    private String linia;
    private String brygada;
    private String godzina;
    private String miejsceZmiany;
    private String pierwszaLinia;

    @Transient
    private MultipartFile file;
}
