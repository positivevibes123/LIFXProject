package testing;

import main.ClientSingleton;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        String token = "c6a42da12dd57e5fd2d48326a01bf4e3aee9a2514bdf16eccbab6f725b531f89";

        ClientSingleton.getInstance().init(token);
        //ClientSingleton.getInstance().authenticate();
        ClientSingleton.getInstance().togglePower();
    }
}
