package com.xzc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Echart {

    private List<String> nameList;
    private List<Long> dataList;

}
