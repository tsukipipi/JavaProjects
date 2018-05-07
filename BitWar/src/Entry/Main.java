package Entry;

import java.util.Scanner;

public class Main {

    public static void main(String [] args){
        System.out.println("          ******   welcome to bitwar   ******          ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=====================================================");
            System.out.println("| command | format                                  |");
            System.out.println("=====================================================");
            System.out.println("|  load   | load / load file                        |");
            System.out.println("|  list   | list                                    |");
            System.out.println("|  show   | show file                               |");
            System.out.println("|  run    | run file                                |");
            System.out.println("|  battle | battle times / battle file1 file2 times |");
            System.out.println("|  exit   | exit                                    |");
            System.out.println("=====================================================");
            System.out.print("input:");
            //读入选择
            String line = scanner.nextLine();
            if (line.equals("exit"))
            {
                System.out.println("          ******   exit system! bye!   ******          ");
                break;
            }
            Cmd cmd = new Cmd(line);
            try {
                cmd.execute();
            }catch (Exception e){
                System.err.println(e.getLocalizedMessage());
            }
            System.out.println();
        }

    }

}
