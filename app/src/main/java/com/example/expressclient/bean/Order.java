package com.example.expressclient.bean;

import java.util.Date;

public class Order {
    int orderId;
    String senderName;
    String senderTelephone;
//        "startPlace": "{\"lng\": 120.1619520000, \"lat\":30.3286630000}",
    String goodsType;
    float goodsWeight;
    String receiverName;
    String receiverTelephone;
//        "endPlace": "{\"lat\": 30.3248220000, \"lng\":120.1500080000}",
    int orderStatus;
    Date createTime;
    Date updateTime;
}
