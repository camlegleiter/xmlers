<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <c:import url="/app/includes/header.jsp">
            <c:param name="title" value="Login - Task Manager" />
        </c:import>

        <style type="text/css">
			body {
				padding-top: 40px;
				padding-bottom: 40px;
				background-color: #eee;
			}
			
			.form-signin {
				max-width: 330px;
				padding: 15px;
				margin: 0 auto;
			}
			
			.form-signin .form-signin-heading,
			.form-signin .checkbox {
				margin-bottom: 10px;
			}
			
			.form-signin .checkbox {
				font-weight: normal;
			}
			
			.form-signin .form-control {
				position: relative;
				font-size: 16px;
				height: auto;
				padding: 10px;
				-webkit-box-sizing: border-box;
				-moz-box-sizing: border-box;
				box-sizing: border-box;
				margin-bottom: -1px;
				border-radius: 0;
			}
			
			.form-signin .form-control:focus {
				z-index: 2;
			}
						
			.form-signin .form-control:first-of-type {
				margin-bottom: -1px;
				border-top-left-radius: 4px;
				border-top-right-radius: 4px;
			}
			
			.form-signin .form-control:last-of-type {
				margin-bottom: 10px;
				border-bottom-left-radius: 4px;
				border-bottom-right-radius: 4px;
			}
			
			.error {
				color: red;
			}
		</style>
    </head>
    <body>
        <div class="container-fluid">
            <c:import url="/app/includes/noscript.jsp" />
            
            <div class="row-fluid">
                <div class="span4"></div>
                <div class="span4">
                    <ul class="nav nav-tabs" id="loginTabs">
                        <li class="active"><a href="#login">Login</a></li>
                        <li><a href="#register">Register</a></li>
                    </ul>
                     
                    <div class="tab-content">
                        <div class="tab-pane active" id="login">
                            <form class="form-signin" action="<%= request.getContextPath() %>/login" method="post">
                                <h2 class="form-signin-heading">Please sign in</h2>
                                <input type="text" class="form-control" placeholder="NetID" name="username" required autofocus>
                                <input type="password" class="form-control" placeholder="Password" name="password" required>
                                <label class="checkbox">
                                	<input type="checkbox" value="remember-me" name="remember"> Remember me
                                </label>
                                <button class="btn btn-large btn-block btn-primary" type="submit">Sign in</button>
                                <span class="help-inline error" id="login-error">
                                    ${loginerror}
                                </span>
                            </form>                    
                        </div>
                        
                        <div class="tab-pane" id="register">
                            <form class="form-signin" action="<%= request.getContextPath() %>/register" method="post">
                                <h2 class="form-signin-heading">Register</h2>
                                <input type="text" class="form-control" placeholder="First Name" name="first-name" required>
                                <input type="text" class="form-control" placeholder="Last Name" name="last-name" required>
                                <input type="text" class="form-control" placeholder="NetID" name="username" required>
                                <input type="text" class="form-control" placeholder="Email" name="email" required>
                                <input type="password" class="form-control" placeholder="Password" name="password" required>
                                <input type="password" class="form-control" placeholder="Re-enter password" name="password-check" required>
                                <button class="btn btn-large btn-block btn-primary" type="submit">Register</button>
                                <span class="help-inline error" id="register-error">
                                    ${registererror}
                                </span>
                            </form>    
                        </div>
                    </div>
                </div>
                <div class="span4"></div>
            </div>    
        </div>
        <script src="assets/js/vendor/jquery.js"></script>
        <script src="assets/js/vendor/bootstrap.min.js"></script>
        <script>
            $(document).ready(function() {
                $('#loginTabs a').click(function(e) {
                    e.preventDefault();
                    $(this).tab('show');
                });
            });
        </script>
    </body>
</html>