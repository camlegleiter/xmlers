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
		<link href="../css/bootstrap.min.css" rel="stylesheet">
		<link href="../css/jquery-ui-1.10.2.custom.min.css" rel="stylesheet">
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
		<link href="../css/bootstrap-responsive.min.css" rel="stylesheet">
		
		<script src="../js/modernizr.js"></script>
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
					<h3>Select Column Type:</h3>
					<div class="well" style="padding: 10px;">
						<ul class="columns">
							<li class="ui-state-highlight">Text Box</li>
							<li class="ui-state-highlight">Multiple Choice</li>
							<li class="ui-state-highlight">Checkbox</li>
						</ul>
					</div>
				</div>
				<div class="span9">
					<h3>Form Builder:</h3>
					<form action="<%=request.getContextPath()%>/create" method="POST">
						<fieldset>
							<input type="text" class="inline-block-level" placeholder="Form Name" name="formName" required>
							<div>
								<ol id="form-builder">
									<li class="placeholder">Drag a column type here!</li>
								</ol>
							</div>
							<div class="form-actions">
								<div style="margin-bottom: 10px">
									<label class="checkbox"><input type="checkbox" name="participantsSeeAll">Participants can see the responses of others.</label>
								</div>
								
								<button class="btn btn-large btn-primary" type="submit">Submit</button>
								<a class="btn btn-large">Cancel</a>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
				<!-- /container -->
		<!-- Le javascript
			================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="../js/jquery.min.js"></script>
		<script src="../js/jquery-ui-1.10.2.custom.min.js"></script>
		<script src="../js/bootstrap.min.js"></script>
		<script>
			$(document).ready(function() {
				$('.columns li').draggable({
					appendTo: "body",
					helper: 'clone',
					cursor: "pointer"
				});
				
				$('#form-builder').droppable({
					activeClass: "ui-state-default",
					hoverClass: "ui-state-hover",
					accept: ":not(.ui-sortable-helper)",
					drop: function(event, ui) {
						$(this).find(".placeholder").remove();
						$('<li></li>').text(ui.draggable.text()).appendTo(this);
					}
				}).sortable({
					placeholder: "ui-placeholder",
					forcePlaceholderSize: true,
					sort: function() {
						$(this).removeClass("ui-state-default");
					}
				});
			});
		</script>
	</body>
</html>