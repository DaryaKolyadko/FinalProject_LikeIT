package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.ValidateUtil;

/**
 * Created by DaryaKolyadko on 15.07.2016.
 */
public class LoginCommand implements Command {
    private static final String PARAM_LOGIN = "userLogin";
    private static final String PARAM_PASSWORD = "userPassword";

    public String execute(RequestContent content) {
        if (isInputDataValid(content)) {
            UserService userService = new UserService();

            try {
                String login = content.getRequestParameter(PARAM_LOGIN);
                String password = content.getRequestParameter(PARAM_PASSWORD);
                User user = userService.findUserWithCredentials(login, password);

                if (user != null) {
                    content.setSessionAttribute("user", user);
                    return MappingManager.HOME_PAGE;
                } else {
                    content.setRequestAttribute("authError", "Incorrect login or password.");
                    //TODO MessageManager.getProperty("message.loginError"));
                }
            } catch (ServiceException e) {
                LOG.error(e);
                return MappingManager.ERROR_PAGE;
            }
        } else {
            content.setRequestAttribute("authError", "Invalid login or password.");
            //TODO MessageManager.getProperty("message.loginError"));
        }

        return MappingManager.LOGIN_PAGE;
    }

    private boolean isInputDataValid(RequestContent content) {
        return ValidateUtil.isLoginValid(content.getRequestParameter(PARAM_LOGIN)) &&
                ValidateUtil.isPasswordValid(content.getRequestParameter(PARAM_PASSWORD));
    }
}