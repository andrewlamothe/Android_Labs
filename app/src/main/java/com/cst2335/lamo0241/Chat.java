package com.cst2335.lamo0241;

public class Chat {
    private String text;
    private Boolean send;
    private int position;
    static long ID =0;
    private long thisID;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Chat(String text, Boolean send){

        this.text = text;
        this.send = send;

    }
    public static void resetID(){
        ID=0;
    }
    public void setID(long ID){
        this.thisID = ID;
    }
    public long getID() {return thisID;}
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getSend() {
        return send;
    }

    public void setSend(Boolean send) {
        this.send = send;
    }
}
