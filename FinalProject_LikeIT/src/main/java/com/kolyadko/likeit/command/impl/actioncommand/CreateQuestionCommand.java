package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Question;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.uncompleteddata.UncompletedQuestionMemoryContainer;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.Validator;
import com.kolyadko.likeit.validator.impl.QuestionActionValidator;

/**
 * Created by DaryaKolyadko on 26.08.2016.
 */

/**
 * Command lets create a question (authorized users only)
 */
public class CreateQuestionCommand extends ActionCommand {
    private static final String PARAM_SECTION_ID = "sectionId";
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_TEXT = "text";

    private static final String SESSION_ATTR_UNCOMPLETED = "uncompleted";
    private static final String CREATE_QUESTION_ERROR_CHECK = "error.checkForm";
    private static final String CREATE_SUCCESS = "info.createQuestion.success";
    private static final String CREATE_PROBLEM = "info.createQuestion.problem";

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isAllowedAction(content)) {
            UncompletedQuestionMemoryContainer container = initUncompletedQuestion(content);

            if (isInputDataValid(content)) {
                try {
                    QuestionService questionService = new QuestionService();
                    Question question = new Question();
                    question.setAuthorId(RequestContentUtil.getCurrentUserLogin(content));
                    question.setSectionId(Integer.parseInt(container.getSectionId()));
                    question.setTitle(container.getTitle());
                    question.setText(container.getText());

                    if (questionService.create(question)) {
                        content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(CREATE_SUCCESS, MemoryContainerType.ONE_OFF));
                        return MappingManager.HOME_PAGE;
                    } else {
                        content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(CREATE_PROBLEM));
                    }
                } catch (ServiceException e) {
                    throw new CommandException("Exception in CreateQuestionCommand", e);
                }
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(CREATE_QUESTION_ERROR_CHECK));
            }

            content.setSessionAttribute(SESSION_ATTR_UNCOMPLETED, container);
            return MappingManager.CREATE_QUESTION_PAGE;
        }

        content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(NOT_ALLOWED));
        return MappingManager.HOME_PAGE;
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return Validator.isNumIdValid(content.getRequestParameter(PARAM_SECTION_ID)) &&
                QuestionActionValidator.isTitleValid(content.getRequestParameter(PARAM_TITLE)) &&
                QuestionActionValidator.isTextValid(content.getRequestParameter(PARAM_TEXT));
    }

    private UncompletedQuestionMemoryContainer initUncompletedQuestion(RequestContent content) {
        return new UncompletedQuestionMemoryContainer(
                content.getRequestParameter(PARAM_SECTION_ID),
                content.getRequestParameter(PARAM_TITLE),
                content.getRequestParameter(PARAM_TEXT));
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        return RequestContentUtil.isAuthenticated(content) && RequestContentUtil.isActive(content);
    }
}