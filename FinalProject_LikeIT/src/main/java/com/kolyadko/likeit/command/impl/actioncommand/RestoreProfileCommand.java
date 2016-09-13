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
import com.kolyadko.likeit.validator.impl.LoginValidator;

/**
 * Created by DaryaKolyadko on 31.08.2016.
 */
public class RestoreProfileCommand extends SimpleActionCommand {
    private static final String RESTORE_SUCCESS = "info.restoreProfile.success";
    private static final String RESTORE_PROBLEM = "info.restoreProfile.problem";

    public RestoreProfileCommand() {
        super("login");
    }

    @Override
    protected void serviceCall(RequestContent content) throws CommandException {
        UserService userService = new UserService();
        String login = content.getRequestParameter(paramId);

        try {
            if (userService.restoreProfileFromArchive(login)) {
                content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(RESTORE_SUCCESS,
                        MemoryContainerType.ONE_OFF));
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(RESTORE_PROBLEM));
            }

            resultPage = MappingManager.PROFILE_PAGE + RequestContentUtil.getParamAsQueryString(content,
                    paramId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return LoginValidator.isLoginValid(content.getRequestParameter(paramId));
    }
}