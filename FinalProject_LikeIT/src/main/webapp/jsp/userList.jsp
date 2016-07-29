<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/dataTables.bootstrap.min.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <link rel="stylesheet"
          href="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css"/>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap.min.js"></script>
    <link rel="stylesheet"
          href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/languages.min.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/navbar_orange.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/main.css"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/userList.css"/>
    <script src="${pageContext.servletContext.contextPath}/resources/js/main.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/js/userList.js"></script>
    <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/img/logo_icon.ico"/>
    <title>Users</title>
</head>
<body>
<%@include file="/jsp/include/menu.jsp" %>
<script type="text/javascript">
    selectMenuItem('#menu_users')
</script>
<div class="container-fluid text-center main-wrapper">
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-9 col-lg-offset-1 text-left">
            <div class="row">
                <div class="col-md-4 col-sm-7">
                    <form class="navbar-form navbar-left" role="search">
                        <div class="form-group has-feedback">
                            <input type="text" class="form-control userSearch" placeholder="Search by name"/>
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                    </form>
                </div>
                <div class="col-md-4 col-sm-7 text-center">
                    <div class="btn-group" id="filter-group">
                        <button type="button" class="btn btn-success btn-filter" data-target="active">Active
                        </button>
                        <button type="button" class="btn btn-death btn-filter" data-target="banned">Banned
                        </button>
                        <button type="button" class="btn btn-default btn-filter" data-target="all">All</button>
                    </div>
                </div>
            </div>
            <div class="panel">
                <div class="panel-body">
                    <table id="user_list" class="table borderless dataTable">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Username</th>
                            <th>Rating</th>
                            <th>Since</th>
                            <th>State</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr data-status="active">
                            <th scope="row">1</th>
                            <td><a href="user.html">Clarissa Fray</a></td>
                            <td>8.88</td>
                            <td>05/15/2016</td>
                            <td class="state-active">Active</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default" data-toggle="modal" title="edit"
                                            data-target="#edit">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </button>
                                    <button type="button" class="btn btn-death" data-toggle="modal"
                                            title="ban">
                                        <span class="glyphicon glyphicon-ban-circle"></span>
                                    </button>
                                    <button type="button" class="btn btn-danger" data-toggle="modal" title="delete"
                                            data-target="#delete">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr data-status="active">
                            <th scope="row">2</th>
                            <td><a href="user.html">Johnathan Wayland</a></td>
                            <td>9.01</td>
                            <td>10/22/2015</td>
                            <td class="state-active">Active</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default" data-toggle="modal" title="edit"
                                            data-target="#edit">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </button>
                                    <button type="button" class="btn btn-death" data-toggle="modal"
                                            title="ban">
                                        <span class="glyphicon glyphicon-ban-circle"></span>
                                    </button>
                                    <button type="button" class="btn btn-danger" data-toggle="modal" title="delete"
                                            data-target="#delete">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr data-status="active">
                            <th scope="row">3</th>
                            <td><a href="user.html">Isabelle Lightwood</a></td>
                            <td>7.15</td>
                            <td>02/24/2016</td>
                            <td class="state-active">Active</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default" data-toggle="modal" title="edit"
                                            data-target="#edit">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </button>
                                    <button type="button" class="btn btn-death" data-toggle="modal"
                                            title="ban">
                                        <span class="glyphicon glyphicon-ban-circle"></span>
                                    </button>
                                    <button type="button" class="btn btn-danger" data-toggle="modal" title="delete"
                                            data-target="#delete">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        <tr data-status="banned">
                            <th scope="row">4</th>
                            <td><a href="user.html">Simon Lewis</a></td>
                            <td>6.83</td>
                            <td>01/12/2016</td>
                            <td class="state-banned">Banned</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default" data-toggle="modal" title="edit"
                                            data-target="#edit">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </button>
                                    <button type="button" class="btn btn-success" data-toggle="modal"
                                            title="activate">
                                        <span class="glyphicon glyphicon-ok-circle"></span>
                                    </button>
                                    <button type="button" class="btn btn-danger" data-toggle="modal" title="delete"
                                            data-target="#delete">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%@include file="/jsp/include/rightSidePanel.jsp" %>
    </div>
    <%@include file="/jsp/include/footer.jsp" %>
</div>
</body>
</html>