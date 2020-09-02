package tn.krh.savsamsung.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "commande_item")
public class commande {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "ref_cmd")
    private int ref;
    @ColumnInfo(name = "user_id")
    private int user_id;
    @ColumnInfo(name = "produit_id")
    private int produit_id;
    @ColumnInfo(name = "quantité")
    private int quantite;
    @ColumnInfo(name = "date")
    private Date date;

    public commande(int user_id, int produit_id, int quantite, Date date,int ref) {
        this.user_id = user_id;
        this.produit_id = produit_id;
        this.quantite = quantite;
        this.date = date;
        this.ref = ref;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }
}
