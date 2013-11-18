<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
        <c:import url="/app/includes/header.jsp">
            <c:param name="title" value="Settings - Task Manager" />
        </c:import>
        
        <style type="text/css">
            ul.nav li p {
                padding: 10px 0px 0px 10px;
            }
        </style>
	</head>
	<body>
			
		<c:import url="/app/includes/nav.jsp" />
		
		<div class="container">
			<c:import url="/app/includes/noscript.jsp" />
			
			<div class="row">
				<div class="span4"></div>
	            <div class="span4">
	            	<div class="well">
						<form class="form-horizontal" method="post" action="<%= request.getContextPath() %>/app/settings">
							<fieldset>
								<legend>Change Password:</legend>
					            <div class="control-group">
					                <label class="control-label" for="formName">Old Password</label>
					                <div class="controls">
					                    <input type="password" id="old-password" required>
					                </div>
					            </div>
					            <div class="control-group">
					                <label class="control-label" for="formName">New Password</label>
					                <div class="controls">
					                    <input type="password" id="new-password" required>
					                </div>
					            </div>
					            <div class="control-group">
					                <label class="control-label" for="formName">Re-enter New Password</label>
					                <div class="controls">
					                    <input type="password" id="new-password-check" required>
					                </div>
					            </div>
							</fieldset>
							<input type="submit" class="btn btn-primary" value="Test Email" />
						</form>
					</div>
				</div>
				<div class="span4"></div>
			</div>
		</div>

        <c:import url="/app/includes/footer.jsp" />	
	</body>
</html>