package tn.krh.savsamsung.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "user__item")

public class user {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "fullname")
    private String fullname;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "adresse")
    private String adresse;
    @ColumnInfo(name = "tel")
    private int tel;
    @ColumnInfo(name = "password")
    private String password;

    public user(String fullname, String email, String adresse, int tel, String password) {
        this.fullname = fullname;
        this.email = email;
        this.adresse = adresse;
        this.tel = tel;
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
