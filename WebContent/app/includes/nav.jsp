<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
		<nav class="navbar navbar-inverse navbar-static-top tm-navbar" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#head-nav-bar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="<%= request.getContextPath() %>/app/index.jsp">Task Manager</a>
				</div>
			
				<div class="collapse navbar-collapse" id="head-nav-bar">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.user.userName}<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<!-- <li><a href="<%= request.getContextPath() %>/app/settings.jsp">Settings</a></li>
								<li class="divider"></li> -->
								<li><a href="<%= request.getContextPath() %>/app/logout?logout=logout">Sign Out</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</nav>