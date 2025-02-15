import duke.exception.CommandException;
import duke.exception.InvalidNumberException;
import duke.exception.LackOfElementException;
/**
 * Create and execute user command.
 * */
public class Command {
    protected String fullCommand;
    protected boolean isExit;
    /**
     * Create new command.
     * @param fullCommand user full command.
     * */
    public Command(String fullCommand) {
        this.fullCommand = fullCommand;
        this.isExit = false;
    }

    private static void CheckFirstWord(String s) throws CommandException {
        if ( !(s.equals("todo" ) || s.equals( "deadline") || s.equals( "event")
                || s.equals( "bye") || s.equals( "list") || s.equals( "done")
                || s.equals("delete") || s.equals("find")) ) {
            throw new CommandException();
        }
    }

    private static void CheckNumber(int size, int index) throws InvalidNumberException {
        if (index >= size) {
            throw new InvalidNumberException();
        }
    }

    private static void CheckElement(String command) throws LackOfElementException {
        if (  command.equals("todo") || command.equals("deadline") || command.equals("event")
                || command.equals("done")|| command.equals("delete") || command.equals("find") ) {
            throw new LackOfElementException();
        }
    }
    /**
     * Execute new command.
     * @param tasks task list to be updated
     * @param storage file address to be updated
     * */
    public void execute(TaskList tasks, Storage storage) {
        String command = Parser.command(fullCommand);
        int size = tasks.tasks.size();

        try {
            CheckFirstWord(command);
        } catch (CommandException e) {
            System.out.println("OOPS!!! Pls key in the valid command");
        }

        switch (command) {
            case "bye":
                UI.byeMessage();
                isExit = true;
                break;
            case "list":
                UI.listMessage(tasks.tasks);
                break;
            case "done":
                try {
                    CheckElement(fullCommand);
                    CheckNumber(size, Parser.taskNumber(fullCommand));
                    tasks.doneCommand(storage, fullCommand);
                } catch (LackOfElementException e) {
                    System.out.println("OOPS!!! Pls key in the number of the task");
                } catch (InvalidNumberException e) {
                    System.out.println("OOPS!!! The number of task is invalid! Please key in again!");
                }
                break;
            case "delete":
                try {
                    CheckElement(fullCommand);
                    CheckNumber(size, Parser.taskNumber(fullCommand));
                    tasks.deleteCommand(storage, fullCommand);
                } catch (LackOfElementException e) {
                    System.out.println("OOPS!!! Pls key in the number of the task");
                } catch (InvalidNumberException e) {
                    System.out.println("OOPS!!! The number of task is invalid! Please key in again!");
                }
                break;
            case "todo":
                try {
                    CheckElement(fullCommand);
                    tasks.addTodoCommand(storage, fullCommand);
                } catch (LackOfElementException e) {
                    System.out.println("OOPS!!! Pls key in the description for the task");
                }
                break;
            case "deadline":
                try {
                    CheckElement(fullCommand);
                    tasks.addDeadlineCommand(storage, fullCommand);
                } catch (LackOfElementException e) {
                    System.out.println("OOPS!!! Pls key in the description for the task");
                }
                break;
            case "event":
                try {
                    CheckElement(fullCommand);
                    tasks.addEventCommand(storage, fullCommand);
                } catch (LackOfElementException e) {
                    System.out.println("OOPS!!! Pls key in the description for the task");
                }
                break;
            case "find":
                try {
                    CheckElement(fullCommand);
                    tasks.findCommand(fullCommand);
                } catch (LackOfElementException e) {
                    System.out.println("OOPS!!! Pls key in the description for finding");
                }
                break;
        }
    }
}
