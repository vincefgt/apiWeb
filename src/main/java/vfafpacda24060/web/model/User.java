package vfafpacda24060.web.model;

import lombok.Data;

@Data
public class User {
    private String idUser;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String password;
}
