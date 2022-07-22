package uk.hpkns.mockitodemo;

public class Database {
    /////////////////////////////////////////////////////////////////////////////////
    // This class isn't really useful to look in, unless you're really interested. //
    /////////////////////////////////////////////////////////////////////////////////

    public void save(Object o) {
        throw new YepTheDatabaseStillIsntThereException();
    }

    public static class YepTheDatabaseStillIsntThereException extends RuntimeException {
        public YepTheDatabaseStillIsntThereException() {
            super("Yep, the database still isn't there!");
        }
    }
}
