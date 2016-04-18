package Common.User;

import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * 用户信息管理
 * Created by Administrator on 2016/4/16.
 */
public class UserInfoMessage {

    //用户信息列表 用户信息<----->channel
    private Map<UserInfo,SocketChannel> userMap = null;

    public UserInfoMessage() {
        userMap = new HashMap<>();

        List<Integer> friendShipList = new ArrayList<>();
        friendShipList.add(348797320);
        UserInfo info = new UserInfo("chihson",403655224,friendShipList);
        userMap.put(info,null);

        List<Integer> friendShipList2 = new ArrayList<>();
        friendShipList.add(403655224);
        UserInfo info2 = new UserInfo("econg",348797320,friendShipList2);
        userMap.put(info2,null);


        System.out.println(userMap.size());
        UserInfo info3 = new UserInfo(null,403655224,null);
        System.out.println(info.equals(info3));
    }

    public void addUserChannel(Integer num,SocketChannel channel) {
        UserInfo info = new UserInfo(null,num,null);
        userMap.put(info,channel);
    }

    public void deleteUserChannel(Integer num) {
        UserInfo info = new UserInfo(null,num,null);
        userMap.put(info,null);
    }

    public SocketChannel getUserChannel(Integer num) {
        UserInfo info = new UserInfo(null,num,null);
        return userMap.get(info);
    }

    public boolean isFriend(int num1,int num2) {
        UserInfo info = new UserInfo(null,num1,null);
        Set<UserInfo> userInfoSet = userMap.keySet();
        Iterator<UserInfo> it = userInfoSet.iterator();

        while (it.hasNext()) {
            UserInfo io = it.next();
            if (io.equals(info)) {
                return io.getFriendShipList().contains(num2);
            }
        }
        return false;
    }

}
