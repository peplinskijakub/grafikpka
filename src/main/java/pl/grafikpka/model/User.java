package pl.grafikpka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User {

    @Id
    private String id;

    @NonNull
    @Size(min = 3, max = 30)
    private String username;

    @NonNull
    @Size(min = 6)
    private String password;

    @NonNull
    private boolean isActive;

    private List<Role> roles;

    private String permissions = "";

    private List<Schedule> schedules;

    @Transient
    private MultipartFile file;

    public User(@NonNull String username, @NonNull String password, boolean isActive, List<Role> roles, String permissions) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.roles = roles;
        this.permissions = permissions;
    }

//    public List<String> getRoleList(){
//        if (this.roles.length()>0){
//            return Arrays.asList(this.roles.split(","));
//        }
//        return new ArrayList<>();
//    }

    public List<String> getPermissionList() {
        if (this.permissions.length() > 0) {
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
}
