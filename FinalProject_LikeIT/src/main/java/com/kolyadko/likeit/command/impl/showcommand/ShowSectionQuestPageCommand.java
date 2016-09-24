package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.dao.impl.QuestionDao;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 24.08.2016.
 */

/**
 * Command prepares RequestContent object to show question.jsp with questions from particular section data
 */
public class ShowSectionQuestPageCommand extends ShowQuestionListCommand {
    private static final String ATTR_LIST_TYPE_VAL = "fromSection";
    private static final String PARAM_SECTION = "section";
    private static final String ATTR_SECTION = "currentSection";
    private static final String SECTION_ERROR_NO_SUCH = "error.noSuchSection";

    public ShowSectionQuestPageCommand() {
        super(MappingManager.QUESTIONS_PAGE, ATTR_LIST_TYPE_VAL);
    }

    @Override
    protected QuestionDao.QuestionListWrapper serviceCall(RequestContent content, int page) throws CommandException {
        QuestionService questionService = new QuestionService();
        SectionService sectionService = new SectionService();
        QuestionDao.QuestionListWrapper dataList = null;

        if (isInputDataValid(content)) {
            try {
                String sectionId = content.getRequestParameter(PARAM_SECTION);
                boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
                Section section = sectionService.findById(Integer.parseInt(sectionId), isAdmin);

                if (section != null) {
                    dataList = questionService.findQuestionsFromSection(section.getId(), page, isAdmin);
                    content.setRequestAttribute(ATTR_SECTION, section);
                } else {
                    content.setRequestAttribute(ATTR_SERVER_ERROR, SECTION_ERROR_NO_SUCH);
                }
            } catch (ServiceException | NumberFormatException e) {
                throw new CommandException("Exception in ShowSectionQuestPageCommand", e);
            }
        } else {
            content.setRequestAttribute(ATTR_SERVER_ERROR, CHECK_INPUT_DATA);
        }

        return dataList;
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return Validator.isNumIdValid(content.getRequestParameter(PARAM_SECTION));
    }
}
