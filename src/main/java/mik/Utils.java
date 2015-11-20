package mik;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mikitjuk on 18.11.15.
 */
public class Utils {

    private static final Logger logger = Logger.getLogger(Utils.class);

    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1)
                result.put(pair[0], pair[1]);
            else
                result.put(pair[0], "");
        }
        return result;
    }


    public static <T> T fromJSON(String json, Class<T> valueType){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, false);
        try {
            return mapper.readValue(json, valueType);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

}
