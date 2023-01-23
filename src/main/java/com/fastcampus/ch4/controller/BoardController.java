package com.fastcampus.ch4.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.service.BoardService;
import com.mysql.cj.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelExtensionsKt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;
    //수정
    @GetMapping("/modify")
    public String modify(Model m){
        m.addAttribute("mode", "new");
        return "board"; //읽기와 쓰기에 사용, 쓰기에 사용할때는 mode=new
    }

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, HttpSession session, Model m, RedirectAttributes rattr){
        //작성자 ID 정보를 얻기 위한 문자열
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt =boardService.modify(boardDto);

            //DB에 제대로 등록되었는지 확인
            if(rowCnt!=1){
                throw new Exception("Modify Failed");
            }
            rattr.addFlashAttribute("msg", "MOD_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            //에러 발생시 작성하던 글로 이동하는데, 작성하던 글 정보를 담아서 전송
            m.addAttribute(boardDto);
            m.addAttribute("msg", "MOD_ERR");
            return "board";
        }
    }

    //쓰기
    @GetMapping("/write")
    public String write(Model m){
        m.addAttribute("mode", "new");
        return "board"; //읽기와 쓰기에 사용, 쓰기에 사용할때는 mode=new
    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, HttpSession session, Model m, RedirectAttributes rattr){
        //작성자 ID 정보를 얻기 위한 문자열
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt =boardService.write(boardDto);

            //DB에 제대로 등록되었는지 확인
            if(rowCnt!=1){
                throw new Exception("Write Failed");
            }
            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            //에러 발생시 작성하던 글로 이동하는데, 작성하던 글 정보를 담아서 전송
            m.addAttribute(boardDto);
            m.addAttribute("msg", "WRT_ERR");
            return "board";
        }
    }

    //삭제
    @PostMapping("/remove")
    public String remove(HttpSession session, Integer bno, Integer page, Integer pageSize, Model m, RedirectAttributes rattr){
        //HttpSession으로 세션 정보가져와서, 속성값인 id를 String으로 저장
       String writer=(String) session.getAttribute("id");
        try {
            //현재 페이지 정보와 페이지 크기 정보 전달하면, 리다이렉트시 쿼리스트링으로 자동생성
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
            //remove 메서드 호출해 writer가 맞으면 삭제
            //: 게시물에서 삭제 버튼이 안보이도록 1차 처리
            int rowCnt= boardService.remove(bno,writer);
            if(rowCnt!=1)
            throw new Exception("board remove error");
            {
//                m.addAttribute("msg", URLEncoder.encode("삭제되었습니다."));
                m.addAttribute("msg", "DEL_OK");
                return "redirect:/board/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
//            m.addAttribute("msg", "DEL_ERR");
            //세션에 저장해 일회성으로 전달
            rattr.addFlashAttribute("msg", "DEL_ERR");
        }
        return "redirect:/board/list";
    }

    //읽기
    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize,Model m){
        try {
            BoardDto boardDto=boardService.read(bno);
//            m.addAttribute("boardDto", boardDto);
            m.addAttribute(boardDto);
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "board";
    }


    @GetMapping("/list")
        public String list (@ModelAttribute SearchCondition sc, Model m, HttpServletRequest request){

            if (!loginCheck(request))
                return "redirect:/login/login?toURL=" + request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

            try {
                int totalCnt = boardService.getSearchResultCnt(sc);
                m.addAttribute("totalCnt", totalCnt);

                PageHandler pageHandler = new PageHandler(totalCnt, sc);

                List<BoardDto> list = boardService.getSearchResultPage(sc);
                m.addAttribute("list", list);
                m.addAttribute("ph", pageHandler);

                Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
                m.addAttribute("startOfToday", startOfToday.toEpochMilli());
            } catch (Exception e) {
                e.printStackTrace();
                m.addAttribute("msg", "LIST_ERR");
                m.addAttribute("totalCnt", 0);
            }

            return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
        }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}
