<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
			<div class="page-header">
					<tiles:insertAttribute name="header" />
			</div>
			
		</div> <!-- fin row header -->
		<div class="row">
			<div class="col-md-12">
				
						<tiles:insertAttribute name="body" />
					
			</div><!-- fin col-md-12 body -->
		</div> <!-- fin row menu y body -->
		<div class="row">
		<br />
		<tiles:insertAttribute name="footer" />
		</div> <!-- fin footer -->
	</div>
	
<!--h3>Session Scope (key==values)</h3 -->
<%--
  java.util.Enumeration<String> sessEnum = request.getSession()
.getAttributeNames();
  while (sessEnum.hasMoreElements()) {
String s = sessEnum.nextElement();
out.print(s);
out.println("==" + request.getSession().getAttribute(s));
--%><br />
<%--
  }
--%>
<script src="${pageContext.request.contextPath}/static/jquery-1.11.2.min.js" type="text/javascript"> </script>
<script src="${pageContext.request.contextPath}/static/bootstrap.min.js" type="text/javascript"> </script>
</body>
</html>