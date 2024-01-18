package com.lijun.canary.mapper;

import com.lijun.canary.entity.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lijun
 */
@Mapper
public interface AccountDao {

    /**
     * 插入
     * @param record .
     * @return int
     */
    int insert(Account record);

    /**
     * 选择插入
     * @param record .
     * @return int
     */
    int insertSelective(Account record);

    /**
     * 根据主键查询
     * @param id 主键ID
     * @return Account
     */
    Account selectByPrimaryKey(Long id);

    /**
     * 根据主键选择更新
     * @param record 。
     * @return int
     */
    int updateByPrimaryKeySelective(Account record);

    /**
     * 根据主键更新
     * @param record 。
     * @return int
     */
    int updateByPrimaryKey(Account record);
}
