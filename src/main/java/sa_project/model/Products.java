package sa_project.model;

public class Products {
    private String productCode;
    private String productName;
    private String productSpec;
    private int quantityIn;
    private int quantityWant;
    private int quantityAvailable;

    public Products(String productCode, String productName, String productSpec, int quantityIn, int quantityWant, int quantityAvailable) {
        this.productCode = productCode;
        this.productName = productName;
        this.productSpec = productSpec;
        this.quantityIn = quantityIn;
        this.quantityWant = quantityWant;
        this.quantityAvailable = quantityAvailable;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public int getQuantityIn() {
        return quantityIn;
    }

    public void setQuantityIn(int quantityIn) {
        this.quantityIn = quantityIn;
    }

    public int getQuantityWant() {
        return quantityWant;
    }

    public void setQuantityWant(int quantityWant) {
        this.quantityWant = quantityWant;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}
