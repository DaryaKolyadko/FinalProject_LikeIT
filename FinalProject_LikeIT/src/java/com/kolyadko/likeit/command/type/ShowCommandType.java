package com.kolyadko.likeit.command.type;

import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public enum ShowCommandType {
    SIMPLE_SHOW,
    SHOW_HOME_PAGE(MappingManager.HOME_PAGE),
    SHOW_USERS_PAGE(MappingManager.USERS_PAGE);

    private String requestURI;

    ShowCommandType() {
        this("");
    }

    ShowCommandType(String requestURI) {
        this.requestURI = requestURI;
    }

    public static ShowCommandType getCommandWithURI(String requestURI) {
        for (ShowCommandType type : ShowCommandType.values()) {
            if (type.requestURI.equals(requestURI)) {
                return type;
            }
        }

        return SIMPLE_SHOW;
    }

    public String getRequestURI() {
        return requestURI;
    }
}