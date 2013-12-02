<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
        <c:import url="/app/includes/header.jsp">
            <c:param name="title" value="Settings - Task Manager" />
        </c:import>
	</head>
	<body>
			
		<c:import url="/app/includes/nav.jsp" />
		
		<div class="container">
			<c:import url="/app/includes/noscript.jsp" />
			
			<div class="row">
	            <div class="col-md-6 col-md-offset-3">
	            	<div class="well">
						<form method="post" action="<%= request.getContextPath() %>/app/settings">
							<fieldset>
								<legend>Change Password:</legend>
					            <div class="form-group">
					                <label for="formName">Old Password</label>
					                <input type="password" id="old-password" class="form-control" required>
					            </div>
					            <div class="form-group">
					                <label for="formName">New Password</label>
					                <input type="password" id="new-password" class="form-control" required>
					            </div>
					            <div class="form-group">
					                <label for="formName">Re-enter New Password</label>
					                <input type="password" id="new-password-check" class="form-control" required>
					            </div>
							</fieldset>
							<input type="submit" class="btn btn-primary" value="Update Password" />
						</form>
					</div>
				</div>
			</div>
		</div>

        <c:import url="/app/includes/footer.jsp" />	
	</body>
</html>