<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
        <jsp:include page="/app/includes/header.jsp">
            <jsp:param name="title" value="View Results - Task Manager" />
        </jsp:include>
        
        <style type="text/css">
            body {
                padding-top: 60px;
                padding-bottom: 40px;
            }
            
            ul.nav li p {
                padding: 10px 0px 0px 10px;
            }
        </style>
	</head>
	<body>
		
        <jsp:include page="/app/includes/nav.jsp" />
		
		<div class="container-fluid">
			<jsp:include page="/app/includes/noscript.jsp" />	
			
			<div class="row-fluid">
				<div class="span2">
				<h2>View Options</h2>
					<div class="well">
						asdf
					</div>
				</div>
				<div class="span10">
					<div class="row-fluid">
						<div class="span10">
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
		<script src="/js/vendor/jquery.min.js"></script>
		<script src="/js/vendor/bootstrap.min.js"></script>
		<script src="/js/vendor/jquery.tablesorter.min.js"></script>
		<script>
			$(document).ready(function() {
				$('table#viewTableRecords').tablesorter({ sortList: [[0, 0], [1, 0]] });
			});
		</script>
	</body>
</html>