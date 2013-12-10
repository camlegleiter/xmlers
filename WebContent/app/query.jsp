<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="xmlers" uri="/WEB-INF/tlds/functions.tld" %>
<%@ page import="dbconnect.DBManager" %>
<%@ page import="form.Form" %>
<!DOCTYPE html>
<c:if test="${!empty param.edit && !empty param.form}">
	<c:set var="DBInstance" value="${xmlers:getInstance()}"></c:set>
    <c:set var="form" value="${DBInstance.fetchForm(param.form)}"></c:set>
</c:if>
<c:set var="isInvalidFormId" value="${form == null}"></c:set>
<c:set var="userCanSeeForm" value="${!isInvalidFormId && form.getOwnerId() == sessionScope.user.getUserID()}"></c:set>
<html>
	<head>
        <c:import url="/app/includes/header.jsp">
            <c:param name="title" value="Query - Task Manager" />
        </c:import>
        
        <style>
        	textarea {
        		font-family: Consolas, Lucida Console, monospace;
        	
        		resize: vertical;
        	}
        </style>
	</head>
	<body>
	
		<c:import url="/app/includes/nav.jsp" />
		
		<div class="container">
			<c:import url="/app/includes/noscript.jsp" />
			
			<div class="row">
				<div id="queries" class="col-md-6"></div>
				<div id="results" class="col-md-6"></div>
			</div>
		</div>
		
		<c:import url="/app/includes/footer.jsp"></c:import>
		
		<script src="<%= request.getContextPath() %>/assets/js/models/queries.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/query/query.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/query/query.queries.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/query/query.results.js"></script>
        
        <c:set var="queryData" value="${xmlers:constructQueryJSON(form.getFormId(), form.getQueries())}"></c:set>
        
		<script>
		    $(document).ready(function() {
			    var queryModel = new TaskManager.Models.Queries(${userCanSeeForm ? queryData : '' });
			    
			    Query = TaskManager.Query;
			    Query.start({ model: queryModel });
			});
		</script>
		
		<script id="query-template" type="text/template">
			<h3>Queries</h3>
			<div class="well">
				<form id="query-form" action="queryForm" method="POST">
					<div class="form-group">
						<textarea class="form-control" rows="25"><@= queries @></textarea>
					</div>
           			<button type="button" class="save btn btn-lg btn-primary">Save</button>
           			<button type="button" class="run-all btn btn-lg btn-default">Run All</button>
					<button type="button" class="run-selected btn btn-lg btn-default">Run Selected</button>
	           		<img class="loading" src="<%= request.getContextPath() %>/assets/img/loading.gif" style="display: none;" />
				</form>
			</div>
		</script>
		
		<script id="results-template" type="text/template">
			<h3>Results</h3>
			<div class="well">
				<form id="query-form" method="POST">
					<div class="form-group">
						<textarea class="form-control" rows="25"></textarea>
					</div>
					<button type="button" class="export-xml btn btn-lg btn-default">Export XML</button>
				</form>
			</div>
		</script>
	</body>
</html>