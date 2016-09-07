package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Question;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

/**
 * Created by DaryaKolyadko on 03.09.2016.
 */
public class ShowEditQuestionPageCommand extends ShowDefaultContentCommand {
    private static final String PARAM_QUESTION = "question";
    private static final String ATTR_QUESTION = "questionToEdit";
    private static final String ATTR_SECTIONS = "sections";
    private static final String QUESTION_ERROR_NO_SUCH = "error.noSuchQuestion";
    protected static final String SESSION_ATTR_ERROR = "actionError";

    public ShowEditQuestionPageCommand() {
        super(MappingManager.EDIT_QUESTION_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        QuestionService questionService = new QuestionService();
        SectionService sectionService = new SectionService();

        try {
            content.setRequestAttribute(ATTR_SECTIONS, sectionService.findNotMajorSections());
            String questionId = content.getRequestParameter(PARAM_QUESTION);
            boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
            Question question = questionService.findById(Integer.parseInt(questionId), isAdmin);

            if (question != null) {
                Object afterError = content.getSessionAttribute(SESSION_ATTR_ERROR);

                if (afterError == null) {
                    content.setRequestAttribute(ATTR_QUESTION, question);
                }
            } else {
                content.setRequestAttribute(ATTR_SERVER_ERROR, QUESTION_ERROR_NO_SUCH);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return super.execute(content);
    }
}