package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.LocaleUtil;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 20.08.2016.
 */
public class ShowDefaultContentCommand extends ShowCommand {
    private static final String ATTR_LIST_TOP5 = "top5";
    private static final int TOP_LIMIT = 5;

    public ShowDefaultContentCommand(String path) {
        super(path);
    }

    @Override
    public String execute(RequestContent content) {
        QuestionService questionService = new QuestionService();

        try {
            content.setRequestAttribute(ATTR_LIST_TOP5, questionService.findTopQuestions(TOP_LIMIT));
        } catch (ServiceException e) {
            LOG.error(e);
            content.setSessionAttribute(EXCEPTION, new ObjectMemoryContainer(e, MemoryContainerType.ONE_OFF));
            return MappingManager.getInstance().getProperty(MappingManager.ERROR_PAGE_500);
        }

        LocaleUtil.changeLocaleIfNeeded(content);
        return super.execute(content);
    }
}