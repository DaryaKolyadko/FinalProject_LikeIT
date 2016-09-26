package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
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
 * Command lets restore a comment from archive (admin only)
 */
public class RestoreCommentCommand extends SimpleActionCommand {
    private static final String RESTORE_SUCCESS = "info.restoreComment.success";
    private static final String RESTORE_PROBLEM = "info.restoreComment.problem";
    private static final String PARAM_QUESTION = "question";

    public RestoreCommentCommand() {
        super("comment");
    }

    @Override
    protected void serviceCall(RequestContent content) throws CommandException {
        CommentService commentService = new CommentService();
        long commentId = Long.parseLong(content.getRequestParameter(paramId));

        try {
            if (commentService.restoreFromArchive(commentId)) {
                content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(RESTORE_SUCCESS,
                        MemoryContainerType.ONE_OFF));
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(RESTORE_PROBLEM));
            }

            resultPage = MappingManager.QUESTION_PAGE + RequestContentUtil.getParamAsQueryString(content,
                    PARAM_QUESTION);
        } catch (ServiceException e) {
            throw new CommandException("Exception in RestoreCommentCommand", e);
        }
    }
}