package com.java.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询条件实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConditionVo {
    private Integer typeid;
    private String author;
    private Double min_price;
    private Double max_price;

}
