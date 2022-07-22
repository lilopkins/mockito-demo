package uk.hpkns.mockitodemo;

public class DataRepository {

    private Database database;

    public DataRepository(Database database) {
        this.database = database;
    }

    public void saveProduct(Product p) {
        // Some database function to save the data...
        database.save(p);
    }

    public void savePurchase(Purchase p) {
        // Some database function to save the data...
        database.save(p);
    }

    public void saveShop(Shop s) {
        // Some database function to save the data...
        database.save(s);
    }
}
