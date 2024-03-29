package com.fastcampus.ch4.domain;

public class PageHandler {
    private int totalCnt;       // 총 게시물 개수
    private int pageSize;       //한 페이지의 크기
    private int naviSize=10;    //페이지 네비게이션의 크기는 기본값 10
    private int totalPage;      //전체 페이지의 개수
    private int page;           //현재 페이지
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

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

    //페이지 계산하는데 pageSize를 전달하지 않을경우 기본값 10을 전달하는 생성자 호출
    public PageHandler(int totalCnt, int page){
        this(totalCnt, page, 10);
    }

    //페이지 계산하는데 필요한 3가지 변수를 받는 생성자 작성
    public PageHandler(int totalCnt, int page, int pageSize){
        this.totalCnt=totalCnt;
        this.page=page;
        this.pageSize=pageSize;

        //페이징에 필요한 변수들 구하기
        totalPage=(int)Math.ceil(totalCnt/(double)pageSize);
        //(정수/정수)의 올림은 +1이 안될 때도 존재하므로 페이지 사이즈를 형변환
        beginPage=(page-1)/naviSize*naviSize+1;
        endPage=Math.min(totalPage, beginPage+naviSize-1);

        showPrev=beginPage!=1;
        showNext=endPage!=totalPage;
    }

    void print(){
        System.out.println("page = " + page);
        System.out.print(showPrev?"[PREV] ": "");
        for(int i=beginPage; i<=endPage;i++){
            System.out.print(i+" ");
        }
        System.out.println(showNext ? " [NEXT] ":"");
    }

    @Override
    public String toString() {
        return "PageHandler{" +
                "totalCnt=" + totalCnt +
                ", pageSize=" + pageSize +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }
}
