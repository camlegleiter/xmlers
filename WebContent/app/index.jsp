<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
        <script src="<%= request.getContextPath() %>/assets/js/forms.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/index/index.forms.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/index/index.content.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/index/index.js"></script>
        
        <script type="text/javascript">
            $(document).ready(function() {
                var ownerCollection = new TaskManager.Collections.Forms();
                ownerCollection.reset([{ formName: "name1" }, { formName: "name2" }, { formName: "name3" }]);
                var participantCollection = new TaskManager.Collections.Forms();
                participantCollection.reset();
                
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
                <a class="add btn" href="create.jsp" title="Create a New Form">Add Form</a>
                <a class="delete btn" title="Delete an Existing Form">Delete Form</a>
                <div id="owner-forms"></div>
                <div id="participant-forms"></div>
            </div>
        </script>
        
        <script id="form-contents-template" type="text/template">
            <h3>Content</h3>
            <div class="well form-content"></div>
        </script>
        
        <script id="form-content-template" type="text/template">
            <div id="form-info">
                <p><strong>Form Name: </strong><@= formName @></p>
                <p><strong>Description: </strong><@= formDescription @></p>
                <form id="form-buttons" class="form-inline" action="/app/index" method="POST" style="display: none;">
                    <input type="hidden" id="form-id" name="formid">
                    <input type="submit" id="viewRecords" class="btn" name="viewRecords" value="View Records" title="See all of the records for this form.">
                    <input type="submit" id="editForm" class="btn" name="editForm" value="Edit Form" title="Make changes to this form.">
                    <button id="reemailParticipants" class="btn" name="reemailParticipants" value="Re-Email Participants" title="Sends a reminder to participants who haven't completed this form to do so.">Re-Email Participants</button>
                </form>
                <form id="other-form-buttons" class="form-inline" action="/app/index" method="POST" style="display: none;">
                    <input type="hidden" id="otherform-id" name="otherformid">
                    <input type="submit" id="inputResponse" class="btn" name="inputResponse" value="Input Response" title="Submit a response to this form.">
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