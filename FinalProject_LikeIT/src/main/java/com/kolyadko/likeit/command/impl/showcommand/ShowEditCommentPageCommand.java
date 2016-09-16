package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.command.ShowEditPage;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Comment;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.CommentService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 03.09.2016.
 */
public class ShowEditCommentPageCommand extends ShowDefaultContentCommand implements ShowEditPage {
    private static final String PARAM_COMMENT_ID = "comment";
    private static final String ATTR_COMMENT = "commentToEdit";
    private static final String COMMENT_ERROR_NO_SUCH = "error.noSuchComment";

    public ShowEditCommentPageCommand() {
        super(MappingManager.EDIT_COMMENT_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isInputDataValid(content)) {
            if (isAllowedAction(content)) {
                CommentService commentService = new CommentService();

                try {
                    String commentId = content.getRequestParameter(PARAM_COMMENT_ID);
                    boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
                    Comment comment = commentService.findById(Integer.parseInt(commentId), isAdmin);

                    if (comment != null) {
                        setAsAttrIfNotAfterError(content, comment, ATTR_COMMENT);
                    } else {
                        content.setRequestAttribute(ATTR_SERVER_ERROR, COMMENT_ERROR_NO_SUCH);
                    }
                } catch (ServiceException e) {
                    throw new CommandException("Exception in ShowEditCommentPageCommand", e);
                }
            } else {
                content.setRequestAttribute(ATTR_SERVER_ERROR, NOT_ALLOWED);
            }
        } else {
            content.setRequestAttribute(ATTR_SERVER_ERROR, CHECK_INPUT_DATA);
        }

        return super.execute(content);
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return Validator.isNumIdValid(content.getRequestParameter(PARAM_COMMENT_ID));
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        try {
            CommentService commentService = new CommentService();
            String commentId = content.getRequestParameter(PARAM_COMMENT_ID);
            Comment comment = commentService.findById(Integer.parseInt(commentId));
            return comment != null && allowedAction(content, comment.getAuthorId());
        } catch (ServiceException e) {
            return false;
        }
    }
}