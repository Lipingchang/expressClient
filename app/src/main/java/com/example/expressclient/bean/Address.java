package com.example.expressclient.bean;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;

public class Address {
    LatLng dst;
    PoiInfo dstDescription;
    String dstUsername;
    String dstTelphone;

    public LatLng getDst() {
        return dst;
    }

    public void setDst(LatLng dst) {
        this.dst = dst;
    }

    public PoiInfo getDstDescription() {
        return dstDescription;
    }

    public void setDstDescription(PoiInfo dstDescription) {
        this.dstDescription = dstDescription;
    }

    public String getDstUsername() {
        return dstUsername;
    }

    public void setDstUsername(String dstUsername) {
        this.dstUsername = dstUsername;
    }

    public String getDstTelphone() {
        return dstTelphone;
    }

    public void setDstTelphone(String dstTelphone) {
        this.dstTelphone = dstTelphone;
    }
}
