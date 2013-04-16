<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
// Cookie[] cookies = request.getCookies(); // request is an instance of type HttpServletRequest
// boolean foundCookie = false;

// if (null != cookies) {
// 	for (int i = 0; i < cookies.length; i++) {
// 		Cookie c = cookies[i];
// 		if (c.getName().equals("userid")) {
// 			String userId = c.getValue();
// 			foundCookie = true;
// 		}
// 	}
// } 

// if (!foundCookie) {
// 	session.invalidate();
// 	response.sendRedirect(request.getContextPath() + "/login.jsp");
// }
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Task Manager Home</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<!-- Le styles -->
		<link href="../css/bootstrap.css" rel="stylesheet">
		<link href="../css/style.css" rel="stylesheet">
		<style type="text/css">
			body {
				padding-top: 60px;
				padding-bottom: 40px;
			}
			
			ul.nav li p {
				padding: 10px 0px 0px 10px;
			}
		</style>
		<link href="../css/bootstrap-responsive.css" rel="stylesheet">
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
	
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="brand" href="<%= request.getContextPath() %>/app/index.jsp">Task Manager</a>
					<div class="nav-collapse collapse">
						<form class="navbar-form pull-right" action="<%= request.getContextPath() %>/app/index" method="GET">
							<input type="hidden" name="logout" value = "logout">
							<button type="submit" class="btn">Sign Out</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<div class="container">
			<noscript>
				<div class="alert alert-error">
					<p style="text-align: center; margin: 10px"><strong>This website works best when JavaScript is enabled.</strong></p>
				</div>
			</noscript>	
			<div class="row">
				<div class="span2">
					<h3>Form List</h3>	
				</div>
				<div class="span2">
					<div class="btn-group pull-right" style="margin-top: 16px">
						<a class="btn" href="<%=request.getContextPath()%>/app/create.jsp" title="Create a New Form">+ Form</a>
						<a class="btn" title="Delete an Existing Form">- Form</a>	
					</div>	
				</div>
				<div class="span8">
					<h3>Content</h3>
				</div>
			</div>
			<div class="row">
				<div class="span4">			
					<div class="accordion" id="formList">
						<div class="accordion-group">
							<div class="accordion-heading">
								<!-- <a class="accordion-toggle" data-toggle="collapse" data-parent="#formList" href="#myForms"> -->
								<a class="accordion-toggle" data-parent="#formList" href="#myForms">
									My Forms
								</a>
	
							</div>
							<div id="myForms" class="accordion-body collapse in">
								<div class="accordion-inner">
									<ul>
										<li><a href="#" id="123456">Form 1</a></li>
										<li><a href="#" id="951732">Form 2</a></li>
										<li><a href="#" id="682457">Form 3</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="accordion-group">
							<div class="accordion-heading">
								<!-- <a class="accordion-toggle" data-toggle="collapse" data-parent="#formList" href="#otherForms"> -->
								<a class="accordion-toggle" data-parent="#formList" href="#otherForms">
									Forms To Complete
								</a>
							</div>
							<div id="otherForms" class="accordion-body collapse in">
								<div class="accordion-inner">
									<ul>
										<li><a href="#">Form 1</a></li>
										<li><a href="#">Form 2</a></li>
										<li><a href="#">Form 3</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="span8">
					<div class="well">
						<div id="form-info">
							<h4 id="form-name"></h4>
							<p id="form-description"></p>
							<h4 id="form-questions"></h4>
							<ol id="form-questions-list">
							</ol>
							<div id="form-buttons">
								<ul class="nav nav-pills">
									<li>
										<a href="#" title="See all of the records for this form">View Records</a>
									</li>
									<li>
										<a href="#" title="Make changes to this form">Edit Form</a>
									</li>
									<li>
										<a href="#" title="Resend an email to respondents who haven't completed this form yet.">Re-Email Respondents</a>
									</li>
								</ul>
								<!-- <button title="See all of the records for this form">View Records</button>
								<button title="Make changes to this form">Edit Form</button>
								<button title="Resend an email to respondents who haven't completed this form yet.">Re-Email Respondents</button> -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<!-- /container -->
		<!-- Le javascript
			================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="../js/jquery.js"></script>
		<script src="../js/bootstrap-transition.js"></script>
		<script src="../js/bootstrap-collapse.js"></script>
		<script src="../js/bootstrap-dropdown.js"></script>
		<script src="../js/bootstrap-tooltip.js"></script>
		<script src="../js/bootstrap-tab.js"></script>
		<script>
			$(document).ready(function() {
				var forms;
				
				$('li a').click(function() {
					//clearFormInfo();
					
					var id = $(this).attr('id');
					if (id) {
						if (!forms) {
							$.ajax({
								url: "form_metadata.json",
								type: "POST",
								dataType: "json"
							}).done(function(json) {
								forms = json["forms"];
							}).fail(function(jqXHR, textStatus) {
								$('div#form-info').html('<h3><strong>Could not load the form data at this time :(</strong></h3>');
							}).always(function() {
								buildFormInfo(forms[id]);
							});
						} else {
							buildFormInfo(forms[id]);
						}
					}
				});
					
				function clearFormInfo() {
					$('#form-name').html('');
					$('#form-description >').html('');
					$('#form-questions').html('');
					$('#form-questions-list').html('');
					//$('#form-buttons >').html('');
				}
				
				function buildFormInfo(json) {
					$('#form-name').html('Name: ' + json.name);
					$('#form-description').html('<h4>Description:</h4>' + json.description);
					
					$('#form-questions').html('');
					$('#form-questions').prepend('Questions:');
					
					$('#form-questions-list').html('');
					for (var q in json.questions) {
						$("#form-questions-list").append('<li><strong>' + json.questions[q].type + ': </strong>' + json.questions[q].description + '</li>');
					}
				}
			});
		</script>
	</body>
</html>