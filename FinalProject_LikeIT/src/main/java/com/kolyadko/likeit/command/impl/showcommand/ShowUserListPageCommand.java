package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.dao.impl.UserDao;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.UserService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowUserListPageCommand extends ShowDefaultContentCommand {
    protected static final String PARAM_PAGE = "page";
    private static final String ATTR_USER_LIST = "userList";
    protected static final String ATTR_CURR_PAGE = "currentPage";
    protected static final String ATTR_NUM_OF_PAGES = "numOfPages";

    public ShowUserListPageCommand() {
        super(MappingManager.USER_LIST_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        UserService userService = new UserService();
        UserDao.UserListWrapper wrapper;

        try {
            String pageStr = content.getRequestParameter(PARAM_PAGE);
            int page = 1;

            if (pageStr != null) {
                page = Integer.parseInt(pageStr);
            }

            wrapper = userService.findAllUsers(page, RequestContentUtil.isCurrentUserAdmin(content));
            content.setRequestAttribute(ATTR_CURR_PAGE, page);
            content.setRequestAttribute(ATTR_NUM_OF_PAGES, wrapper.getPagesNumber());
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException(e);
        }

        content.setRequestAttribute(ATTR_USER_LIST, wrapper.getUserList());
        return super.execute(content);
    }
}