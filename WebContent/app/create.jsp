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
				<div class="span3">
					<h3>Select Column Type:</h3>
				</div>
				<div class="span9">
					<h3>Form Builder:</h3>
				</div>
			</div>
			<div class="row">
				<div class="span3">
					<div class="well">
						<ul class="columns">
							<li class="ui-state-highlight">Test Entry Type1</li>
							<li class="ui-state-highlight">Test Entry Type2</li>
							<li class="ui-state-highlight">Test Entry Type3</li>
						</ul>
					</div>
				</div>
				<div class="span9">
					<div class="well">
						<form action="<%=request.getContextPath()%>/create" method="POST">
							<ol id="form-builder">
								<li class="placeholder">Drag a column type here!</li>
							</ol>
						</form>
					</div>
				</div>
			</div>
		</div>
				<!-- /container -->
		<!-- Le javascript
			================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="../js/jquery.js"></script>
		<script src="../js/jquery-ui-1.10.2.custom.min.js"></script>
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
					items: "li:not(.placeholder)",
					placeholder: "ui-state-highlight",
					forcePlaceholderSize: true,
					sort: function() {
						$(this).removeClass("ui-state-default");
					}
				});
			});
		</script>
	</body>
</html>