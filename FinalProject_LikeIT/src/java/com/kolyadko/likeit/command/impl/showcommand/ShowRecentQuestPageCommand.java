package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 13.08.2016.
 */
public class ShowRecentQuestPageCommand extends ShowQuestionListCommand {
    private static final String ATTR_LIST_TYPE_VAL = "recent";

    public ShowRecentQuestPageCommand() {
        super(MappingManager.RECENT_PAGE, ATTR_LIST_TYPE_VAL);
    }

    @Override
    public QuestionService.ReturnData serviceCall() {
        QuestionService questionService = new QuestionService();
        QuestionService.ReturnData data = null;

        try {
            data = questionService.findRecentQuestions();
        } catch (ServiceException e) {
            LOG.error(e);
        }

        return data;
    }
}