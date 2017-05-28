package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

/**
 * @author zzgwh
 *
 * 2017年5月8日
 */
public interface SeckillDao {
	/**
	 * 减库存
	* @param seckillId
	* @param killTime
	* @return int （如果影响行数>1，表示更新的记录行数）
	* @throws
	 */
	int reduceNumber(@Param("seckillId")long seckillId, @Param("killTime")Date killTime);
	
	/**
	 * 根据id查询秒杀库存对象
	* @param seckillId
	* @return Seckill 
	* @throws
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * 根据偏移量查询秒杀商品列表
	* @param offset
	* @param limit
	* @return List<Seckill> 
	* @throws
	 */
	List<Seckill> queryAll(@Param("offset")int offset, @Param("limit")int limit);
}
