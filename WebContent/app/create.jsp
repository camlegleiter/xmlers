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
            <c:param name="title" value="Create - Task Manager" />
        </c:import>
	</head>
	<body>
			
		<c:import url="/app/includes/nav.jsp" />
		
		<div class="container">
			<c:import url="/app/includes/noscript.jsp" />	
			
			<div class="row">
				<div class="col-md-3">
					<div id="question-options"></div>
				</div>
				<div class="col-md-6">
                    <div class="well">
                        <form id="user-form" action="upsertForm" method="POST"></form>
                    </div>
				</div>
			</div>
		</div>
        
        <c:import url="/app/includes/footer.jsp" />
        
        <script src="<%= request.getContextPath() %>/assets/js/models/forms.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/create/create.questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/create/create.form.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/create/create.js"></script>
        
		<script>
		    $(document).ready(function() {
			    var formModel = new TaskManager.Models.Form(${userCanSeeForm ? form.getJSON() : ""});
			    isEdit = ${!empty param.edit};
			    
			    Create = TaskManager.Create;
			    Create.start({ model: formModel });
			});
		</script>
    
    	<script id="question-option-template" type="text/template">
			<input type="radio" id="question-<@= id @>" name="question-option">            
			<label for="question-<@= id @>"><@= label @></label>
        </script>
    
        <script id="question-options-template" type="text/template">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Select Field Type:</h3>
				</div>
            	<div class="list-group"></div>
			</div>
           	<a class="add disabled btn btn-default" style="width: 100%;">Add Field</a>
        </script>    
    
        <script id="form-template" type="text/template">
            <div class="form-group">
                <label for="formName">Form Name</label>
				<input type="text" id="formName" class="form-control required-input" placeholder="Form Name" value="<@= formName @>" required>
            </div>

            <div class="form-group">
                <label for="formDesc">Form Description</label>
                <textarea id="formDesc" class="form-control required-input" placeholder="Enter a description of the form" required><@= formDescription @></textarea>
            </div>

            <div class="form-group">
                <label for="formParticipants">Participants</label>
				<br>
                <input type="hidden" id="formParticipants" style="width: 300px;">
            </div>

            <div id="form-content"></div>

			<hr>
            <label class="checkbox">
                <input type="checkbox" id="participantsCanSeeAll" <@ if (participantsCanSeeAll) { @> checked <@ } @>>
                Participants can see the responses of others.
            </label>

            <label class="checkbox">
                <input type="checkbox" id="participantsCanEditResponse" <@ if (participantsCanEditResponse) { @> checked <@ } @>>
                Participants can edit their response after submitting.
            </label>

            <label class="checkbox">
                <input type="checkbox" id="participantResponseIsRequired" <@ if (participantResponseIsRequired) { @> checked <@ } @>>
                Participants are required to fill out this form.
            </label>
            
            <button type="button" class="submit btn btn-lg btn-primary">Submit</button>
            <button type="button" class="cancel btn btn-lg btn-default">Cancel</button>
            <img class="loading" src="<%= request.getContextPath() %>/assets/img/loading.gif" style="display: none;" />
			<span class="error error-message" style="display: none"></span>
		</script>
    
        <script id="checkbox-template" type="text/template">
            <div class="clearfix">
                <h4 class="pull-left">Checkbox</h3>
                <a class="close">&times;</a>
            </div>

            <div>
                <div class="form-group">
                    <label for="prompt-<@= id @>">Prompt</label>
                    <input type="text" id="prompt-<@= id @>" class="form-control required-input" value="<@= data.prompt @>" placeholder="Enter a prompt to the user" required>
                </div>

				<label>Options</label>
                <ol class="content"></ol>

                <div class="form-group">
                     <a class="btn btn-default add">Add Checkbox Option</a>
                </div>
            </div>
        </script>
        
        <script id="checkbox-item-template" type="text/template">
            <input type="text" class="form-control required-input" value="<@= data.label @>" placeholder="Checkbox text" style="display: inline; width: 40%;" required>
			<a class="close">&times;</a>
        </script>
        
        <script id="radio-template" type="text/template">
            <div class="clearfix">
                <h4 class="pull-left">Radio</h3>
                <a class="close">&times;</a>
            </div>

            <div>
                <div class="form-group">
                    <label for="prompt-<@= id @>">Prompt</label>
                    <input type="text" id="prompt-<@= id @>" class="form-control required-input" value="<@= data.prompt @>" placeholder="Enter a prompt to the user" required>
                </div>

				<label>Options</label>
                <ol class="content"></ol>

                <div class="form-group">
                    <a class="btn btn-default add">Add Radio Option</a>
                </div>
            </div>
        </script>
        
        <script id="radio-item-template" type="text/template">
            <input type="text" class="form-control required-input" value="<@= data.label @>" placeholder="Radio text" style="display: inline; width: 40%;" required>
            <a class="close">&times;</a>
        </script>
        
        <script id="textbox-template" type="text/template">
            <div class="clearfix">
                <h4 class="pull-left">Textbox</h3>
                <a class="close">&times;</a>
            </div>

            <div>
                <div class="form-group">
                    <label for="prompt-<@= id @>">Prompt</label>
                    <input type="text" id="prompt-<@= id @>" class="form-control required-input" value="<@= _.unescape(data.prompt) @>" placeholder="Enter a prompt to the user" required>
                </div>

                <div class="form-group">
                    <label for="max-length-<@= id @>">Maximum Length</label>
                    <input type="number" id="max-length-<@= id @>" class="form-control required-input" value="<@= data.maxLength @>" placeholder="Maximum number of characters" required>
                </div>
            </div>
        </script>
        
        <script id="select-template" type="text/template">
            <div class="clearfix">
                <h4 class="pull-left">Select (Drop Down)</h3>
                <a class="close">&times;</a>
            </div>

            <div>
                <div class="form-group">
                    <label for="prompt-<@= id @>">Prompt</label>
                    <input type="text" id="prompt-<@= id @>" class="form-control required-input" value="<@= data.prompt @>" placeholder="Enter a prompt to the user" required>
                </div>

                <label class="checkbox">
                    <input class="is-multi" type="checkbox" <@ if (data.isMulti) { @> checked <@ } @>>
					Allow for multiple selections?
                </label>

				<label>Options</label>
                <ol class="content"></ol>

                <div class="form-group">
                    <a class="btn btn-default add">Add Select Option</a>
                </div>
            </div>
        </script>
        
        <script id="select-option-item-template" type="text/template">
            <input type="text" class="form-control required-input" value="<@= data.value @>" placeholder="Option text" style="display: inline; width: 40%;" required>
            <a class="close">&times;</a>
        </script>
        
        <script id="empty-view-template" type="text/template">
            <div class="controls">
                <@= message @>
            </div>
        </script>
	</body>
</html>