package tn.krh.savsamsung.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import tn.krh.savsamsung.entity.shoppingCart;

@Dao
public interface UserDao {
    @Insert
    void insertUserShoppingCartItem(shoppingCart item);
    @Query("SELECT * FROM shoppingCart__item WHERE user_id=:id")
    List<shoppingCart> getShoppingCartItems(int id);

    @Delete
    void deleteShoppingCartItem(shoppingCart item);

    @Query("DELETE FROM shoppingCart__item")
    void deleteAllItems();

    @Query("DELETE FROM shoppingCart__item WHERE user_id=:id")
    void deleteAllShoppingCartItemsForUser(int id);
}
