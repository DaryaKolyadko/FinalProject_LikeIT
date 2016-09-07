package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.LoginValidator;

/**
 * Created by DaryaKolyadko on 03.09.2016.
 */
public class DeleteProfileCommand extends SimpleActionCommand {
    private static final String DELETE_SUCCESS = "info.deleteProfile.success";
    private static final String DELETE_PROBLEM = "info.deleteProfile.problem";

    public DeleteProfileCommand() {
        super("login");
    }

    @Override
    protected void serviceCall(RequestContent content) throws CommandException {
        UserService userService = new UserService();
        String login = content.getRequestParameter(paramId);

        try {
            if (userService.moveProfileToArchive(login)) {
                content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(DELETE_SUCCESS,
                        MemoryContainerType.ONE_OFF));
                content.invalidateSession();
                resultPage = MappingManager.HOME_PAGE;
                return;
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(DELETE_PROBLEM));
            }

            resultPage = MappingManager.PROFILE_PAGE + RequestContentUtil.getParamAsQueryString(content,
                    paramId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    @Override
    protected boolean isAllowedAction(RequestContent content) {
        return allowedAction(content, content.getRequestParameter(paramId)) &&
                !RequestContentUtil.isCurrentUserAdmin(content);
    }

    @Override
    protected boolean isInputDataValid(RequestContent content) {
        return LoginValidator.isLoginValid(content.getRequestParameter(paramId));
    }
}