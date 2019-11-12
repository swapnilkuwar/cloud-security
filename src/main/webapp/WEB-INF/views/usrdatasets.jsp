<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>Users</title>
<link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
<link href="<c:url value='/static/css/default.css' />" rel="stylesheet"></link>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Ladda/1.0.6/ladda-themeless.min.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css" rel="stylesheet" />
<link href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.bootstrap.min.css" rel="stylesheet" />

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Ladda/1.0.6/spin.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Ladda/1.0.6/ladda.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.3/js/responsive.bootstrap.min.js"></script>


</head>
<body style="background: #dcdcdc36;">
<article class="container">
	<div class="gap-50"></div>
	<div class="row col-xs-12 col-md-8 col-md-offset-2">
		<div class="btn-group btn-group-justified">
		   <div class="btn-group">
		     <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#uploadModal">Upload Files</button>
		   </div>
		</div>
	</div>
	<div class="gap-50"></div>	
	<section class="row">
		<div class="col-xs-12 col-md-6">
			<h1 class="text-center">M Table</h1>
			<table id="mTable" class="table table-striped table-bordered dt-responsive nowrap" style="width:100%">
				<thead>
		            <tr>
			        	<th> DataSet Id </th> 
						<th> Amount(Total No of files) </th>
				    	<th> Weight(priority) </th>
				    </tr>
		        </thead>
		        <tbody>
		        
		        </tbody>
			</table>
		</div>
		<div class="col-xs-12 col-md-6">
			<h1 class="text-center">S Table</h1>
			<table id="sTable" class="table table-striped table-bordered dt-responsive nowrap" style="width:100%">
				<thead>
		            <tr>
						<th> SD </th> 
						<th> Weight(priority) </th>
				    </tr>
		        </thead>
		        <tbody>
		        
		        </tbody>
			</table>
		</div>
	</section>
	<section class="row">
		<div class="col-xs-12 col-md-12">
			<h1 style="margin-top: 80px;" class="text-center">Files</h1>
			<table id="files" class="table table-striped table-bordered dt-responsive nowrap" style="width:100%">
				<thead>
		            <tr>
			        	<th> File Id </th> 
						<th> DataSet Id </th> 
						<th> AES ENC. </th> 
						<th> AES DEC. </th> 
						<th> RSA ENC. </th> 
						<th> RSA DEC. </th> 
						<th> CPABE ENC. </th> 
						<th> CPABE DEC. </th> 
						<th> Encryption Status </th>
						<th> File Path </th>
						<th> Download </th>
						<th> Size </th>
				    </tr>
		        </thead>
		        <tbody>
		        
		        </tbody>
			</table>
		</div>
	</section>
	<div class="gap-50"></div>
		
	<!-- Modal -->
	<div class="modal fade" id="uploadModal" role="dialog">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Cloud Security</h4>
	      </div>
	      <div class="modal-body">
		      	<h4>File Upload:</h4>
				<form action="<c:url value='/files/upload/status' />" method="post" enctype="multipart/form-data">
				    <div class="form-group">
				    <p>Group: pdf => Dataset 1, Dataset 2 </p>
				    <p>Group: images => Dataset 3, Dataset 4 </p>
				    <p>Group: audio => Dataset 5, Dataset 6 </p>
				    <p>Group: video => Dataset 7, Dataset 8 </p>
				    <p>Group: other => Dataset 9, Dataset 10 </p>
					<br>
			    	<p>DataSets:</p>
				    	<div class="datasetid-holder"></div>
					</div>
				    <div class="form-group">
				      <label>Select File</label> 
				      <input class="form-control" type="file" name="file">
				    </div>
				    <div class="form-group">
				      <button class="btn btn-primary uploadForm" type="button" data-style="zoom-in">Upload</button>
				    </div>
			    </form>
			    <br />
				
			   <div class="progress">
			     <div id="progressBar" class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar"
			       aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">0%</div>
			   </div>
			
			<div id="alertMsg" style="color: red;font-size: 18px;"></div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
</article>
<script type="text/javascript">
	files = "";
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$(document).ready(function() {
	    load();	
	});

	load = function(){
	  $('.datasetid-holder').text('');
	  $.ajax({
			url:'datasets/list',
			type:'GET',     
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader(header, token);
	        },
			success: function(response){
				data = response.data;
				$('.tr').remove();
				for(i=0; i<response.data.length; i++){
					
					$('.datasetid-holder').append("<label class='radio-inline'><input type='radio' value='"
							+ response.data[i].id + "' name='dataset'>Dataset " 
							+ response.data[i].id + "</label>");
					for(j=0; j<response.data[i].userFiles.length; j++){
						$("#files tbody").append("<tr class='tr'> <td> "
								+response.data[i].userFiles[j].file_id
								+" </td> <td> "+response.data[i].id
								+" </td> <td> "+response.data[i].userFiles[j].aes_enc_time
								+" </td> <td> "+response.data[i].userFiles[j].aes_dec_time
								+" </td> <td> "+response.data[i].userFiles[j].rsa_enc_time
								+" </td> <td> "+response.data[i].userFiles[j].rsa_dec_time
								+" </td> <td> "+response.data[i].userFiles[j].cpabe_enc_time
								+" </td> <td> "+response.data[i].userFiles[j].cpabe_dec_time
								+" </td> <td> "+response.data[i].userFiles[j].encryptedStatus
								+" </td> <td> "+response.data[i].userFiles[j].filepath
								+" </td> <td> <a href='<c:url value='/files/download/' />" + response.data[i].userFiles[j].file_id
								+"'> Download </a> </td> <td> "+response.data[i].userFiles[j].size
								+" </td> </tr>");
					}
					$("#mTable tbody").append("<tr class='tr'> <td> "
							+response.data[i].id
							+" </td> <td> "+response.data[i].userFiles.length
							+" </td> <td> "+response.data[i].weight
							+" </td> </tr>");
					
					$("#sTable tbody").append("<tr class='tr'> <td> SD"
							+response.data[i].id
							+" </td> <td> "+response.data[i].weight / response.data[i].encryption_time
							+" </td> </tr>");
				}

		 	    $('#mTable').DataTable( {
			        "searching": false, 
			        "paging": false
			    });
		 	    $('#sTable').DataTable( {
			        "searching": false, 
			        "paging": false
			    });
		 	    $('#files').DataTable( {
			        "searching": true,
			        "responsive": true,
			        "paging": true
			    });
			}				
		});	
	}
  </script>
  <script type="text/javascript">
	$(function() {
	  $('.progress').hide();
	  $('.uploadForm').click(function(e) {
		var l = Ladda.create(this);
	 	l.start();
		var form = document.forms[0];
	    var formData = new FormData(form);
	    	
	    // Ajax call for file uploaling
	    var ajaxReq = $.ajax({
	      url : '<c:url value='/files/upload/status' />',
	      type : 'POST',
	      data : formData,
	      cache : false,
	      contentType : false,
	      processData : false,
	      xhr: function(){
	        //Get XmlHttpRequest object
	         var xhr = $.ajaxSettings.xhr() ;
	        
	        //Set onprogress event handler 
	         xhr.upload.onprogress = function(event){
	          	var perc = Math.round((event.loaded / event.total) * 100);
	          	$('#progressBar').text(perc + '%');
	          	$('#progressBar').css('width',perc + '%');
	          	if(perc == 100){
	          		$('#alertMsg').text('Processing File...');
	          	}
	         };
	         return xhr ;
	    	},
	    	beforeSend: function( xhr ) {
	    		//Reset alert message and progress bar
	    		$('#alertMsg').text('');
	    		$('.progress').show();
	    		$('#progressBar').text('');
	    		$('#progressBar').css('width','0%');
	    		$('#progressBar').addClass('active');
	       	}
	    });
	  
	    // Called on success of file upload
	    ajaxReq.done(function(msg) {
			$('#alertMsg').text(msg);
			$('#progressBar').removeClass('active');
			$('input[type=file]').val('');
			$('#mTable').dataTable().fnDestroy();
			$('#sTable').dataTable().fnDestroy();
			$('#files').dataTable().fnDestroy();
			load();
			l.stop();
	    });
	    
	    // Called on failure of file upload
	    ajaxReq.fail(function(jqXHR) {
	    	l.stop();
	    	$('#progressBar').removeClass('active');
			$('#alertMsg').text('Error occured in uploading file. Please check the server stack trace for more info.('+
	    		  jqXHR.status+' - '+jqXHR.statusText+')');
	    });
	  });
	});
  </script>
</body>
</html>