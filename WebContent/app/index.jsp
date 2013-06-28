<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Task Manager Home</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<!-- Le styles -->
		<link href="/css/bootstrap.min.css" rel="stylesheet">
		<link href="/css/style.css" rel="stylesheet">
		<style type="text/css">
			body {
				padding-top: 60px;
				padding-bottom: 40px;
			}
			
			ul.nav li p {
				padding: 10px 0px 0px 10px;
			}
		</style>
		<link href="../css/bootstrap-responsive.min.css" rel="stylesheet">	
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
				<div class="container-fluid">
					<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="brand" href="<%= request.getContextPath() %>/app/index.jsp">Task Manager</a>
					<div class="nav-collapse collapse">
						<ul class="nav pull-right">
							<li id="fat-menu" class="dropdown">
								<a href="#" id="settingsDrop" role="button" class="dropdown-toggle" data-toggle="dropdown">Options<b class="caret"></b></a>
								<ul class="dropdown-menu" role="menu" aria-labelledby="settingsDrop">
									<li><a role="menuitem" href="<%= request.getContextPath() %>/app/settings.jsp">User Settings</a></li>
									<li class="divider"></li>
									<li><a role="menuitem" href="<%= request.getContextPath() %>/app/logout?logout=logout">Sign Out</a></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		
		<div class="container-fluid">
		
			<noscript>
				<div class="alert alert-error">
					<p style="text-align: center; margin: 10px"><strong>This website works best when JavaScript is enabled.</strong></p>
				</div>
			</noscript>	
			
			<div class="row-fluid">
				<div class="span3">
					<div class="row-fluid">
						<div class="span4">
							<h3 style="display: inline-block">Forms</h3>
						</div>
						<div class="span8">
							<div class="btn-group pull-right" style="margin-top: 16px">
								<a class="btn" href="<%= request.getContextPath() %>/app/create.jsp" title="Create a New Form">+ Form</a>
								<a class="btn" title="Delete an Existing Form">- Form</a>	
							</div>
						</div>						
					</div>
					<div class="well sidebar-nav">
						<ul id="myForms" class="nav nav-list">
							<li class="nav-header">My Forms</li>
							<li><a href="#" id="123456">Form 1</a></li>
							<li><a href="#" id="951732">Form 2</a></li>
							<li><a href="#" id="682457">Form 3</a></li>
						</ul>
						<ul id="otherForms" class="nav nav-list">
							<li class="nav-header">Forms I Need to Complete</li>
							<li><a href="#">Other Form 1</a></li>
							<li><a href="#">Other Form 2</a></li>
							<li><a href="#">Other Form 3</a></li>
						</ul>
					</div>
				</div>
				<div class="span9">
					<h3>Content</h3>
					<div class="well">
						<div id="initial-content"><h4>Click on a form to get started!</h4></div>
						<div id="form-info">
							<h4 id="form-name"></h4>
							<p id="form-description"></p>
							<h4 id="form-questions"></h4>
							<ol id="form-questions-list"></ol>
							<form id="form-buttons" class="form-inline" action="<%= request.getContextPath() %>/app/index" method="POST" style="display: none;">
								<input type="hidden" id="form-id" name="formid">
								<input type="submit" id="viewRecords" class="btn" name="viewRecords" value="View Records" title="See all of the records for this form.">
								<input type="submit" id="editForm" class="btn" name="editForm" value="Edit Form" title="Make changes to this form.">
								<button id="reemailParticipants" class="btn" name="reemailParticipants" value="Re-Email Participants" title="Sends a reminder to participants who haven't completed this form to do so.">Re-Email Participants</button>
							</form>
							<form id="other-form-buttons" class="form-inline" action="<%= request.getContextPath() %>/app/index" method="POST" style="display: none;">
								<input type="hidden" id="otherform-id" name="otherformid">
								<input type="submit" id="inputResponse" class="btn" name="inputResponse" value="Input Response" title="Submit a response to this form.">
							</form>
						</div>
					</div>
				</div>
			</div>		
		</div>
	
		<!-- /container -->
		<!-- Le javascript
			================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="/js/vendor/jquery.min.js"></script>
		<script src="/js/vendor/bootstrap.min.js"></script>
		<script>
			$(document).ready(function() {
				var myForms, otherForms;
				
				$('#myForms li a').click(function() {
					$('#initial-content').remove();
					var id = $(this).attr('id');
					if (id) {
						if (!myForms) {
							$.ajax({
								url: "form_metadata.json",
								type: "POST",
								dataType: "json"
							}).done(function(json) {
								myForms = json["forms"];
							}).fail(function(jqXHR, textStatus) {
								$('div#form-info').html('<h3><strong>Could not load the form data at this time :(</strong></h3>');
							}).always(function() {
								if (myForms) buildFormInfo(myForms[id], id);
							});
						} else {
							buildFormInfo(myForms[id], id);
						}
					}
				});
							
				$('#otherForms li a').click(function() {
					$('#initial-content').remove();
					var id = $(this).attr('id');
					if (id) {
						if (!otherForms) {
							$.ajax({
								
							})
						}
					}
				});
							
				$('#reemailParticipants').click(function() {
					$('#form-id').attr('value');
					
					$.ajax({
						url: "<%= request.getContextPath() %>/app/index",
						type: "POST",
						data: $('#form-id').attr('value')
					}).done(function(result) {
						
					}).fail(function(result) {
						
					}).always(function() {
						
					});
					
					return false;
				});
					
				function clearFormInfo() {
					$('#form-name').html('');
					$('#form-description').html('');
					$('#form-questions').html('');
					$('#form-questions-list').html('');
					$('#form-buttons').hide();
					$('#other-form-buttons').hide();
				}
				
				function buildFormInfo(json, id) {
					clearFormInfo();
					$('#form-name').html('Name: ' + json.name);
					$('#form-description').html('<h4>Description:</h4>' + json.description);
					
					$('#form-questions').html('').prepend('Questions:');
					
					$('#form-questions-list').html('');
					$.each(json.questions, function(index, q) {
						$('#form-questions-list').append('<li><strong>' + q.type + ': </strong>' + q.description + '</li>');
						if (index == 5) {
							$('#form-questions-list').append('<li>...</li>');
							return false;
						}
					});
					
					$('#form-id').attr('value', id);
					$('#form-buttons').show();
				}
				
				function buildOtherFormInfo(json, id) {
					clearFormInfo();
					$('#form-name').html('Name: ' + json.name);
					$('#form-description').html('<h4>Description:</h4>' + json.description);
					
					$('#form-questions').html('').prepend('Questions:');
					$.each(json.questions, function(index, q) {
						$('#form-questions-list').append('<li><strong>' + q.type + ': </strong>' + q.description + '</li>');
						if (index == 5) {
							$('#form-questions-list').append('<li>...</li>');
							return false;
						}
					});
					
					$('#otherform-id').attr('value', id);
					$('#other-form-buttons').show();
				}
			});
		</script>
	</body>
</html>