package pack;

public class App {

    public static void main(String[] args) throws Exception {
        int mode = 0, hashmode = 0, paramsIndex = args.length;                         //hash_mode 1 for md5, 2 for sha256
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-i")) {
                mode = 1;
                break;
            }
            if (args[i].equals("-f")) {
                paramsIndex = i;
            }
            if (args[i].equals("-md5") && hashmode == 0) {
                hashmode = 1;
            }
            else if (args[i].equals("-sha256") && hashmode == 0) {
                hashmode = 2;
            }
        }

        if (mode == 1) {
            Interactive intermode = new Interactive();
            intermode.doWork(args);
        } else if (hashmode == 0) {
            System.out.println("No hash mode");
        }
        else {
            NormalMode normalmode = new NormalMode();
            normalmode.doWork(hashmode, args, paramsIndex);
        }
    }
}
