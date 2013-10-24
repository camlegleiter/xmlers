<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
        <c:import url="/app/includes/header.jsp">
            <c:param name="title" value="Settings - Task Manager" />
        </c:import>
        
        <style type="text/css">
            body {
                padding-top: 60px;
                padding-bottom: 40px;
            }
            
            ul.nav li p {
                padding: 10px 0px 0px 10px;
            }
        </style>
	</head>
	<body>
			
		<c:import url="/app/includes/nav.jsp" />
		
		<div class="container-fluid">
			<c:import url="/app/includes/noscript.jsp" />
			
			<form method="POST" action="<%= request.getContextPath() %>/app/settings">
				<input type="submit" class="btn btn-primary" value="Test Email" />
			</form>
		</div>
		<script src="/js/vendor/jquery.min.js"></script>
		<script src="/js/vendor/bootstrap.min.js"></script>		
	</body>
</html>