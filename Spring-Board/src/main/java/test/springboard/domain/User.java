package test.springboard.domain;

import java.util.Date;

public class User {

    private Long userno;
    private String id;
    private String password;
    private String nickname;

    private String email;

    private Date registerdate;

    public Long getUserno() { return userno; }
    public void setUserno(Long userno) { this.userno = userno;}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getRegisterdate() { return registerdate; }

    public void setRegisterdate(Date registerdate) { this.registerdate = registerdate; }
}
