package com.datasensor.fish.appversion.model;

import java.util.Date;

public class AppVersionInfo {
    private Integer id;

    private String appversion;

    private Date updatetime;

    private String appdesc;

    private String downloadurl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion == null ? null : appversion.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getAppdesc() {
        return appdesc;
    }

    public void setAppdesc(String appdesc) {
        this.appdesc = appdesc == null ? null : appdesc.trim();
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl == null ? null : downloadurl.trim();
    }
}