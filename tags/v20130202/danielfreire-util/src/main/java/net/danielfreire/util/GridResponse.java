package net.danielfreire.util;

import java.util.ArrayList;
import java.util.List;

public class GridResponse {
	
	private Integer page;
	private Integer totalPages;
	private ArrayList<GridTitleResponse> titles;
	private List<?> rows;
	
	public Integer getPage() {
		return page;
	}


	public void setPage(Integer page) {
		this.page = page;
	}


	public Integer getTotalPages() {
		return totalPages;
	}


	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}


	public ArrayList<GridTitleResponse> getTitles() {
		return titles;
	}


	public void setTitles(ArrayList<GridTitleResponse> titles) {
		this.titles = titles;
	}


	public List<?> getRows() {
		return rows;
	}


	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
