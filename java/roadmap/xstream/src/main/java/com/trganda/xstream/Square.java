package com.trganda.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("square")
public class Square {
    @XStreamAlias("size")
    int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
