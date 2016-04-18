package Server.ReceiveHandle;

import Common.User.UserInfoMessage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 接收信息处理器
 * 数据包格式：json
 * {
 *     "sendNum":403655224
 *     "receiveNum":792316738
 *     "type":"xxxx"  // transmit传递 request请求 result返回
 *     "data":"xxxxxxx"
 * }
 */

public class ServerReceiveHandler {
    private Integer sendNum;
    private Integer receiveNum;
    private String type;
    private String data;

    private String inputStr;
    private UserInfoMessage userInfoMessage;
    private SocketChannel sendChannel;

    public ServerReceiveHandler(String inputStr,UserInfoMessage userInfoMessage, SocketChannel sendChannel) throws Exception{
        this.inputStr = inputStr;
        this.userInfoMessage = userInfoMessage;
        this.sendChannel = sendChannel;
        inputSplit();
    }

    public void inputSplit() throws Exception{

        JsonReader jReader = new JsonReader(new StringReader(inputStr));
        jReader.setLenient(true);
        JsonObject object = new JsonParser().parse(jReader).getAsJsonObject();

        sendNum = object.get("sendNum").getAsInt();
        receiveNum = object.get("receiveNum").getAsInt();
        data = object.get("data").getAsString();
        type = object.get("type").getAsString();

        System.out.println("sendNum:" + sendNum);
        System.out.println("receiveNum" + receiveNum);
        System.out.println("data:" + data);
        System.out.println("type:" + type);
    }

    public void handle() throws IOException {
        switch(type) {
            case "transmit"://传输
                transmitHandle();
                break;

            case "request"://对服务器的请求
                requestHandle();
                break;
        }
    }

    public void sendMessage(SocketChannel channel ,Integer sendNum, Integer receiveNum,String type,String data)
    throws IOException {

        JsonObject object = new JsonObject();
        object.addProperty("sendNum",sendNum);
        object.addProperty("receiveNum",receiveNum);
        object.addProperty("type",type);
        object.addProperty("data",data);
        channel.write(ByteBuffer.wrap(object.toString().getBytes()));

    }

    public void transmitHandle() throws IOException {
        SocketChannel receiveChannel = userInfoMessage.getUserChannel(receiveNum);
        if (receiveChannel != null && receiveChannel.isOpen() && userInfoMessage.isFriend(sendNum,receiveNum)) {//找到接收方的channel，而且是好友，转发
                receiveChannel.write(ByteBuffer.wrap(inputStr.getBytes()));

        } else if ((receiveChannel == null || !receiveChannel.isOpen())&& userInfoMessage.isFriend(sendNum,receiveNum)) {//是好友，但不在线
            sendMessage(sendChannel,0,sendNum,"result","dont't on line");

        } else if ((receiveChannel != null || receiveChannel.isOpen()) && !userInfoMessage.isFriend(sendNum,receiveNum)) {//在线，但不是好友
            sendMessage(sendChannel,0,sendNum,"result","not your friend");

        } else if ((receiveChannel == null || !receiveChannel.isOpen()) && !userInfoMessage.isFriend(sendNum,receiveNum)) {//不在线也不是好友
            sendMessage(sendChannel,0,sendNum,"result","dont't on line and not your friend");
        }
    }

    public void requestHandle() throws IOException {
        if (data.equals("ON_LINE")) {
            userInfoMessage.addUserChannel(sendNum,sendChannel);//添加channel
            JsonObject object = new JsonObject();
            object.addProperty("sendNum",0);
            object.addProperty("receiveNum",sendNum);
            object.addProperty("type","result");
            object.addProperty("data","ok");
            sendChannel.write(ByteBuffer.wrap(object.toString().getBytes()));
        }
    }

}
