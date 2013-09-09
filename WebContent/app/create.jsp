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
					<form action="<%=request.getContextPath()%>/create" method="POST">
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
		<script src="<%= request.getContextPath() %>/assets/js/vendor/jquery.min.js"></script>
		<script src="<%= request.getContextPath() %>/assets/js/vendor/jquery-ui.min.js"></script>
		<script src="<%= request.getContextPath() %>/assets/js/vendor/bootstrap.min.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/vendor/underscore.min.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/vendor/backbone.min.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/vendor/marionette.min.js"></script>
		<script>
			$(document).ready(function() {
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
	</body>
</html>