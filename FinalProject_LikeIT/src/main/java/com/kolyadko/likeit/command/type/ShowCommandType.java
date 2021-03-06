package com.kolyadko.likeit.command.type;

import com.kolyadko.likeit.command.impl.showcommand.*;

/**
 * Created by DaryaKolyadko on 28.07.2016.
 */

/**
 * List of available show commands
 */
public enum ShowCommandType {
    SIMPLE_SHOW(null),
    SHOW_HOME_PAGE(new ShowHomePageCommand()),
    SHOW_CREATE_QUESTION_PAGE(new ShowCreateQuestionPageCommand()),
    SHOW_CREATE_SECTION_PAGE(new ShowCreateSectionPageCommand()),
    SHOW_EDIT_SECTION_PAGE(new ShowEditSectionPageCommand()),
    SHOW_EDIT_COMMENT_PAGE(new ShowEditCommentPageCommand()),
    SHOW_EDIT_QUESTION_PAGE(new ShowEditQuestionPageCommand()),
    SHOW_EDIT_PROFILE_PAGE(new ShowEditProfilePageCommand()),
    SHOW_USER_LIST_PAGE(new ShowUserListPageCommand()),
    SHOW_QUESTION_PAGE(new ShowQuestionPageCommand()),
    SHOW_PROFILE_PAGE(new ShowProfilePageCommand()),
    SHOW_RECENT_PAGE(new ShowRecentQuestPageCommand()),
    SHOW_TOP_PAGE(new ShowTopQuestPageCommand()),
    SHOW_SECTION_QUESTIONS_PAGE(new ShowSectionQuestPageCommand());

    private ShowCommand showCommand;

    ShowCommandType(ShowCommand showCommand) {
        this.showCommand = showCommand;
    }

    public static ShowCommandType getCommandWithURI(String requestURI) {
        for (ShowCommandType type : ShowCommandType.values()) {
            if (type.showCommand != null) {
                if (type.showCommand.getPath().equals(requestURI)) {
                    return type;
                }
            }
        }

        return SIMPLE_SHOW;
    }

    public ShowCommand getShowCommand() {
        return showCommand;
    }
}