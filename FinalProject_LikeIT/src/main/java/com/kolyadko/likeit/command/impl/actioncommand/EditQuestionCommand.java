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
import com.kolyadko.likeit.validator.impl.QuestionActionValidator;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 29.08.2016.
 */
public class EditQuestionCommand extends ActionCommand {
    private static final String PARAM_QUESTION_ID = "question";
    private static final String PARAM_SECTION_ID = "sectionId";
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_TEXT = "text";

    private static final String SESSION_ATTR_UNCOMPLETED = "uncompleted";
    private static final String EDIT_QUESTION_ERROR_CHECK = "error.checkForm";
    private static final String EDIT_SUCCESS = "info.editQuestion.success";
    private static final String EDIT_PROBLEM = "info.editQuestion.problem";

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isAllowedAction(content)) {
            UncompletedQuestionMemoryContainer container = initUncompletedQuestion(content);

            if (isInputDataValid(content)) {
                try {
                    QuestionService questionService = new QuestionService();
                    int questionId = Integer.parseInt(content.getRequestParameter(PARAM_QUESTION_ID));
                    Question question = questionService.findById(questionId,
                            RequestContentUtil.isCurrentUserAdmin(content));

                    if (question != null) {
                        question.setSectionId(Integer.parseInt(container.getSectionId()));
                        question.setTitle(container.getTitle());
                        question.setText(container.getText());

                        if (questionService.updateQuestion(question)) {
                            content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(EDIT_SUCCESS,
                                    MemoryContainerType.ONE_OFF));
                        } else {
                            content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(EDIT_PROBLEM));
                        }

                        return MappingManager.QUESTION_PAGE + RequestContentUtil.getParamAsQueryString(content,
                                PARAM_QUESTION_ID);
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(EDIT_QUESTION_ERROR_CHECK));
            }

            content.setSessionAttribute(SESSION_ATTR_UNCOMPLETED, container);
            return MappingManager.EDIT_QUESTION_PAGE + RequestContentUtil.getParamAsQueryString(content,
                    PARAM_QUESTION_ID);
        } else {
            content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(NOT_ALLOWED));
        }

        return MappingManager.HOME_PAGE;
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return Validator.isNumIdValid(content.getRequestParameter(PARAM_SECTION_ID)) &&
                QuestionActionValidator.isTitleValid(content.getRequestParameter(PARAM_TITLE)) &&
                QuestionActionValidator.isTextValid(content.getRequestParameter(PARAM_TEXT));
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        try {
            QuestionService questionService = new QuestionService();
            String questionId = content.getRequestParameter(PARAM_QUESTION_ID);
            Question question = questionService.findById(Integer.valueOf(questionId));
            return question != null && allowedAction(content, question.getAuthorId());
        } catch (ServiceException e) {
            return false;
        }
    }

    private UncompletedQuestionMemoryContainer initUncompletedQuestion(RequestContent content) {
        return new UncompletedQuestionMemoryContainer(
                content.getRequestParameter(PARAM_SECTION_ID),
                content.getRequestParameter(PARAM_TITLE),
                content.getRequestParameter(PARAM_TEXT));
    }
}