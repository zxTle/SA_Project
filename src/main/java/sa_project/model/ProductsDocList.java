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

    public String toInsert (String rqNo){
        String text="";
        for(ProductDoc prDoc : productList){
            if(prDoc.getItemNum() == productList.size()){
                text += "("+ prDoc.getItemNum()+","+"'"+rqNo+"'"+","+"'"+prDoc.getProductId()+"'"+","+prDoc.getQuantity()+")"+";";
            }
            else{
                text += "("+ prDoc.getItemNum()+","+"'"+rqNo+"'"+","+"'"+prDoc.getProductId()+"'"+","+prDoc.getQuantity()+")"+",";
            }
        }
        return text;
    }
}
