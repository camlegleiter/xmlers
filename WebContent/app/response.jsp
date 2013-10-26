<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <c:import url="/app/includes/header.jsp">
            <c:param name="title" value="Response - Task Manager" />
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
        
        <div class="container">
            <c:import url="/app/includes/noscript.jsp" />   
            
            <div class="row">
                <div class="span2"></div>
                <div class="span8">
                    <div class="well">
                        <form id="response-form" class="form-horizontal" action="<%= request.getContextPath() %>/create" method="POST"></form>
                    </div>
                </div>
                <div class="span2"></div>
            </div>
        </div>
        
        <c:import url="/app/includes/footer.jsp" />
        
        <script src="<%= request.getContextPath() %>/assets/js/models/questions.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/models/forms.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/response/response.form.js"></script>
        <script src="<%= request.getContextPath() %>/assets/js/response/response.js"></script>
        
        <script>
            $(document).ready(function() {
                // Insert JSP tag to bootstrap model data within the Form constructor 
                var data = {
                  "formID":123456789,
                  "formName":"Example Form",
                  "formDescription":"Bacon ipsum dolor sit amet cow pork belly deserunt excepteur.",
                  "formOwner":987654321,
                  "formParticipants":[
                    "email1@example.com",
                    "email2@example.com",
                    "email3@example.com"
                  ],
                  "participantsCanSeeAll":false,
                  "formQuestions":[
                    {
                      "type":"Textbox",
                      "maxLength":1000,
                      "prompt":"Duis pork belly salami aute ea, non dolore pork ullamco ut ham hock."
                    },
                    {
                      "type":"Checkbox",
                      "prompt":"Aliquip ham laboris, bacon jerky tempor magna proident capicola id do voluptate.",
                      "checkboxes":[
                        {
                          "label":"Checkbox1"
                        },
                        {
                          "label":"Checkbox2"
                        },
                        {
                          "label":"Checkbox3"
                        }
                      ]
                    },
                    {
                      "type":"Radio",
                      "prompt":"Labore cillum capicola short loin et, tempor non in fugiat qui swine occaecat reprehenderit doner.",
                      "radios":[
                        {
                          "label":"Radio1"
                        },
                        {
                          "label":"Radio2"
                        },
                        {
                          "label":"Radio3"
                        }
                      ]
                    },
                    {
                      "type":"Radio",
                      "prompt":"Labore cillum capicola short loin et, tempor non in fugiat qui swine occaecat reprehenderit doner.",
                      "radios":[
                        {
                          "label":"Radio1"
                        },
                        {
                          "label":"Radio2"
                        },
                        {
                          "label":"Radio3"
                        }
                      ]
                    },
                    {
                      "type":"Select",
                      "prompt":"Aliquip beef ribs dolore shoulder ad consectetur pork belly minim.",
                      "isMulti":true,
                      "options":[
                        {
                          "value":"Option1"
                        },
                        {
                          "value":"Option2"
                        },
                        {
                          "value":"Option3"
                        }
                      ]
                    }
                  ]
                };
                
                var formData = new TaskManager.Models.Form(data);
                
                Response = TaskManager.Response;
                Response.start(formData);
            });
        </script>
    
        <script id="empty-view-template" type="text/template">
            <div class="controls">
                <@= message @>
            </div>
        </script>
    
        <script id="response-template" type="text/template">
            <h3>Form Name: <@= formName @></h3>
            <h4>Description: <@= formDescription @></h4>

            <ol class="response-content"></ol>

            <div class="form-actions">
                <a class="submit btn btn-large btn-primary">Submit</a>
                <a href="index.jsp" class="cancel btn btn-large">Cancel</a>
                <img class="loading" src="<%= request.getContextPath() %>/assets/img/loading.gif" style="display: none;" />
            </div>
        </script>
    
        <script id="checkbox-template" type="text/template">
            <strong><@= prompt @></strong>
            <div class="content"></div>
        </script>
        
        <script id="checkbox-item-template" type="text/template">
            <label class="checkbox inline">
                <input type="checkbox" class="required-input" name="optionsCheckboxes<@= id @>" value="<@= data.label @>" required>
                <@= data.label @>
            </label>
        </script>
        
        <script id="radio-template" type="text/template">
            <strong><@= prompt @></strong>
            <div class="content"></div>
        </script>
        
        <script id="radio-item-template" type="text/template">
            <label class="radio inline">
                <input type="radio" class="required-input" name="optionsRadios<@= id @>" value="<@= data.label @>" required>
                <@= data.label @>
            </label>
        </script>
        
        <script id="textbox-template" type="text/template">
            <strong><@= prompt @></strong>
            <div>
                <textarea class="required-input" rows="4" maxlength="<@= maxLength @>" required></textarea>
            </div>
            <p>Characters remaining: <span class="character-count"><@= maxLength @></span></p>
        </script>
        
        <script id="select-template" type="text/template">
            <strong><@= prompt @></strong>
            <div>
                <select class="content required-input" <@ if (isMulti) { @> multiple <@ } @>>
                    <option></option>
                </select>
            </div>
        </script>
        
        <script id="select-option-item-template" type="text/template">
            <@= value @>
        </script>
    </body>
</html>