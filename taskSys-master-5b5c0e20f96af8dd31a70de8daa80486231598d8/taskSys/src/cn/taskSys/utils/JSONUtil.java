package cn.taskSys.utils;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * fxm on 14-8-14.
 */
public class JSONUtil {

    /**
     * 转化成json数据
    */
    public static String bean2Json(Object obj) throws IOException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
        mapper.writeValue(gen, obj);
        gen.close();
        return sw.toString();
    }

    /*
    * json数据转换成对象
    * */
    public static <T> T json2Bean(String jsonStr, Class<T> objClass)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, objClass);
    }


//    /*
//    * json数组数据转换成对象
//    * */
    public static <T> List<T> jsonArray2Bean(String jsonStr, Class<T> objClass) throws IOException {
        List<T> list = new ArrayList<T>();
        JsonFactory f = new JsonFactory();
        JsonParser jp = f.createJsonParser(jsonStr);
        jp.nextToken();
        ObjectMapper mapper = new ObjectMapper();
        while(jp.nextToken() == JsonToken.START_OBJECT){
            T obj = mapper.readValue(jp,objClass);    // process
            list.add(obj);
        }
        return list;
    }
}
