package mik.handler;

import com.hazelcast.core.MultiMap;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import mik.InMemoryData;
import mik.Utils;
import mik.model.ResultTop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by mikitjuk on 23.11.15.
 */
public abstract class InfoHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Map<String, String> params = Utils.queryToMap(httpExchange.getRequestURI().getQuery());
        MultiMap<Integer, ResultTop> listResult = InMemoryData.getInstance().getMultiMap(getTitleList());

        Collection<ResultTop> results = listResult.get(Integer.valueOf(params.get(getParam())));

        if (results.size() > 0){
            List result = new ArrayList<ResultTop>(results);
            Collections.sort(result);
            results = result;
        }

        httpExchange.sendResponseHeaders(200, 0);
        PrintWriter out = new PrintWriter(httpExchange.getResponseBody());
        out.println(getTitle());
        for(ResultTop item : results)
            out.println(getParam() + " " + item.getParamId() + " = " + item.getResult());

        out.close();
        httpExchange.close();
    }

    public abstract String getTitleList();

    public abstract String getTitle();

    public abstract String getParam();

}
