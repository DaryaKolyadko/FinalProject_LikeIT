package com.kolyadko.likeit.command.impl.actioncommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.memorycontainer.impl.ErrorMemoryContainer;
import com.kolyadko.likeit.memorycontainer.impl.TextMemoryContainer;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.type.MemoryContainerType;

/**
 * Created by DaryaKolyadko on 31.08.2016.
 */
public class RestoreSectionCommand extends SimpleActionCommand {
    private static final String RESTORE_SUCCESS = "info.restoreSection.success";
    private static final String RESTORE_PROBLEM = "info.restoreSection.problem";

    public RestoreSectionCommand() {
        super("sectionId");
    }

    @Override
    protected void serviceCall(RequestContent content) throws CommandException {
        SectionService sectionService = new SectionService();
        int sectionId = Integer.parseInt(content.getRequestParameter(paramId));

        try {
            if (sectionService.restoreSectionFromArchive(sectionId)) {
                content.setSessionAttribute(SESSION_ATTR_INFO, new TextMemoryContainer(RESTORE_SUCCESS,
                        MemoryContainerType.ONE_OFF));
            } else {
                content.setSessionAttribute(SESSION_ATTR_ERROR, new ErrorMemoryContainer(RESTORE_PROBLEM));
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}