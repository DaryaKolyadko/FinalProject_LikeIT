package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Question;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

/**
 * Created by DaryaKolyadko on 31.08.2016.
 */

/**
 * Command lets delete a question (admin and question's author only)
 */
public class DeleteQuestionCommand extends SimpleActionCommand {
    private static final String DELETE_SUCCESS = "info.deleteQuestion.success";
    private static final String DELETE_PROBLEM = "info.deleteQuestion.problem";

    public DeleteQuestionCommand() {
        super("question");
    }

    @Override
    protected void serviceCall(RequestContent content) throws CommandException {
        QuestionService questionService = new QuestionService();
        int sectionId = Integer.parseInt(content.getRequestParameter(paramId));

        try {
            if (questionService.moveQuestionToArchive(sectionId)) {
                content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(DELETE_SUCCESS,
                        MemoryContainerType.ONE_OFF));

                if (RequestContentUtil.isCurrentUserAdmin(content)) {
                    resultPage = MappingManager.QUESTION_PAGE + RequestContentUtil.getParamAsQueryString(content,
                            paramId);
                }
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(DELETE_PROBLEM));
                resultPage = MappingManager.QUESTION_PAGE + RequestContentUtil.getParamAsQueryString(content,
                        paramId);
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in DeleteQuestionCommand", e);
        }
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        try {
            QuestionService questionService = new QuestionService();
            String questionId = content.getRequestParameter(paramId);
            Question question = questionService.findById(Integer.valueOf(questionId));
            return question != null && allowedAction(content, question.getAuthorId()) ||
                    super.isAllowedAction(content);
        } catch (ServiceException e) {
            return false;
        }
    }
}