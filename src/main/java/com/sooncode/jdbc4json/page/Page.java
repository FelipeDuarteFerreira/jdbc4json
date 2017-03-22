package com.sooncode.jdbc4json.page;
 

/**
 * 分页
 * 
 * @author hechen
 *
 * @param <T>
 */
public class Page  {
	
	//------------------------------------------ 属性 --------------------------------------------------------
	
	private    Result  result;
	//private JsonBean  jsonBean;
	/** 总记录数 */
	private Long total = 0L; // 总记录数
	
	/** 每页显示记录数 */
	private Long pageSize = 20L; // 默认20
	
	/** 总页数 */
	private Long totalPages = 1L; // 总页数
	
	/** 当前页 */
	private Long pageNumber = 1L; // 当前页

	/** 是否为第一页 */
	private boolean isFirstPage = false; // 是否为第一页
	
	/** 是否为最后一页 */
	private boolean isLastPage = false; // 是否为最后一页
	
	/** 是否有前一页 */
	private boolean hasPreviousPage = false; // 是否有前一页
	
	/** 是否有下一页 */
	private boolean hasNextPage = false; // 是否有下一页
	
	//--------------------------------------------- 构造器 ----------------------------------------------------------------

 
	public Page (long pageNumber, long pageSize, long total, Result  result) {
		init( total ,pageNumber, pageSize);
		this.result = result;
	}
	
	 

	/** 设置基本参数 */
	private void init(long total, long pageNumber, long pageSize) {
		// 设置基本参数
		this.total = total;
		this.pageSize = pageSize;
		if(total==0L){
			this.totalPages= 0L;	
		}else{
			this.totalPages = (this.total - 1) / this.pageSize + 1;
		}

		// 根据输入可能错误的当前号码进行自动纠正
		if (pageNumber < 1) {
			this.pageNumber = 1L;
		} else if (pageNumber > this.totalPages) {
			this.pageNumber = this.totalPages;
		} else {
			this.pageNumber = pageNumber;
		}
		judgePageBoudary();
	}

	/**
	 * 判定页面边界
	 */
	private void judgePageBoudary() {
		isFirstPage = pageNumber == 1;
		isLastPage = pageNumber == totalPages && pageNumber != 1;
		hasPreviousPage = pageNumber > 1;
		hasNextPage = pageNumber < totalPages;
	}
//-----------------------------------------------------get set 方法-----------------------------------------------------------------------
	
	 
//--------------------------------------------------------------------------------
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	//--------------------------------------------------------------------------------
	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	//--------------------------------------------------------------------------------
	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	
	//--------------------------------------------------------------------------------
	public Long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	//--------------------------------------------------------------------------------
	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	//--------------------------------------------------------------------------------
	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	//--------------------------------------------------------------------------------
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	//--------------------------------------------------------------------------------
	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

 
	 
	public <L> One<L> getOne(){
		@SuppressWarnings("unchecked")
		One<L> one = (One<L>) result;
		return one;
	}
 
	
	public <L,R> One2One<L,R> getOne2One(){
		@SuppressWarnings("unchecked")
		One2One<L,R> o2o = (One2One<L,R>) result;
		return o2o;
	}
	public <L,R> One2Many<L,R> getOne2Many(){
		@SuppressWarnings("unchecked")
		One2Many<L,R> o2m = (One2Many<L,R>) result;
		return o2m;
	}
	public <L,M,R> Many2Many<L,M,R> getMany2Many(){
		@SuppressWarnings("unchecked")
		Many2Many<L,M,R> m2m = (Many2Many<L,M,R>) result;
		return m2m;
	}
	
	
	
}