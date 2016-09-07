package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.entity.Section;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.uncompleteddata.UncompletedSectionMemoryContainer;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;
import com.kolyadko.likeit.validator.SectionActionValidator;
import com.kolyadko.likeit.validator.Validator;

/**
 * Created by DaryaKolyadko on 29.08.2016.
 */
public class EditSectionCommand extends ActionCommand {
    private static final String PARAM_SECTION_ID = "section";
    private static final String PARAM_SECTION_NAME = "sectionName";
    private static final String PARAM_MAJOR_SECTION_ID = "majorSectionId";
    private static final String PARAM_LABEL_COLOR = "labelColor";

    private static final String SESSION_ATTR_UNCOMPLETED = "uncompleted";
    private static final String EDIT_SECTION_ERROR_CHECK = "error.checkForm";
    private static final String EDIT_SUCCESS = "info.editSection.success";
    private static final String EDIT_PROBLEM = "info.editSection.problem";

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isAllowedAction(content)) {
            UncompletedSectionMemoryContainer container = initUncompletedSection(content);

            if (isInputDataValid(content)) {
                try {
                    SectionService sectionService = new SectionService();
                    int sectionId = Integer.parseInt(content.getRequestParameter(PARAM_SECTION_ID));
                    Section section = sectionService.findById(sectionId, RequestContentUtil.isCurrentUserAdmin(content));

                    if (section != null) {
                        section.setName(container.getName());

                        if (section.isMajor()) { // is major
                            section.setLabelColor(container.getLabelColor());
                        }

                        if (sectionService.updateSection(section)) {
                            content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(EDIT_SUCCESS, MemoryContainerType.ONE_OFF));
                        } else {
                            content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(EDIT_PROBLEM));
                        }

                        return MappingManager.HOME_PAGE;
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(EDIT_SECTION_ERROR_CHECK));
            }

            content.setSessionAttribute(SESSION_ATTR_UNCOMPLETED, container);
            return MappingManager.EDIT_SECTION_PAGE + RequestContentUtil.getParamAsQueryString(content,
                    PARAM_SECTION_ID);
        } else {
            content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(NOT_ALLOWED));
        }

        return MappingManager.HOME_PAGE;
    }

    @Override
    protected boolean isInputDataValid(RequestContent content) {
        String optional = content.getRequestParameter(PARAM_LABEL_COLOR);
        boolean result = SectionActionValidator.isSectionNameValid(content.getRequestParameter(PARAM_SECTION_NAME)) &&
                Validator.isNumIdValid(content.getRequestParameter(PARAM_SECTION_ID));
        return optional == null ? result : result && SectionActionValidator.isHexColorValid(content.
                getRequestParameter(PARAM_LABEL_COLOR));
    }

    private UncompletedSectionMemoryContainer initUncompletedSection(RequestContent content) {
        UncompletedSectionMemoryContainer memoryContainer = new UncompletedSectionMemoryContainer();
        memoryContainer.setName(content.getRequestParameter(PARAM_SECTION_NAME));
        memoryContainer.setMajorSectionId(content.getRequestParameter(PARAM_MAJOR_SECTION_ID));
        memoryContainer.setLabelColor(content.getRequestParameter(PARAM_LABEL_COLOR));
        return memoryContainer;
    }
}