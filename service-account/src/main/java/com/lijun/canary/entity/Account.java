package com.lijun.canary.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * @author : LiJun
 * @since : 2024-01-15 14:22
 */
@Data
public class Account implements Serializable {
    private Long id;

    /**
     * 姓名
     */
    private String accountName;

    /**
     * 手机号
     */
    private String phone;

    private static final long serialVersionUID = 1L;
}
