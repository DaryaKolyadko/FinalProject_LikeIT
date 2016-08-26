package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.validator.LoginValidator;

/**
 * Created by DaryaKolyadko on 30.07.2016.
 */
public class ShowProfilePageCommand extends ShowDefaultContentCommand {
    private static final String PARAM_PROFILE_LOGIN = "login";
    private static final String ATTR_PROFILE = "profile";
    private static final String SESSION_ATTR_USER = "userContainer";
    private static final String ATTR_ERROR = "profileError";
    private static final String PROFILE_ERROR_NO_SUCH = "error.noSuchProfile";

    public ShowProfilePageCommand() {
        super(MappingManager.PROFILE_PAGE);
    }

    @Override
    public String execute(RequestContent content) {
        UserService userService = new UserService();
        String profileLogin = content.getRequestParameter(PARAM_PROFILE_LOGIN);
        ObjectMemoryContainer user = (ObjectMemoryContainer) content.getSessionAttribute(SESSION_ATTR_USER);
        User currentUser = null;

        if (user != null) {
            currentUser = (User) user.getObject();
        }

        if (LoginValidator.isLoginValid(profileLogin)) {
            try {
                User userProfile = userService.findById(profileLogin);

                if (userProfile != null && (!userProfile.isAdmin() || currentUser != null && currentUser.isAdmin())) {
                    content.setRequestAttribute(ATTR_PROFILE, userProfile);
//                    content.setSessionAttribute(ATTR_PROFILE, new ObjectMemoryContainer(userProfile, MemoryContainerType.ONE_OFF));
                    return super.execute(content);
                }
            } catch (ServiceException e) {
                LOG.error(e);
            }
        }

        content.setRequestAttribute(ATTR_ERROR, PROFILE_ERROR_NO_SUCH);
        return super.execute(content);
    }
}