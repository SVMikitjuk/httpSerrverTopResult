package mik.handler;

import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import mik.InMemoryData;
import mik.Utils;
import mik.model.ResultTop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by mikitjuk on 23.11.15.
 */
public abstract class InfoHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Map<String, String> params = Utils.queryToMap(httpExchange.getRequestURI().getQuery());
        IMap<Integer, ResultTop> listResult = InMemoryData.getInstance().getMap(getTitleList());

        Predicate predicate = Predicates.equal("paramId", params.get(getParam()));
        Collection<ResultTop> results = listResult.values(predicate);

        if (results.size() > 0)
            Collections.sort((List<ResultTop>) results);

        httpExchange.sendResponseHeaders(200, 0);
        PrintWriter out = new PrintWriter(httpExchange.getResponseBody());
        out.println(getTitle());
        for(ResultTop item : results)
            out.println(item.getParamId() + "=" + item.getResult());

        out.close();
        httpExchange.close();
    }

    public abstract String getTitleList();

    public abstract String getTitle();

    public abstract String getParam();

}
