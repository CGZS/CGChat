package Common.User;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息类
 * Created by Administrator on 2016/4/16.
 */
public class UserInfo {
    private String name;
    private Integer num;
    private List<Integer> friendShipList = null;
    private List<JsonObject> offLineMessage = new ArrayList<>();//存放离线消息

    public UserInfo (String name, Integer num, List<Integer> friendShipList) {
        this.name = name;
        this.num = num;
        this.friendShipList = friendShipList;
    }

    public List<Integer> getFriendShipList() {
        return this.friendShipList;
    }

    public String getName() {
        return this.name;
    }

    public Integer getNum() {
        return this.num;
    }

    public boolean equals(Object other) {
        if (other == this)  {
            return true;
        }

        if (other instanceof UserInfo) {
            UserInfo info = (UserInfo)other;
            if (info.getNum().equals(this.num)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.num.hashCode();
    }


}
