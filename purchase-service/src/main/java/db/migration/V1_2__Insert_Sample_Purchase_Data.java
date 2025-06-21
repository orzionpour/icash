package db.migration;

import com.opencsv.CSVReader;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

public class V1_2__Insert_Sample_Purchase_Data extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        Connection conn = context.getConnection();

        Map<String, Integer> productIdMap = new HashMap<>();

        // Load product name -> ID mapping
        try (PreparedStatement ps = conn.prepareStatement("SELECT id, name FROM product");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                productIdMap.put(rs.getString("name"), rs.getInt("id"));
            }
        }

        // Cache existing users and supermarkets to avoid redundant inserts
        Set<String> existingUsers = new HashSet<>();
        Set<String> existingSupermarkets = new HashSet<>();

        PreparedStatement insertSupermarket = conn.prepareStatement("INSERT INTO supermarket(id) VALUES (?)");
        PreparedStatement insertUser = conn.prepareStatement("INSERT INTO \"user\"(id) VALUES (?)");
        PreparedStatement insertPurchase = conn.prepareStatement(
                "INSERT INTO purchase(supermarket_id, timestamp, user_id, total_amount) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        PreparedStatement insertPurchaseProduct = conn.prepareStatement(
                "INSERT INTO purchase_products(purchase_id, product_id) VALUES (?, ?)");

        // Open CSV file for reading
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("./db/purchases.csv"),
                StandardCharsets.UTF_8))) {

            String[] row;
            boolean header = true;

            while ((row = csvReader.readNext()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                String supermarketId = row[0].trim();
                String timestamp = row[1].trim();
                String userId = row[2].trim();
                String itemsList = row[3].trim();
                double totalAmount = Double.parseDouble(row[4].trim());

                // Insert supermarket if not exists
                if (!existingSupermarkets.contains(supermarketId)) {
                    insertSupermarket.setString(1, supermarketId);
                    insertSupermarket.executeUpdate();
                    existingSupermarkets.add(supermarketId);
                }

                // Insert user if not exists
                if (!existingUsers.contains(userId)) {
                    insertUser.setObject(1, UUID.fromString(userId));
                    insertUser.executeUpdate();
                    existingUsers.add(userId);
                }

                // Insert purchase
                insertPurchase.setString(1, supermarketId);
                insertPurchase.setTimestamp(2, Timestamp.valueOf(timestamp.replace("T", " ")));
                insertPurchase.setObject(3, UUID.fromString(userId));
                insertPurchase.setDouble(4, totalAmount);
                insertPurchase.executeUpdate();

                ResultSet generatedKeys = insertPurchase.getGeneratedKeys();
                generatedKeys.next();
                int purchaseId = generatedKeys.getInt(1);

                // Insert products for this purchase
                for (String item : itemsList.split(",")) {
                    item = item.trim();
                    Integer productId = productIdMap.get(item);
                    if (productId != null) {
                        insertPurchaseProduct.setInt(1, purchaseId);
                        insertPurchaseProduct.setInt(2, productId);
                        insertPurchaseProduct.addBatch();
                    }
                }

                insertPurchaseProduct.executeBatch();
            }
        }
    }
}
