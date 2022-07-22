package uk.hpkns.mockitodemo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WhyMockitoTests {

    Product bananas = new Product(9264816, "Bananas", 1.20f);
    Product apples  = new Product(1490215, "Apples" , 1.00f);
    Product pears   = new Product(8255028, "Pears"  , 2.10f);

    /*
     * Mockito is useful when a project isn't in a complete environment. This project has no database because that team
     * are three weeks behind(!!), but we still need to test.
     *
     * Let's try to make a transaction...
     */
    @Test
    void testMakingATransaction() {
        DataRepository dataRepository = new DataRepository(new Database());

        Purchase purchase = new Purchase();
        purchase.add(bananas);
        purchase.add(bananas);
        purchase.add(pears);

        assertEquals(4.50f, purchase.getTotal(), "We need to pay the right amount!");

        purchase.setPaid(true);
        // Now that we've paid, the shop wants to keep a copy of the purchase in the database.
        // Just `dataRepository.save(purchase);`, right???
        // Not quite, at the moment that will cause an error.
        assertThrows(Database.YepTheDatabaseStillIsntThereException.class, () -> dataRepository.savePurchase(purchase));

        // In the next test, you'll see how we can test this properly, without a full database ready.
    }

    @Test
    void testSavingATransaction() {
        // In order to test that we can save it, we need to understand just a little about our save function.
        // At the moment, it looks like this:
        /*
            public void save(Product p) {
                // Some database function to save the data...
                saveThing(p);
            }
         */
        // Ah! Maybe we can check if the saveThing() method ever is sent our purchase... this is where Mockito comes in!

        // Lets make a purchase again!
        Purchase purchase = new Purchase();
        purchase.add(apples);
        purchase.add(bananas);
        purchase.setPaid(true);

        // Now, let's tell Mockito to mock our Database link, and we'll give that to our DataRepository.
        Database database = mock(Database.class);
        DataRepository dataRepository = new DataRepository(database);
        // This looks a little different, right? Our DataRepository hasn't been constructed at all, but Mockito will
        // keep track of what we do with it.

        // Lets try to save our purchase now...
        dataRepository.savePurchase(purchase);
        // No error? But the database still doesn't exist. The key to understanding Mockito is that it will let you
        // **call** any method, but that method won't run. Mockito will just remember that you called it.

        // Now, we can check if our database was called to save that purchase...
        verify(database).save(purchase);
        // If it wasn't, Mockito will fail the test here. Hopefully when this test runs, it should pass!

        // You can use verify() with any method, as long as it's something that you have asked Mockito to mock.
    }
}
