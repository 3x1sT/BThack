package com.bt.BThack.impl.Commands;


import com.bt.BThack.api.CommandSystem.command.AbstractCommand;

public class CalcCommand extends AbstractCommand {

    public CalcCommand() {
        super("Just a calculator", "calc ...", "calc"
        );
    }

    @Override
    public void execute(String[] args) {

    }
}
