package pack;

import java.io.File;
import java.util.Scanner;

public class NormalMode {

    public NormalMode() {
    }

    public void doWork(int hashType, String[] parameters, int index) {
        MakeHash mkHash = new MakeHash();
        String line;
        if (hashType == 1) {
            for (int i = index + 1; i < parameters.length; i++) {
                line = "";
                File file = new File(parameters[i]);
                if (!file.isFile()){
                    System.out.println(parameters[i] + " is not a file");
                }
                else {
                    try {
                    Scanner scanner = new Scanner(file);
                    scanner.useDelimiter("\n");
                    while (scanner.hasNext()) {
                        line += scanner.next();
                    }
                    scanner.close();
                    System.out.println(mkHash.makeHashMd5(line));
                    } catch(Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            for (int i = index + 1; i < parameters.length; i++) {
                line = "";
                File file = new File(parameters[i]);
                if (!file.isFile()) {
                    System.out.println(parameters[i] + " is not a file");
                }
                else {
                    try {
                        Scanner scanner = new Scanner(file);
                        scanner.useDelimiter("\n");
                        while (scanner.hasNext()) {
                            line += scanner.next();
                        }
                        scanner.close();
                        System.out.println(mkHash.makeHash256(line));
                    } catch(Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
