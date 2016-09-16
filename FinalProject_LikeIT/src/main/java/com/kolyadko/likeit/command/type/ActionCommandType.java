package com.kolyadko.likeit.command.type;


import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.command.impl.actioncommand.*;

/**
 * Created by DaryaKolyadko on 16.07.2016.
 */

/**
 * List of available ActionCommand-s
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
    EDIT_COMMENT(new EditCommentCommand()),
    EDIT_PROFILE(new EditProfileCommand()),
    DELETE_SECTION(new DeleteSectionCommand()),
    DELETE_QUESTION(new DeleteQuestionCommand()),
    DELETE_COMMENT(new DeleteCommentCommand()),
    DELETE_PROFILE(new DeleteProfileCommand()),
    RESTORE_SECTION(new RestoreSectionCommand()),
    RESTORE_QUESTION(new RestoreQuestionCommand()),
    RESTORE_COMMENT(new RestoreCommentCommand()),
    RESTORE_PROFILE(new RestoreProfileCommand()),
    BAN_PROFILE(new BanProfileCommand()),
    ACTIVATE_PROFILE(new ActivateProfileCommand()),
    SET_QUESTION_MARK(new SetQuestionMarkCommand()),
    SET_COMMENT_MARK(new SetCommentMarkCommand()),
    NOTE_AS_ANSWER(new NoteAsAnswerCommand());

    private Command command;

    ActionCommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}