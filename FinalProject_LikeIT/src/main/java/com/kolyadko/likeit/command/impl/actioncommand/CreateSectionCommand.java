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
import com.kolyadko.likeit.validator.Validator;
import com.kolyadko.likeit.validator.impl.SectionActionValidator;

/**
 * Created by DaryaKolyadko on 29.08.2016.
 */

/**
 * Command lets create a section (admin only)
 */
public class CreateSectionCommand extends ActionCommand {
    private static final String PARAM_SECTION_NAME = "sectionName";
    private static final String PARAM_MAJOR_SECTION_ID = "majorSectionId";
    private static final String PARAM_LABEL_COLOR = "labelColor";

    private static final String SESSION_ATTR_UNCOMPLETED = "uncompleted";
    private static final String CREATE_SECTION_ERROR_CHECK = "error.checkForm";
    private static final String CREATE_SUCCESS = "info.createSection.success";
    private static final String CREATE_PROBLEM = "info.createSection.problem";

    @Override
    public String execute(RequestContent content) throws CommandException {
        if (isAllowedAction(content)) {
            UncompletedSectionMemoryContainer container = initUncompletedSection(content);

            if (isInputDataValid(content)) {
                try {
                    SectionService sectionService = new SectionService();
                    Section section = new Section();
                    section.setName(container.getName());

                    if (container.getMajorSectionId().isEmpty()) { // => create major section
                        section.setLabelColor(container.getLabelColor());
                    } else {
                        section.setMajorSectionId(Integer.parseInt(container.getMajorSectionId()));
                    }

                    if (sectionService.create(section)) {
                        content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(CREATE_SUCCESS, MemoryContainerType.ONE_OFF));
                        return MappingManager.HOME_PAGE;
                    } else {
                        content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(CREATE_PROBLEM));
                    }
                } catch (ServiceException e) {
                    throw new CommandException("Exception in CreateSectionCommand", e);
                }
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(CREATE_SECTION_ERROR_CHECK));
            }

            content.setSessionAttribute(SESSION_ATTR_UNCOMPLETED, container);
            return MappingManager.CREATE_SECTION_PAGE;
        }

        content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(NOT_ALLOWED));
        return MappingManager.HOME_PAGE;
    }

    @Override
    public boolean isInputDataValid(RequestContent content) {
        return SectionActionValidator.isSectionNameValid(content.getRequestParameter(PARAM_SECTION_NAME)) &&
                (SectionActionValidator.isHexColorValid(content.getRequestParameter(PARAM_LABEL_COLOR)) ||
                        Validator.isNumIdValid(content.getRequestParameter(PARAM_MAJOR_SECTION_ID)));
    }

    private UncompletedSectionMemoryContainer initUncompletedSection(RequestContent content) {
        return new UncompletedSectionMemoryContainer(
                content.getRequestParameter(PARAM_SECTION_NAME),
                content.getRequestParameter(PARAM_LABEL_COLOR),
                content.getRequestParameter(PARAM_MAJOR_SECTION_ID));
    }
}