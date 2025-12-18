package model;

public class CheeseProduct {
    private int productId;
    private String productName;
    private String cheeseType;
    private String description;
    private double price;
    private int stockQuantity;
    private int supplierId;

    public CheeseProduct() {}

    public CheeseProduct(String productName, String cheeseType, String description, double price, int stockQuantity, int supplierId) {
        this.productName = productName;
        this.cheeseType = cheeseType;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.supplierId = supplierId;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCheeseType() { return cheeseType; }
    public void setCheeseType(String cheeseType) { this.cheeseType = cheeseType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    @Override
    public String toString() {
        return productName + " (" + cheeseType + ") - $" + price;
    }
}