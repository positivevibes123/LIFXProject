package testing;

import main.ClientSingleton;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        String token = "(INSERT TOKEN HERE)";

        ClientSingleton.getInstance().init(token);
        //ClientSingleton.getInstance().authenticate();
        ClientSingleton.getInstance().togglePower();
    }
}
