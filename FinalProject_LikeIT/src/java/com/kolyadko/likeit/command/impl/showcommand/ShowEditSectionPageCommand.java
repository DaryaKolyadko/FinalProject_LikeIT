package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowEditSectionPageCommand extends ShowDefaultContentCommand {
    private static final String PARAM_SECTION = "section";
    private static final String ATTR_SECTION = "sectionToEdit";
    private static final String SECTION_ERROR_NO_SUCH = "error.noSuchSection";
    protected static final String SESSION_ATTR_ERROR = "actionError";

    public ShowEditSectionPageCommand() {
        super(MappingManager.EDIT_SECTION_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        SectionService sectionService = new SectionService();

        try {
            String sectionId = content.getRequestParameter(PARAM_SECTION);
            boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
            Section section = sectionService.findById(Integer.parseInt(sectionId), isAdmin);

            if (section != null) {
                Object afterError = content.getSessionAttribute(SESSION_ATTR_ERROR);

                if (afterError == null) {
                    content.setRequestAttribute(ATTR_SECTION, section);
                }
            } else {
                content.setRequestAttribute(ATTR_SERVER_ERROR, SECTION_ERROR_NO_SUCH);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return super.execute(content);
    }
}