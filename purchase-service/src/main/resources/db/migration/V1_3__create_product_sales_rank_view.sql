CREATE OR REPLACE VIEW product_sales_rank AS
SELECT
    p.id,
    p.name,
    p.price,
    COUNT(*) AS sales_count,
    RANK() OVER (ORDER BY COUNT(*) DESC) AS rnk
FROM product p
JOIN purchase_products pp ON p.id = pp.product_id
GROUP BY p.id, p.name, p.price;