package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.CommentService;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 07.09.2016.
 */

/**
 * Command lets set a comment mark (authorized only)
 */
public class SetCommentMarkCommand extends SimpleActionCommand {
    private static final String PARAM_MARK = "mark";

    public SetCommentMarkCommand() {
        super("comment");
    }

    @Override
    protected void serviceCall(RequestContent content) throws CommandException {
        CommentService commentService = new CommentService();
        long commentId = Long.parseLong(content.getRequestParameter(paramId));
        int mark = Integer.parseInt(content.getRequestParameter(PARAM_MARK));

        try {
            commentService.setCommentMark(commentId, RequestContentUtil.getCurrentUserLogin(content), mark);
        } catch (ServiceException e) {
            throw new CommandException("Exception in SetCommentMarkCommand", e);
        }
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return Validator.isNumIdValid(content.getRequestParameter(paramId));
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        return RequestContentUtil.isAuthenticated(content);
    }
}