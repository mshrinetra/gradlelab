package com.mshri.gradlelab;

public class MailEmbedObject {
    private String cid;
    private String path;

    MailEmbedObject(String newCid, String newPath){
        this.cid = newCid;
        this.path = newPath;
    }

    public String getCid(){
        return "<" + this.cid + ">";
    }

    public String getPath(){
        return this.path;
    }

}
