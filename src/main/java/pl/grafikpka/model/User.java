package pl.grafikpka.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    @NonNull
    private String username;

    @Column(nullable = false)
    @NonNull
    private String password;

    @NonNull
    private boolean isActive;

    private String roles = "";

    private String permissions = "";

    public User(@NonNull String username, @NonNull String password,boolean isActive, String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.isActive=isActive;
        this.roles = roles;
        this.permissions = permissions;
    }

    public List<String> getRoleList(){
        if (this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if (this.permissions.length()>0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
}
