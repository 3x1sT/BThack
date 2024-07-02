package com.bt.BThack.api.CommandSystem.Exception;

public class VentureBadArgumentSizeException extends VentureIllegalArgumentException {
    public VentureBadArgumentSizeException() { this(""); }
    public VentureBadArgumentSizeException(String message) { super(message); }
}