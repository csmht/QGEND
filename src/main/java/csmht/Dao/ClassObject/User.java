package csmht.Dao.ClassObject;

import com.alibaba.fastjson2.annotation.JSONField;
import csmht.Dao.UserClass;

public class User extends UserClass {


    @JSONField(name = "UserName")
    private String UserName;

    @JSONField(name="PassWord")
    private String password;

    @JSONField(name = "email")
    private String email;

    @JSONField(name="admin")
    private Boolean admin;

    @JSONField(name="pass")
    private String pass;

    @JSONField(name="image_id")
    private int image_id;

    @JSONField(name="image")
    private byte[] image;

    @JSONField(name="create_time")
    private String create_time;


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {return admin;}

    public void setAdmin(Boolean admin) {this.admin = admin;}

    public String getPass() {return pass;}

    public void setPass(String pass) {this.pass = pass;}

    public int getImage_id() {return image_id;}

    public void setImage_id(int image_id) {this.image_id = image_id;}

    public byte[] getImage() {return image;}

    public void setImage(byte[] image) {this.image = image;}

}
