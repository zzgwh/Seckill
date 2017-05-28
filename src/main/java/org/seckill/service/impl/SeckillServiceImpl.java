package org.seckill.service.impl;

import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zzgwh
 *
 * 2017年5月28日
 */
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SeckillDao seckillDao;
	private SuccessKilledDao successKilledDao;
	
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	/* (non-Javadoc)
	 * @see org.seckill.service.SeckillService#exportSeckillUrl(long)
	 */
	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.seckill.service.SeckillService#executeSeckill(long, long, java.lang.String)
	 */
	@Override
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		// TODO Auto-generated method stub
		return null;
	}

}
