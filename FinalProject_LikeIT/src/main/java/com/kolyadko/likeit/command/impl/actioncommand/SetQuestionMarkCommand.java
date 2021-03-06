package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 07.09.2016.
 */

/**
 * Command lets set a question mark (authorized only)
 */
public class SetQuestionMarkCommand extends SimpleActionCommand {
    private static final String PARAM_MARK = "mark";

    public SetQuestionMarkCommand() {
        super("question");
    }

    @Override
    protected void serviceCall(RequestContent content) throws CommandException {
        QuestionService questionService = new QuestionService();
        long questionId = Long.parseLong(content.getRequestParameter(paramId));
        int mark = Integer.parseInt(content.getRequestParameter(PARAM_MARK));

        try {
            questionService.setQuestionMark(questionId, RequestContentUtil.getCurrentUserLogin(content), mark);
        } catch (ServiceException e) {
            throw new CommandException("Exception in SetQuestionMarkCommand", e);
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