package JsonTest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;

/**
 *
 * Created by Administrator on 2016/4/16.
 */
public class JsonRead {
    public static void main(String[] args) throws Exception{
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject)parser.parse("{\"sendnum\":0,\"receivenum\":403655224,\"type\":\"result\",\"data\":\"ok\"}");
        //System.out.println("cat=" + object.get("cat").getAsString());
        //System.out.println("pop=" + object.get("pop").getAsBoolean());
    }
}
