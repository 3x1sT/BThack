package com.bt.BThack.api.CommandSystem.Exception;

public class CommandIllegalArgumentException extends CommandException {
    public CommandIllegalArgumentException() { this(""); }
    public CommandIllegalArgumentException(String message) { super(message); }
}