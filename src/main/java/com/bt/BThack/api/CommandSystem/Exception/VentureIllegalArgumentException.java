package com.bt.BThack.api.CommandSystem.Exception;

public class VentureIllegalArgumentException extends VentureException {
    public VentureIllegalArgumentException() { this(""); }
    public VentureIllegalArgumentException(String message) { super(message); }
}