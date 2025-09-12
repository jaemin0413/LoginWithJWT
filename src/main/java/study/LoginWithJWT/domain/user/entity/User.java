package study.LoginWithJWT.domain.user.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import study.LoginWithJWT.global.common.BaseEntity;

@Entity
@Table(name="users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(unique=true, nullable=false)
    private String loginId;

    private String name;
    @Column(nullable = false)
    private String password;

    //생성자
    public User() {}


    //getter
    public Long getId() {return id;}
    public LoginType getLoginType() {return loginType;}
    public String getLoginId() {return loginId;}
    public String getName() {return name;}
    public String getPassword() {return password;}

    //setter
    public void setId(Long id) {this.id = id;}
    public void setLoginType(LoginType loginType) {this.loginType = loginType;}
    public void setLoginId(String loginId) {this.loginId = loginId;}
    public void setName(String name) {this.name = name;}
    public void setPassword(String password) {this.password = password;}


}
