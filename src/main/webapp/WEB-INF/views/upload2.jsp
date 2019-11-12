<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>Upload File Request Page</title>
<link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
<link href="<c:url value='/static/css/default.css' />" rel="stylesheet"></link>
</head>
<body>
<section class='upload-section'>
	<form method="POST" action="upload/status" enctype="multipart/form-data">
      <h3>Step 1. Upload the file</h3>
      <div class="form-group">
      	<input class='form-control' placeholder="File Name..." type="text" name="name" required>
      </div>
      <div class="form-group">
		<input class="form-control" placeholder="Dataset ID" type="number" name="dataset" required>
      </div>
      <div class="form-group">
      	<input class="form-control" type="file" name="file" required>
      </div>
      <div class="form-group">
      	<input class="form-control btn btn-info" type="submit" value="Upload">
      </div>
	</form>
</section>
</body>
</html>