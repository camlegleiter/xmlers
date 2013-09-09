<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:include page="app/includes/header.jsp">
            <jsp:param name="title" value="Task Manager Sign In" />
        </jsp:include>

        <style type="text/css">
            body {
                padding-top: 40px;
                padding-bottom: 40px;
                background-color: #f5f5f5;
            }
            .form-signin {
                max-width: 300px;
                padding: 19px 29px 29px;
                margin: 0 auto 20px;
                background-color: #fff;
                border: 1px solid #e5e5e5;
                -webkit-border-radius: 5px;
                -moz-border-radius: 5px;
                border-radius: 5px;
                -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
                -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
                box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            }
            .form-signin .form-signin-heading, .form-signin .checkbox {
                margin-bottom: 10px;
            }
            .form-signin input[type="text"], .form-signin input[type="password"] {
                font-size: 16px;
                height: auto;
                margin-bottom: 15px;
                padding: 7px 9px;
            }
            .center {
                float: none;
                margin: 0 auto;
                text-align: center;
                max-width: 300px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <jsp:include page="/app/includes/noscript.jsp" />
            
            <div class="row-fluid">
                <div class="span4"></div>
                <div class="span4">
                    <ul class="nav nav-tabs" id="loginTabs">
                        <li class="active"><a href="#login">Login</a></li>
                        <li><a href="#register">Register</a></li>
                    </ul>
                     
                    <div class="tab-content">
                        <div class="tab-pane active" id="login">
                            <form class="form-signin" action="<%= request.getContextPath() %>/login" method="POST">
                                <h2 class="form-signin-heading">Please sign in</h2>
                                <div class="control-group" id="signin-input-group">
                                    <div class="controls">
                                        <input type="text" class="input-block-level" placeholder="User name" name="username">
                                        <input type="password" class="input-block-level" placeholder="Password" name="password">
                                    </div>
                                </div>
                                <label class="checkbox">
                                <input type="checkbox" value="remember-me" name="remember"> Remember me
                                </label>
                                <button class="btn btn-large btn-primary" type="submit">Sign in</button>
                                <span class="help-inline" id="login-error"></span>
                            </form>                    
                        </div>
                        
                        <div class="tab-pane" id="register">
                            <form class="form-signin" action="<%= request.getContextPath() %>/register" method="POST">
                                <h2 class="form-signin-heading">Register</h2>
                                <div class="control-group" id="input-group">
                                    <div class="controls">
                                        <input type="text" required class="input-block-level" placeholder="First Name" name="first-name">
                                        <input type="text" required class="input-block-level" placeholder="Last Name" name="last-name">
                                        <input type="text" required class="input-block-level" placeholder="NetID" name="username">
                                        <input type="text" required class="input-block-level" placeholder="Email" name="email">
                                        <input type="password" required class="input-block-level" placeholder="Password" name="password">
                                        <input type="password" required class="input-block-level" placeholder="Re-enter password" name="password-check">
                                    </div>
                                </div>
                                <button class="btn btn-large btn-primary" type="submit">Register</button>
                                <span class="help-inline" id="register-error"></span>
                            </form>    
                        </div>
                    </div>
                
                </div>
                <div class="span4"></div>
            </div>    
        </div>
        <script src="assets/js/vendor/jquery.min.js"></script>
        <script src="assets/js/vendor/bootstrap.min.js"></script>
        <script>
            $(document).ready(function() {
                $('.form-signin').submit(function() {
                    
                    $form = $(this);
                    $.post($form.attr('action'), $form.serialize(), function(responseText) {
                        if ("" != responseText) {
                            $('#signin-input-group').addClass("error");
                            $('#login-error').html(responseText).slideDown();
                        } else {
                            window.location.replace("app/index.jsp");
                        }
                    });
                    return false;
                });
                
                $('#error').hide();
                
                $('#loginTabs a').click(function(e) {
                    e.preventDefault();
                    $(this).tab('show');
                });
            });
        </script>
    </body>
</html>