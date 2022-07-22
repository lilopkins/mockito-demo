package uk.hpkns.mockitodemo;

public class Product {

    private int barcode;
    private String name;
    private float price;

    public Product(int barcode, String name, float price) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public int getBarcode() {
        return barcode;
    }
}
