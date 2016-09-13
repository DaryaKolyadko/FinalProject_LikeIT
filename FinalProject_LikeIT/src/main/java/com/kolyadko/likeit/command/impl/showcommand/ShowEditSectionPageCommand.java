package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.command.ShowEditPage;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.type.RoleType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowEditSectionPageCommand extends ShowDefaultContentCommand implements ShowEditPage {
    private static final String PARAM_SECTION = "section";
    private static final String ATTR_SECTION = "sectionToEdit";
    private static final String SECTION_ERROR_NO_SUCH = "error.noSuchSection";

    public ShowEditSectionPageCommand() {
        super(MappingManager.EDIT_SECTION_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isInputDataValid(content)) {
            if (isAllowedAction(content)) {
                SectionService sectionService = new SectionService();

                try {
                    String sectionId = content.getRequestParameter(PARAM_SECTION);
                    boolean isAdmin = RequestContentUtil.isCurrentUserAdmin(content);
                    Section section = sectionService.findById(Integer.parseInt(sectionId), isAdmin);

                    if (section != null) {
                        setAsAttrIfNotAfterError(content, section, ATTR_SECTION);
                    } else {
                        content.setRequestAttribute(ATTR_SERVER_ERROR, SECTION_ERROR_NO_SUCH);
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e);
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
        return Validator.isNumIdValid(content.getRequestParameter(PARAM_SECTION));
    }

    @Override
    public boolean isAllowedAction(RequestContent content) {
        return allowedAction(content, RoleType.ADMIN);
    }
}