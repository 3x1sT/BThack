package com.bt.BThack.api.CommandSystem.Exception;

public class BadArgumentSizeException extends CommandIllegalArgumentException {
    public BadArgumentSizeException() { this(""); }
    public BadArgumentSizeException(String message) { super(message); }
}