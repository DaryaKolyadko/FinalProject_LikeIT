package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.service.impl.CommentService;
import com.kolyadko.likeit.service.impl.QuestionService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

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
    public String execute(RequestContent content) {
        QuestionService questionService = new QuestionService();
        CommentService commentService = new CommentService();
        String questionId = content.getRequestParameter(PARAM_QUESTION);

        if (questionId != null) {
            try {
                String currentUserLogin = RequestContentUtil.getCurrentUserLogin(content);
                QuestionService.QuestionData data = questionService.findQuestionData(Integer.parseInt(questionId),
                        currentUserLogin);

                if (data != null) {
                    content.setRequestAttribute(ATTR_QUESTION, data);
                    content.setRequestAttribute(ATTR_COMMENTS,
                            commentService.findByQuestionId(data.getQuestion().getId(), currentUserLogin));
                    return super.execute(content);
                }
            } catch (ServiceException | NumberFormatException e) {
                LOG.error(e);
                content.setSessionAttribute(EXCEPTION, new ObjectMemoryContainer(e, MemoryContainerType.ONE_OFF));
                return MappingManager.getInstance().getProperty(MappingManager.ERROR_PAGE_500);
            }
        }

        content.setRequestAttribute(ATTR_SERVER_ERROR, QUESTION_ERROR_NO_SUCH);
        return super.execute(content);
    }
}