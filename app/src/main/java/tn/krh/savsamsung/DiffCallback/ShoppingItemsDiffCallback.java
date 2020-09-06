package tn.krh.savsamsung.DiffCallback;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import tn.krh.savsamsung.entity.shoppingCart;

public class ShoppingItemsDiffCallback  extends DiffUtil.Callback{
    private final List<shoppingCart> mOldShoppingList;
    private final List<shoppingCart> mNewShoppingList;

    public ShoppingItemsDiffCallback(List<shoppingCart> mOldShoppingList, List<shoppingCart> mNewShoppingList) {
        this.mOldShoppingList = mOldShoppingList;
        this.mNewShoppingList = mNewShoppingList;
    }

    @Override
    public int getOldListSize() {
        return mOldShoppingList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewShoppingList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldShoppingList.get(oldItemPosition).getId() == mNewShoppingList.get(
                newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final shoppingCart oldsItem = mOldShoppingList.get(oldItemPosition);
        final shoppingCart newsItem = mNewShoppingList.get(newItemPosition);

        return returnState(oldsItem.getId(),newsItem.getId());
    }
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
    public boolean returnState(int id1,int id2){
        if(id1 == id2) return true;
        else return false;
    }
}
