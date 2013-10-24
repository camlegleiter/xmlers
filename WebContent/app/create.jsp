<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
        <c:import url="/app/includes/header.jsp">
            <c:param name="title" value="Create - Task Manager" />
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
				<div class="span3">
					<h3>Select Entry Type:</h3>
					<div id="question-options" class="well" style="padding: 10px;"></div>
				</div>
				<div class="span6">
				    <h3>Form Builder:</h3>
                    <div class="well">
                        <form id="user-form" class="form-horizontal" action="<%= request.getContextPath() %>/create" method="POST"></form>
                    </div>
				</div>
			</div>
		</div>
    
        <c:import url="/app/includes/footer.jsp" />
        
        <script src="<%= request.getContextPath() %>/assets/js/globals.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/forms.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/create/create.questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/create/create.form.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/create/create.js"></script>
        
		<script>
		    $(document).ready(function() {
			    <% // if there is an edit queryarg, need to get form ID to edit from it and bootstrap data %>
			    
			    var formModel = new TaskManager.Models.Form();
			    
			    Create = TaskManager.Create;
			    Create.start({ model: formModel });
			});
		</script>
    
        <script id="empty-view-template" type="text/template">
            <div class="controls">
                <@= message @>
            </div>
        </script>
    
        <script id="question-options-template" type="text/template">
            <div id="questions-content"></div>
            <hr />
            <a class="add disabled btn pull-right">Add Question</a>
        </script>    
    
        <script id="question-option-template" type="text/template">
            <input type="radio" id="question-<@= id @>" name="question-option">            
            <label for="question-<@= id @>"><@= label @></label>
        </script>
    
        <script id="form-template" type="text/template">
            <div class="control-group">
                <label class="control-label" for="formName">Form Name</label>
                <div class="controls">
                    <input type="text" id="formName" class="required-input" placeholder="Form Name" value="<@= formName @>" required>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="formDesc">Form Description</label>
                <div class="controls">
                    <textarea id="formDesc" class="required-input" placeholder="Enter a description of the form" required><@= formDescription @></textarea>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="formParticipants">Participants</label>
                <div class="controls">
                    <input type="hidden" id="formParticipants" style="width: 300px;">
                </div>
            </div>

            <div id="form-content"></div>

            <div class="form-actions">
                <div style="margin-bottom: 10px">
                    <label class="checkbox">
                        <input type="checkbox" id="participantsCanSeeAll" <@ if (participantsCanSeeAll) { @> checked <@ } @>>
                        Participants can see the responses of others.
                    </label>
                </div>

                <div style="margin-bottom: 10px">
                    <label class="checkbox">
                        <input type="checkbox" id="participantsCanEditResponse" <@ if (participantsCanEditResponse) { @> checked <@ } @>>
                        Participants can edit their response after submitting.
                    </label>
                </div>

                <div style="margin-bottom: 10px">
                    <label class="checkbox">
                        <input type="checkbox" id="participantResponseIsRequired" <@ if (participantResponseIsRequired) { @> checked <@ } @>>
                        Participants <strong>cannot</strong> opt out of completing this form
                    </label>
                </div>
                
                <a class="submit btn btn-large btn-primary">Submit</a>
                <a href="index.jsp" class="cancel btn btn-large">Cancel</a>
                <img class="loading" src="<%= request.getContextPath() %>/assets/img/loading.gif" style="display: none;" />
            </div>
        </script>
    
        <script id="checkbox-template" type="text/template">
            <div class="clearfix">
                <h4 class="pull-left">Checkbox</h3>
                <a class="delete pull-right">Delete</a>
            </div>

            <div>
                <div class="control-group">
                    <label class="control-label" for="prompt-<@= id @>">Prompt</label>
                    <div class="controls">
                        <input type="text" id="prompt-<@= id @>" class="required-input" value="<@= data.prompt @>" placeholder="Enter a prompt to the user" required>
                    </div>
                </div>

                <ol class="content"></ol>

                <div class="control-group">
                    <div class="controls">
                        <a class="btn add">Add Checkbox Option</a>
                    </div>
                </div>
            </div>
        </script>
        
        <script id="checkbox-item-template" type="text/template">
            <li class="controls">
                <input type="text" class="required-input" value="<@= data.label @>" placeholder="Checkbox text" required>
                <a class="delete">Delete</a>
            </li>
        </script>
        
        <script id="radio-template" type="text/template">
            <div class="clearfix">
                <h4 class="pull-left">Radio</h3>
                <a class="delete pull-right">Delete</a>
            </div>

            <div>
                <div class="control-group">
                    <label class="control-label" for="prompt-<@= id @>">Prompt</label>
                    <div class="controls">
                        <input type="text" id="prompt-<@= id @>" class="required-input" value="<@= data.prompt @>" placeholder="Enter a prompt to the user" required>
                    </div>
                </div>

                <ol class="content"></ol>

                <div class="control-group">
                    <div class="controls">
                        <a class="btn add">Add Radio Option</a>
                    </div>
                </div>
            </div>
        </script>
        
        <script id="radio-item-template" type="text/template">
            <li class="controls">
                <input type="text" class="required-input" value="<@= data.label @>" placeholder="Radio text" required>
                <a class="delete">Delete</a>
            </li>
        </script>
        
        <script id="textbox-template" type="text/template">
            <div class="clearfix">
                <h4 class="pull-left">Textbox</h3>
                <a class="delete pull-right">Delete</a>
            </div>

            <div>
                <div class="control-group">
                    <label class="control-label" for="prompt-<@= id @>">Prompt</label>
                    <div class="controls">
                        <input type="text" id="prompt-<@= id @>" class="required-input" value="<@= data.prompt @>" placeholder="Enter a prompt to the user" required>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="max-length-<@= id @>">Maximum Length</label>
                    <div class="controls">
                        <input type="number" id="max-length-<@= id @>" class="required-input" value="<@= data.maxLength @>" placeholder="Maximum number of characters" required>
                    </div>
                </div>
            </div>
        </script>
        
        <script id="select-template" type="text/template">
            <div class="clearfix">
                <h4 class="pull-left">Select (Drop Down)</h3>
                <a class="delete pull-right">Delete</a>
            </div>

            <div>
                <div class="control-group">
                    <label class="control-label" for="prompt-<@= id @>">Prompt</label>
                    <div class="controls">
                        <input type="text" id="prompt-<@= id @>" class="required-input" value="<@= data.prompt @>" placeholder="Enter a prompt to the user" required>
                    </div>
                </div>

                <div class="control-group">
                    <div class="controls">
                        <label class="checkbox">
                            <input id="is-multi-<@= id @>" type="checkbox"
                                <@ if (data.isMulti) { @>
                                    checked
                                <@ } @>
                            >Allow for multiple selections?
                        </label>
                    </div>
                </div>

                <ol class="content"></ol>

                <div class="control-group">
                    <div class="controls">
                        <a class="btn add">Add Select Option</a>
                    </div>
                </div>
            </div>
        </script>
        
        <script id="select-option-item-template" type="text/template">
            <li class="controls">
                <input type="text" class="required-input" value="<@= data.value @>" placeholder="Option text" required>
                <a class="delete">Delete</a>
            </li>
        </script>
        
        <script id="datetime-template" type="text/template"></script>
	</body>
</html>