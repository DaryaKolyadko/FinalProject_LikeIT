package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Comment;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.service.impl.CommentService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

/**
 * Created by DaryaKolyadko on 08.09.2016.
 */

/**
 * Command lets delete a comment (admin and comment's author only)
 */
public class DeleteCommentCommand extends SimpleActionCommand {
    private static final String DELETE_SUCCESS = "info.deleteComment.success";
    private static final String DELETE_PROBLEM = "info.deleteComment.problem";
    private static final String PARAM_QUESTION = "question";

    public DeleteCommentCommand() {
        super("comment");
    }

    @Override
    protected void serviceCall(RequestContent content) throws CommandException {
        CommentService commentService = new CommentService();
        int commentId = Integer.parseInt(content.getRequestParameter(paramId));

        try {
            if (commentService.moveCommentToArchive(commentId)) {
                content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(DELETE_SUCCESS,
                        MemoryContainerType.ONE_OFF));
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(DELETE_PROBLEM));
            }

            resultPage = MappingManager.QUESTION_PAGE + RequestContentUtil.getParamAsQueryString(content,
                    PARAM_QUESTION);
        } catch (ServiceException e) {
            throw new CommandException("Exception in DeleteCommentCommand", e);
        }
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        try {
            CommentService commentService = new CommentService();
            String commentId = content.getRequestParameter(paramId);
            Comment comment = commentService.findById(Integer.valueOf(commentId));
            return comment != null && allowedAction(content, comment.getAuthorId()) ||
                    super.isAllowedAction(content);
        } catch (ServiceException e) {
            return false;
        }
    }
}