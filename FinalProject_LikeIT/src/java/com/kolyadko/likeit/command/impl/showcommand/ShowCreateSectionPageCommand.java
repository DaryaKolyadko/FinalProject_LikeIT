package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowCreateSectionPageCommand extends ShowDefaultContentCommand {
    private static final String ATTR_MAJOR_SECTIONS = "majorSections";

    public ShowCreateSectionPageCommand() {
        super(MappingManager.CREATE_SECTION_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        SectionService sectionService = new SectionService();

        try {
            content.setRequestAttribute(ATTR_MAJOR_SECTIONS, sectionService.findMajorSections());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return super.execute(content);
    }
}