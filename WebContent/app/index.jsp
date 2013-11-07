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
        
		<style type="text/css">
			body {
				padding-top: 60px;
				padding-bottom: 40px;
			}
			
			ul.nav li p {
				padding: 10px 0px 0px 10px;
			}
		</style>
        <link href="<%= request.getContextPath() %>/assets/css/style.css" rel="stylesheet">
	</head>
	<body>
	
        <c:import url="/app/includes/nav.jsp" />
		
		<div class="container-fluid">
			<c:import url="/app/includes/noscript.jsp" />
			
			<div class="row-fluid">
				<div id="forms" class="span3"></div>
				<div id="form-contents" class="span9"></div>
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
        
        <script id="forms-layout" type="text/template">
            <h3>Forms</h3>
            <div class="well sidebar-nav">
                <div class="pagination-centered clearfix">
                    <a class="add btn" href="create.jsp" title="Create a New Form">Add Form</a>
                    <a class="delete btn" title="Delete an Existing Form">Delete Form</a>
                </div>
                <div id="owner-forms"></div>
                <div id="participant-forms"></div>
            </div>
        </script>
        
        <script id="form-contents-template" type="text/template">
            <h3>Content</h3>
            <div class="well form-content"></div>
        </script>
        
        <script id="owner-form-template" type="text/template">
            <div id="form-info">
                <p><strong>Form Name: </strong><@= formName @></p>
                <p><strong>Description: </strong><@= formDescription @></p>
                <p><strong>Participants: </strong><@= getFormParticipants() @></p>
                <form class="form-inline owner-buttons" action="/app/index" method="POST">
                    <a href="viewResponses.jsp?form=<@= formID @>" class="btn view-records" title="See all of the records for this form.">View Records</a>
                    <a href="create.jsp?edit=1&form=<@= formID @>" class="btn edit-form" title="Make changes to this form.">Edit Form</a>
                    <a class="btn reemail-participants" title="Sends a reminder to participants who haven't completed this form to do so.">Re-Email Participants</a>
                </form>
            </div>
        </script>
        
        <script id="participant-form-template" type="text/template">
            <div id="form-info">
                <p><strong>Form Name: </strong><@= formName @></p>
                <p><strong>Description: </strong><@= formDescription @></p>
                <form class="form-inline participant-buttons" action="/app/index" method="POST">
                    <@ if (participantsCanSeeAll) { @>
                        <a href="viewResponses.jsp?form=<@= formID @>" class="btn view-records" title="See all of the records for this form.">View Records</a>
                    <@ } @>
                    <a href="response.jsp?form=<@= formID @>" class="btn submit-response" title="Submit a response to this form.">Submit Response</a>
                </form>
            </div>
        </script>
        
        <script id="empty-view-template" type="text/template">
            <@= message @>
        </script>
        
        <script id="form-item-template" type="text/template">
            <input type="radio" id="form-<@= id @>" name="form-option">            
            <label for="form-<@= id @>"><@= form.formName @></label>
        </script>
	</body>
</html>