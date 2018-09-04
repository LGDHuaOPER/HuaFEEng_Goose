package com.eoulu.commonality;


/**
 * 分页的实体类
 * 
 * @author zhangkai
 * 
 * @date 2017/3/22
 * */
public class Page {

	
	/**
	 * 一页显示的行数
	 * */
	private int rows;
	/**
	 * 所有记录数
	 * */
	private int recordCounts;
	/**
	 * 总页数
	 * */
	private int pageCounts;
	/**
	 * 当前页
	 * */
	private int currentPage;


	public Page(){
		this.rows = 15;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRecordCounts() {
		return recordCounts;
	}

	public void setRecordCounts(int recordCounts) {
		this.recordCounts = recordCounts;
		this.pageCounts = recordCounts%rows==0?((recordCounts/rows)==0?1:(recordCounts/rows)):recordCounts/rows+1;
	}

	public int getPageCounts() {
		return pageCounts;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if(currentPage>0)
			this.currentPage = currentPage;
		else
			this .currentPage = 1;
	}


}
