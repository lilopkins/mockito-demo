package uk.hpkns.mockitodemo;

public class LotteryMachine {

    public Product buyLotteryTicket(DataRepository dataRepository) {
        int ticketNumber = (int) (Math.random() * 999999);
        int barcode = 991000000 + ticketNumber;
        String ticketName = "Lottery Ticket #" + ticketNumber;
        Product ticket = new Product(barcode, ticketName, 2.50f);
        dataRepository.saveLotteryTicket(ticketNumber);
        return ticket;
    }
}
