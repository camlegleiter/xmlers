<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="form.utils.Forms" %>
<!DOCTYPE html>
<html>
	<head>
        <jsp:include page="/app/includes/header.jsp">
            <jsp:param name="title" value="Home - Task Manager" />
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
        <link href="<%= request.getContextPath() %>/assets/css/style.css" rel="stylesheet">
	</head>
	<body>
	
        <jsp:include page="/app/includes/nav.jsp" />
		
		<div class="container-fluid">
			<jsp:include page="/app/includes/noscript.jsp" />
			
			<div class="row-fluid">
				<div id="forms" class="span3"></div>
				<div id="form-contents" class="span9"></div>
			</div>		
		</div>
	
        <jsp:include page="/app/includes/footer.jsp" />
        
        <script src="<%= request.getContextPath() %>/assets/js/globals.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/forms.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/index/index.forms.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/index/index.content.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/index/index.js"></script>
        
        <script type="text/javascript">
            var form1 = {
                formID: 123456789,
                formName: 'Form 1',
                formDescription: 'This is the form thing you are an owner of',
                formOwner: 123456789,
                formParticipants: ['test1@example.com', 'test2@example.com', 'test3@example.com'],
                participantsCanSeeAll: false,
                formQuestions: new TaskManager.Collections.Questions()
            };
            
            var form2 = {
                formID: 987654321,
                formName: 'Form 2',
                formDescription: 'This is the form thing you are a participant of',
                formOwner: 123456789,
                formParticipants: [],
                participantsCanSeeAll: true,
                formQuestions: new TaskManager.Collections.Questions()
            };
        
            $(document).ready(function() {
                var ownerCollection = new TaskManager.Collections.Forms();
                ownerCollection.reset(<%= Forms.getFormsUserIsOwnerOf("testuser") %>);
                var participantCollection = new TaskManager.Collections.Forms();
                participantCollection.reset(<%= Forms.getFormsUserIsParticipantOf("testuser") %>);
                
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
                    <a class="btn edit-form" title="Make changes to this form.">Edit Form</a>
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