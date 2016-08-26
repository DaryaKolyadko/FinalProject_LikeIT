package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 13.08.2016.
 */
public class ShowTopQuestPageCommand extends ShowQuestionListCommand {
    private static final String ATTR_LIST_TYPE_VAL = "top";

    public ShowTopQuestPageCommand() {
        super(MappingManager.TOP_PAGE, ATTR_LIST_TYPE_VAL);
    }

    @Override
    public QuestionService.QuestionListData serviceCall(RequestContent content) {
        QuestionService questionService = new QuestionService();
        QuestionService.QuestionListData data = null;

        try {
            data = questionService.findTopQuestions();
        } catch (ServiceException e) {
            LOG.error(e);
        }

        return data;
    }
}