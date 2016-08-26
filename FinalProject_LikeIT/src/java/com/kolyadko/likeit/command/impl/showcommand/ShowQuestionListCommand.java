package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.type.MemoryContainerType;

/**
 * Created by DaryaKolyadko on 13.08.2016.
 */
public abstract class ShowQuestionListCommand extends ShowDefaultContentCommand {
    protected static final String ATTR_LIST_TYPE = "listType";
    protected static final String ATTR_QUEST_LIST = "questionsList";
    protected static final String ATTR_SECTION_LIST = "sectionsList";
    protected static final String ATTR_AUTHORS_LIST = "authorsList";

    private String ATTR_LIST_TYPE_VAL;

    public ShowQuestionListCommand(String path, String listType) {
        super(path);
        this.ATTR_LIST_TYPE_VAL = listType;
    }

    protected abstract QuestionService.QuestionListData serviceCall(RequestContent content);

    @Override
    public String execute(RequestContent content) {
        content.setSessionAttribute(ATTR_LIST_TYPE, new TextMemoryContainer(ATTR_LIST_TYPE_VAL,
                MemoryContainerType.ONE_OFF));
        QuestionService.QuestionListData data = serviceCall(content);

        if (data != null) {
            content.setRequestAttribute(ATTR_QUEST_LIST, data.getQuestions());
            content.setRequestAttribute(ATTR_SECTION_LIST, data.getSections());
            content.setRequestAttribute(ATTR_AUTHORS_LIST, data.getUsers());
        }

        return super.execute(content);
    }
}