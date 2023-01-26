<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
    <h2>{name:"abc", age:10}</h2>
    <button id="sendBtn" type="button">SEND</button>
    <button id="modBtn" type="button">MODIFY</button>
    comment: <input type="text" name="comment"><br>
    <button id="writeBtn" type="button">WRITE</button>
    <h2>Data From Server :</h2>
    <div id="commentList"></div>
    <script>

        let bno=1
        let showList=function (bno){
            $.ajax({
                type:'GET',       // 요청 메서드
                url: '/ch4/comments?bno='+bno,  // 요청 URI
                headers : { "content-type": "application/json"}, // 요청 헤더
                dataType : 'json', // 전송받을 데이터의 타입으로 생략할경우 json으로 전송 -> Json이면 변환 필요없음
                // data : JSON.stringify(person),  // 서버로 전송할 데이터. stringify()로 직렬화 필요. -> Json이면 변환 필요없음
                success : function(result){
                    alert(result);
                    //문자열을 toHtml 메서드로 html로 변환
                    $("#commentList").html(toHtml(result));     //commentList의 내용을 result에 담는다.
                    // person2 = JSON.parse(result);    // 서버로부터 응답이 도착하면 호출될 함수 -> Json이면 변환 필요없음
                    alert("received="+toHtml(result));       // result는 서버가 전송한 데이터
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        }
        $(document).ready(function() {

            showList(bno);
            //id는 #
            $("#sendBtn").click(function () {
                showList(bno);
            });
            $("#writeBtn").click(function () {
                let comment = $("input[name=comment]").val();
                if (comment.trim() == '') {
                    alert("댓글을 입력해주세요.");
                    $("input[name=comment]").focus()
                    return;
                }
                $.ajax({
                    type    : 'POST',       // 요청 메서드
                    url     : '/ch4/comments?bno=' + bno,  // 요청 URI
                    headers : {"content-type": "application/json"}, // 요청 헤더
                    dataType: 'text', // 전송받을 데이터의 타입
                    data    : JSON.stringify({bno: bno, comment: comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                    success : function (result) {
                        alert(result)
                        showList(bno);
                    },
                    error   : function () {
                        alert("error")
                    } // 에러가 발생했을 때, 호출될 함수
                }); // $.ajax()
            });
            //클래스는 .
            //: 요청이 오기전에 실행되어 이벤트 요청이 걸리지 않는다. (이벤트는 고정된 요소에 걸도록 변경)
            // $(".delBtn").click(function(){

            //동적으로 생성된 요소에 이벤트를 거는 방법
            $("#commentList").on("click", ".delBtn", function () {

                //li에 존재하는 cno을 가져온다
                //: data-로 시작하는 attribute로 사용자 정의 속성 ==$0은 선택한 데이터의 참조
                //현재 객체의 부모의 속성을 가져온다.
                let cno = $(this).parent().attr("data-cno");
                let bno = $(this).parent().attr("data-bno");

                alert("delete button clicked");
                $.ajax({
                    type    : 'DELETE',       // 요청 메서드
                    url     : '/ch4/comments/' + cno + '?bno=' + bno,  // 요청 URI
                    headers : {"content-type": "application/json"}, // 요청 헤더
                    dataType: 'json', // 전송받을 데이터의 타입으로 생략할경우 json으로 전송 -> Json이면 변환 필요없음
                    // data : JSON.stringify(person),  // 서버로 전송할 데이터. stringify()로 직렬화 필요. -> Json이면 변환 필요없음
                    success: function (result) {
                        alert(result)
                        //목록 갱신을 위해
                        showList(bno);
                    },
                    error  : function () {
                        alert("error")
                    } // 에러가 발생했을 때, 호출될 함수
                }); // $.ajax()
            });

            //수정 버튼
            //1. 수정버튼 클릭시 커멘트 내용이 인풋태그에 넣어준다.
            //2. 수정 완료 버튼 클릭시 컨트롤러의 update메서드 실행돼 내용이 갱신
            //3. 댓글번호인 cno과 커멘트 내용 전달
            //동적으로 생성된 요소에 이벤트를 거는 방법
            $("#commentList").on("click", ".modBtn", function () {

                //li에 존재하는 cno을 가져온다
                //: data-로 시작하는 attribute로 사용자 정의 속성 ==$0은 선택한 데이터의 참조
                //현재 객체의 부모의 속성을 가져온다.
                let cno = $(this).parent().attr("data-cno");

                //li아래의 span태그 comment 클래스인데 (인자1), 그중 클릭한 객체인 li의 부모로 범위 제한(인자2)
                let comment = $("span.comment", $(this).parent()).text();

                //1. 수정버튼 클릭시 커멘트 내용을 인풋태그에 넣어준다.
                $("input[name=comment]").val(comment);
                //: id인 modBtn의 속성으로 추가
                $("#modBtn").attr("data-cno", cno);
                //2. 수정 완료 버튼 클릭시 컨트롤러의 update 메서드 실행돼 내용이 갱신
            });


            //id는 #
            $("#modBtn").click(function () {
                //해당 객체의 속성에서 가져옴
                let cno = $(this).attr("data-cno");
                let comment = $("input[name=comment]").val();
                if (comment.trim() == '') {
                    alert("댓글을 입력해주세요.");
                    $("input[name=comment]").focus()
                    return;
                }
                $.ajax({
                    type    : 'PATCH',       // 요청 메서드
                    url     : '/ch4/comments/'+cno,  // 요청 URI
                    headers : {"content-type": "application/json"}, // 요청 헤더
                    // dataType: 'json', // 전송받을 데이터의 타입
                    data    : JSON.stringify({cno: cno, comment: comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                    success : function (result) {
                        alert(result)
                        showList(bno);
                    },
                    error   : function () {
                        alert("error")
                    } // 에러가 발생했을 때, 호출될 함수
                }); // $.ajax()
            });
        });

        //문자열을 HTML로 만드는 함수
        let toHtml= function (comments){
            let tmp="<ul>";

            comments.forEach(function(comment){
                tmp+='<li data-cno='+comment.cno
                tmp+=' data-pcno='+comment.pcno
                tmp+=' data-bno='+comment.bno+ '>'
                tmp+=' commenter=<span c  lass="commenter">' + comment.commenter+'</span>'
                tmp+=' comment=<span class="comment">' + comment.comment+'</span>'
                tmp+=' up_date='+ comment.up_date
                tmp+='<button class="delBtn">삭제</button>'
                tmp+='<button class="modBtn">수정</button>'
                tmp+='</li>'
                //commenter를 가져오기위해 span태그에 감싼다.

            })
            return tmp+ "</ul>";
        }

    </script>
</body>
</html>
