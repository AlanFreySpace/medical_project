<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人画像</title>
    <link href="https://cdn.bootcss.com/jquery-contextmenu/3.0.0-beta.2/jquery.contextMenu.min.css" rel="stylesheet">
    <link href="../../resources/admin/jquery.image-label.css" rel="stylesheet">
</head>
<body>
    <button id="btnCreate">重新生成画像</button>
    <button id="btnSave">保存画像</button>
    <button id="btnShow">隐藏画像</button>
    <button id="btnCon">自定义画像</button>
    <img src="../../resources/admin/demo.jpg" style="width:1500px;">

    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/json2/20160511/json2.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery-contextmenu/3.0.0-beta.2/jquery.contextMenu.min.js"></script>
    <script src="../../resources/admin/jquery.image-label.js"></script>
    <script>
    
    console.log("${userpic}");
    console.log("${id}");
    
        //初始化
        $('img').imageLabel();
        //加载数据
        var _arr = JSON.parse(localStorage.getItem('${id}'));
        //var kk=3;
        //var _arr = JSON.parse(localStorage.getItem(kk));
        $('img').imageLabel('loadData', {
            data: _arr
        });
        //create
        $('#btnCreate').click(function(){
            /*$('img').imageLabel('create');*/
            var pic="${userpic}";
            var content="";
            if(pic!=""){
            	pic.split(",").forEach(
            			function(val){
            				content=content+val+"<br/>";
            			}
            			);
            }
            $('img').imageLabel('create', {
                text:content
            });
        });
        
        $('#btnCon').click(function(){
        	$('img').imageLabel('create');		
        }); 
        
        //save
        $('#btnSave').click(function(){
            var data = $('img').imageLabel('getData');
            localStorage.setItem('${id}', JSON.stringify(data));
            //localStorage.setItem(kk, JSON.stringify(data));
        });
        //toggle
        $('#btnShow').click(function(){
            var isShow = $(this).attr('_flag');
            isShow = typeof(isShow)=='undefined'?1:isShow;
            if (isShow && isShow==1){
                $(this).text('展示标签');
                $('img').imageLabel('hide');
                $(this).attr('_flag', 0);
            }else{
                $(this).text('隐藏标签');
                $('img').imageLabel('show');
                $(this).attr('_flag', 1);
            }
        });

        $.contextMenu({
            selector: '.kbs-label-area',
            callback: function(key, options) {
                var e = window.event || arguments[0];
                $('img').imageLabel('create', {
                    top: $('.context-menu-list')[0].offsetTop || e.clientY,
                    left: $('.context-menu-list')[0].offsetLeft || e.clientX
                });
            },
            items: {
                "create": {name: "新增标签"}
            }
        });
    </script>
</body>
</html>