package com.zividig.newnestziv.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 设备信息
 * Created by adolph
 * on 2017-03-10.
 */
@Entity(nameInDb = "deviceInfo")
public class DeviceInfo {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "username")
    private String userName;

    @Property(nameInDb = "deviceid")
    private String deviceId;

    @Property(nameInDb = "devicetype")
    private String deviceType;

    @Property(nameInDb = "alias")
    private String alias;

    @Property(nameInDb = "carid")
    private String carid;

    public String getCarid() {
        return this.carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1194859872)
    public DeviceInfo(Long id, String userName, String deviceId, String deviceType,
            String alias, String carid) {
        this.id = id;
        this.userName = userName;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.alias = alias;
        this.carid = carid;
    }

    @Generated(hash = 2125166935)
    public DeviceInfo() {
    }
}
