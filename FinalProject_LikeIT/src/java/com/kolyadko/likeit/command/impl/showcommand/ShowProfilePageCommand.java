package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.User;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.ValidateUtil;

/**
 * Created by DaryaKolyadko on 30.07.2016.
 */
public class ShowProfilePageCommand extends ShowCommand {
    private static final String PARAM_PROFILE_LOGIN = "login";
    private static final String ATTR_PROFILE = "profile";
    private static final String SESSION_ATTR_USER = "user";
    private static final String ATTR_ERROR = "noSuchProfileError";

    public ShowProfilePageCommand() {
        super(MappingManager.PROFILE_PAGE);
    }

    @Override
    public String execute(RequestContent content) {
        UserService userService = new UserService();
        String profileLogin = content.getRequestParameter(PARAM_PROFILE_LOGIN);
        User currentUser = (User) content.getSessionAttribute(SESSION_ATTR_USER);

        if (ValidateUtil.isLoginValid(profileLogin)) {
            if (currentUser != null && profileLogin.equals(currentUser.getId())) {
                content.setRequestAttribute(ATTR_PROFILE, content.getSessionAttribute(SESSION_ATTR_USER));
                return super.execute(content);
            } else {
                try {
                    User userProfile = userService.findById(profileLogin);

                    if (userProfile != null && (!userProfile.isAdmin() || currentUser != null && currentUser.isAdmin())) {
                        content.setRequestAttribute(ATTR_PROFILE, userProfile);
                        return super.execute(content);
                    }
                } catch (ServiceException e) {
                    LOG.error(e);
                }
            }
        }

        content.setRequestAttribute(ATTR_ERROR, "No such profile");
        //TODO MessageManager.getProperty("message.loginError"));

        return super.execute(content);
    }
}