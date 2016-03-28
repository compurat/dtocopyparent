package com.purat;

import com.purat.dto.copy.annotations.CopyDto;

@CopyDto(copyFromPackage = "com.purat",copyToPackage = "com.purat.copied")
public class TestBean extends Object{
    private String a;
    private int b;
    private Long c;

    public TestBean() {

    }

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
