package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.util.MappingManager;

import java.util.ArrayList;

/**
 * Created by DaryaKolyadko on 13.08.2016.
 */
public abstract class ShowQuestionListCommand extends ShowDefaultContentCommand {
    protected static final String ATTR_LIST_TYPE = "listType";
    protected static final String ATTR_QUEST_DATA_LIST = "questionDataList";

    private String ATTR_LIST_TYPE_VAL;

    public ShowQuestionListCommand(String path, String listType) {
        super(path);
        this.ATTR_LIST_TYPE_VAL = listType;
    }

    protected abstract ArrayList<QuestionService.QuestionData> serviceCall(RequestContent content);

    @Override
    public String execute(RequestContent content) {
        content.setRequestAttribute(ATTR_LIST_TYPE, ATTR_LIST_TYPE_VAL);
        ArrayList<QuestionService.QuestionData> dataList = serviceCall(content);

        if (dataList == null) {
            return MappingManager.getInstance().getProperty(MappingManager.ERROR_PAGE_500);
        }

        content.setRequestAttribute(ATTR_QUEST_DATA_LIST, dataList);
        return super.execute(content);
    }
}