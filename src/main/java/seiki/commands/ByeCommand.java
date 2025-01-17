package seiki.commands;

import seiki.data.TaskList;
import seiki.data.exception.SeikiException;
import seiki.storage.Storage;
import seiki.ui.Ui;

/**
 * Represents the 'bye' command.
 */
public class ByeCommand extends Command {
    public static final String COMMAND_WORD = "bye";
    public static final String COMMAND_USAGE = COMMAND_WORD + ": Ends the chatbot.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public String execute(Storage storage, TaskList taskList, Ui ui) throws SeikiException {
        return ui.showEnd();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
