<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="xmlers" uri="/WEB-INF/tlds/functions.tld" %>
<%@ page import="form.utils.Forms" %>
<%@ page import="form.User" %>
<!DOCTYPE html>
<html>
	<head>
        <c:import url="/app/includes/header.jsp">
            <c:param name="title" value="Home - Task Manager" />
        </c:import>
        
        <link href="<%= request.getContextPath() %>/assets/css/style.css" rel="stylesheet">
	</head>
	<body>
	
        <c:import url="/app/includes/nav.jsp" />
        
        <div class="container">
        
<c:choose>
    <c:when test="${param.m == 'a'}">
			<div class="alert alert-success">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				Your new form has been successfully created!
			</div>
    </c:when>
    <c:when test="${param.m == 'u'}">
			<div class="alert alert-success">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				Your form was successfully updated!
			</div>
    </c:when>   
    <c:when test="${param.m == 'e'}">
			<div class="alert alert-error">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				An error occurred trying to process your request. Please try again soon!
			</div>
    </c:when>             
</c:choose>
		
			<c:import url="/app/includes/noscript.jsp" />
			
			<div class="row">
				<div id="forms" class="col-md-3"></div>
				<div id="form-contents" class="col-md-9"></div>
			</div>		
		</div>
	
        <c:import url="/app/includes/footer.jsp" />
        
        <script src="<%= request.getContextPath() %>/assets/js/models/forms.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/index/index.forms.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/index/index.content.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/index/index.js"></script>
        
        <script type="text/javascript">
            $(document).ready(function() {
                var ownerCollection = new TaskManager.Collections.Forms();
                ownerCollection.reset(${xmlers:getFormsUserIsOwnerOf(sessionScope.user.getUserID())});
                
                var participantCollection = new TaskManager.Collections.Forms();
                participantCollection.reset(${xmlers:getFormsUserIsParticipantOf(sessionScope.user.getUserID())});
                
                Index = TaskManager.Index;
                Index.start({
                    ownerCollection: ownerCollection,
                    participantCollection: participantCollection
                });
            });
        </script>
        
        <script id="form-item-template" type="text/template">
            <input type="radio" id="form-<@= id @>" name="form-option">            
            <label for="form-<@= id @>"><@= form.formName @></label>
        </script>
        
        <script id="form-list-template" type="text/template">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a><@= getPanelHeading() @></a>
					<span class="caret"></span>
				</h4>
			</div>
			<div class="list-group"></div>
		</script>
        
        <script id="forms-layout" type="text/template">
            <h3>Forms</h3>
			<div class="btn-group btn-group-justified" style="margin-bottom: 10px">
                <a class="add btn btn-primary" href="create.jsp" title="Create a New Form">Add Form</a>
                <a class="delete btn btn-danger" title="Delete an Existing Form">Delete Form</a>
            </div>
            <div id="owner-forms"></div>
            <div id="participant-forms"></div>
        </script>
        
        <script id="form-contents-template" type="text/template">
            <div class="well form-content"></div>
        </script>
        
        <script id="owner-form-template" type="text/template">
            <h3>Form Name: <@= formName @></h3>
            <h4>Description: <@= formDescription @></h4>
			<hr>
            <p><strong>Participants: </strong><@= getFormParticipants() @></p>
			<p><strong>Number of Questions: </strong><@= formQuestions.length @></p>
            <form class="form-inline owner-buttons" action="/app/index" method="POST">
                <a href="viewResponses.jsp?form=<@= formID @>" class="view-records btn btn-default" title="See all of the records for this form.">View Records</a>
                <a href="create.jsp?edit=1&form=<@= formID @>" class="edit-form btn btn-default" title="Make changes to this form.">Edit Form</a>
                <a class="reemail-participants btn btn-default" title="Sends a reminder to participants who haven't completed this form to do so.">Re-Email Participants</a>
            </form>
        </script>
        
        <script id="participant-form-template" type="text/template">
            <h3>Form Name: <@= formName @></h3>
            <h4>Description: <@= formDescription @></h4>
			<hr>
			<p><strong>Participation Required: <strong><@= participantResponseIsRequired ? 'Yes' : 'No' @></p>
            <form class="form-inline participant-buttons" action="/app/index" method="POST">
                <@ if (participantsCanSeeAll) { @>
                    <a href="viewResponses.jsp?form=<@= formID @>" class="view-records btn btn-default" title="See all of the records for this form.">View Records</a>
                <@ } @>
                <a href="response.jsp?form=<@= formID @>" class="submit-response btn btn-default" title="Submit a response to this form.">Submit Response</a>
            </form>
        </script>
        
        <script id="empty-view-template" type="text/template">
            <@= message @>
        </script>
	</body>
</html>