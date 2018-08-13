package com.unbaja.inggi.bengkos.model;

/**
 * Created by sigit on 12/08/2018.
 */

public class EditBengkelEvent {

    public static int EVENT_JAM = 1;
    public static int EVENT_HARI = 2;


    public static EditBengkelEvent createEventJam(String text1, String text2) {
        EditBengkelEvent event = new EditBengkelEvent(text1, text2);
        event.setEvent(EVENT_JAM);
        return event;
    }

    public static EditBengkelEvent createEventHari(String text1, String text2) {
        EditBengkelEvent event = new EditBengkelEvent(text1, text2);
        event.setEvent(EVENT_HARI);
        return event;
    }

    private String text1;
    private String text2;
    private int event;


    private EditBengkelEvent(String text1, String text2) {
        this.setText1(text1);
        this.setText2(text2);
    }


    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }
}
