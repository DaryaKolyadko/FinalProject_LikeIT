package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.CommentService;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 07.09.2016.
 */

/**
 * Command lets note a comment as an answer (question's author only)
 */
public class NoteAsAnswerCommand extends SimpleActionCommand {
    private static final String PARAM_ANSWER = "answer";
    private static final String PARAM_QUEST_AUTHOR = "questionAuthorId";

    public NoteAsAnswerCommand() {
        super("comment");
    }

    @Override
    protected void serviceCall(RequestContent content) throws CommandException {
        CommentService commentService = new CommentService();
        long commentId = Long.parseLong(content.getRequestParameter(paramId));
        boolean isAnswer = Boolean.parseBoolean(content.getRequestParameter(PARAM_ANSWER));

        try {
            commentService.noteAsAnswer(commentId, !isAnswer);
        } catch (ServiceException e) {
            throw new CommandException("Exception in NoteAsAnswerCommand", e);
        }
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return Validator.isBooleanValid(content.getRequestParameter(PARAM_ANSWER)) &&
                super.isInputDataValid(content);
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        return allowedAction(content, content.getRequestParameter(PARAM_QUEST_AUTHOR));
    }
}