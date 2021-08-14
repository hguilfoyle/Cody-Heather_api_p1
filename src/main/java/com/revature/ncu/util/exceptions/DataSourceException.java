package com.revature.ncu.util.exceptions;

public class DataSourceException extends RuntimeException {

    public DataSourceException(Throwable cause) {
        super("An unexpected exception occurred.", cause);
    }

}
