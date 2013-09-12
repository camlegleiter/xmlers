<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
       <jsp:include page="/app/includes/header.jsp">
            <jsp:param name="title" value="Task Manager Home" />
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
					<h3>Select Column Type:</h3>
					<div class="well" style="padding: 10px;">
						<ul class="columns">
							<li class="ui-state-highlight">Text Box</li>
							<li class="ui-state-highlight">Multiple Choice</li>
							<li class="ui-state-highlight">Checkbox</li>
						</ul>
					</div>
				</div>
				<div class="span9">
					<h3>Form Builder:</h3>
					<form id="user-form" action="<%=request.getContextPath()%>/create" method="POST">
						<fieldset>
							<input type="text" class="inline-block-level" placeholder="Form Name" name="formName" required>
							<div>
								<ol id="form-builder">
									<li class="placeholder">Drag a column type here!</li>
								</ol>
							</div>
							<div class="form-actions">
								<div style="margin-bottom: 10px">
									<label class="checkbox"><input type="checkbox" name="participantsSeeAll">Participants can see the responses of others.</label>
								</div>
								
								<button class="btn btn-large btn-primary" type="submit">Submit</button>
								<a class="btn btn-large">Cancel</a>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
        <jsp:include page="/app/includes/footer.jsp" />
        
        <script src="<%= request.getContextPath() %>/assets/js/globals.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/create.js"></script>
		<script>
			$(document).ready(function() {
			    Create = TaskManager.Create;
			    Create.start();
			    
				$('.columns li').draggable({
					appendTo: "body",
					helper: 'clone',
					cursor: "pointer"
				});
				
				$('#form-builder').droppable({
					activeClass: "ui-state-default",
					hoverClass: "ui-state-hover",
					accept: ":not(.ui-sortable-helper)",
					drop: function(event, ui) {
						$(this).find(".placeholder").remove();
						$('<li></li>').text(ui.draggable.text()).appendTo(this);
					}
				}).sortable({
					placeholder: "ui-placeholder",
					forcePlaceholderSize: true,
					sort: function() {
						$(this).removeClass("ui-state-default");
					}
				});
			});
		</script>
    
        <script id="checkbox-template" type="text/template">
            <div class="clearfix">
                <h3 class="pull-left">Checkbox</h3>
                <a class="delete">Delete</span>
            </div>
            <div>
                <div class="control-group">
                    <label class="control-label" for="description">Description</label>
                    <div class="controls">
                        <input type="text" id="description" placeholder="Enter a description of the check field">
                    </div>
                </div>
                <ul class="checkbox-content"></ul>
                <a class="btn add-checkbox">Add Checkbox Option</a>
            </div>
        </script>
        
        <script id="checkbox-item-template" type="text/template">
            <input type="text" placeholder="Checkbox text">
            <a class="delete">Delete</a>
        </script>
        
        <script id="radio-template" type="text/template">
            <div class="clearfix">
                <h3 class="pull-left">Radio</h3>
                <a class="delete">Delete</span>
            </div>
            <div>
                <div class="control-group">
                    <label class="control-label" for="description">Description</label>
                    <div class="controls">
                        <input type="text" id="description" placeholder="Enter a description of the radio field">
                    </div>
                </div>
                <ul class="radio-content"></ul>
                <a class="btn add-radio">Add Radio Option</a>
            </div>
        </script>
        
        <script id="radio-item-template" type="text/template">
            <input type="text" placeholder="Radio text">
            <a class="delete">Delete</a>
        </script>
        
        <script id="text-template" type="text/template">
            <div class="clearfix">
                <h3 class="pull-left">Textbox</h3>
                <a class="delete">Delete</span>
            </div>
            <div>
                <div class="control-group">
                    <label class="control-label" for="description">Description</label>
                    <div class="controls">
                        <input type="text" id="description" placeholder="Enter a description of the text field">
                    </div>
                </div>
            </div>
        </script>
        
        <script id="datetime-template" type="text/template">
            
        </script>
	</body>
</html>