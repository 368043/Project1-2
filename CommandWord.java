public enum CommandWord {

    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), LOOK("look"), GRAB("grab"), BACK("back"), DROP("drop"), USE("use");

    private String commandWord;

    CommandWord(String commandWord) {
        this.commandWord = commandWord;
    }

    public String toString(){
        return commandWord;
    }
}
