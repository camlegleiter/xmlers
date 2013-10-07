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
	</head>
	<body>
	
        <jsp:include page="/app/includes/nav.jsp" />
		
		<div class="container-fluid">
			<jsp:include page="/app/includes/noscript.jsp" />
			
			<div class="row-fluid">
				<div class="span3">
					<div class="row-fluid">
						<div class="span4">
							<h3 style="display: inline-block">Forms</h3>
						</div>
						<div class="span8">
							<div class="btn-group pull-right" style="margin-top: 16px">
								<a class="btn" href="<%= request.getContextPath() %>/app/create.jsp" title="Create a New Form">+ Form</a>
								<a class="btn" title="Delete an Existing Form">- Form</a>	
							</div>
						</div>						
					</div>
					<div class="well sidebar-nav">
						<ul id="myForms" class="nav nav-list">
							<li class="nav-header">My Forms</li>
							<li><a href="#" id="123456">Form 1</a></li>
							<li><a href="#" id="951732">Form 2</a></li>
							<li><a href="#" id="682457">Form 3</a></li>
						</ul>
						<ul id="otherForms" class="nav nav-list">
							<li class="nav-header">Forms I Need to Complete</li>
							<li><a href="#">Other Form 1</a></li>
							<li><a href="#">Other Form 2</a></li>
							<li><a href="#">Other Form 3</a></li>
						</ul>
					</div>
				</div>
				<div class="span9">
					<h3>Content</h3>
					<div class="well">
						<div id="initial-content"><h4>Click on a form to get started!</h4></div>
						<div id="form-info">
							<h4 id="form-name"></h4>
							<p id="form-description"></p>
							<h4 id="form-questions"></h4>
							<ol id="form-questions-list"></ol>
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
					</div>
				</div>
			</div>		
		</div>
	
        <jsp:include page="/app/includes/footer.jsp" />

		<script>
			$(document).ready(function() {
				var myForms, otherForms;
				
				$('#myForms li a').click(function() {
					$('#initial-content').remove();
					var id = $(this).attr('id');
					if (id) {
						if (!myForms) {
							$.ajax({
								url: "form_metadata.json",
								type: "POST",
								dataType: "json"
							}).done(function(json) {
								myForms = json["forms"];
							}).fail(function(jqXHR, textStatus) {
								$('div#form-info').html('<h3><strong>Could not load the form data at this time :(</strong></h3>');
							}).always(function() {
								if (myForms) buildFormInfo(myForms[id], id);
							});
						} else {
							buildFormInfo(myForms[id], id);
						}
					}
				});
							
				$('#otherForms li a').click(function() {
					$('#initial-content').remove();
					var id = $(this).attr('id');
					if (id) {
						if (!otherForms) {
							$.ajax({
								
							})
						}
					}
				});
							
				$('#reemailParticipants').click(function() {
					$('#form-id').attr('value');
					
					$.ajax({
						url: "<%= request.getContextPath() %>/app/index",
						type: "POST",
						data: $('#form-id').attr('value')
					}).done(function(result) {
						
					}).fail(function(result) {
						
					}).always(function() {
						
					});
					
					return false;
				});
					
				function clearFormInfo() {
					$('#form-name').html('');
					$('#form-description').html('');
					$('#form-questions').html('');
					$('#form-questions-list').html('');
					$('#form-buttons').hide();
					$('#other-form-buttons').hide();
				}
				
				function buildFormInfo(json, id) {
					clearFormInfo();
					$('#form-name').html('Name: ' + json.name);
					$('#form-description').html('<h4>Description:</h4>' + json.description);
					
					$('#form-questions').html('').prepend('Questions:');
					
					$('#form-questions-list').html('');
					$.each(json.questions, function(index, q) {
						$('#form-questions-list').append('<li><strong>' + q.type + ': </strong>' + q.description + '</li>');
						if (index == 5) {
							$('#form-questions-list').append('<li>...</li>');
							return false;
						}
					});
					
					$('#form-id').attr('value', id);
					$('#form-buttons').show();
				}
				
				function buildOtherFormInfo(json, id) {
					clearFormInfo();
					$('#form-name').html('Name: ' + json.name);
					$('#form-description').html('<h4>Description:</h4>' + json.description);
					
					$('#form-questions').html('').prepend('Questions:');
					$.each(json.questions, function(index, q) {
						$('#form-questions-list').append('<li><strong>' + q.type + ': </strong>' + q.description + '</li>');
						if (index == 5) {
							$('#form-questions-list').append('<li>...</li>');
							return false;
						}
					});
					
					$('#otherform-id').attr('value', id);
					$('#other-form-buttons').show();
				}
			});
		</script>
	</body>
</html>