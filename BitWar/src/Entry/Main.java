package Entry;

import java.util.Scanner;

public class Main {

    public static void main(String [] args){

        System.out.println("============================================");
        System.out.println("|                                          |");
        System.out.println("|             欢迎来到比特大战             |");
        System.out.println("|                                          |");
        System.out.println("============================================");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("============================================");
            System.out.println("|load: load or load file|");
            System.out.println("|list:|");
            System.out.println("|show:|");
            System.out.println("|run:|");
            System.out.println("|battle:|");
            System.out.println("|exit:|");
            System.out.println("============================================");
            System.out.println("请输入选择：");
            //读入选择
            String line = scanner.nextLine();
            if (line.equals("exit"))
            {
                System.out.println("exit system! bye!");
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
