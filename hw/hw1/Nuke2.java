import java.io.*;

class Nuke2 {
    public static void main(String[] arg) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String input = bf.readLine();
        String output = input.substring(0,1)+input.substring(2);
        System.out.println(output);
    }
}
