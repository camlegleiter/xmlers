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
		<link href="../css/bootstrap-responsive.min.css" rel="stylesheet">
		<link href="../css/style.css" rel="stylesheet">
		<link href="../css/themes/blue/style.css" rel="stylesheet">
		<style type="text/css">
			body {
				padding-top: 60px;
				padding-bottom: 40px;
			}
			
			ul.nav li p {
				padding: 10px 0px 0px 10px;
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
		
		<div class="container">
			<noscript>
				<div class="alert alert-error">
					<p style="text-align: center; margin: 10px"><strong>This website works best when JavaScript is enabled.</strong></p>
				</div>
			</noscript>	
			
			<div class="row">
				<div class="span10 offset1">
					<div class="row">
						<div class="span8">
							<h2>Form 1 Records</h2>
						</div>
						<div class="span2">
							<div class="btn-group pull-right" style="margin-top: 16px">
								<a class="btn" href="<%=request.getContextPath()%>/app/index.jsp" title="Go back to the index">Back</a>	
							</div>
						</div>
					</div>
				
					<table id="viewTableRecords" class="table table-bordered table-striped">
						<thead>
							<tr>
								<th class="header">Participant</th>
								<th class="header">1. What is your name?</th>
								<th class="header">2. What is your quest?</th>
								<th class="header">3. What is your favorite color?</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Sir Lancelot the Brave</td>
								<td>Sir Lancelot</td>
								<td>To seek the Holy Grail</td>
								<td>Blue</td>
							</tr>
							<tr>
								<td>Sir Robin the Not-Quite-So-Brave-as-Sir Lancelot</td>
								<td>Sir Robin</td>
								<td>To seek the Holy Grail</td>
								<td></td>
							</tr>
							<tr>
								<td>Sir Galahad the Pure</td>
								<td>Sir Galahad</td>
								<td>To seek the Holy Grail</td>
								<td>Blue... No yel-</td>
							</tr>
							<tr>
								<td>King Arthur</td>
								<td>Arthur, King of the Britains</td>
								<td>To seek the Holy Grail</td>
								<td>What do you mean? An African or European swallow?</td>
							</tr>							
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<!-- /container -->
		<!-- Le javascript
			================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="../js/jquery.js"></script>
		<script src="../js/jquery.tablesorter.js"></script>
		<script>
			$(document).ready(function() {
				$('table#viewTableRecords').tablesorter({ sortList: [[0, 0], [1, 0]] });
			});
		</script>
	</body>
</html>