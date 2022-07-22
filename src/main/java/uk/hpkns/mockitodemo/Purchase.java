package uk.hpkns.mockitodemo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Purchase {

    private LocalDateTime purchaseTime;
    private List<Product> products;
    private boolean paid;

    /**
     * Makes a new, blank purchase.
     */
    public Purchase() {
        purchaseTime = LocalDateTime.now();
        paid = false;
        products = new ArrayList<>();
    }

    public float getTotal() {
        float total = 0f;
        for (Product p : products) {
            total += p.getPrice();
        }
        return total;
    }

    public void add(Product product) {
        products.add(product);
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
