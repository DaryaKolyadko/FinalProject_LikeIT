package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.dao.impl.QuestionDao;
import com.kolyadko.likeit.exception.CommandException;

/**
 * Created by DaryaKolyadko on 13.08.2016.
 */

/**
 * Command prepares RequestContent object to show question.jsp with data
 * This jsp is used to show different data: top questions, recent questions and question from particular
 * section
 */
public abstract class ShowQuestionListCommand extends ShowDefaultContentCommand {
    /**
     * Request parameter - page to show
     */
    protected static final String PARAM_PAGE = "page";
    /**
     * Request attribute - page which is displayed
     */
    protected static final String ATTR_CURR_PAGE = "currentPage";
    /**
     * Request attribute - number of all pages
     */
    protected static final String ATTR_NUM_OF_PAGES = "numOfPages";
    /**
     * Request attribute - list type: top questions, recent questions and question from particular section
     */
    protected static final String ATTR_LIST_TYPE = "listType";
    /**
     * Request attribute - question list data
     */
    protected static final String ATTR_QUEST_DATA_LIST = "questionDataList";

    private String ATTR_LIST_TYPE_VAL;

    public ShowQuestionListCommand(String path, String listType) {
        super(path);
        this.ATTR_LIST_TYPE_VAL = listType;
    }

    protected abstract QuestionDao.QuestionListWrapper serviceCall(RequestContent content, int page)
            throws CommandException;

    @Override
    public String execute(RequestContent content) throws CommandException {
        content.setRequestAttribute(ATTR_LIST_TYPE, ATTR_LIST_TYPE_VAL);
        QuestionDao.QuestionListWrapper wrapper;

        try {
            String pageStr = content.getRequestParameter(PARAM_PAGE);
            int page = 1;

            if (pageStr != null) {
                page = Integer.parseInt(pageStr);
            }

            wrapper = serviceCall(content, page);
            content.setRequestAttribute(ATTR_CURR_PAGE, page);
            content.setRequestAttribute(ATTR_NUM_OF_PAGES, wrapper.getPagesNumber());
        } catch (CommandException | NumberFormatException e) {
            throw new CommandException("Exception in ShowQuestionListPageCommand", e);
        }

        content.setRequestAttribute(ATTR_QUEST_DATA_LIST, wrapper.getQuestionList());
        return super.execute(content);
    }
}