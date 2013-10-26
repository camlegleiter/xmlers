<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="dbconnect.DBManager" %>
<%@ page import="form.Form" %>
<%@ page import="form.User" %>
<%@ page import="form.utils.Forms" %>
<%@ page import="utils.Utils" %>
<c:set var="formId" value="-1"></c:set>
<c:set var="isInvalidFormId" value="false"></c:set>
<c:catch var="invalidFormIdException">
    <c:set var="formId" value="${Integer.parseInt(param.form)}"></c:set>
</c:catch>
<c:if test="">
    <c:set var="isInvalidFormId" value="true"></c:set>
</c:if>
<c:set var="form" value="${DBManager.getInstance().fetchForm(formId)}"></c:set>
<c:set var="isInvalidFormId" value="${form == null}"></c:set>
<c:set var="currentUser" value="${sessionScope.user})"></c:set>
<c:set var="userCanSeeForm" value="${form.getOwnerId() == user.getUserID() || form.participantsCanSeeAll() || form.containsParticipant(user.getUserID())}"></c:set>
<!DOCTYPE html>
<html>
	<head>
        <c:import url="/app/includes/header.jsp">
            <c:param name="title" value="View - Task Manager" />
        </c:import>
        
        <style type="text/css">
            body {
                padding-top: 60px;
                padding-bottom: 40px;
            }
            
            ul.nav li p {
                padding: 10px 0px 0px 10px;
            }
        </style>
        <link href="/xmlers/assets/css/style.css" rel="stylesheet">
	</head>
	<body>
		
        <c:import url="/app/includes/nav.jsp" />
		
		<div class="container-fluid">
			<c:import url="/app/includes/noscript.jsp" />	
			
			<div class="row-fluid">
				<div id="viewOptions" class="span2">
				    <h2>View Options</h2>
					<div class="well"></div>
				</div>
				<div id="responseTable" class="span10">
<c:choose>
    <c:when test="${isInvalidFormId}">
                    <h2>Uh oh!</h2>
                    <p>We don't seem to have a record of this form. Return to the <a href="<%= request.getContextPath() %>/app/index.jsp">home page</a> and try a different form.</p>
    </c:when>
    <c:when test="${userCanSeeForm}">
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
                    model: new TaskManager.Models.View("${Forms.getResponseRecordsForForm(${formId})")
                }); 
            });
        </script>
    </c:if>

        <script id="table-layout" type="text/template">
            <h3><@= formName @> Records</h3>
            <h4>Form Description: <@= formDescription @></h4>
            
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