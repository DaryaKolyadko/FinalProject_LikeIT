package com.kolyadko.likeit.command.impl.actioncommand;


import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.type.GenderType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.ValidateUtil;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by DaryaKolyadko on 16.07.2016.
 */
public class SignUpCommand implements Command {
    private static final String PARAM_FIRST_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String PARAM_LOGIN = "userLogin";
    private static final String PARAM_EMAIL = "userEmail";
    private static final String PARAM_PASSWORD = "userPassword";
    private static final String PARAM_CONFIRM_PASSWORD = "userConfirmPassword";
    private static final String PARAM_BIRTHDAY = "birthday";
    private static final String PARAM_GENDER = "gender";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public String execute(RequestContent content) {
        if (isInputDataValid(content)) {
            try {
                UserService userService = new UserService();
                User user = new User(content.getRequestParameter(PARAM_LOGIN));
                user.setFirstName(content.getRequestParameter(PARAM_FIRST_NAME));
                user.setLastName(content.getRequestParameter(PARAM_LAST_NAME));
                user.setEmail(content.getRequestParameter(PARAM_EMAIL));
                user.setPassword(content.getRequestParameter(PARAM_PASSWORD));
                user.setBirthDate(new Date(DATE_FORMAT.parse(content.getRequestParameter(PARAM_BIRTHDAY)).getTime()));
                user.setGender(GenderType.getGenderType(content.getRequestParameter(PARAM_GENDER)));
                userService.create(user);
                content.setSessionAttribute("user", user);
                return MappingManager.HOME_PAGE;
            } catch (ServiceException | ParseException e) {
                content.setRequestAttribute("signUpError", "User with such login already exists.");
                LOG.error(e.getMessage());
            }
        } else {
            content.setRequestAttribute("signUpError", "Check your sign up form.");
            content.copyParamsToRequestAttributes();
        }

        // TODO MessageManager.getProperty("message.signUpError"));
        return MappingManager.SIGN_UP_PAGE;
    }

    private boolean isInputDataValid(RequestContent content) {
        return ValidateUtil.isLoginValid(content.getRequestParameter(PARAM_LOGIN)) &&
                ValidateUtil.isStringValid(content.getRequestParameter(PARAM_FIRST_NAME)) &&
                ValidateUtil.isStringValid(content.getRequestParameter(PARAM_LAST_NAME)) &&
                ValidateUtil.isEmailValid(content.getRequestParameter(PARAM_EMAIL)) &&
                ValidateUtil.isPasswordValid(content.getRequestParameter(PARAM_PASSWORD)) &&
                content.getRequestParameter(PARAM_PASSWORD).equals(content.getRequestParameter(PARAM_CONFIRM_PASSWORD)) &&
                ValidateUtil.isDateValid(content.getRequestParameter(PARAM_BIRTHDAY));
    }
}