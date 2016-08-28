package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 24.08.2016.
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
    protected ArrayList<QuestionService.QuestionData> serviceCall(RequestContent content) {
        QuestionService questionService = new QuestionService();
        SectionService sectionService = new SectionService();
        ArrayList<QuestionService.QuestionData> dataList = null;

        try {
            String sectionName = content.getRequestParameter(PARAM_SECTION);
            boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
            Section section = sectionService.findById(Integer.parseInt(sectionName), isAdmin);

            if (section != null) {
                dataList = questionService.findQuestionsFromSection(section.getId(), isAdmin);
                content.setRequestAttribute(ATTR_SECTION, section);
            }
            else {
                content.setRequestAttribute(ATTR_SERVER_ERROR, SECTION_ERROR_NO_SUCH);
            }
        } catch (ServiceException e) {
            LOG.error(e);
            content.setSessionAttribute(EXCEPTION, new ObjectMemoryContainer(e, MemoryContainerType.ONE_OFF));
        }

        return dataList;
    }
}
