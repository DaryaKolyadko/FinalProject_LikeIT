package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.command.type.ShowCommandType;
import com.kolyadko.likeit.content.RequestContent;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowMainPageCommand extends ShowCommand {
    public ShowMainPageCommand() {
        super(ShowCommandType.SHOW_HOME_PAGE.getRequestURI());
    }

    @Override
    public String execute(RequestContent content) {
        //TODO take data from database
        return super.execute(content);
    }
}