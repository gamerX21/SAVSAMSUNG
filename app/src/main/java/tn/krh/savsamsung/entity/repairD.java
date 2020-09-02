package tn.krh.savsamsung.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "repair__item")

public class repairD {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "phone")
    private int phone;
    @ColumnInfo(name = "ville")
    private String ville;
    @ColumnInfo(name = "adresse")
    private String adresse;
    @ColumnInfo(name = "generated_num")
    private int generated_num;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "dateA")
    private java.util.Date dateA;

    public repairD(String type, int phone, String ville, String adresse, int generated_num) {
        this.type = type;
        this.phone = phone;
        this.ville = ville;
        this.adresse = adresse;
        this.generated_num = generated_num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getGenerated_num() {
        return generated_num;
    }

    public void setGenerated_num(int generated_num) {
        this.generated_num = generated_num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateA() {
        return dateA;
    }

    public void setDateA(Date dateA) {
        this.dateA = dateA;
    }
}
