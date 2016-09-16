package com.kolyadko.likeit.command.impl.actioncommand;


import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.uncompleteddata.UncompletedUserMemoryContainer;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.type.GenderType;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.validator.impl.LoginValidator;
import com.kolyadko.likeit.validator.impl.SignUpValidator;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by DaryaKolyadko on 16.07.2016.
 */

/**
 * Command lets sign up into system
 */
public class SignUpCommand extends ActionCommand {
    private static final String PARAM_FIRST_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String PARAM_LOGIN = "userLogin";
    private static final String PARAM_EMAIL = "userEmail";
    private static final String PARAM_PASSWORD = "userPassword";
    private static final String PARAM_CONFIRM_PASSWORD = "userConfirmPassword";
    private static final String PARAM_BIRTHDAY = "birthDate";
    private static final String PARAM_GENDER = "gender";

    private static final String SESSION_ATTR_USER = "userContainer";
    private static final String SESSION_ATTR_ERROR = "signUpError";
    private static final String SESSION_ATTR_UNCOMPLETED = "uncompleted";
    private static final String SIGN_UP_ERROR_EXISTS = "error.userExists";
    private static final String SIGN_UP_ERROR_CHECK = "error.checkForm";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public String execute(RequestContent content) throws CommandException {
        UncompletedUserMemoryContainer container = initUncompletedUser(content);

        if (isInputDataValid(content)) {
            try {
                UserService userService = new UserService();
                User user = new User(container.getLogin());

                if (!userService.isLoginInUse(user.getId())) {
                    user.setFirstName(container.getFirstName());
                    user.setLastName(container.getLastName());
                    user.setEmail(container.getEmail());
                    user.setPassword(container.getPassword());
                    user.setBirthDate(new Date(DATE_FORMAT.parse(container.getBirthDate()).getTime()));
                    user.setGender(GenderType.getGenderType(container.getGender()));
                    userService.create(user);
                    content.setSessionAttribute(SESSION_ATTR_USER, new ObjectMemoryContainer(user,
                            MemoryContainerType.LONG_LIVER));
                    return MappingManager.HOME_PAGE;
                } else {
                    content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(SIGN_UP_ERROR_EXISTS));
                }
            } catch (ServiceException | ParseException | IllegalArgumentException e) {
                throw new CommandException("Exception in SignUpCommand", e);
            }
        } else {
            content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(SIGN_UP_ERROR_CHECK));
        }

        content.setSessionAttribute(SESSION_ATTR_UNCOMPLETED, container);
        return MappingManager.SIGN_UP_PAGE;
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return LoginValidator.isLoginValid(content.getRequestParameter(PARAM_LOGIN)) &&
                SignUpValidator.isStringValid(content.getRequestParameter(PARAM_FIRST_NAME)) &&
                SignUpValidator.isStringValid(content.getRequestParameter(PARAM_LAST_NAME)) &&
                SignUpValidator.isEmailValid(content.getRequestParameter(PARAM_EMAIL)) &&
                SignUpValidator.isGenderValid(content.getRequestParameter(PARAM_GENDER)) &&
                LoginValidator.isPasswordValid(content.getRequestParameter(PARAM_PASSWORD)) &&
                content.getRequestParameter(PARAM_PASSWORD).equals(content.getRequestParameter(PARAM_CONFIRM_PASSWORD)) &&
                SignUpValidator.isDateValid(content.getRequestParameter(PARAM_BIRTHDAY));
    }

    private UncompletedUserMemoryContainer initUncompletedUser(RequestContent content) {
        UncompletedUserMemoryContainer memoryContainer = new UncompletedUserMemoryContainer();
        memoryContainer.setLogin(content.getRequestParameter(PARAM_LOGIN));
        memoryContainer.setFirstName(content.getRequestParameter(PARAM_FIRST_NAME));
        memoryContainer.setLastName(content.getRequestParameter(PARAM_LAST_NAME));
        memoryContainer.setEmail(content.getRequestParameter(PARAM_EMAIL));
        memoryContainer.setGender(content.getRequestParameter(PARAM_GENDER));
        memoryContainer.setPassword(content.getRequestParameter(PARAM_PASSWORD));
        memoryContainer.setBirthDate(content.getRequestParameter(PARAM_BIRTHDAY));
        return memoryContainer;
    }
}