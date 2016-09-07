package com.kolyadko.likeit.command.type;


import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.command.impl.actioncommand.*;

/**
 * Created by DaryaKolyadko on 16.07.2016.
 */
public enum ActionCommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    SIGN_UP(new SignUpCommand()),
    CREATE_QUESTION(new CreateQuestionCommand()),
    CREATE_SECTION(new CreateSectionCommand()),
    CREATE_COMMENT(new CreateCommentCommand()),
    EDIT_SECTION(new EditSectionCommand()),
    EDIT_QUESTION(new EditQuestionCommand()),
    EDIT_PROFILE(new EditProfileCommand()),
    DELETE_SECTION(new DeleteSectionCommand()),
    DELETE_QUESTION(new DeleteQuestionCommand()),
    DELETE_PROFILE(new DeleteProfileCommand()),
    RESTORE_SECTION(new RestoreSectionCommand()),
    RESTORE_QUESTION(new RestoreQuestionCommand()),
    RESTORE_PROFILE(new RestoreProfileCommand()),
    BAN_PROFILE(new BanProfileCommand()),
    ACTIVATE_PROFILE(new ActivateProfileCommand());

    private Command command;

    ActionCommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}