package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */

/**
 * Command prepares RequestContent object to show createQuestion.jsp with data
 */
public class ShowCreateQuestionPageCommand extends ShowDefaultContentCommand {
    private static final String ATTR_SECTIONS = "sections";

    public ShowCreateQuestionPageCommand() {
        super(MappingManager.CREATE_QUESTION_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isAllowedAction(content)) {
            SectionService sectionService = new SectionService();

            try {
                content.setRequestAttribute(ATTR_SECTIONS, sectionService.findNotMajorSections());
            } catch (ServiceException e) {
                throw new CommandException("Exception in ShowCreateQuestionPageCommand", e);
            }
        } else {
            content.setRequestAttribute(ATTR_SERVER_ERROR, NOT_ALLOWED);
        }

        return super.execute(content);
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        return RequestContentUtil.isAuthenticated(content) && RequestContentUtil.isActive(content);
    }
}