package library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibraryHandler {
    private String category;
    private  H2Database h2Database;
    public LibraryHandler(){
        this.h2Database = new H2Database();
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String sendCommand(String command){
        String result = null;
        ParseCommandResult parseCommandResult = parseCommnand(command);
        if(parseCommandResult.getError()!=null){
            return parseCommandResult.getError();
        }
        Command comm = parseCommandResult.getCommand();
        switch(comm.getAction()){
            case "add" :
                result = add(comm.getModels().get(0));

                break;
            case "remove" :
                result = remove (comm.getModels().get(0));
                break;
            case "edit" :
                result = edit(comm.getModels().get(0),comm.getModels().get(1));
                break;
        }
            return result;
    }
    private List<String> split(String line){
        List<String> matchList = new ArrayList<String>();
        Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
        Matcher regexMatcher = regex.matcher(line);
        while (regexMatcher.find()) {
            String l = regexMatcher.group();
            if(  (l.charAt(0)=='"' && l.charAt(l.length()-1)=='"') || (l.charAt(0)=='\'' && l.charAt(l.length()-1)=='\'')){
                l = l.substring(1,l.length()-1);
            }
            matchList.add(l);
        }
        return matchList;
    }

    private ParseCommandResult parseCommnand(String command){
        List<String> comms = split(command);
        ParseCommandResult parseCommandResult=null;
        String error = null;
        Command comm=null;
        switch(comms.get(0)){
            case "add" :
                if(comms.size()==3){
                    Model model = new Model(comms.get(1),comms.get(2));
                    comm = new Command("add",model);
                }
                else{
                    error = "There is error in command structure.\n"+
                            "must look like -  add \"Author Name\" \"Model Name\" ";
                }

                break;
            case "remove" :
                if(comms.size()==3){
                    Model model = new Model(comms.get(1),comms.get(2));
                    comm = new Command("remove",model);
                }
                else{
                    error = "There is error in command structure.\n"+
                            "must look like -  remove \"Author Name\" \"Model Name\" ";
                }
                break;
            case "edit" :
                if(comms.size()==6){
                    Model oldModel = new Model(comms.get(1),comms.get(2));
                    Model newModel = new Model(comms.get(4),comms.get(5));
                    comm = new Command("edit",oldModel,newModel);
                }
                else{
                    error = "There is an error in the command structure.\n"+
                            "must look like -  edit \"Author Name\" \"Model Name\" to \"Author Name\" \"Model Name\" \n "+
                    "The first pair is old book, the second one is new book";
                }
                break;
            default:
                error = comms.get(0)+ " is unknown command. add, remove, edit only exist";
        }
        parseCommandResult = new ParseCommandResult(comm,error);
        return parseCommandResult;
    }
    private String add(Model model){
        return h2Database.add(this.category,model);
    }
    private String remove(Model model){

        return h2Database.remove(this.category,model);
    }
    private String edit(Model oldModel, Model newModel ){
            return h2Database.edit(category,oldModel,newModel);
    }
    public void show(){
        h2Database.show(this.category);
    }
}
