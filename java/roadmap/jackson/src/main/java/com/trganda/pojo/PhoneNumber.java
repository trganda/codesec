package com.trganda.pojo;

public class PhoneNumber {
    public int areaCode;
    public int local;

    public PhoneNumber() {

    }

    public void setAreaCode(int areaCode) {
        System.out.println("call setAreaCode");
        this.areaCode = areaCode;
    }

    public PhoneNumber(int areaCode, int local) {
        this.areaCode = areaCode;
        this.local = local;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
            "areaCode=" + areaCode +
            ", local=" + local +
            '}';
    }
}
