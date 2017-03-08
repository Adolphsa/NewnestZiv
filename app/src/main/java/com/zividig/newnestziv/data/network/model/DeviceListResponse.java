package com.zividig.newnestziv.data.network.model;

import java.util.List;

/**
 * 获取设备列表返回
 * Created by adolph
 * on 2017-03-08.
 */

public class DeviceListResponse {

    /**
     * status : 200
     * userid : 13480995624
     * devnum : 2
     * devinfo : [{"devid":"ZIV3C00010000AD0849","devtype":null,"alias":"","carid":""},{"devid":"ZIV3C00010000AD08FD","devtype":null,"alias":"","carid":"有线"}]
     */

    private int status;
    private String userid;
    private int devnum;
    private List<DevinfoBean> devinfo;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getDevnum() {
        return devnum;
    }

    public void setDevnum(int devnum) {
        this.devnum = devnum;
    }

    public List<DevinfoBean> getDevinfo() {
        return devinfo;
    }

    public void setDevinfo(List<DevinfoBean> devinfo) {
        this.devinfo = devinfo;
    }

    public static class DevinfoBean {
        /**
         * devid : ZIV3C00010000AD0849
         * devtype : null
         * alias :
         * carid :
         */

        private String devid;
        private Object devtype;
        private String alias;
        private String carid;

        public String getDevid() {
            return devid;
        }

        public void setDevid(String devid) {
            this.devid = devid;
        }

        public Object getDevtype() {
            return devtype;
        }

        public void setDevtype(Object devtype) {
            this.devtype = devtype;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getCarid() {
            return carid;
        }

        public void setCarid(String carid) {
            this.carid = carid;
        }

        @Override
        public String toString() {
            return "DevinfoBean{" +
                    "devid='" + devid + '\'' +
                    ", devtype=" + devtype +
                    ", alias='" + alias + '\'' +
                    ", carid='" + carid + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DeviceListResponse{" +
                "status=" + status +
                ", userid='" + userid + '\'' +
                ", devnum=" + devnum +
                ", devinfo=" + devinfo +
                '}';
    }
}
