<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
Cookie[] cookies = request.getCookies(); // request is an instance of type HttpServletRequest
boolean foundCookie = false;

if (null != cookies) {
	for (int i = 0; i < cookies.length; i++) {
		Cookie c = cookies[i];
		if (c.getName().equals("userid")) {
			String userId = c.getValue();
			foundCookie = true;
		}
	}
} 

if (!foundCookie) {
	if (session.isNew()) {
		//session.invalidate();
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Task Manager Home</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<!-- Le styles -->
		<link href="css/bootstrap.css" rel="stylesheet">
		<style type="text/css">
			body {
				padding-top: 60px;
				padding-bottom: 40px;
			}
			
			ul.nav li p {
				padding: 10px 0px 0px 10px;
			}
		</style>
		<link href="css/bootstrap-responsive.css" rel="stylesheet">
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
		<script src="js/html5shiv.js"></script>
		<![endif]-->
		<!-- Fav and touch icons -->
		<link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
		<link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
		<link rel="shortcut icon" href="../assets/ico/favicon.png">
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>		
	</head>
	<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="brand" href="<%= request.getContextPath() %>/index.jsp">Task Manager</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li><a><% out.print("Welcome, " + session.getAttribute("username")); %></a></li>
					</ul>
					<form class="navbar-form pull-right" action="<%= request.getContextPath() %>/index" method="GET">
						<input type="hidden" name="logout" value = "logout">
						<button type="submit" class="btn">Sign Out</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	</body>
</html>