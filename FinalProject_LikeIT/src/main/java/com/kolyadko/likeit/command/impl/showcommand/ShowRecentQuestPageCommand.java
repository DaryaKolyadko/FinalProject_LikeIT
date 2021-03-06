package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.dao.impl.QuestionDao;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 13.08.2016.
 */

/**
 * Command prepares RequestContent object to show question.jsp with recent questions data
 */
public class ShowRecentQuestPageCommand extends ShowQuestionListCommand {
    private static final String ATTR_LIST_TYPE_VAL = "recent";

    public ShowRecentQuestPageCommand() {
        super(MappingManager.RECENT_PAGE, ATTR_LIST_TYPE_VAL);
    }

    @Override
    public QuestionDao.QuestionListWrapper serviceCall(RequestContent content, int page) throws CommandException {
        QuestionService questionService = new QuestionService();

        try {
            return questionService.findRecentQuestions(page);
        } catch (ServiceException e) {
            throw new CommandException("Exception in ShowRecentQuestPageCommand", e);
        }
    }
}