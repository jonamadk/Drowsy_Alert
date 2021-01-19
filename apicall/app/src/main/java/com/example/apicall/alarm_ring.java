package com.example.apicall;

public class alarm_ring {

    private  Boolean set_alarm;

    private Integer mouth_status;

    public alarm_ring(Boolean set_alarm, Integer mouth_status) {
        this.set_alarm = set_alarm;
        this.mouth_status = mouth_status;
    }

    public Boolean getSet_alarm() {
        return set_alarm;
    }

    public Integer getMouth_status() {
        return mouth_status;
    }
}
