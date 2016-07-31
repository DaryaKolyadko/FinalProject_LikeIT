package com.kolyadko.likeit.command.type;


import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.command.impl.actioncommand.LoginCommand;
import com.kolyadko.likeit.command.impl.actioncommand.LogoutCommand;
import com.kolyadko.likeit.command.impl.actioncommand.SignUpCommand;

/**
 * Created by DaryaKolyadko on 16.07.2016.
 */
public enum ActionCommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    SIGN_UP(new SignUpCommand());

    private Command command;

    ActionCommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}