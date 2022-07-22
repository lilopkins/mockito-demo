package uk.hpkns.mockitodemo;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

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
        // Just `dataRepository.savePurchase(purchase);`, right???
        // Not quite, at the moment that will cause an error.
        assertThrows(Database.YepTheDatabaseStillIsntThereException.class, () -> dataRepository.savePurchase(purchase));

        // In the next test, you'll see how we can test this properly, without a full database ready.
    }

    @Test
    void testSavingATransaction() {
        // In order to test that we can save it, we need to understand just a little about our save function.
        // At the moment, it looks something like this:
        /*
            public void saveProduct(Product p) {
                // Some database function to save the data...
                database.save(p);
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

    @Test
    void testArgumentCaptors() {
        // Sometimes, we don't know exactly what has been passed to a method, but we still need to do some testing with
        // it. For example, let's introduce a lottery machine [always gamble responsibly ;)] to our shop.
        LotteryMachine lotteryMachine = new LotteryMachine();

        // Lets start a purchase now:
        Purchase purchase = new Purchase();
        purchase.add(apples);
        purchase.add(bananas);

        // And lets add our ticket. Our lottery machine needs access to the database, so we'll mock that as well:
        Database database = mock(Database.class);
        DataRepository dataRepository = new DataRepository(database);
        Product ticket = lotteryMachine.buyLotteryTicket(dataRepository);
        purchase.add(ticket);

        // We really want to check that the ticket we've added to our purchase was also correctly saved to the database.
        // Ticket barcodes from this machine start with 991, followed by the 6 ticket numbers. We can use an
        // ArgumentCaptor to get the ticket number as it was sent to the database.
        ArgumentCaptor<Integer> ticketNumberCaptor = ArgumentCaptor.forClass(Integer.class);

        // We capture the number that was sent using the same verify method as before
        verify(database).save(ticketNumberCaptor.capture());

        // But now we can access the ticket number
        int ticketNumber = ticketNumberCaptor.getValue();

        // Hopefully, it matches our ticket.
        int ourTicket = ticket.getBarcode() - 991000000;
        assertEquals(ourTicket, ticketNumber, "the ticket number should match what would be going into the database");
    }

    // Mockito has a lot more to offer. Once you're comfortable with the `mock()` and `verify()` methods, and the
    // `ArgumentCaptor`, it is worth browsing the documentation to learn more about it.
}
