package com.example.app_update.update.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class VersionDataBean implements Serializable {
    private String title;
    private String url;
    private String versionCode;
    private String md5;
    private String content;

    //TODO 解析数据 {
    //    "title":"4.5.0更新啦！",
    //    "content":"1. 优化了阅读体验；\n2. 上线了 hyman 的课程；\n3. 修复了一些已知问题。",
    //    "url":"http://59.110.162.30/v450_imooc_updater.apk",
    //    "md5":"14480fc08932105d55b9217c6d2fb90b",
    //    "versionCode":"450"
    //}

    public static VersionDataBean parse(String result) {

        try {
            JSONObject repJson = new JSONObject(result);
            String title = repJson.optString("title");
            String url = repJson.optString("url");
            String content = repJson.optString("content");
            String md5 = repJson.optString("md5");
            String versionCode = repJson.optString("versionCode");
            VersionDataBean bean = new VersionDataBean();
            bean.title = title;
            bean.url = url;
            bean.content = content;
            bean.md5 = md5;
            bean.versionCode = versionCode;
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
