package Node;

import java.io.IOException;

public abstract class Node {

    private int port;

    public abstract void established() throws IOException;

    public abstract boolean state() throws IOException;
}
