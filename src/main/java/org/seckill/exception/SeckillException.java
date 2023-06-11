package org.seckill.exception;

/**
 * 所有秒杀相关的业务异常
 * @author zzgwh
 *
 * 2017年5月23日
 */
public class SeckillException extends RuntimeException{
	public SeckillException(String message) {
		super(message);
	}

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}
}
