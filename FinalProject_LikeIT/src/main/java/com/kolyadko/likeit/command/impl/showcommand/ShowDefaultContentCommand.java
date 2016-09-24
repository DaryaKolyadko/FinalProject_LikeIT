package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.util.LocaleUtil;

/**
 * Created by DaryaKolyadko on 20.08.2016.
 */

/**
 * Command prepares RequestContent object to show any jsp page (fills it with default data such as
 * top5 questions)
 */
public class ShowDefaultContentCommand extends ShowCommand {
    private static final String ATTR_LIST_TOP5 = "top5";
    private static final int TOP_LIMIT = 5;

    public ShowDefaultContentCommand(String path) {
        super(path);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        QuestionService questionService = new QuestionService();

        try {
            content.setRequestAttribute(ATTR_LIST_TOP5, questionService.findTopQuestions(TOP_LIMIT));
        } catch (ServiceException e) {
            throw new CommandException("Exception in ShowDefaultContentPageCommand", e);
        }

        LocaleUtil.changeLocaleIfNeeded(content);
        return super.execute(content);
    }
}