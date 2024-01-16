package com.lijun.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : LiJun
 * @date : 2024-01-15 14:27
 **/
@Mapper
public interface MemberPointsMapper {

    /**
     * 更新积分
     * @param uid 用户id
     * @param amount 积分余额
     */
    void updateByUid(@Param("uid") Long uid, @Param("amount") Double amount);

    /**
     * 是否存在
     * @param uid 用户id
     * @return 是否存在
     */
    Boolean existByUid(Long uid);
}
