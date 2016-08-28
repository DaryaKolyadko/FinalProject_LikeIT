package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;

import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 13.08.2016.
 */
public class ShowTopQuestPageCommand extends ShowQuestionListCommand {
    private static final String ATTR_LIST_TYPE_VAL = "top";

    public ShowTopQuestPageCommand() {
        super(MappingManager.TOP_PAGE, ATTR_LIST_TYPE_VAL);
    }

    @Override
    public ArrayList<QuestionService.QuestionData> serviceCall(RequestContent content) {
        QuestionService questionService = new QuestionService();
        ArrayList<QuestionService.QuestionData> dataList = null;

        try {
            dataList = questionService.findTopQuestions();
        } catch (ServiceException e) {
            LOG.error(e);
            content.setSessionAttribute(EXCEPTION, new ObjectMemoryContainer(e, MemoryContainerType.ONE_OFF));
        }

        return dataList;
    }
}