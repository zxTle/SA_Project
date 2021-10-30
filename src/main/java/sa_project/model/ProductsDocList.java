package sa_project.model;

import java.util.ArrayList;

public class ProductsDocList {
    private ArrayList<ProductDoc> productList;

    public ProductsDocList() {
        this.productList = new ArrayList<>();
    }

    public void addProduct(ProductDoc product){
        productList.add(product);
    }
    public ArrayList<ProductDoc> toList(){return productList;}
}
