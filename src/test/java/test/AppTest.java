package test;

import com.sun.net.httpserver.HttpServer;
import mik.handler.AddResultHandler;
import mik.handler.LevelTopHandler;
import mik.handler.UserTopHandler;
import org.junit.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;


public class AppTest {

    private static HttpServer server = null;
    private static final String host = "http://localhost";
    private static final int port = 8081;
    private static final String addUser = "/setinfo";
    private static final String getUserTop = "/userinfo";
    private static final String getLevelTop = "/levelinfo";

    @BeforeClass
      public static void startServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext(getUserTop, new UserTopHandler());
        server.createContext(getLevelTop, new LevelTopHandler());
        server.createContext(addUser, new AddResultHandler());
        server.start();
    }

    @Before
    public void addData() {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(host + ":" + port + addUser);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty( "Content-Type", "application/json" );
            OutputStream os = connection.getOutputStream();
            os.write("{\"user_id\":\"1\",\"level_id\":\"1\",\"result\":\"1\"}".getBytes());
            connection.connect();

            InputStream stream = connection.getInputStream();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddRezult(){

        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(host + ":" + port + addUser);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty( "Content-Type", "application/json" );
            OutputStream os = connection.getOutputStream();
            os.write("{\"user_id\":\"2\",\"level_id\":\"2\",\"result\":\"2\"}".getBytes());
            connection.connect();

            StringBuffer response = getResponse(connection);

            Assert.assertEquals("Result added!",response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserTop(){

        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(host + ":" + port + getUserTop + "?user_id=1");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            StringBuffer response = getResponse(connection);

            Assert.assertEquals("User TOP!user_id 1 = 1",response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getLevelTop(){

        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(host + ":" + port + getLevelTop + "?level_id=1");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            StringBuffer response = getResponse(connection);

            Assert.assertEquals("Level TOP!level_id 1 = 1",response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuffer getResponse(HttpURLConnection connection) throws IOException {
        InputStream stream = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    @AfterClass
    public static void stopServer() {
        server.stop(0);
    }
}
