package com.fastcampus.ch4.domain;

import org.springframework.web.util.UriComponentsBuilder;

//검색 조건 클래스
//
public class SearchCondition {
    //command + shift + 8, esc
    private Integer page=1;
    private Integer pageSize=10;
//    private Integer offset=0;
    private String keyword="";
    private String option="";

    //getter, setter

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

//    public Integer getOffset() {
//        return offset;
//    }
    public Integer getOffset() {
        System.out.println((page-1)*pageSize);
        return (page-1)*pageSize;
    }

//    public void setOffset(Integer offset) {
//        this.offset = offset;
//    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    //기본 생성자, 생성자 (offset 제외)

    public SearchCondition(){}
    public SearchCondition(Integer page, Integer pageSize, String keyword, String option) {
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.option = option;
    }
    //toString

    @Override
    public String toString() {
        return "SearchCondition{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", offset=" + getOffset() +
                ", keyword='" + keyword + '\'' +
                ", option='" + option + '\'' +
                '}';
    }

    //검색 정보와 페이지 정보를 담은 SearchCondition을 쿼리스트링에 메서드로 전달
    //현재 페이지 정보를 전달해 쿼리스트링 반환
    //:페이지 지정시
    public String getQueryString(Integer page){
        //?page=1&pageSize=10&option=T&keyword="title"
        //UriComponentsBuilder가 UriComponents 생성
        return UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
                .queryParam("option", option)
                .queryParam("keyword", keyword)
                .build().toString();
    }

    //페이지 지정하지 않을 시
    public String getQueryString(){
        return getQueryString(page);
    }

}
