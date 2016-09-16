package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

import java.util.HashMap;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowHomePageCommand extends ShowDefaultContentCommand {
    private static final String ATTR_SECTIONS_CATALOGUE = "sectionsCatalogue";

    public ShowHomePageCommand() {
        super(MappingManager.HOME_PAGE);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        SectionService sectionService = new SectionService();

        try {
            HashMap catalogue = sectionService.selectSectionsCatalogueTree(
                    RequestContentUtil.isCurrentUserAdmin(content));
            content.setRequestAttribute(ATTR_SECTIONS_CATALOGUE, catalogue);
        } catch (ServiceException e) {
            throw new CommandException("Exception in ShowHomePageCommand", e);
        }

        return super.execute(content);
    }
}