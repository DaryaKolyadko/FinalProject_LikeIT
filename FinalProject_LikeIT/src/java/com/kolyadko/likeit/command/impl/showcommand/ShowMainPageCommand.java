package com.kolyadko.likeit.command.impl.showcommand;

import com.kolyadko.likeit.command.type.ShowCommandType;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.ServiceException;
import com.kolyadko.likeit.service.impl.SectionService;
import com.kolyadko.likeit.util.MappingManager;
import com.kolyadko.likeit.util.RequestContentUtil;

import java.util.HashMap;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */
public class ShowMainPageCommand extends ShowCommand {
    private static final String ATTR_SECTIONS_CATALOGUE = "sectionsCatalogue";

    public ShowMainPageCommand() {
        super(ShowCommandType.SHOW_HOME_PAGE.getRequestURI());
    }

    @Override
    public String execute(RequestContent content) {
        SectionService sectionService = new SectionService();

        try {
            HashMap catalogue = sectionService.selectSectionsCatalogueTree(
                    RequestContentUtil.isCurrentUserAdmin(content));
            content.setRequestAttribute(ATTR_SECTIONS_CATALOGUE, catalogue);
        } catch (ServiceException e) {
            LOG.error(e);
            return MappingManager.ERROR_PAGE;
        }

        return super.execute(content);
    }
}