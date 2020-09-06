package tn.krh.savsamsung.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoppingCart__item")
public class shoppingCart {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "produit_id")
    private int produitId;
    @ColumnInfo(name = "produit_quan")
    private int produitQuantite;

    @ColumnInfo(name = "prix_yy")
    private int prixTT;
    public shoppingCart(){

    }
    public shoppingCart(int userId, int produitId, int produitQuantite, int prixTT) {
        this.userId = userId;
        this.produitId = produitId;
        this.produitQuantite = produitQuantite;
        this.prixTT = prixTT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public int getProduitQuantite() {
        return produitQuantite;
    }

    public void setProduitQuantite(int produitQuantite) {
        this.produitQuantite = produitQuantite;
    }

    public int getPrixTT() {
        return prixTT;
    }

    public void setPrixTT(int prixTT) {
        this.prixTT = prixTT;
    }
}
