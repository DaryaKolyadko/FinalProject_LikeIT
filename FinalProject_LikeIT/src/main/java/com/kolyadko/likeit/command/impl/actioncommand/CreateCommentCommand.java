package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Comment;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.uncompleteddata.UncompletedCommentMemoryContainer;
import com.kolyadko.likeit.service.impl.CommentService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.impl.CommentActionValidator;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 03.09.2016.
 */
public class CreateCommentCommand extends ActionCommand {
    private static final String PARAM_QUESTION_ID = "question";
    private static final String PARAM_TEXT = "text";

    private static final String SESSION_ATTR_UNCOMPLETED = "uncompleted";
    private static final String CREATE_COMMENT_ERROR_CHECK = "error.checkForm";
    private static final String CREATE_SUCCESS = "info.createComment.success";
    private static final String CREATE_PROBLEM = "info.createComment.problem";

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isAllowedAction(content)) {
            UncompletedCommentMemoryContainer container = initUncompletedComment(content);

            if (isInputDataValid(content)) {
                try {
                    CommentService commentService = new CommentService();
                    Comment comment = new Comment();
                    comment.setAuthorId(RequestContentUtil.getCurrentUserLogin(content));
                    comment.setQuestionId(Integer.parseInt(container.getQuestionId()));
                    comment.setText(container.getText());

                    if (commentService.create(comment)) {
                        content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(CREATE_SUCCESS, MemoryContainerType.ONE_OFF));
                    } else {
                        content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(CREATE_PROBLEM));
                        content.setSessionAttribute(SESSION_ATTR_UNCOMPLETED, container);
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(CREATE_COMMENT_ERROR_CHECK));
                content.setSessionAttribute(SESSION_ATTR_UNCOMPLETED, container);
            }

            return MappingManager.QUESTION_PAGE + RequestContentUtil.getParamAsQueryString(content,
                    PARAM_QUESTION_ID);
        }

        content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(NOT_ALLOWED));
        return MappingManager.HOME_PAGE;
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return Validator.isNumIdValid(content.getRequestParameter(PARAM_QUESTION_ID)) &&
                CommentActionValidator.isTextValid(content.getRequestParameter(PARAM_TEXT));
    }

    private UncompletedCommentMemoryContainer initUncompletedComment(RequestContent content) {
        UncompletedCommentMemoryContainer memoryContainer = new UncompletedCommentMemoryContainer();
        memoryContainer.setQuestionId(content.getRequestParameter(PARAM_QUESTION_ID));
        memoryContainer.setText(content.getRequestParameter(PARAM_TEXT));
        return memoryContainer;
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        return RequestContentUtil.isAuthenticated(content) && RequestContentUtil.isActive(content);
    }
}