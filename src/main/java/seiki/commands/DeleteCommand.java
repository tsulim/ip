package seiki.commands;

import static seiki.common.Messages.MESSAGE_EMPTY_TASKLIST;

import seiki.data.TaskList;
import seiki.data.exception.SeikiException;
import seiki.data.task.Task;
import seiki.storage.Storage;
import seiki.ui.Ui;

/**
 * Represents the 'delete' command.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " [TASK_NUMBER]";
    public static final String COMMAND_HELPER = "Please use the following format: " + COMMAND_FORMAT;
    public static final String COMMAND_USAGE = COMMAND_WORD
            + ": Delete a task identified by the task number.\n"
            + "Parameters: TASK_NUMBER (must be positive interger)\n"
            + "Example: " + COMMAND_WORD + " 1";
    private final Integer taskNumber;

    public DeleteCommand(String taskNumber) {
        this.taskNumber = Integer.parseInt(taskNumber);
    }

    @Override
    public String execute(Storage storage, TaskList taskList, Ui ui) throws SeikiException {
        taskList.checkIfListEmpty(MESSAGE_EMPTY_TASKLIST);
        taskList.checkIfNumberValid(taskNumber);

        assert taskNumber >= 0 || taskNumber < taskList.getTaskCount()
                : "Task number should be within range of task list";

        Task task = taskList.getTaskByNumber(taskNumber);
        taskList.deleteTask(task);
        storage.save(taskList);
        return ui.showDeleteTask(task, taskList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
