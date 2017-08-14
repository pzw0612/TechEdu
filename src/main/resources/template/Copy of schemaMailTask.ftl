<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>schema mail task</title>
		
	<link href="css/bootstrap.min.css" rel="stylesheet">
    
    <link href="css/offcanvas.css" rel="stylesheet">

    <!--[if lt IE 9]><script src="js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
      	
	  </head>
	  <body>
	    <div><p>schema mail task-${currentDate}</p><div>
	    <hr>
	    <div class="container">
		  <#if schemaMailTaskList?size = 0>
		           <div class="row">
		              <div class="col-xs-12 col-sm-9">
				          <p class="pull-right visible-xs">
				               No messages.
				          </p>
				       </div>
				   </div>  
		  <#else>
		    <p>The messages  as follow:
		    <table border=0 cellspacing=2 cellpadding=2 width="100%">
		      <tr align=center valign=top>
		        <th bgcolor="#C0C0C0">id
		        <th bgcolor="#C0C0C0">poolId
		        <th bgcolor="#C0C0C0">serviceName
		        <th bgcolor="#C0C0C0">methodName
		        <th bgcolor="#C0C0C0">createTime
		        <th bgcolor="#C0C0C0">exception
		      <#list schemaMailTaskList as vo>
		        <tr align=left valign=top>
		          <td bgcolor="#E0E0E0">${vo.id}
		          <td bgcolor="#E0E0E0">${vo.poolId}
		          <td bgcolor="#E0E0E0">${vo.serviceName}
		          <td bgcolor="#E0E0E0">${vo.methodName}
		          <td bgcolor="#E0E0E0">${vo.createTime}
		          <td bgcolor="#E0E0E0">${vo.exception}
		      </#list>
		    </table>
		  </#if>
	    </div>
	    <hr>
        <footer>
              <p>Copyright© 1号店网上超市 2007-2015，All Rights Reserved</p>
        </footer>
	  </body>
 </html>
