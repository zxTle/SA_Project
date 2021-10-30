package sa_project.model;

public class ProductDoc {
    private int itemNum;
    private String productId;
    private String productName;
    private String description;
    private int quantity;

    public ProductDoc(int itemNum, String productId, String productName, String description, int quantity) {
        this.itemNum = itemNum;
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
