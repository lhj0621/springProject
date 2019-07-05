package iducs.springboot.board.utils;

public class PageInfo {
	private long startPage; //시작 페이지
	private long endPage;   //끝 페이지
	private long curPage;   //현제 페이지
	private long startCut;  //첫 번호
	private long endCut;    //끝 번호
	private boolean prevPage;  //이전 페이지
	private boolean nextPage;  //다음 페이지
	
	public PageInfo(long curPage, long endPage) {
		this.startPage = 1;
		this.curPage = curPage;
		this.endPage = endPage;
	}
	
	public void setting(long pageCount) {
		startCut = (curPage -1) / pageCount * pageCount +1; 
		endCut = (startCut + pageCount - 1 < endPage) ? startCut + pageCount -1 : endPage;		
		prevPage = (startCut != 1) ? true : false;
		nextPage = (startCut +pageCount <= endPage) ? true : false;
	}
	
	public long getStartPage() {
		return startPage;
	}
	public void setStartPage(long startPage) {
		this.startPage = startPage;
	}
	public long getEndPage() {
		return endPage;
	}
	public void setEndPage(long endPage) {
		this.endPage = endPage;
	}
	public long getCurPage() {
		return curPage;
	}
	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}
	public long getStartCut() {
		return startCut;
	}
	public void setStartCut(long startCut) {
		this.startCut = startCut;
	}
	public long getEndCut() {
		return endCut;
	}
	public void setEndCut(long endCut) {
		this.endCut = endCut;
	}
	public boolean isPrevPage() {
		return prevPage;
	}
	public void setPrevPage(boolean prevPage) {
		this.prevPage = prevPage;
	}
	public boolean isNextPage() {
		return nextPage;
	}
	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}
}