<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
	<script src="https://code.jquery.com/jquery-3.6.3.js"></script>
</head>
<body>
<div id="menu">
	<ul>
	    <li id="logo">fastcampus</li>
	    <li><a href="<c:url value='/'/>">Home</a></li>
	    <li><a href="<c:url value='/board/list'/>">Board</a></li>
	    <li><a href="<c:url value='/login/login'/>">login</a></li>    
	    <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
	    <li><a href=""><i class="fas fa-search small"></i></a></li>
	</ul> 
</div>
<div style="text-align:center">

	<h2>게시물 ${mode=="new" ? "글쓰기" : "읽기"}</h2>
	<form action="" id="form">
		<input type="hidden" name="bno" value="${boardDto.bno}" ${mode=="new" ? '' : 'readonly="readonly"'} >
		<input type="text" name="title"value="${boardDto.title}"${mode=="new" ? '' : 'readonly="readonly"'}>
		<textarea name="content" id="" cols="30" rows="10" ${mode=="new" ? '' : 'readonly="readonly"'} >${boardDto.content}</textarea>
		<button type="button" id="writeBtn" class="btn">글쓰기</button>
		<button type="button" id="modifyBtn" class="btn">수정</button>
		<button type="button" id="removeBtn" class="btn">삭제</button>
		<button type="button" id="listBtn" class="btn">목록</button>
	</form>

	</div>
<script>
	let msg="${msg}" //get의 경우 파라미터로 전달
	if(msg=="WRT_ERR") alert("등록에 실패했습니다.")
	if(msg=="UPDATE_ERR") alert("수정에 실패했습니다.")
</script>
</div>

<script>
	//브라우저가 DOM 로딩완료시 document.ready 실행
	//: html의 내용을 읽고 변경하기 위해서 사용
	$(document).ready(function (){//main
		//목록 버튼 처리
		//: id의 요소 찾는 경우 css의 셀렉터와 동일하게 '#'
		$('#listBtn').on("click", function (){
			alert("listBtn clicked")
			location.href="<c:url value='/board/list'/>?page=${page}&pageSize=${pageSize}";
		});
	});

	$(document).ready(function (){//main
		//삭제 버튼 처리 -> POST 처리
		//: id의 요소 찾는 경우 css의 셀렉터와 동일하게 '#'
		$('#removeBtn').on("click", function (){
			if(!confirm("정말로 삭제하시겠습니까?")) return;
			alert("removeBtn clicked")
			//id가 form인 요소에 대한 참조를 얻어서 객체를 가져온다.
			let form= $('#form');
			//action을 정해주기 위해 form이라는 변수에 속성으로 지정
			//:action을 '/board/remove'로 설정
			form.attr("action", "<c:url value='/board/remove'/>?page=${page}&pageSize=${pageSize}");

			//:method를 post로 설정
			form.attr("method", "post");

			form.submit();
		});
	});

	$(document).ready(function (){//main
		//등록 버튼 처리 -> POST 처리
		//: id의 요소 찾는 경우 css의 셀렉터와 동일하게 '#'
		$('#writeBtn').on("click", function (){
			// if(!confirm("정말로 등록하시겠습니까?")) return;
			alert("removeBtn clicked")
			//id가 form인 요소에 대한 참조를 얻어서 객체를 가져온다.
			let form= $('#form');
			//action을 정해주기 위해 form이라는 변수에 속성으로 지정
			//:action을 '/board/write'로 설정
			form.attr("action", "<c:url value='/board/write'/>");

			//:method를 post로 설정
			form.attr("method", "post");

			form.submit();
		});
	});

	$(document).ready(function (){//main
		//수정 버튼 처리 -> POST 처리
		//: id의 요소 찾는 경우 css의 셀렉터와 동일하게 '#'
		$('#modifyBtn').on("click", function (){
			alert("modifyBtn clicked")

			//1. 읽기 상태면 수정 상태로 변경
			let form= $('#form');
			//input 태그의 name이 title인 속성의 readonly 여부 저장
			let isReadOnly =$("input[name=title]").attr('readonly');
			if(isReadOnly=='readonly'){
				$("input[name=title]").attr('readonly', false);
				$("textarea").attr('readonly', false);
				$("modifyBtn").html("등록"); //버튼의 내용 변경
				$("h2").html("게시물 수정");
				//전송되지않고 리턴되도록
				return;
			}
			//2. 수정 상태이면, 수정된 내용을 서버로 전송
			//id가 form인 요소에 대한 참조를 얻어서 객체를 가져온다.
			//action을 정해주기 위해 form이라는 변수에 속성으로 지정
			//:action을 '/board/update'로 설정
			form.attr("action", "<c:url value='/board/modify'/>?page=${page}&pageSize=${pageSize}");
			//:method를 post로 설정
			form.attr("method", "post");
			form.submit();
		});
	});
</script>

</body>
</html>
