package uk.hpkns.mockitodemo;

public class DataRepository {

    private final Database database;

    public DataRepository(Database database) {
        this.database = database;
    }

    public void savePurchase(Purchase p) {
        // Some database function to save the data...
        database.save(p);
    }

    public void saveLotteryTicket(int ticket) {
        // Some database function to save the data...
        database.save(ticket);
    }
}
