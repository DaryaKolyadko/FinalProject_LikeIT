package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.validator.impl.LoginValidator;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */
public class LoginCommand extends ActionCommand {
    private static final String PARAM_LOGIN = "userLogin";
    private static final String PARAM_PASSWORD = "userPassword";

    private static final String SESSION_ATTR_USER = "userContainer";
    private static final String SESSION_ATTR_ERROR = "loginError";
    private static final String LOGIN_ERROR_INCORRECT = "error.incorrect";
    private static final String LOGIN_ERROR_INVALID = "error.invalid";


    public String execute(RequestContent content) throws CommandException {
        if (isInputDataValid(content)) {
            UserService userService = new UserService();

            try {
                String login = content.getRequestParameter(PARAM_LOGIN);
                String password = content.getRequestParameter(PARAM_PASSWORD);
                User user = userService.findUserWithCredentials(login, password);

                if (user != null) {
                    content.setSessionAttribute(SESSION_ATTR_USER, new ObjectMemoryContainer(user,
                            MemoryContainerType.LONG_LIVER));
                    return MappingManager.HOME_PAGE;
                }

                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(LOGIN_ERROR_INCORRECT));
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(LOGIN_ERROR_INVALID));
        }

        return MappingManager.LOGIN_PAGE;
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return LoginValidator.isLoginValid(content.getRequestParameter(PARAM_LOGIN)) &&
                LoginValidator.isPasswordValid(content.getRequestParameter(PARAM_PASSWORD));
    }
}