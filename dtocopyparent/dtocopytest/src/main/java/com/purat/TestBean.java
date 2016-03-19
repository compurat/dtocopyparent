package com.purat;

import com.purat.dto.copy.annotations.CopyDto;

/**
 * Created by compurat on 3/16/16.
 */
@CopyDto(copyTo = "com.purat")
public class TestBean {


    public void calculate() {
        System.out.println("1 + 1 = 2");
    }
}
