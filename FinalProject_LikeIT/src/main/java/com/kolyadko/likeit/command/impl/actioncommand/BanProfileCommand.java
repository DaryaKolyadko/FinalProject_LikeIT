package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
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
 * Created by DaryaKolyadko on 05.09.2016.
 */
public class BanProfileCommand extends SimpleActionCommand {
    private static final String BAN_SUCCESS = "info.banProfile.success";
    private static final String BAN_PROBLEM = "info.banProfile.problem";

    public BanProfileCommand() {
        super("login");
    }

    @Override
    protected void serviceCall(RequestContent content) throws CommandException {
        UserService userService = new UserService();
        String login = content.getRequestParameter(paramId);

        try {
            User user = userService.findById(login, RequestContentUtil.isCurrentUserAdmin(content));

            if (!user.isAdmin() && userService.banProfileById(login)) {
                content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(BAN_SUCCESS,
                        MemoryContainerType.ONE_OFF));
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(BAN_PROBLEM));
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