package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       String fileName=args[0];
        StringBuilder sb=new StringBuilder();
        try (Scanner scanner=new Scanner(new File(fileName))){
            while (scanner.hasNextLine()){
                sb.append(scanner.nextLine()).append(" ");
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found: " + fileName);
            return;
        }


        String text= sb.toString();
        TextLengthCheck textLengthCheck=new TextLengthCheck(text);
        textLengthCheck.textControl();
    }
}
