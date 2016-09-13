package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 02.09.2016.
 */
public abstract class SimpleActionCommand extends ActionCommand {
    protected String paramId;
    protected String resultPage;

    public SimpleActionCommand(String paramId) {
        this.paramId = paramId;
        resultPage = MappingManager.HOME_PAGE;
    }

    protected abstract void serviceCall(RequestContent content) throws CommandException;

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isAllowedAction(content)) {
            if (isInputDataValid(content)) {
                serviceCall(content);
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(CHECK_INPUT_DATA));
            }
        } else {
            content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(NOT_ALLOWED));
        }

        return resultPage;
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return Validator.isNumIdValid(content.getRequestParameter(paramId));
    }
}