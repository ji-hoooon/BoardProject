//package com.fastcampus.ch4.controller;
//
//import com.fastcampus.ch4.domain.CommentDto;
//import com.fastcampus.ch4.service.CommentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.xml.stream.events.Comment;
//import java.util.List;
//
//@Controller
//public class CommentController {
//    @Autowired
//    CommentService service;
//
//    @GetMapping("/comments")
//    @ResponseBody public List<CommentDto> list(Integer bno){
//        List<CommentDto> list = null;
//
//        try{
//            list = service.getList(bno);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return list;
//    }
//}
