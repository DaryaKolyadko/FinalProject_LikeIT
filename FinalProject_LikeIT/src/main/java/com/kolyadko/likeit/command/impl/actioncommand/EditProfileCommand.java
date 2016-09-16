package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.uncompleteddata.UncompletedUserMemoryContainer;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.type.GenderType;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.impl.SignUpValidator;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by DaryaKolyadko on 29.08.2016.
 */

/**
 * Command lets edit a user profile (current user only)
 */
public class EditProfileCommand extends ActionCommand {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_FIRST_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String PARAM_EMAIL = "userEmail";
    private static final String PARAM_BIRTHDAY = "birthDate";
    private static final String PARAM_GENDER = "gender";

    private static final String SESSION_ATTR_USER = "userContainer";
    private static final String SESSION_ATTR_UNCOMPLETED = "uncompleted";
    private static final String EDIT_SECTION_ERROR_CHECK = "error.checkForm";
    private static final String EDIT_SUCCESS = "info.editProfile.success";
    private static final String EDIT_PROBLEM = "info.editProfile.problem";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isAllowedAction(content)) {
            UncompletedUserMemoryContainer container = initUncompletedSection(content);

            if (isInputDataValid(content)) {
                try {
                    UserService userService = new UserService();
                    String login = content.getRequestParameter(PARAM_LOGIN);
                    User user = userService.findById(login, RequestContentUtil.isCurrentUserAdmin(content));

                    if (user != null) {
                        user.setFirstName(container.getFirstName());
                        user.setLastName(container.getLastName());
                        user.setEmail(container.getEmail());
                        user.setBirthDate(new Date(DATE_FORMAT.parse(container.getBirthDate()).getTime()));
                        user.setGender(GenderType.getGenderType(container.getGender()));

                        if (userService.updateUser(user)) {
                            content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(EDIT_SUCCESS, MemoryContainerType.ONE_OFF));
                        } else {
                            content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(EDIT_PROBLEM));
                        }

                        content.setSessionAttribute(SESSION_ATTR_USER, new ObjectMemoryContainer(user,
                                MemoryContainerType.LONG_LIVER));
                        return MappingManager.PROFILE_PAGE + RequestContentUtil.getParamAsQueryString(content,
                                PARAM_LOGIN);
                    }
                } catch (ServiceException | ParseException | IllegalArgumentException e) {
                    throw new CommandException("Exception in EditProfileCommand", e);
                }
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(EDIT_SECTION_ERROR_CHECK));
            }

            content.setSessionAttribute(SESSION_ATTR_UNCOMPLETED, container);
            return MappingManager.EDIT_PROFILE_PAGE + RequestContentUtil.getParamAsQueryString(content,
                    PARAM_LOGIN);
        } else {
            content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(NOT_ALLOWED));
        }

        return MappingManager.HOME_PAGE;
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return SignUpValidator.isStringValid(content.getRequestParameter(PARAM_FIRST_NAME)) &&
                SignUpValidator.isStringValid(content.getRequestParameter(PARAM_LAST_NAME)) &&
                SignUpValidator.isEmailValid(content.getRequestParameter(PARAM_EMAIL)) &&
                SignUpValidator.isDateValid(content.getRequestParameter(PARAM_BIRTHDAY)) &&
                SignUpValidator.isGenderValid(content.getRequestParameter(PARAM_GENDER));
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        return allowedAction(content, content.getRequestParameter(PARAM_LOGIN));
    }

    private UncompletedUserMemoryContainer initUncompletedSection(RequestContent content) {
        UncompletedUserMemoryContainer memoryContainer = new UncompletedUserMemoryContainer();
        memoryContainer.setFirstName(content.getRequestParameter(PARAM_FIRST_NAME));
        memoryContainer.setLastName(content.getRequestParameter(PARAM_LAST_NAME));
        memoryContainer.setEmail(content.getRequestParameter(PARAM_EMAIL));
        memoryContainer.setGender(content.getRequestParameter(PARAM_GENDER));
        memoryContainer.setBirthDate(content.getRequestParameter(PARAM_BIRTHDAY));
        return memoryContainer;
    }
}