package pl.grafikpka.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

public class UserDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

//    @Email
//    @NotEmpty
//    private String email;
//
//    @Email
//    @NotEmpty
//    private String confirmEmail;

    @AssertTrue
    private Boolean terms;

}
