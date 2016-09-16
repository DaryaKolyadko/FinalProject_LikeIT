package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.command.ShowEditPage;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.impl.LoginValidator;

/**
 * Created by DaryaKolyadko on 03.09.2016.
 */
public class ShowEditProfilePageCommand extends ShowDefaultContentCommand implements ShowEditPage {
    private static final String PARAM_PROFILE = "login";
    private static final String ATTR_PROFILE = "profileToEdit";
    private static final String PROFILE_ERROR_NO_SUCH = "error.noSuchProfile";

    public ShowEditProfilePageCommand() {
        super(MappingManager.EDIT_PROFILE_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isInputDataValid(content)) {
            if (isAllowedAction(content)) {
                UserService userService = new UserService();

                try {
                    String login = content.getRequestParameter(PARAM_PROFILE);
                    boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
                    User profile = userService.findById(login, isAdmin);

                    if (profile != null) {
                        setAsAttrIfNotAfterError(content, profile, ATTR_PROFILE);
                    } else {
                        content.setRequestAttribute(ATTR_SERVER_ERROR, PROFILE_ERROR_NO_SUCH);
                    }
                } catch (ServiceException e) {
                    throw new CommandException("Exception in ShowEditProfilePageCommand", e);
                }
            } else {
                content.setRequestAttribute(ATTR_SERVER_ERROR, NOT_ALLOWED);
            }
        } else {
            content.setRequestAttribute(ATTR_SERVER_ERROR, CHECK_INPUT_DATA);
        }

        return super.execute(content);
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return LoginValidator.isLoginValid(content.getRequestParameter(PARAM_PROFILE));
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        return allowedAction(content, content.getRequestParameter(PARAM_PROFILE));
    }
}