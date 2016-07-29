package com.kolyadko.likeit.command.impl.actioncommand;


import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.content.RequestContent;

/**
 * Created by DaryaKolyadko on 16.07.2016.
 */
public class EmptyCommand implements Command {
    public String execute(RequestContent content) {
        return null;
    }
}