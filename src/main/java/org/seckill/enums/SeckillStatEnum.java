package org.seckill.enums;

/**
 * 使用枚举表示常量数据字典
 * @author zzgwh
 * 2017年5月28日
 */
public enum SeckillStatEnum {
	SUCCESS(1,"秒杀成功"),
	END(0,"秒杀结束"),
	REPEAT_KILL(-1,"重复秒杀"),
	INNER_ERROR(-2,"系统异常"),
	DATE_REWRITE(-3,"数据篡改");
	private int state;
	private String StateInfo;
	
	private SeckillStatEnum(int state, String stateInfo) {
		this.state = state;
		StateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return StateInfo;
	}

	public void setStateInfo(String stateInfo) {
		StateInfo = stateInfo;
	}
	
	public static SeckillStatEnum stateof(int index) {
		for (SeckillStatEnum state : values()) {
			if(state.getState() == index) {
				return state;
			}
		}
		
		return null;
	}
}
