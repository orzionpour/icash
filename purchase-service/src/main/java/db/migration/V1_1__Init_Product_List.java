package db.migration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1_1__Init_Product_List extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream("./db/products_list.csv"),
                        StandardCharsets.UTF_8))) {

            String line;
            boolean header = true;

            PreparedStatement ps = context.getConnection().prepareStatement(
                    "INSERT INTO product (name, price) VALUES (?, ?) ON CONFLICT (name) DO NOTHING");

            while ((line = reader.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                String[] parts = line.split(",");
                String name = parts[0].trim();
                double price = Double.parseDouble(parts[1].trim());

                ps.setString(1, name);
                ps.setDouble(2, price);
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }
}
