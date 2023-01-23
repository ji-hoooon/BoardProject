package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class BoardDaoImpl implements BoardDao {

    //root-context에 등록한 SqlSessiontemplate 의존성 주입
    @Autowired
    SqlSession session;

    //mapper 네임스페이스 지정 sql id을 뒤에 추가하기 위해 마지막에 '.'
    String namespace="com.fastcampus.ch4.dao.BoardMapper.";

    //서비스 계층으로 예외 선언
    @Override
    public BoardDto select(Integer bno) throws Exception{
        return session.selectOne(namespace+"select", bno);
    }

    @Override
    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    } // T selectOne(String statement)

    @Override
    public int deleteAll() {
        return session.delete(namespace+"deleteAll");
    } // int delete(String statement)

    @Override
    //삭제시에는 게시물 번호와 작성자가 일치할 때만
    public int delete(Integer bno, String writer) throws Exception {
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("writer", writer);
        return session.delete(namespace+"delete", map);
    } // int delete(String statement, Object parameter)

    @Override
    public int insert(BoardDto dto) throws Exception {
        return session.insert(namespace+"insert", dto);
    } // int insert(String statement, Object parameter)

    @Override
    public List<BoardDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    } // List<E> selectList(String statement)


    @Override
    public List<BoardDto> selectPage(Map map) throws Exception {
        return session.selectList(namespace+"selectPage", map);
    } // List<E> selectList(String statement, Object parameter)

    @Override
    public int update(BoardDto dto) throws Exception {
        return session.update(namespace+"update", dto);
    } // int update(String statement, Object parameter)

    @Override
    public int increaseViewCnt(Integer bno) throws Exception {
        return session.update(namespace+"increaseViewCnt", bno);
    } // int update(String statement, Object parameter)

//    @Override
//    public int searchResultCnt(SearchCondition sc) throws Exception {
//        System.out.println("sc in searchResultCnt() = " + sc);
//        System.out.println("session = " + session);
//        return session.selectOne(namespace+"searchResultCnt", sc);
//    } // T selectOne(String statement, Object parameter)
//
//    @Override
//    public List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception {
//        return session.selectList(namespace+"searchSelectPage", sc);
//    } // List<E> selectList(String statement, Object parameter)

}
