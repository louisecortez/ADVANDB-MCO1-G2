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
		$('#droptimeopt').show();
		$('#dropdateb').show();
		$('#dropyear').hide();
		$('#dropdatea').hide();
		$('#betwtext').hide();
		
		$('#droptimeopt').on('change', function() {
			if(this.value == '2'){
				$('#dropdatea').show();
				$('#betwtext').show();
			} else {
				$('#dropdatea').hide();
				$('#betwtext').hide();
			}
		})
		
    	$('#dropfilter').on('change', function() {
    	  if(this.value == '0') {
    		  $('#droptimeopt').show();
    		  $('#dropdateb').show();
    		  $('#dropyear').hide();
    		  $("#desc-id").text("Generates the count of books wherein the book was returned after the due date given before, during, between, or after a certain date. This gives libraries an easier way to see statistics regarding overdue books which may be used for analysis reports.");
    	  } else if(this.value == '1'){
    		  $('#droptimeopt').hide();
    		  $('#dropdateb').hide();
    		  $('#dropyear').show();
    		  $("#desc-id").text("Generates the result wherein it gives the branch number of the branch that has the highest number of book loans based on the year in DateOut in a certain year. This query allows libraries to see which branch has performed the best in terms of book loans. Sub querying was used for this item");
    	  } else if(this.value == '2'){
    		  $("#desc-id").text("The tables used for this query are borrower and book_loans. Generates which borrower borrowed the most books per month of a certain year. Libraries usually acknowledge the top borrowers in their libraries thus, the query would be helpful in filtering the borrowers easier.");
    	  } else if(this.value == '3'){
    		  $('#dropdate').show();
    		  $("#desc-id").text("The tables used for this query are borrower and book_loans. Generates all borrower rate of returning books on time based on a given year. This query is relevant for statistical analysis on borrowers for libraries to know how frequent they return books on or before the due date.");
    	  } else if(this.value == '4'){
    		  $("#desc-id").text("The tables used for this query are borrower, book_loans, and library_branch. Generates which borrower has visited a branch the most based on the number of books borrowed given a certain year. This query allows libraries to see which borrower has frequented their branch the most based on the total number of book loans per borrower.");
		  } else if(this.value == '5'){
    		  $("#desc-id").text("The tables used for this query are book, book_loans, and library_branch. Generates which book was the most borrowed for each branch for a certain year. This allows libraries to know which books are the most popular among all the other books, which can aid them in determining if they need to increase the quantity of the books to accommodate the demand.");
    	  } else if(this.value == '6'){
    		  $("#desc-id").text("The tables used for this query are book, book_loans, library_branch and publisher. Generates which publisher has the most number of borrowed books in each branch for a certain year. For this query, the data could be used by publishers to assess which libraries they would allot a higher number of quantity of their books. This could also be used for statistical analysis.");
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
            	<option value="0">1 Table - Overdue Books</option>
            	<option value="1">1 Table - Best Performing Branch Per Year</option>
            	<option value="2">2 Tables - Overall Top Borrowers Per Month</option>
            	<option value="3">2 Tables - Borrower Return Rate</option>
            	<option value="4">3 Tables - Top Borrowers Per Branch</option>
            	<option value="5">3 Tables - Most Borrowed Book Per Branch</option>
            	<option value="6">4 Tables - Top Publishers Per Branch</option>
            	<option value="7">4 Tables - Average of</option>
            </select>
            
            <div class="header-label">using</div>
            
            <select name="optimize" class="drop-down" id="dropopt">
            	<option value="0">NONE</option>
            	<option value="1">Indexing with 1 index</option>
            	<option value="2">Indexing with 2 indexes</option>
            	<option value="3">Halu</option>
            </select>
            		
            <input type="submit" value="Search" method="post" class="filter-btn" name="filter">
            	
            <div class="settings-cont">
            	<div class="emphasis-text">Settings</div>
            	<select name="time" class="drop-down setdd" id="droptimeopt">
            		<option value="0">Before</option>
            		<option value="1">During</option>
            		<option value="2">Between</option>
            		<option value="3">After</option>
            	</select>   
            	<input type="date" name="date" class="drop-down setdd datepicker" id="dropdateb">
            				
            	</input>
            	<div class="emphasis-text" id="betwtext">and</div>
            	<input type="date" name="date" class="drop-down setdd datepicker" id="dropdatea">
            				
            	</input>           	
            	<input type="number" class= "drop-down setdd" name="year" min="1970" max="2017" value="2017" id="dropyear"/> 		
            </div>
   		</form>
  	</div>
            
    <div class="desc-container">
      	<div class="query-desc">
       		<div class="emphasis-text" >Query Description:</div> 
       		<div id="desc-id">
       			Generate the count of books wherein the book was returned after the due date given before, during, between, or after a certain date. This gives libraries an easier way to see statistics regarding overdue books which may be used for analysis reports.
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