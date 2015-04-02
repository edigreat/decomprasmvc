<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
   <tiles:insertAttribute name="title" ignore="true" />
</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-theme.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap.min.css" />
	<STYLE TYPE="text/css">
        img{
            width: 100px;
            height: 50px;
        }
        .container{
            padding-left:150px;
            padding-right:150px;
        }
        .flotante{
            float: right;
        }
    </STYLE>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
   	 					<h3 class="panel-title"><tiles:insertAttribute name="header" /></h3>
   	 				</div>
   	 				<div class="panel-body">
						<tiles:insertAttribute name="body" />
					</div>
				</div><!-- panel panel-primary -->

			</div><!-- fin col-md-7 body -->
		</div> <!-- fin row menu y body -->
	</div> <!--  fin container -->


<script src="${pageContext.request.contextPath}/static/jquery-1.11.2.min.js" type="text/javascript"> </script>
<script src="${pageContext.request.contextPath}/static/bootstrap.min.js" type="text/javascript"> </script>
</body>
</html>