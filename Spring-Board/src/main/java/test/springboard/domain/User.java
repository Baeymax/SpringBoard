package test.springboard.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private Long index;
    private String id;
    private String password;

    private String name;

    private String email;

    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    Date now = new Date();
    String registerdate = sdf1.format(now);
    public Long getIndex() { return index; }
    public void setIndex(Long index) { this.index = index;}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
