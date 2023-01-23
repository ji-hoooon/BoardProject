package com.fastcampus.ch4.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageHandlerTest {

    //페이징 테스트 시 시작페이지, 마지막페이지와 같은 경계 페이지를 빠짐없이 테스트해야한다.
    @Test
    public void pagingTest1(){
        PageHandler ph = new PageHandler(250, 1);
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.getBeginPage() ==1);
        assertTrue(ph.getEndPage() ==10);
    }
    @Test
    public void pagingTest2(){
        PageHandler ph = new PageHandler(250, 11);
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.getBeginPage() ==11);
        assertTrue(ph.getEndPage() ==20);
    }
    @Test
    public void pagingTest3(){
        PageHandler ph = new PageHandler(255, 25);
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.getBeginPage() ==21);
        assertTrue(ph.getEndPage() ==26);
    }
    @Test
    public void pagingTest4(){
        PageHandler ph = new PageHandler(255, 10);
        ph.print();
        System.out.println("ph = " + ph);
        assertTrue(ph.getBeginPage() ==1);
        assertTrue(ph.getEndPage() ==10);
    }

}