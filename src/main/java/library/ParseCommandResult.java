package library;

class ParseCommandResult{
    private Command command;
    private String error;
    public ParseCommandResult(Command command, String error){
        this.command = command;
        this.error = error;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}