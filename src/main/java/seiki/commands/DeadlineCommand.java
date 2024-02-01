package seiki.commands;

import seiki.data.TaskList;
import seiki.data.exception.SeikiException;
import seiki.data.task.Deadline;
import seiki.data.task.Event;
import seiki.data.task.ToDo;
import seiki.storage.Storage;
import seiki.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static seiki.common.DateTime.DATE_TIME_FORMATTER;

public class DeadlineCommand extends Command {
    public static final String COMMAND_WORD = "deadline";
    private final ArrayList<String> args;
    public DeadlineCommand(ArrayList<String> args) {
        this.args = args;
    }

    @Override
    public void execute(Storage storage, TaskList taskList, Ui ui) throws SeikiException {
        if (!this.args.contains("/by")
                || this.args.subList(this.args.indexOf("/by") + 1, this.args.size()).size() == 0) {
            throw new SeikiException("The date time for the task is not found.\n"
                    + "Please use the following format: deadline [task title] /by [datetime]");
        } else if (this.args.subList(0, this.args.indexOf("/by")).size() == 0) {
            throw new SeikiException("The task title is missing.\n"
                    + "Please use the following format: deadline [task title] /by [datetime]");
        } else {
            String taskName = String.join(" ", this.args.subList(0, this.args.indexOf("/by")));
            String dateTimeStr = String.join(" ",
                    this.args.subList(this.args.indexOf("/by") + 1, this.args.size()));
            try {
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
                Deadline task = new Deadline(taskName, dateTime);
                taskList.addTask(task);
                storage.save(taskList);
                ui.showAddTask(task, taskList);
            } catch (DateTimeParseException e) {
                throw new SeikiException("The format of the date time is incorrect.\n"
                        + "Please use the following format: yyyy/MM/dd HHmm");
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}