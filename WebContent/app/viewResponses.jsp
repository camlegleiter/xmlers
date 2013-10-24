<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="dbconnect.DBManager" %>
<%@ page import="form.Form" %>
<%@ page import="form.utils.Forms" %>
<%@ page import="utils.Utils" %>
<% 
String formId = request.getParameter("form");
boolean isInvalidFormId = Utils.isNullOrEmpty(formId);

Form form = DBManager.getInstance().fetchForm(formId);
isInvalidFormId = form == null;

String userId = (String) request.getSession().getAttribute("userid");
boolean userCanSeeForm = true;
if (!form.getOwner().equals(userId) || !form.participantsCanSeeAll() || !form.containsParticipant(userId)) {
    userCanSeeForm = false;
}
%>
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
        <link href="/xmlers/assets/css/style.css" rel="stylesheet">
	</head>
	<body>
		
        <jsp:include page="/app/includes/nav.jsp" />
		
		<div class="container-fluid">
			<jsp:include page="/app/includes/noscript.jsp" />	
			
			<div class="row-fluid">
				<div id="viewOptions" class="span2">
				    <h2>View Options</h2>
					<div class="well"></div>
				</div>
				<div id="responseTable" class="span10">
<% if (isInvalidFormId) { %>
                    <h3>We don't seem to have a record of this form. Please make sure you're viewing the right form!</h3>
<% } else if (!userCanSeeForm) { %>
                    <h3>You are not allowed to view data for this form. Check with the owner of the form to verify your access to this data.</h3>
<% } %>
                </div>
			</div>
		</div>
    
		<jsp:include page="/app/includes/footer.jsp" />
    
		<script src="<%= request.getContextPath() %>/assets/js/vendor/jquery.tablesorter.min.js"></script>
        
        <script src="<%= request.getContextPath() %>/assets/js/globals.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/responses.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/views.js"></script>
        
        <script src="<%= request.getContextPath() %>/assets/js/view/view.options.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/view/view.table.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/view/view.js"></script>

<% if (!isInvalidFormId || userCanSeeForm) { %>
        <script>
            $(document).ready(function() {
                View = TaskManager.View;
                View.start({
                    model: new TaskManager.Models.View(<%= Forms.getResponseRecordsForForm(formId) %>)
                }); 
            });
        </script>
<% } %>

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
        <%= userId %>
        <%= form.getOwner() %>
	</body>
</html>