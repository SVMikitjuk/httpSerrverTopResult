package mik.handler;

import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicates;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import mik.InMemoryData;
import mik.Utils;
import mik.model.Level;
import mik.model.RequestAdd;
import mik.model.ResultTop;
import mik.model.User;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by mikitjuk on 18.11.15.
 */
public class AddResultHandler implements HttpHandler {

    private static final Logger logger = Logger.getLogger(AddResultHandler.class);

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String request = convertStreamToString(httpExchange.getRequestBody());
        RequestAdd newRes = Utils.fromJSON(request, RequestAdd.class);

        //IMap<Integer, Result> listResult = InMemoryData.getInstance().getMap("result");

        // проверка и добавление в справочник пользователей
        cheackAddUserToCatalog(newRes);

        // проверка и добавление в справочник уровней
        cheackAddLevelToCatalog(newRes);

        // определенеи на добавление в нового результата
        cheackAddNewResult(newRes);

        httpExchange.sendResponseHeaders(200, 0);
        PrintWriter out = new PrintWriter(httpExchange.getResponseBody());
        out.println("Result added!" );
        out.close();
        httpExchange.close();
    }

    private void cheackAddNewResult(RequestAdd newRes) {

        IMap<Integer, ResultTop> listOfUser = InMemoryData.getInstance().getMap("topOfUser");
        IMap<Integer, ResultTop> listOfLevel = InMemoryData.getInstance().getMap("topOfLevel");

        //находим минимальный результат в каждом разрезе
        Collection<ResultTop> listForUserResult = listOfUser.values(Predicates.equal("userId", newRes.getUserId()));
        ResultTop minUser = getMinResult(listForUserResult);

        Collection<ResultTop>listForLevelResult = listOfLevel.values(Predicates.equal("levelId", newRes.getLevelId()));
        ResultTop minLevel = getMinResult(listForLevelResult);

        //определяем будет ли входить новое значение в один из топ по разрезам
        if(minUser == null || minUser.getResult() < newRes.getResult()) {
            logger.debug("add new result to TOP users : userId = " + newRes.getUserId()
                    + ", levelId = " + newRes.getLevelId()
                    + ", result = " + newRes.getResult());
            listOfUser.put(newRes.getUserId(), new ResultTop(newRes.getLevelId(), newRes.getResult()));

            if (listOfUser.size() > 20){
                logger.debug("delete result from TOP users : userId = " + newRes.getUserId()
                        + ", levelId = " + minUser.getParamId()
                        + ", result = " + minUser.getResult());
                listOfUser.delete(minUser);
            }
        }

        if (minLevel == null || minLevel.getResult() < newRes.getResult()){
            logger.debug("add new result to TOP levels : userId = " + newRes.getUserId()
                    + ", levelId = " + newRes.getLevelId()
                    + ", result = " + newRes.getResult());
            listOfLevel.put(newRes.getLevelId(), new ResultTop(newRes.getUserId(), newRes.getResult()));

            if (listOfLevel.size() > 20){
                logger.debug("delete result from TOP levels : userId = " + minLevel.getParamId()
                        + ", levelId = " + newRes.getLevelId()
                        + ", result = " + minLevel.getResult());
                listOfLevel.delete(minLevel);
            }
        }
    }

    private void cheackAddLevelToCatalog(RequestAdd newRes) {
        IMap<Integer, Level> mapLevel = InMemoryData.getInstance().getMap("levels");
        if (mapLevel.get(newRes.getLevelId()) == null){
            logger.debug("add new level to catalog id = " + newRes.getUserId());
            mapLevel.put(newRes.getLevelId(), new Level(newRes.getLevelId(), "name"+newRes.getLevelId()));
        }
    }

    private void cheackAddUserToCatalog(RequestAdd newRes) {
        IMap<Integer, User> mapUser = InMemoryData.getInstance().getMap("users");
        if (mapUser.get(newRes.getUserId()) == null){
            logger.debug("add new user to catalog id = " + newRes.getUserId());
            mapUser.put(newRes.getUserId(), new User(newRes.getUserId(), "name"+newRes.getUserId()));
        }
    }

    private ResultTop getMinResult(Collection<ResultTop> listResult) {

        ResultTop retMin = null;
        for(ResultTop item : listResult)
            if (retMin == null || retMin.getResult() > item.getResult())
                retMin = item;

        return retMin;
    }

    String convertStreamToString(java.io.InputStream is) {
        Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
