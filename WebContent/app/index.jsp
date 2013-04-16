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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Task Manager Home</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<!-- Le styles -->
		<link href="../css/bootstrap.css" rel="stylesheet">
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
					<div class="btn-group pull-right" style="margin-top: 15px;">
						<button type="button" class="btn" title="Add a New Form">+ Form</button>
						<button type="button" class="btn" title="Delete an Existing Form">- Form</button>	
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
										<li><a href="#">Form 2</a></li>
										<li><a href="#">Form 3</a></li>
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
							<h4>Name: Form 1</h4>
							<h4>Description:</h4>
							<p>
								Bacon ipsum dolor sit amet cow pork belly deserunt excepteur. Duis pork belly salami aute ea, non dolore pork ullamco ut ham hock. Aliquip ham laboris, bacon jerky tempor magna proident capicola id do voluptate. Labore cillum capicola short loin et, tempor non in fugiat qui swine occaecat reprehenderit doner. Aliquip beef ribs dolore shoulder ad consectetur pork belly minim. Flank duis consequat ut dolore doner ribeye turkey. Bacon fugiat in dolore, doner laborum tongue shankle.
							</p>
							<h4>Questions: </h4>
							<p>
								<ol>
									<li><strong>Text: </strong>What is your name?</li>
									<li><strong>Text: </strong>What is your quest?</li>
									<li><strong>Multiple Choice: </strong>What is your favorite color?</li>
								</ol>
							</p>
							<div>
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
			/*$(document).ready(function() {
				$('li a').click(function() {
					var id = $(this).attr('id');
					$.ajax({
						url: "example_form1.json",
						type: "POST",
						dataType: "json",
						data: id
					}).done(function(json) {
						$('div#form-info').html('');
						$('div#form-info').
					}).fail(function(jqXHR, textStatus) {
						$('div#form-info').html('<h3><strong>Could not load the form data at this time :(</strong></h3>');
					});
				});
			});*/
		</script>
	</body>
</html>