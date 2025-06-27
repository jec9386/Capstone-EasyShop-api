package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao
{
    private ProductDao productDao;//product objects

    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao)
    {
        super(dataSource);
        this.productDao = productDao;
    }

    @Override
    public ShoppingCart getByUserId(int userId)
    {
        ShoppingCart cart = new ShoppingCart();
        Map<Integer, ShoppingCartItem> items = new HashMap<>();

        String sql = "SELECT sc.product_id, sc.quantity " +
                "FROM shopping_cart sc " +// call shopping_cart = sc from now on shorter easier to read, less typing
                "WHERE sc.user_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet row = statement.executeQuery();

            while (row.next())
            {
                int productId = row.getInt("product_id");
                int quantity = row.getInt("quantity");

                // Get full product details
                Product product = productDao.getById(productId);

                if (product != null)
                {
                    // Create cart item
                    ShoppingCartItem item = new ShoppingCartItem();
                    item.setProduct(product);
                    item.setQuantity(quantity);

                    // Add to items map
                    items.put(productId, item);//create key:value pair, when called upon can get product detail and quantity
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        cart.setItems(items);
        return cart;
    }

    @Override
    public void saveCart(int userId, ShoppingCart cart)
    {
        // First clear existing cart items, so that the new data doesnt get added to old
        clearCart(userId);

        // Then add all items from the cart
        for (ShoppingCartItem item : cart.getItems().values())
        {
            addItem(userId, item.getProductId(), item.getQuantity());
        }
    }

    @Override
    public void clearCart(int userId)
    {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addItem(int userId, int productId, int quantity)
    {
        // Check if item already exists in cart, if so we can update the item in the cart already
        String checkSql = "SELECT quantity FROM shopping_cart WHERE user_id = ? AND product_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setInt(1, userId);
            checkStatement.setInt(2, productId);

            ResultSet row = checkStatement.executeQuery();

            if (row.next())
            {

                int existingQuantity = row.getInt("quantity");
                int newQuantity = existingQuantity + quantity;
                updateItem(userId, productId, newQuantity);
            }
            else
            {

                String insertSql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";

                PreparedStatement insertStatement = connection.prepareStatement(insertSql);
                insertStatement.setInt(1, userId);
                insertStatement.setInt(2, productId);
                insertStatement.setInt(3, quantity);

                insertStatement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItem(int userId, int productId, int quantity)
    {
        if (quantity <= 0)
        {
            // If quantity is 0 or negative, remove the item
            removeItem(userId, productId);
            return;
        }

        String sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeItem(int userId, int productId)
    {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ? AND product_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, productId);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasItems(int userId)
    {
        String sql = "SELECT COUNT(*) as item_count FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet row = statement.executeQuery();

            if (row.next())
            {
                int itemCount = row.getInt("item_count");
                return itemCount > 0;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return false;
    }

}