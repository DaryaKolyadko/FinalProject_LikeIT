package com.kolyadko.likeit.command;

import com.kolyadko.likeit.content.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */
public interface Command {
    Logger LOG = LogManager.getLogger(Command.class);
    String EXCEPTION = "exceptionContainer";

    String execute(RequestContent content);
}