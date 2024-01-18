package com.lijun.canary.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : LiJun
 * @date : 2024-01-15 14:22
 **/
@Data
public class MemberPoints implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Double amount;

    private Long uid;
}
