<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
// 	Cookie[] cookies = request.getCookies(); // request is an instance of type HttpServletRequest
// 	boolean foundCookie = false;

// 	if (null != cookies) {
// 		for (int i = 0; i < cookies.length; i++) {
// 			Cookie c = cookies[i];
// 			if (c.getName().equals("userid")) {
// 				String userId = c.getValue();
// 				foundCookie = true;
// 			}
// 		}

// 		if (foundCookie) {
// 			response.sendRedirect(request.getContextPath() + "/index.jsp");
// 		} else {
// 			session.invalidate();
// 		}
// 	}
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Sign in &middot; Task Manager</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<!-- Le styles -->
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/bootstrap-responsive.min.css" rel="stylesheet">
		<style type="text/css">
			body {
				padding-top: 40px;
				padding-bottom: 40px;
				background-color: #f5f5f5;
			}
			.form-signin {
				max-width: 300px;
				padding: 19px 29px 29px;
				margin: 0 auto 20px;
				background-color: #fff;
				border: 1px solid #e5e5e5;
				-webkit-border-radius: 5px;
				-moz-border-radius: 5px;
				border-radius: 5px;
				-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
				-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
				box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
			}
			.form-signin .form-signin-heading, .form-signin .checkbox {
				margin-bottom: 10px;
			}
			.form-signin input[type="text"], .form-signin input[type="password"] {
				font-size: 16px;
				height: auto;
				margin-bottom: 15px;
				padding: 7px 9px;
			}
			.center {
				float: none;
				margin: 0 auto;
				text-align: center;
				max-width: 300px;
			}
		</style>
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
	</head>
	<body>
		<div class="container">
			<noscript>
				<div class="alert alert-error">
					<p style="text-align: center; margin: 10px"><strong>This website works best when JavaScript is enabled.</strong></p>
				</div>
			</noscript>
			
			<div class="row">
				<div class="span4"></div>
				<div class="span4">
					<div class="tabbable">
						<ul class="nav nav-tabs" id="loginTabs">
							<li><a class="active" data-toggle="tab" data-target="#login")>Login</a></li>
							<li><a data-toggle="tab" data-target="#register">Register</a></li>
						</ul>
						<div class="tab-content">
						
							<div class="tab-pane active" id="login">
								<form class="form-signin" action="<%= request.getContextPath() %>/login" method="POST">
									<h2 class="form-signin-heading">Please sign in</h2>
									<div class="control-group" id="signin-input-group">
										<div class="controls">
											<input type="text" class="input-block-level" placeholder="User name" name="username">
											<input type="password" class="input-block-level" placeholder="Password" name="password">
											<span class="help-inline" id="login-error"></span>
										</div>
									</div>
									<label class="checkbox">
									<input type="checkbox" value="remember-me" name="remember"> Remember me
									</label>
									<button class="btn btn-large btn-primary" type="submit">Sign in</button>
								</form>					
							</div>
							
							<div class="tab-pane" id="register">
								<form class="form-signin" action="<%= request.getContextPath() %>/register" method="POST">
									<h2 class="form-signin-heading">Register</h2>
									<div class="control-group" id="input-group">
										<div class="controls">
											<input type="text" required class="input-block-level" placeholder="First Name" name="first-name">
											<input type="text" required class="input-block-level" placeholder="Last Name" name="last-name">
											<input type="text" required class="input-block-level" placeholder="NetID" name="username">
											<input type="text" required class="input-block-level" placeholder="Email" name="email">
											<input type="password" required class="input-block-level" placeholder="Password" name="password">
											<input type="password" required class="input-block-level" placeholder="Re-enter password" name="password-check">
											<span class="help-inline" id="register-error"></span>
										</div>
									</div>
									<button class="btn btn-large btn-primary" type="submit">Register</button>
								</form>	
							</div>
							
						</div>
					</div>
				</div>
				<div class="span4"></div>
			</div>	
		</div>
		<!-- /container -->
		<!-- Le javascript
			================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="js/jquery.js"></script>
		<script src="js/bootstrap.js"></script>
		<script>
			$(document).ready(function() {
			    $('.form-signin').submit(function() {
			    	
			        $form = $(this);
			        $.post($form.attr('action'), $form.serialize(), function(responseText) {
			            if ("" != responseText) {
			            	$('#signin-input-group').addClass("error");
			            	$('#login-error').html(responseText).slideDown();
			        	} else {
			        		window.location.replace("app/index.jsp");
			        	}
			        });
			        return false;
			    });
			    
			    $('#error').hide();
			    
			    $(function() {
			    	$('#loginTabs').tab();
			    	$('#loginTabs').bind("show", function(e) {
			    		var contentID = $(e.target).attr("data-target");
			    		var contentURL = $(e.target).attr("href");
			    		
			    		if (typeof(contentURL) != 'undefined')
			    			$(contentID).load(contentURL, function() { $('#loginTabs').tab(); });
			    		else
			    			$(contentID).tab('show');
			    	});
			    	$('#loginTabs a:first').tab('show');
			    });
			});
		</script>
	</body>
</html>