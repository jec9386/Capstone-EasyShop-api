package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao
{
    //get user shopping cart
    ShoppingCart getByUserId(int userId);

    // Save cart
    void saveCart(int userId, ShoppingCart cart);

    // Clear all items from this user's cart
    void clearCart(int userId);

    // Add a product to cart or increase if product already exist in cart
    void addItem(int userId, int productId, int quantity);

    // Update quantity of determined product
    void updateItem(int userId, int productId, int quantity);

    // Remove a specific product from cart
    void removeItem(int userId, int productId);

    // Check if user has any items in cart
    boolean hasItems(int userId);


}
