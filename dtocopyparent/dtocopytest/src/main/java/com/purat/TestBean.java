package com.purat;

import com.purat.dto.copy.annotations.CopyDto;

/**
 * Created by compurat on 3/16/16.
 */
@CopyDto(copyFromPackage = "com.purat",copyToPackage = "com.purat.copied")
public class TestBean {
    private String a;
    private int b;
    private Long c;


    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public Long getC() {
        return c;
    }

    public void setC(Long c) {
        this.c = c;
    }
}
