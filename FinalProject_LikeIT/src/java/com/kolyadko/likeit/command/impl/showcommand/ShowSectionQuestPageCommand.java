package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

/**
 * Created by DaryaKolyadko on 24.08.2016.
 */
public class ShowSectionQuestPageCommand extends ShowQuestionListCommand {
    private static final String ATTR_LIST_TYPE_VAL = "fromSection";
    private static final String PARAM_SECTION = "section";
    private static final String ATTR_SECTION = "currentSection";
    private static final String ATTR_ERROR = "sectionError";
    private static final String SECTION_ERROR_NO_SUCH = "error.noSuchSection";

    public ShowSectionQuestPageCommand() {
        super(MappingManager.QUESTIONS_PAGE, ATTR_LIST_TYPE_VAL);
    }

    @Override
    protected QuestionService.QuestionListData serviceCall(RequestContent content) {
        QuestionService questionService = new QuestionService();
        SectionService sectionService = new SectionService();
        QuestionService.QuestionListData data = null;

        try {
            String sectionName = content.getRequestParameter(PARAM_SECTION);
            boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
            Section section = sectionService.findByName(sectionName, isAdmin);

            if (section != null) {
                data = questionService.findQuestionsFromSection(sectionName, isAdmin);
                content.setRequestAttribute(ATTR_SECTION, section);
            }
            else {
                content.setRequestAttribute(ATTR_ERROR, SECTION_ERROR_NO_SUCH);
            }
        } catch (ServiceException e) {
            LOG.error(e);
        }

        return data;
    }
}
