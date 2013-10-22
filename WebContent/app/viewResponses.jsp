<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
        <jsp:include page="/app/includes/header.jsp">
            <jsp:param name="title" value="View Results - Task Manager" />
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
        <link href="/xmlers/assets/css/style.css" rel="stylesheet">
	</head>
	<body>
		
        <jsp:include page="/app/includes/nav.jsp" />
		
		<div class="container-fluid">
			<jsp:include page="/app/includes/noscript.jsp" />	
			
			<div class="row-fluid">
				<div id="viewOptions" class="span2">
				    <h2>View Options</h2>
					<div class="well"></div>
				</div>
				<div id="responseTable" class="span10"></div>
			</div>
		</div>
    
		<jsp:include page="/app/includes/footer.jsp" />
    
		<script src="<%= request.getContextPath() %>/assets/js/vendor/jquery.tablesorter.min.js"></script>
        
        <script src="<%= request.getContextPath() %>/assets/js/globals.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/responses.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/views.js"></script>
        
        <script src="<%= request.getContextPath() %>/assets/js/view/view.options.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/view/view.table.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/view/view.js"></script>

        <script>
            $(document).ready(function() {
                <% // Insert view bootstrap data %>
                var data = {
                    "formID":123456789,
                    "formName":"Bridge Keeper Questionnaire",
                    "formDescription":"Who would cross the Bridge of Death must answer me these questions three, ere the other side he see.",
                    "formOwner":987654321,
                    "formQuestions":[
                      {
                        "id":234567890,
                        "type":"Textbox",
                        "prompt":"What is your name?"
                      },
                      {
                        "id":234567891,
                        "type":"Radio",
                        "prompt":"What is your quest?"
                      },
                      {
                        "id":234567892,
                        "type":"Select",
                        "prompt":"What is your favorite color?"
                      },
                      {
                        "id":234567893,
                        "type":"Radio",
                        "prompt":"Did you survive the Bridge of Death?"
                      }
                    ],
                    "responses":[
                      {
                        "responseID":999999999,
                        "responseOwner":888888888,
                        "responseOwnerName":"Sir Launcelot the Brave",
                        "responses":[
                          {
                            "questionID":234567890,
                            "type":"Textbox",
                            "value":"Sir Launcelot of Camelot"
                          },
                          {
                            "questionID":234567891,
                            "type":"Radio",
                            "value":"To seek the Holy Grail"
                          },
                          {
                            "questionID":234567892,
                            "type":"Select",
                            "value":"Blue"
                          },
                          {
                            "id":234567893,
                            "type":"Radio",
                            "value":"Yes"
                          }
                        ]
                      },
                      {
                        "responseID":222222222,
                        "responseOwner":333333333,
                        "responseOwnerName":"Sir Robin the Not-Quite-So-Brave-As-Sir Launcelot",
                        "responses":[
                          {
                            "questionID":234567890,
                            "type":"Textbox",
                            "value":"Sir Robin of Camelot"
                          },
                          {
                            "questionID":234567891,
                            "type":"Radio",
                            "value":"To seek the Holy Grail"
                          },
                          {
                            "questionID":234567892,
                            "type":"Select",
                            "values":[
                            ]
                          },
                          {
                            "id":234567893,
                            "type":"Radio",
                            "value":"No"
                          }
                        ]
                      },
                      {
                        "responseID":222222222,
                        "responseOwner":333333333,
                        "responseOwnerName":"Sir Galahad the Pure",
                        "responses":[
                          {
                            "questionID":234567890,
                            "type":"Textbox",
                            "value":"Sir Galahad of Camelot"
                          },
                          {
                            "questionID":234567891,
                            "type":"Radio",
                            "value":"I seek the Grail"
                          },
                          {
                            "questionID":234567892,
                            "type":"Select",
                            "values":[
                              "Blue",
                              "Yellow"
                            ]
                          },
                          {
                            "id":234567893,
                            "type":"Radio",
                            "value":"No"
                          }
                        ]
                      },
                      {
                        "responseID":333333333,
                        "responseOwner":444444444,
                        "responseOwnerName":"King Arthur",
                        "responses":[
                          {
                            "questionID":234567890,
                            "type":"Textbox",
                            "value":"Arthur, King of the Britains"
                          },
                          {
                            "questionID":234567891,
                            "type":"Radio",
                            "value":"To seek the Holy Grail"
                          },
                          {
                            "questionID":234567892,
                            "type":"Select",
                            "values":[
                            ]
                          },
                          {
                            "id":234567893,
                            "type":"Radio",
                            "value":"Yes"
                          }
                        ]
                      }
                    ]
                  };
                
                var tableData = new TaskManager.Models.View(data);
                
                View = TaskManager.View;
                View.start({ model: tableData }); 
            });
        </script>

        <script id="table-layout" type="text/template">
            <h3><@= formName @> Records</h3>
            <h4>Form Description: <@= formDescription @></h4>
            
            <table class="table table-bordered table-striped">
                <thead></thead>
                <tbody></tbody>
            </table>
        </script>
        
        <script id="table-header-template" type="text/template">
            <@= prompt @>
        </script>
        
        <script id="table-row-template" type="text/template">
            <td><@= responseOwnerName @></td>
            <@ _.each(responses.models, function(response) { @>
                <td><@= getResponseValue(response) @></td>
            <@ }); @>
        </script>
	</body>
</html>