import library.LibraryHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String help ="";
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        LibraryHandler libraryHandler = new LibraryHandler();
        System.out.println("Select the category (for example.. Movie, Book, Song, Game etc.)\n if you want you can change the category by writing command like - 'change Song'");
        String category = sc.nextLine();
        libraryHandler.setCategory(category.trim());
        while(sc.hasNextLine()){
            String comm = sc.nextLine();
            if(comm==null || comm.length()==0)
                continue;
            if(comm.toLowerCase().equals("all")){
                libraryHandler.show();
            }else{
                String[] cm = comm.split(" ");
                if(cm[0].toLowerCase().equals("change") && cm.length>1){
                    String cName = "";
                    for(int i=1;i<cm.length;i++)
                        cName+=cm[i]+" ";
                   libraryHandler.setCategory(cName.trim());
                   System.out.println("The category was changed. Now it is"+cName.trim());
                }
                else {
                    String response = libraryHandler.sendCommand(comm);
                    System.out.println(response);
                }
            }

        }

    }
}
