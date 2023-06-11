package org.seckill.exception;

/**
 * 秒杀关闭异常
 * @author zzgwh
 *
 * 2017年5月23日
 */
public class SeckillCloseException extends SeckillException{

	public SeckillCloseException(String message) {
		super(message);
	}
	
	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}
}
