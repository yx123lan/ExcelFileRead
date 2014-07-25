package com.zxt.table;

public enum TimeBucket {
	AM("上午"), PM("下午");
	
	private String timeStr;
	private TimeBucket(String timeStr){
		this.timeStr = timeStr;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return timeStr;
	}
}
