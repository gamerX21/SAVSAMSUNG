package tn.krh.savsamsung.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "article__item")

public class article {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "nom")
    private String nom;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "img")
    private String img;
    @ColumnInfo(name = "titre")
    private String titre;
    public article(String nom, String type,String description, String img) {
        this.nom = nom;
        this.type = type;
        this.description = description;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
