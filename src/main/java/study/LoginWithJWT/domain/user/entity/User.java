package study.LoginWithJWT.domain.user.entity;

import jakarta.persistence.*;
import study.LoginWithJWT.global.common.BaseEntity;

@Entity
@Table(name="users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String email;

    private String name;
    private String password;

    //getter
    public Long getId() {return id;};
    public LoginType getLoginType() {return loginType;}
    public String getEmail() {return email;}
    public String getName() {return name;}
    public String getPassword() {return password;}

    //setter
    public void setId(Long id) {this.id = id;}
    public void setLoginType(LoginType loginType) {this.loginType = loginType;}
    public void setEmail(String email) {this.email = email;}
    public void setName(String name) {this.name = name;}
    public void setPassword(String password) {this.password = password;}


}
