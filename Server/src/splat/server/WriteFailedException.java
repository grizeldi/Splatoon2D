package splat.server;

import java.io.IOException;
import java.io.PrintWriter;

public class WriteFailedException extends IOException{
    private PrintWriter cause;

    public WriteFailedException(PrintWriter cause) {
        this.cause = cause;
    }

    public PrintWriter getReason() {
        return cause;
    }
}
