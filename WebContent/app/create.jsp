<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
       <jsp:include page="/app/includes/header.jsp">
            <jsp:param name="title" value="Create a Form - Task Manager" />
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
				<div class="span3">
					<h3>Select Entry Type:</h3>
					<div id="question-options" class="well" style="padding: 10px;"></div>
				</div>
				<div class="span9">
				    <h3>Form Builder:</h3>
                    <div class="well">
                        <form id="user-form" class="form-horizontal" action="<%=request.getContextPath()%>/create" method="POST"></form>
                    </div>
				</div>
			</div>
		</div>
        <jsp:include page="/app/includes/footer.jsp" />
        
        <script src="<%= request.getContextPath() %>/assets/js/globals.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/create/questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/create/create.questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/create/create.form.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/create/create.js"></script>
        
		<script>
			$(document).ready(function() {
			    Create = TaskManager.Create;
			    Create.start();
			    
// 				$('.columns li').draggable({
// 					appendTo: "body",
// 					helper: 'clone',
// 					cursor: "pointer"
// 				});
				
// 				$('#form-builder').droppable({
// 					activeClass: "ui-state-default",
// 					hoverClass: "ui-state-hover",
// 					accept: ":not(.ui-sortable-helper)",
// 					drop: function(event, ui) {
// 						$(this).find(".placeholder").remove();
// 						$('<li></li>').text(ui.draggable.text()).appendTo(this);
// 					}
// 				}).sortable({
// 					placeholder: "ui-placeholder",
// 					forcePlaceholderSize: true,
// 					sort: function() {
// 						$(this).removeClass("ui-state-default");
// 					}
// 				});
			});
		</script>
    
        <script id="question-option-template" type="text/template">
            <input type="radio" id="question-<@= id @>" name="question-option">            
            <label for="question-<@= id @>"><@= label @></label>
        </script>
    
        <script id="question-options-template" type="text/template">
            <div id="questions-content"></div>
            <hr />
            <a class="add disabled btn pull-right">Add Question</a>
        </script>
    
        <script id="form-template" type="text/template">
            <div class="control-group">
                <label class="control-label" for="formName">Form Name</label>
                <div class="controls">
                    <input type="text" id="formName" placeholder="Form Name">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="description">Form Description</label>
                <div class="controls">
                    <textarea id="formDesc" placeholder="Enter a description of the form"></textarea>
                </div>
            </div>
            <ol id="form-content"></ol>
            <div class="form-actions">
                <div style="margin-bottom: 10px">
                    <label class="checkbox"><input type="checkbox" name="participantsSeeAll">Participants can see the responses of others.</label>
                </div>
                
                <button class="btn btn-large btn-primary" type="submit">Submit</button>
                <a class="btn btn-large">Cancel</a>
            </div>
        </script>
    
        <script id="checkbox-template" type="text/template">
            <div class="clearfix">
                <h4 class="pull-left">Checkbox</h3>
                <a class="delete pull-right">Delete</a>
            </div>
            <div>
                <div class="control-group">
                    <label class="control-label" for="description">Description</label>
                    <div class="controls">
                        <input type="text" id="description" placeholder="Enter a description of the check field">
                    </div>
                </div>
                <ol class="content"></ol>
                <a class="add btn">Add Checkbox Option</a>
            </div>
        </script>
        
        <script id="checkbox-item-template" type="text/template">
            <input type="text" placeholder="Checkbox text">
            <a class="delete">Delete</a>
        </script>
        
        <script id="radio-template" type="text/template">
            <div class="clearfix">
                <h4 class="pull-left">Radio</h3>
                <a class="delete pull-right">Delete</a>
            </div>
            <div>
                <div class="control-group">
                    <label class="control-label" for="description">Description</label>
                    <div class="controls">
                        <input type="text" id="description" placeholder="Enter a description of the radio field">
                    </div>
                </div>
                <ol class="content"></ol>
                <a class="add btn">Add Radio Option</a>
            </div>
        </script>
        
        <script id="radio-item-template" type="text/template">
            <input type="text" placeholder="Radio text">
            <a class="delete">Delete</a>
        </script>
        
        <script id="textbox-template" type="text/template">
            <div class="clearfix">
                <h4 class="pull-left">Textbox</h3>
                <a class="delete pull-right">Delete</a>
            </div>
            <div>
                <div class="control-group">
                    <label class="control-label" for="description">Description</label>
                    <div class="controls">
                        <input type="text" id="description" placeholder="Enter a description of the text field">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="maxLength">Maximum Length</label>
                    <div class="controls">
                        <input type="text" id="maxLength" placeholder="Maximum number of characters" value="1000">
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
                    <label class="control-label" for="description">Description</label>
                    <div class="controls">
                        <input type="text" id="description" placeholder="Enter a description of the text field">
                    </div>
                </div>
                <div class="control-group">
                    <div class="controsl">
                        <label class="control-label">
                            <input type="checkbox">Allow for multiple selections?
                        </label>
                    </div>
                </div>
                <ol class="content"></ol>
                <a class="btn add">Add Select Option</a>
            </div>
        </script>
        
        <script id="select-option-item-template" type="text/template">
            <input type="text" placeholder="Option text">
            <a class="delete">Delete</a>
        </script>
        
        <script id="datetime-template" type="text/template"></script>
	</body>
</html>