<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="ico/1.ico">

    <title>图像检索</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
  

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	
</head>
<body background="ico/bg.jpg">
<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">图像检索</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="textSearch.jsp">金庸小说检索</a></li>
           
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
<script type="text/javascript">
	function f(val){
		if(val == 1){
			document.getElementById("hd").value ="hash";
		}else{
			document.getElementById("hd").value ="nohash";
		}
	}
</script>
    <div class="container">

      <div class="starter-template">
        <h1>图片检索</h1>
        
      </div>
		<div class="modal-body">
         <form action="UploadServlet" method="POST" enctype="multipart/form-data">
          <div class="form-group">
            <label for="recipient-name" class="control-label">请选择上传的图片:</label>
            <input type="file" name="file" class="form-control" >
          </div>
          <input type = "hidden" name ="types" id="hd" value= "1"/> 
          <div class="form-group col-sm-offset-5 col-sm-10" >
            <input type="submit" value="上传_h" class="" onclick="f(1)"/> 
            <input type="submit" value="上传_n" class="" onclick="f(2)"/> 
          </div>
        </form>
      </div>
    </div><!-- /.container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery-1.10.2.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    
</body>
</html>