package com.fastcampus.ch4.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageHandler {
//    private int page;           //현재 페이지
//    private int pageSize;       //한 페이지의 크기
//
//    private String option;
//    private String keyword;

    private SearchCondition sc;
    private int totalCnt;       // 총 게시물 개수
    private int naviSize=10;    //페이지 네비게이션의 크기는 기본값 10
    private int totalPage;      //전체 페이지의 개수
    private int beginPage;      //네비게이션의 첫번째 페이지
    private int endPage;        //네비게이션의 마지막 페이지
    private boolean showPrev;   //이전 페이지로 이동하는 링크를 보여줄 것인지의 여부
    private boolean showNext;   //다음 페이지로 이동하는 링크를 보여줄 것인지의 여부

    //getter, setter
    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

//    public int getPageSize() {
//        return pageSize;
//    }

//    public void setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//    }

    public SearchCondition getSc() {
        return sc;
    }

    public void setSc(SearchCondition sc) {
        this.sc = sc;
    }

    public int getNaviSize() {
        return naviSize;
    }

    public void setNaviSize(int naviSize) {
        this.naviSize = naviSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

//    public int getPage() {
//        return page;
//    }

//    public void setPage(int page) {
//        this.page = page;
//    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isShowPrev() {
        return showPrev;
    }

    public void setShowPrev(boolean showPrev) {
        this.showPrev = showPrev;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public PageHandler(int totalCnt, SearchCondition sc) {
        this.totalCnt=totalCnt;
        this.sc=sc;
        doPaging(totalCnt, sc);
    }

    //페이지 계산하는데 필요한 3가지 변수를 받는 생성자 작성
    //: doPaging 메서드로 변경
    public void doPaging(int totalCnt, SearchCondition sc){
        this.totalCnt=totalCnt;
        this.sc=sc;

        //페이징에 필요한 변수들 구하기
        totalPage=(int)Math.ceil(totalCnt/(double)sc.getPageSize());
        //(정수/정수)의 올림은 +1이 안될 때도 존재하므로 페이지 사이즈를 형변환
        beginPage=(sc.getPage()-1)/naviSize*naviSize+1;
        endPage=Math.min(totalPage, beginPage+naviSize-1);

        showPrev=beginPage!=1;
        showNext=endPage!=totalPage;
    }

    void print(){
        System.out.println("page = " + sc.getPage());
        System.out.print(showPrev?"[PREV] ": "");
        for(int i=beginPage; i<=endPage;i++){
            System.out.print(i+" ");
        }
        System.out.println(showNext ? " [NEXT] ":"");
    }

    @Override
    public String toString() {
        return "PageHandler{" +
                " SearchCount="+sc+
                ", totalCnt=" + totalCnt +
//                ", pageSize=" + pageSize +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
//                ", page=" + page +

                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }
}
