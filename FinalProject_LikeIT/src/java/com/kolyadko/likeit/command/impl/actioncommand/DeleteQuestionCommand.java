package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
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
public class DeleteQuestionCommand extends SimpleActionCommand {
    private static final String DELETE_SUCCESS = "info.deleteQuestion.success";
    private static final String DELETE_PROBLEM = "info.deleteQuestion.problem";
    private static final String PARAM_AUTHOR = "authorId";

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
            throw new CommandException(e);
        }
    }

    @Override
    protected boolean isAllowedAction(RequestContent content) {
        return allowedAction(content, content.getRequestParameter(PARAM_AUTHOR)) ||
                super.isAllowedAction(content);
    }
}