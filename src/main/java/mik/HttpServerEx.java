package mik;

import com.sun.net.httpserver.HttpServer;
import mik.handler.AddResultHandler;
import mik.handler.LevelTopHandler;
import mik.handler.UserTopHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by mikitjuk on 18.11.15.
 */
public class HttpServerEx {

    private static final Logger logger = Logger.getLogger(UserTopHandler.class);

    public static void main( String[] args ) throws IOException {

        InMemoryData.getInstance();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 10);
        server.createContext("/userinfo", new UserTopHandler());
        server.createContext("/levelinfo", new LevelTopHandler());
        server.createContext("/setinfo", new AddResultHandler());
        server.start();

        logger.debug("Server started\n" + "Press any key to stop...");
        System.in.read();

        server.stop(0);
        logger.debug("Server stoped");
    }
}
