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
//    PARSE_XML(new ParseXmlCommand()),
//    SHOW_LOGIN_PAGE(new ShowLoginPageCommand()),
//    SHOW_SIGN_UP_PAGE(new ShowSignUpPageCommand()),
//    SHOW_PARSER_HOME_PAGE(new ShowParserHomePageCommand()),
//    SHOW_PARSER_RESULT_COMMAND(new ShowParserResultPageCommand());

    private Command command;

    ActionCommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}