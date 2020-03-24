package pl.grafikpka.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@Document
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int active;

    private String roles = "";

    private String permissions = "";

    public User(String id, String username, String password, int active, String roles, String permissions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = 1;
        this.roles = roles;
        this.permissions = permissions;
    }

    public User(String username, String password, String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
    }

    public List<String> getRoleList(){
        if (this.roles.length()>0){
            return Arrays.asList(this.roles.split("'"));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if (this.permissions.length()>0){
            return Arrays.asList(this.permissions.split("'"));
        }
        return new ArrayList<>();
    }
}
