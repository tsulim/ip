package seiki.commands;

import static seiki.common.Messages.MESSAGE_EMPTY_TASKLIST;
import static seiki.common.Messages.MESSAGE_FIND_FAIL;

import seiki.data.TaskList;
import seiki.data.exception.SeikiException;
import seiki.storage.Storage;
import seiki.ui.Ui;

/**
 * Represents the 'find' command.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " [KEYWORD]";
    public static final String COMMAND_HELPER = "Please use the following format: " + COMMAND_FORMAT;
    public static final String COMMAND_USAGE = COMMAND_WORD
            + ": Find tasks containing the given keyword.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " book";
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(Storage storage, TaskList taskList, Ui ui) throws SeikiException {
        taskList.checkIfListEmpty(MESSAGE_EMPTY_TASKLIST);

        assert taskList.getTaskCount() > 0 : "Task list should contain tasks";
        assert !keyword.isEmpty() : "Keyword should not be empty";

        TaskList resultList = taskList.searchByKeyword(keyword);
        resultList.checkIfListEmpty(String.format(MESSAGE_FIND_FAIL, keyword));

        assert resultList.getTaskCount() > 0 : "Result list should contain tasks";

        return ui.showFindTask(keyword, resultList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
