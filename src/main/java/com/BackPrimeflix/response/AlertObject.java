package com.BackPrimeflix.response;

import java.io.Serializable;

public class AlertObject implements Serializable {
    private String alertCode;
    private String alertMessage;
    private String alertType;
    public enum alertTypeEnum {SUCCESS, ERROR;}

    public AlertObject() {
    }

    public String getAlertCode() {
        return alertCode;
    }

    public void setAlertCode(String errorCode) {
        this.alertCode = errorCode;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }
}
