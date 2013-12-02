<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="xmlers" uri="/WEB-INF/tlds/functions.tld" %>
<%@ page import="dbconnect.DBManager" %>
<%@ page import="form.Form" %>
<%@ page import="form.User" %>
<%@ page import="form.utils.Forms" %>
<!DOCTYPE html>
<c:if test="${!empty param.form}">
	<c:set var="DBInstance" value="${xmlers:getInstance()}"></c:set>
    <c:set var="form" value="${DBInstance.fetchForm(param.form)}"></c:set>
</c:if>
<c:set var="isInvalidFormId" value="${form == null}"></c:set>
<c:set var="userCanSeeForm" value="${!isInvalidFormId && form.containsParticipant(sessionScope.user.getUserID())}"></c:set>
<html>
	<head>
        <c:import url="/app/includes/header.jsp">
            <c:param name="title" value="View - Task Manager" />
        </c:import>
	</head>
	<body>
		
        <c:import url="/app/includes/nav.jsp" />
		
		<div class="container">
			<c:import url="/app/includes/noscript.jsp" />	
			
			<div class="row">
				<div id="viewOptions" class="col-md-2">
				    <h3>Options</h3>
					<div class="well"></div>
				</div>
				<div id="responseTable" class="col-md-10">
<c:choose>
    <c:when test="${isInvalidFormId}">
                    <h2>Uh oh!</h2>
                    <p>We don't seem to have a record of this form. Return to the <a href="<%= request.getContextPath() %>/app/index.jsp">home page</a> and try a different form.</p>
    </c:when>
    <c:when test="${!userCanSeeForm}">
                    <h2>Uh oh!</h2>
                    <p>You are not allowed to view data for this form. Check with the owner of the form to verify your access to this data.</p>
    </c:when>             
</c:choose>
                </div>
			</div>
		</div>
    
		<c:import url="/app/includes/footer.jsp" />
    
		<script src="<%= request.getContextPath() %>/assets/js/vendor/jquery.tablesorter.min.js"></script>
        
        <script src="<%= request.getContextPath() %>/assets/js/models/questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/responses.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/views.js"></script>
        
        <script src="<%= request.getContextPath() %>/assets/js/view/view.options.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/view/view.table.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/view/view.js"></script>

    <c:if test="${!isInvalidFormId || userCanSeeForm}">
        <script>
            $(document).ready(function() {
                View = TaskManager.View;
                View.start({
                    model: new TaskManager.Models.View(${xmlers:getResponseRecordsForForm(param.form)})
                }); 
            });
        </script>
    </c:if>

        <script id="table-layout" type="text/template">
            <h3>"<@= formName @>" Records</h3>
            <h4><@= formDescription @></h4>
            
            <table class="table table-bordered table-striped">
                <thead></thead>
                <tbody></tbody>
            </table>
        </script>
        
        <script id="table-header-template" type="text/template">
            <@= prompt @>
        </script>
        
        <script id="table-row-template" type="text/template">
            <td><@= responseOwnerName @></td>
            <@ _.each(responses.models, function(response) { @>
                <td><@= getResponseValue(response) @></td>
            <@ }); @>
        </script>
	</body>
</html>