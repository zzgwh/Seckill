package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * @author zzgwh
 *
 * 2017年5月8日
 */
public interface SuccessKilledDao {
	/**
	 * 插入购买明细，可过滤重复（数据库联合主键）
	* @param seckillId
	* @param userPhone
	* @return int （插入的行数）
	* @throws
	 */
	int insertSuccessKilled(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
	
	/**
	 * 根据id和userPhone查询购买明细并携带秒杀产品对象实体
	* @param seckillId
	* @return SuccessKilled 
	* @throws
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
}
