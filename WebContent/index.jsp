<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="stylesheet.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script>
	$(document).ready(function(){
		$('#dropyear').show();
		$('#droptime').show()
		$('#dropbranch').hide();
		$('#dropdate').hide();
		
    	$('#dropfilter').on('change', function() {
    	  if(this.value == '0') {
    		  $("#desc-id")
    		  .text("Generates a list of book names and dates of a certain year wherein the book was returned after the due date. This query is relevant to allow libraries to filter which borrowers they need to notify of overdue books.");
    		  $('#dropyear').show();
    		  $('#droptime').show();
    		  $('#dropbranch').hide();
    	  } else if(this.value == '1'){
    		  $("#desc-id").text("Generates the result wherein it gives the branch that has the most number of book loans in a certain year. This query is relevant when a user would want to know which libraries are most frequented by borrowers.");
    		  $('#dropyear').show();
    		  $('#droptime').show();
    		  $('#dropbranch').hide();
    	  } else if(this.value == '2'){
    		  $("#desc-id").text("Generate which borrower borrowed the most books before a certain date.");
    		  $('#dropyear').hide();
    		  $('#droptime').hide();
    		  $('#dropbranch').hide();
    		  $('#dropdate').show();
    	  } else if(this.value == '3'){
    		  $("#desc-id").text("Generates which books have always been returned before or on the due date. <Reason here>");
    	  } else if(this.value == '4'){
    		  $("#desc-id").text("Given a branch, generates which borrower has visited a branch the most based on the number of books borrowed for a certain year. Some libraries give incentives for the top borrowers for their branch. This query easily lets them see which borrower has borrowed the most books in a specified year.");
    		  $('#dropbranch').hide();
    		  $('#dropyear').show();
    	  } else if(this.value == '5'){
    		  $("#desc-id").text("Generates which book/s was/were the most borrowed in a branch for a certain year. <Reason here>");
    		  $('#dropyear').show();
    		  $('#dropbranch').show();
    	  } else if(this.value == '6'){
    		  $("#desc-id").text("Generates which publisher has the most number of borrowed books in each branch for a certain year. <Reason here>");
    		  $('#dropyear').show();
    		  $('#dropbranch').show();
    	  } else if(this.value == '7'){
    		  $("#desc-id").text("Average of");
    	  }
    	});
	})
</script>

<title>Library</title>
</head>
<body>
	<div class="header-menu">
    	<div class="header-toolbar">
        	<div class="header-left">
            	<div class="header-text">LIBRARY</div>
            </div>
       </div>
   </div>
            
            
            <div class="filter-div">
            	<form action="index.jsp">
            		<select name="query" class="drop-down" id="dropfilter">
            			<option value="0">1 Table - Books returned after due date</option>
            			<option value="1">1 Table - Branch that has the highest book loans</option>
            			<option value="2">2 Tables - Borrower that has the highest book loans</option>
            			<option value="3">2 Tables - Books returned before due date</option>
            			<option value="4">3 Tables - Borrower who borrows the most in a branch</option>
            			<option value="5">3 Tables - Most borrowed book in the branch</option>
            			<option value="6">4 Tables - Publisher with most borrowed books in branch</option>
            			<option value="7">4 Tables - Average of</option>
            		</select>
            		
            		<div class="header-label">using</div>
            		
            		<select name="optimize" class="drop-down" id="dropopt">
            			<option value="0">NONE</option>
            			<option value="1">Indexing (how many index)</option>
            			<option value="2">Materialized Views</option>
            			<option value="3">Optimize 4</option>
            		</select>
            		
            		<input type="submit" value="Search" method="post" class="filter-btn" name="filter">
            		
            		<div class="settings-cont">
            			<div class="emphasis-text">Settings</div>
            			<select name="time" class="drop-down setdd" id="droptime">
            				<option value="0">Before</option>
            				<option value="1">During</option>
            				<option value="2">After</option>
            			</select>   
            			<select name="date" class="drop-down setdd" id="dropdate">
            				<option value="0">Date 1</option>
            				<option value="1">Date 2</option>
            				<option value="2">Date 3</option>
            				<option value="3">Date 4</option>
            			</select> 
            			<select name="year" class="drop-down setdd" id="dropyear">
            				<option value="0">2017</option>
            				<option value="1">2016</option>
            				<option value="2">2015</option>
            				<option value="3">2014</option>
            			</select>            	
            			<select name="branch" class="drop-down setdd" id="dropbranch">
            				<option value="0">Branch 1</option>
            				<option value="1">Branch 2</option>
            				<option value="2">Branch 3</option>
            				<option value="3">Branch 4</option>
            			</select>  		
            		</div>
            	</form>
            </div>
            
            <div class="desc-container">
            	<div class="query-desc">
            		<div class="emphasis-text" >Query Description:</div> 
            		<div id="desc-id">
            			Generates a list of book names and dates of a certain year wherein the book was returned after the due date. This query is relevant to allow libraries to filter which borrowers they need to notify of overdue books.
            		</div>
            	</div>
            </div>
            
            <div class="container">
            	<div class="emphasis-text">Results</div>
            </div>
			            
            <div class="results-cont">
            	<table class="results-table">
            		<tr>
            			<th>Category 1</th>
            			<th>Category 2</th>
            			<th>Category 3</th>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            		
            		<tr>
            			<td>Data</td>
            			<td>Data</td>
            			<td>Data</td>
            		</tr>
            	</table>
		</div>
		
		<div class="exec-container">
			<div class="emphasis-text exec-header">Execution Log</div>
			<div class="exec-log">
				<div class="query-desc" id="log-item">Sample log</div>
				<div class="query-desc" id="log-item">Sample log</div>
				<div class="query-desc" id="log-item">Sample log</div>
			</div>
		</div>
</body>
</html>