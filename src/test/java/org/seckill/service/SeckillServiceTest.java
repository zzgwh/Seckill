package org.seckill.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zzgwh
 *
 * 2017年5月30日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> seckills = seckillService.getSeckillList();
		logger.info("seckill={}", seckills);
		//Closing non transactional SqlSession
	}

	@Test
	public void testGetById() {
		long seckillId = 1000L;
		Seckill seckill = seckillService.getById(seckillId);
		logger.info("seckill={}", seckill);
	}

	@Test
	public void testExportSeckillUrl() {
		long seckillId = 1000L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		logger.info("exposer={}", exposer);
		/*Exposer [
		exposed=true, md5=0780feadb1a19656ffea6214522599ab, 
		seckillId=1000, now=0, start=0, end=0]
		 */
	}

	@Test
	public void testExecuteSeckill() {
		long seckillId = 1000L;
		long phone = 12345988877L;
		String md5 = "0780feadb1a19656ffea6214522599ab";
		try {
			SeckillExecution execution =seckillService.executeSeckill(seckillId, phone, md5);
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage());
		}
		
		/*
		 DEBUG org.mybatis.spring.SqlSessionUtils - Creating a new SqlSession
01:41:25.167 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3370f42]
01:41:25.179 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@3b8f0a79] will be managed by Spring
01:41:25.185 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==>  Preparing: update seckill set number = number - 1 where seckill_id = ? and start_time <= ? and end_time >= ? and number > 0; 
01:41:25.241 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==> Parameters: 1000(Long), 2017-05-30 01:41:25.147(Timestamp), 2017-05-30 01:41:25.147(Timestamp)
01:41:25.243 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - <==    Updates: 1
01:41:25.244 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3370f42]
01:41:25.245 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3370f42] from current transaction
01:41:25.245 [main] DEBUG o.s.d.S.insertSuccessKilled - ==>  Preparing: insert ignore into success_killed(seckill_id, user_phone, state) values (?, ?, 0) 
01:41:25.247 [main] DEBUG o.s.d.S.insertSuccessKilled - ==> Parameters: 1000(Long), 12345988866(Long)
01:41:25.248 [main] DEBUG o.s.d.S.insertSuccessKilled - <==    Updates: 1
01:41:25.258 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3370f42]
01:41:25.259 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3370f42] from current transaction
01:41:25.261 [main] DEBUG o.s.d.S.queryByIdWithSeckill - ==>  Preparing: select sk.seckill_id, sk.user_phone, sk.create_time, sk.state, s.seckill_id "seckill.seckill_id", s.name "seckill.name", s.number "seckill.number", s.start_time "seckill.start_time", s.end_time "seckill.end_time", s.create_time "seckill.create_time" from success_killed sk inner join seckill s on sk.seckill_id = s.seckill_id where sk.seckill_id = ? and sk.user_phone = ? 
01:41:25.262 [main] DEBUG o.s.d.S.queryByIdWithSeckill - ==> Parameters: 1000(Long), 12345988866(Long)
01:41:25.288 [main] DEBUG o.s.d.S.queryByIdWithSeckill - <==      Total: 1
01:41:25.297 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3370f42]
01:41:25.298 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3370f42]
01:41:25.299 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3370f42]
01:41:25.299 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3370f42]
01:41:25.312 [main] INFO  o.seckill.service.SeckillServiceTest - result=org.seckill.dto.SeckillExecution@ae3540e
		 */
	}
	
	//集成测试代码完整逻辑，注意可重复执行
	@Test
	public void testSeckillLogic() {
		long seckillId = 1000L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if(exposer.isExposed()) {
			logger.info("exposer={}", exposer);
			long phone = 12345988877L;
			String md5 = exposer.getMd5();
			try {
				SeckillExecution execution =seckillService.executeSeckill(seckillId, phone, md5);
			} catch (RepeatKillException e) {
				logger.error(e.getMessage());
			} catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			}
		} else {
			//秒杀未开启
			logger.warn("exposer={}", exposer);
		}	
	}
	
	@Test
	public void executeSeckillProcedure() {
		long seckillId = 1L;
		long phone = 13821254121L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if (exposer.isExposed()) {
			String md5 = exposer.getMd5();
			SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
			logger.info(execution.getStateInfo());
		}
	}
}
