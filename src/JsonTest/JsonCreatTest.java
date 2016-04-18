package JsonTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 *
 * Created by Administrator on 2016/4/17.
 */
public class JsonCreatTest {
    public static void main(String[] args) {
        /*JsonObject object = new JsonObject();
        object.addProperty("cat","it");

        JsonArray array = new JsonArray();

        JsonObject lan1 = new JsonObject();
        lan1.addProperty("id",1);
        lan1.addProperty("name","java");
        lan1.addProperty("ide","eclipse");
        array.add(lan1);

        JsonObject lan2 = new JsonObject();
        lan2.addProperty("id",2);
        lan2.addProperty("name","Swift");
        lan2.addProperty("ide","XCode");
        array.add(lan2);

        object.add("lan",array);

        System.out.println(object.toString());
*/
        JsonObject object = new JsonObject();
        object.addProperty("sendNum",0);
        object.addProperty("receiveNum",403655224);
        object.addProperty("type","result");
        object.addProperty("data","ok");
        System.out.println(object.toString());
    }


}
