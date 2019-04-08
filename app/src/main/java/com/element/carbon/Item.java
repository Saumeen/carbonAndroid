package com.element.carbon;

public class Item {
    private String product_id;
    private String product_name;
    private String product_summary;
    private int product_quantity;
    private int product_price;
    private String barcode;



    public Item(String product_id, String product_name, String product_summary, int product_price, String barcode) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_summary = product_summary;
        this.product_price = product_price;
        this.product_quantity = 1;
        this.barcode = barcode;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_summary() {
        return product_summary;
    }

    public String getBarcode() { return barcode; }

    public void setBarcode(String barcode) { this.barcode = barcode; }

    public void setProduct_summary(String product_summary) {
        this.product_summary = product_summary;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

}
