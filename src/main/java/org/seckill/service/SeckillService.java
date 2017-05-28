package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

/**
 * 业务接口：站在“使用者”的角度去设计接口
 * 三个方面：方法定义的粒度，参数，返回类型(return 类型/异常)
 * @author zzgwh
 *
 * 2017年5月23日
 */
public interface SeckillService {

	/**
	 * 查询所有秒杀库存记录
	* @return List<Seckill> 
	* @throws
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀库存记录
	* @param seckillId
	* @return Seckill 
	* @throws
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
	* @param seckillId 
	* return Exposer 
	* @throws
	 */
	Exposer exportSeckillUrl(long seckillId);

	/**
	 * 执行秒杀操作
	* @param seckillId
	* @param userPhone
	* @param md5 void 
	* @throws RepeatKillException,SeckillCloseException 这两个异常是为了明确告诉用户异常是是什么
	 SeckillException 此异常告诉用户秒杀错误
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
	throws SeckillException,RepeatKillException,SeckillCloseException;
}
