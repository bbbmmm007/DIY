package com.zhny.computer.vo;

import lombok.Data;

@Data
public class ProductVO {
    private String itemType;
    private Integer ancestorId;
    private Integer parentId;
    private Integer childId;
    private String sortOrder;
}
