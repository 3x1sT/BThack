package com.bt.BThack.api.CommandSystem.Exception;

public class VentureException extends Exception {
    /**
     * the message left from the place throwing the exception
     */
    private final String message;

    public VentureException() {
        this("");
    }

    public VentureException(String message) {
        this.message = message;
    }

    /**
     * @return the message
     */
    public final String getMessage() { return message; }
}