public enum CommandWord {

    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), LOOK("look"), GRAB("grab"), BACK("back"), DROP("drop"), USE("use");

    private String commandWord;

    /**
     * Construct a command word
     * @param commandWord
     */
    CommandWord(String commandWord) {
        this.commandWord = commandWord;
    }

    /**
     * Returns a string of the command
     * @return a String
     */
    public String toString(){
        return commandWord;
    }
}
