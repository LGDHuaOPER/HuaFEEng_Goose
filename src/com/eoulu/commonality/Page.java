package com.eoulu.commonality;


/**
 * ��ҳ��ʵ����
 * 
 * @author zhangkai
 * 
 * @date 2017/3/22
 * */
public class Page {

	
	/**
	 * һҳ��ʾ������
	 * */
	private int rows;
	/**
	 * ���м�¼��
	 * */
	private int recordCounts;
	/**
	 * ��ҳ��
	 * */
	private int pageCounts;
	/**
	 * ��ǰҳ
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
