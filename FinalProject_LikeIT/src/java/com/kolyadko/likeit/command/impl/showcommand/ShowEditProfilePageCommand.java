package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

/**
 * Created by DaryaKolyadko on 03.09.2016.
 */
public class ShowEditProfilePageCommand extends ShowDefaultContentCommand {
    private static final String PARAM_PROFILE = "login";
    private static final String ATTR_PROFILE = "profileToEdit";
    private static final String PROFILE_ERROR_NO_SUCH = "error.noSuchProfile";
    protected static final String SESSION_ATTR_ERROR = "actionError";

    public ShowEditProfilePageCommand() {
        super(MappingManager.EDIT_PROFILE_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        UserService userService = new UserService();

        try {
            String login = content.getRequestParameter(PARAM_PROFILE);
            boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
            User profile = userService.findById(login, isAdmin);

            if (profile != null) {
                Object afterError = content.getSessionAttribute(SESSION_ATTR_ERROR);

                if (afterError == null) {
                    content.setRequestAttribute(ATTR_PROFILE, profile);
                }
            } else {
                content.setRequestAttribute(ATTR_SERVER_ERROR, PROFILE_ERROR_NO_SUCH);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return super.execute(content);
    }
}