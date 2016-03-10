package com.sun.comm.base;

/**
 * Created by qxh on 2015/11/10.
 */
public class Page {
    private int page = 0;
    private int perpage = 0;
    private int start = 0;

    public Page(int page, int perpage) {
        this.page = Math.max(page,1);
        this.perpage = Math.max(perpage,1);
        this.start = (this.page-1) * this.perpage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

}
