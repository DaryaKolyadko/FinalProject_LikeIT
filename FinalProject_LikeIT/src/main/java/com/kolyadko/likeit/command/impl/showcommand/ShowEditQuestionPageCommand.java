package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.command.ShowEditPage;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Question;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 03.09.2016.
 */

/**
 * Command prepares RequestContent object to show editQuestion.jsp with data
 */
public class ShowEditQuestionPageCommand extends ShowDefaultContentCommand implements ShowEditPage {
    private static final String PARAM_QUESTION = "question";
    private static final String ATTR_QUESTION = "questionToEdit";
    private static final String ATTR_SECTIONS = "sections";
    private static final String QUESTION_ERROR_NO_SUCH = "error.noSuchQuestion";

    public ShowEditQuestionPageCommand() {
        super(MappingManager.EDIT_QUESTION_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isInputDataValid(content)) {
            if (isAllowedAction(content)) {
                QuestionService questionService = new QuestionService();
                SectionService sectionService = new SectionService();

                try {
                    content.setRequestAttribute(ATTR_SECTIONS, sectionService.findNotMajorSections());
                    String questionId = content.getRequestParameter(PARAM_QUESTION);
                    boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
                    Question question = questionService.findById(Long.parseLong(questionId), isAdmin);

                    if (question != null) {
                        setAsAttrIfNotAfterError(content, question, ATTR_QUESTION);
                    } else {
                        content.setRequestAttribute(ATTR_SERVER_ERROR, QUESTION_ERROR_NO_SUCH);
                    }
                } catch (ServiceException e) {
                    throw new CommandException("Exception in ShowEditQuestionPageCommand", e);
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
        return Validator.isNumIdValid(content.getRequestParameter(PARAM_QUESTION));
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        try {
            QuestionService questionService = new QuestionService();
            String questionId = content.getRequestParameter(PARAM_QUESTION);
            Question question = questionService.findById(Long.parseLong(questionId));
            return question != null && allowedAction(content, question.getAuthorId());
        } catch (ServiceException e) {
            return false;
        }
    }
}