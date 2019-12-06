package com.lanswon.management.domain.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/3 16:35
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 8875576360114757274L;
    /**
     * 当前页数，默认第一页
     */
    private int currentPage = 1;
    /**
     * 每页个数，默认20
     */
    private int numPerPage = 20;
    /**
     * 总数，默认0
     */
    private int totalCount = 0;

    /**
     * 返回的集合
     */
    private List<T> list = new ArrayList<>();

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 计算总页数
     * @return
     */
    public int getPageCount() {
        pageCount=totalCount/numPerPage;
        if(totalCount%numPerPage!=0){
            pageCount++;
        }
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 判断是否是首页
     * @return
     */
    public boolean isFirstPage() {
        return currentPage<=1?true:false;
    }

    /**
     * 判断是否是尾页
     * @return
     */
    public boolean isLastPage() {
        return currentPage>=getPageCount()?true:false;
    }

    /**
     * @param currentPage
     * @param numPerPage
     * @param totalCount
     */
    public Page(int currentPage, int numPerPage, int totalCount) {
        super();
        this.currentPage = currentPage;
        this.numPerPage = numPerPage;
        this.totalCount = totalCount;
    }

    /**
     * 得到分页后的数据(list分页)
     *
     * @return 分页后结果
     */
//    public List<T> getPagedList() {
//        int fromIndex = (currentPage - 1) * numPerPage;
//        if (fromIndex >= list.size()) {
//            //空数组
//            return Collections.emptyList();
//        }
//        if(fromIndex<0){
//            //空数组
//            return Collections.emptyList();
//        }
//        int toIndex = currentPage * numPerPage;
//        if (toIndex >= list.size()) {
//            toIndex = list.size();
//        }
//        return list.subList(fromIndex, toIndex);
//    }


    public int getCurrentPage() {
        return currentPage;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

//    /**
//     *  页标数字多少个，默认10
//     */
//    private int pageNumShown = 10;
//    /**
//     * 排序方式
//     */
//    private String orderField;
//    /**
//     * 升序降序
//     */
//    private String orderDirection;

}
