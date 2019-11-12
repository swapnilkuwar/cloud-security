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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<link href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Ladda/1.0.6/ladda-themeless.min.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/Ladda/1.0.6/spin.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Ladda/1.0.6/ladda.min.js"></script>
</head>
<body style="background: #dcdcdc36;">
<article class="container">
	<div class="gap-50"></div>
	<div class="row col-xs-12 col-md-8 col-md-offset-2">
		<div class="btn-group btn-group-justified">
		   <div class="btn-group">
		     <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#uploadModal">Upload Files</button>
		   </div>
		   <div class="btn-group">
		     <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#mtableModal">Regenerate M-Table</button>
		   </div>
		   <div class="btn-group">
		     <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#encryptModal">Encrypt Files</button>
		   </div>
		   <div class="btn-group">
		     <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#decryptModal">Decrypt All Files</button>
		   </div>
		</div>
	</div>
	<div class="gap-50"></div>	
	<section class="row">
		<div class="col-xs-12 col-md-6">
			<h1 class="text-center">M Table</h1>
			<table id="mTable" class="display responsive no-wrap" style="width:100%">
				<thead>
		            <tr>
			        	<th> DataSet Id </th> 
						<th> Encryption Time </th> 
						<th> Amount </th>
				    	<th> Weight </th>
				    </tr>
		        </thead>
		        <tbody>
		        
		        </tbody>
			</table>
		</div>
		<div class="col-xs-12 col-md-6">
			<h1 class="text-center">S Table</h1>
			<table id="sTable" class="display responsive no-wrap" style="width:100%">
				<thead>
		            <tr>
						<th> SD </th> 
						<th> Weight </th>
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
			<table id="files" class="display responsive no-wrap" style="width:100%">
				<thead>
		            <tr>
			        	<th> File Id </th> 
						<th> DataSet Id </th> 
						<th> AES ENC. Time </th> 
						<th> AES DEC. Time </th> 
						<th> RSA ENC. Time </th> 
						<th> RSA DEC. Time </th> 
						<th> CPABE ENC. Time </th> 
						<th> CPABE DEC. Time </th> 
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
	<div class="modal fade" id="decryptModal" role="dialog">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Cloud Security</h4>
	      </div>
	      <div class="modal-body">
			<h4>Are you sure you want to decrypt all files from server.</h4>
			<div class="">
				<button class="btn btn-info decAll" data-style="zoom-in">Confirm</button>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="mtableModal" role="dialog">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Cloud Security</h4>
	      </div>
	      <div class="modal-body">
	    	<h4>Are you sure you want to regenerate M-Table</h4>  	
			<button class="btn btn-info calcenctime" data-style="zoom-in">Confirm</button>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="encryptModal" role="dialog">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Cloud Security</h4>
	      </div>
	      <div class="modal-body">
			<h1 class='text-center'>Encrypt Files</h1>
			<article class="row">
				<section class="col-xs-10 col-xs-offset-1">
					<div class="row">
						<div class="form-group col-xs-12 col-md-4">
							<p class='font-16'>Time</p>
						</div>
						<div class="form-group col-xs-12 col-md-8">
							<input class='form-control time' placeholder="Time" type="number" name="time">
						</div>
						<div class="form-group col-xs-12 col-md-4">
							<p class='font-16'>Encryption Algorithm</p>
						</div>
						<div class="form-group col-xs-12 col-md-8">
							<select class="form-control" id="algorithm">
								<option value="AES">AES</option>
								<option value="CPABE">CPABE</option>
								<option value="RSA">RSA</option>
							</select>
						</div>
					</div>
					<button class="btn btn-primary encAll" data-style="zoom-in">Encrypt All</button>
				</section>
			</article>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
	
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
				    	<p>DataSet ID:</p>
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

	$('.encAll').click(function(){
		var l = Ladda.create(this);
	 	l.start();
		if(($('.time').val()) == ''){
			setTimeout(function(){l.stop()},500);
			return false;
		}
		$.ajax({
			url:"encAll",
			type:'GET',
			data:{time: $('.time').val(),enc_type: $('#algorithm').val()},
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader(header, token);
	        },
			success: function(response){
				setTimeout(function(){ 
					$('#mTable').dataTable().fnDestroy();
					$('#sTable').dataTable().fnDestroy();
					$('#files').dataTable().fnDestroy();
					load();
					l.stop();
					$("#encryptModal .close").click();
				}, 1000);
			}				
		});			
	});

	$('.decAll').click(function(){		
		var l = Ladda.create(this);
	 	l.start();
		$.ajax({
			url:"decAll",
			type:'GET',
			data:{},
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader(header, token);
	        },
			success: function(response){
				setTimeout(function(){ 
					$('#mTable').dataTable().fnDestroy();
					$('#sTable').dataTable().fnDestroy();
					$('#files').dataTable().fnDestroy();
					load();
					l.stop();
					$("#decryptModal .close").click();
				}, 1000);
			}				
		});			
	});

	$('.calcenctime').click(function(){
		var l = Ladda.create(this);
	 	l.start();
		$.ajax({
			url:"calcenctime",
			type:'GET',
			beforeSend: function(xhr) {
	            xhr.setRequestHeader(header, token);
	        },
			success: function(response){
				setTimeout(function(){ 
					$('#mTable').dataTable().fnDestroy();
					$('#sTable').dataTable().fnDestroy();
					$('#files').dataTable().fnDestroy();
					load();
					l.stop();
					$("#mtableModal .close").click();
				}, 1000);
			}
		});
	});

	load = function(){
	  $('.datasetid-holder').text('');
	  $.ajax({
			url:'list',
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
								+" </td> <td> "+response.data[i].userFiles[j].cpabe_enc_time
								+" </td> <td> "+response.data[i].userFiles[j].cpabe_dec_time
								+" </td> <td> "+response.data[i].userFiles[j].rsa_enc_time
								+" </td> <td> "+response.data[i].userFiles[j].rsa_dec_time
								+" </td> <td> "+response.data[i].userFiles[j].encryptedStatus
								+" </td> <td> "+response.data[i].userFiles[j].filepath
								+" </td> <td> <a href='<c:url value='/files/download/' />" + response.data[i].userFiles[j].file_id
								+"'> Download </a> </td> <td> "+response.data[i].userFiles[j].size
								+" </td> </tr>");
					}
					$("#mTable tbody").append("<tr class='tr'> <td> "
							+response.data[i].id
							+" </td> <td> "+response.data[i].encryption_time
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