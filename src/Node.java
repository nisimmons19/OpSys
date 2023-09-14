import java.net.ServerSocket;

public class Node {
    private int id;
    private boolean active;
    private int minPerActive;
    private int maxPerActive;
    private int maxNumberMessages;
    private int[] neighbors;
    private ServerSocket serverSocket;
}
