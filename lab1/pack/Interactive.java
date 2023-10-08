package pack;

import java.io.File;
import java.util.Scanner;

public class Interactive {

    public Interactive() {
    }

    public void doWork(String[] parameters) {
        MakeHash mkHash = new MakeHash();
        String filename = "";
        String line;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type exit to end program");
        System.out.println("Print path to file");
        System.out.println("Print type of hash (-md5 or -sha256)");
        while (true) {
            filename = scanner.nextLine();
            if (filename.equals("exit")) {
                break;
            } else {
                File file = new File(filename);
                if (!file.isFile()) {
                    System.out.println("Not a file");
                    continue;
                }
                line = "";
                try {
                    Scanner fileScan = new Scanner(file);
                    fileScan.useDelimiter("\n");
                    while (fileScan.hasNext()) {
                        line += fileScan.next();
                    }
                    fileScan.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                filename = scanner.nextLine();
                if (filename.equals("-md5")) {
                    System.out.println(mkHash.makeHashMd5(line));
                } else if (filename.equals("-sha256")) {
                     System.out.println(mkHash.makeHash256(line));
                } else {
                    System.out.println("Wrong parameter");
                }
            }
        }
        scanner.close();
    }
}
