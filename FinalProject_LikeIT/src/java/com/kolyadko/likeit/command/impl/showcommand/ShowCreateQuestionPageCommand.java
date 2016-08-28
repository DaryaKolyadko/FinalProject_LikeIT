package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowCreateQuestionPageCommand extends ShowDefaultContentCommand {
    private static final String ATTR_SECTIONS = "sections";

    public ShowCreateQuestionPageCommand() {
        super(MappingManager.CREATE_QUESTION_PAGE);
    }

    @Override
    public String execute(RequestContent content) {
        SectionService sectionService = new SectionService();

        try {
            content.setRequestAttribute(ATTR_SECTIONS, sectionService.findNotMajorSections());
        } catch (ServiceException e) {
            LOG.error(e);
            content.setSessionAttribute(EXCEPTION, new ObjectMemoryContainer(e, MemoryContainerType.ONE_OFF));
            return MappingManager.getInstance().getProperty(MappingManager.ERROR_PAGE_500);
        }

        return super.execute(content);
    }
}