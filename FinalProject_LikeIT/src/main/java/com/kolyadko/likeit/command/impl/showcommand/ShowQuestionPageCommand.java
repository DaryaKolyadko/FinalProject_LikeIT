package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.dao.impl.QuestionDao;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.CommentService;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 27.08.2016.
 */
public class ShowQuestionPageCommand extends ShowDefaultContentCommand {
    private static final String PARAM_QUESTION = "question";
    private static final String ATTR_QUESTION = "questionData";
    private static final String ATTR_COMMENTS = "commentsData";
    private static final String QUESTION_ERROR_NO_SUCH = "error.noSuchQuestion";

    public ShowQuestionPageCommand() {
        super(MappingManager.QUESTION_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isInputDataValid(content)) {
            QuestionService questionService = new QuestionService();
            CommentService commentService = new CommentService();
            String questionId = content.getRequestParameter(PARAM_QUESTION);

            if (questionId != null) {
                try {
                    String currentUserLogin = RequestContentUtil.getCurrentUserLogin(content);
                    boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
                    QuestionDao.QuestionData data = questionService.findQuestionData(Integer.parseInt(questionId),
                            isAdmin, currentUserLogin);

                    if (data != null) {
                        content.setRequestAttribute(ATTR_QUESTION, data);
                        content.setRequestAttribute(ATTR_COMMENTS,
                                commentService.findByQuestionId(data.getQuestion().getId(), currentUserLogin, isAdmin));
                        return super.execute(content);
                    }
                } catch (ServiceException | NumberFormatException e) {
                    throw new CommandException("Exception in ShowQuestionPageCommand", e);
                }
            }

            content.setRequestAttribute(ATTR_SERVER_ERROR, QUESTION_ERROR_NO_SUCH);
        } else {
            content.setRequestAttribute(ATTR_SERVER_ERROR, CHECK_INPUT_DATA);
        }
        return super.execute(content);
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return Validator.isNumIdValid(content.getRequestParameter(PARAM_QUESTION));
    }
}