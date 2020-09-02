package tn.krh.savsamsung.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "astuce__item")

public class astuce {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "article_id")
    private int article_id;

    public astuce(String description, int article_id) {
        this.description = description;
        this.article_id = article_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }
}
