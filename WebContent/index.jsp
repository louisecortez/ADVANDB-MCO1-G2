<%@ page import="java.util.ArrayList" %>
<%@ page import="resources.DynamicSet" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <!-- HEAD -->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="stylesheet.css">
        <script src="jquery-3.2.1.js"></script>
        <title>Library</title>
    </head>

    <!-- BODY -->
    <body>
        <div class="header-menu">
            <div class="header-toolbar">
                <div class="header-left">
                    <div class="header-text">LIBRARY</div>
                </div>
           </div>
       </div>

       <div class="filter-div">
            <form action="Search" method="post">
                <div class="emphasis-text">Query:&nbsp;&nbsp;</div>
                <select name="query" class="drop-down" id="dropfilter">
                    <option value="0">1 Table - Overdue Books</option>
                    <option value="1">1 Table - Best Performing Branch of the Year</option>
                    <option value="2">2 Tables - Overall Top Borrowers Per Year</option>
                    <option value="3">2 Tables - Borrower Return Rate</option>
                    <option value="4">3 Tables - Top Borrowers Per Branch</option>
                    <option value="5">3 Tables - Most Borrowed Book Per Branch</option>
                    <option value="6">4 Tables - Same Library and Publisher Addresses Books</option>
                    <option value="7">4 Tables - Borrowed Books Per Borrower</option>
                </select>

                <div class="header-label"><p id="usingword">using</p></div>

                <select name="optimize" class="drop-down" id="dropopt">
                    <option value="0">NONE</option>
                    <option value="1">Indexing with 1 index</option>
                    <option value="2">Indexing with 2 indexes</option>
                    <option value="3">Inner Join</option>
                </select>

                <input type="submit" value="Search" class="filter-btn" name="filter">

                <div class="settings-cont">
                    <div class="emphasis-text"><p id="settingsword">Available Settings: </p></div>
                    <select name="time" class="drop-down setdd" id="droptimeopt">
                        <option value="0">Before</option>
                        <option value="1">During</option>
                        <option value="2">Between</option>
                        <option value="3">After</option>
                    </select>
                    <input type="date" name="dateb" class="drop-down setdd datepicker" id="dropdateb">

                    </input>
                    <div class="emphasis-text" id="betwtext">and</div>
                    <input type="date" name="datea" class="drop-down setdd datepicker" id="dropdatea">

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
                <!-- RESULT TABLE -->
                <%
                    // If there is no session, do not print
                    if (session.getAttribute("dynamicSet") == null) {
                        out.println("<tr><th>N/A</th><th>N/A</th><th>N/A</th></tr>");
                        out.println("<tr><td>Data will be displayed here</td><td>-</td><td>-</td></tr>");
                    }
                    else {
                        DynamicSet dset = (DynamicSet) session.getAttribute("dynamicSet");

                        // Print the headers
                        ArrayList<String> headers = dset.getHeaders();
                        out.println("<tr>");
                        for (String header : headers) {
                            out.println("<th>" + header + "</th>");
                        }
                        out.println("</tr>");

                        // Print the rows of data
                        ArrayList<ArrayList<String>> data = dset.getData();
                        for (ArrayList<String> datapoint : data) {
                            out.println("<tr>");
                            for (String res : datapoint) {
                                out.println("<td>" + res + "</td>");
                            }
                            out.println("</tr>");
                        }
                    }
                %>

            </table>
        </div>

        <div class="exec-container">
            <div class="emphasis-text exec-header">Execution Log</div>
            <div class="exec-log">
                <!-- EXEC LOG -->
                <%
                    // If there is no logs in session, do not print. Otherwise, print all logs.
                    if (session.getAttribute("executionLog") == null) {
                        out.println("<div class=\"query-desc\" id=\"log-item\">Logged queries go here. Go make one!</div>");
                    }
                    else {
                        ArrayList<String> log = (ArrayList<String>) session.getAttribute("executionLog");
                        for (String l : log) {
                            out.println("<div class=\"query-desc\" id=\"log-item\">" + l + "</div>");
                        }
                    }
                %>

            </div>
        </div>
    </body>

    <!-- Display Script  -->
    <script>
        $(document).ready(function(){
            // Show and display starting boxes
            $('#droptimeopt').show();
            $("#dropopt").hide();
            $("#usingword").hide()
            $('#dropdateb').show();
            $('#dropyear').hide();
            $('#dropdatea').hide();
            $('#betwtext').hide();

            // Droptimeoptions change functions
            $('#droptimeopt').on('change', function() {
                if(this.value == '2'){
                    $('#dropdatea').show();
                    $('#betwtext').show();
                } else {
                    $('#dropdatea').hide();
                    $('#betwtext').hide();
                }
            })

            // Filter change functions
            $('#dropfilter').on('change', function() {
                if(this.value == '0') {
                    $("#settingsword").text("Available Settings: ")
                    $("#dropopt").hide();
                    $("#usingword").hide();
                    $('#droptimeopt').show();
                    $('#dropdateb').show();
                    $('#dropyear').hide();
                    $("#desc-id").text("Generates the count of books wherein the book was returned after the due date given before, during, between, or after a certain date. This gives libraries an easier way to see statistics regarding overdue books which may be used for analysis reports.");
                } else if(this.value == '1'){
                    $("#settingsword").text("Available Settings: ")
                    $("#dropopt").show();
                    $("#usingword").show();
                    $('#droptimeopt').hide();
                    $('#dropdateb').hide();
                    $('#dropyear').show();
                    $("#desc-id").text("Generates the result wherein it gives the branch number of the branch that has the highest number of book loans based on the year in DateOut in a certain year. This query allows libraries to see which branch has performed the best in terms of book loans. Sub querying was used for this item");
                } else if(this.value == '2'){
                    $("#settingsword").text("Available Settings: ")
                    $("#dropopt").show();
                    $("#usingword").show();
                    $('#droptimeopt').hide();
                    $('#dropdateb').hide();
                    $('#dropyear').show();
                    $("#desc-id").text("The tables used for this query are borrower and book_loans. Generates which borrower borrowed the most books per month of a certain year. Libraries usually acknowledge the top borrowers in their libraries thus, the query would be helpful in filtering the borrowers easier.");
                } else if(this.value == '3'){
                    $("#settingsword").text("Available Settings: ")
                    $("#dropopt").show();
                    $("#usingword").show();
                    $('#droptimeopt').hide();
                    $('#dropdateb').hide();
                    $('#dropyear').show();
                    $('#dropdate').show();
                    $("#desc-id").text("The tables used for this query are borrower and book_loans. Generates all borrower rate of returning books on time based on a given year. This query is relevant for statistical analysis on borrowers for libraries to know how frequent they return books on or before the due date.");
                } else if(this.value == '4'){
                    $("#settingsword").text("Available Settings: ")
                    $("#dropopt").show();
                    $("#usingword").show();
                    $('#droptimeopt').hide();
                    $('#dropdateb').hide();
                    $('#dropyear').show();
                    $("#desc-id").text("The tables used for this query are borrower, book_loans, and library_branch. Generates which borrower has visited a branch the most based on the number of books borrowed given a certain year. This query allows libraries to see which borrower has frequented their branch the most based on the total number of book loans per borrower.");
                } else if(this.value == '5'){
                    $("#settingsword").text("Available Settings: ")
                    $("#dropopt").show();
                    $("#usingword").show();
                    $('#droptimeopt').hide();
                    $('#dropdateb').hide();
                    $('#dropyear').show();
                    $("#desc-id").text("The tables used for this query are book, book_loans, and library_branch. Generates which book was the most borrowed for each branch for a certain year. This allows libraries to know which books are the most popular among all the other books, which can aid them in determining if they need to increase the quantity of the books to accommodate the demand.");
                } else if(this.value == '6'){
                    $("#settingsword").text("No Settings Allowed For Query.")
                    $("#dropopt").show();
                    $("#usingword").show();
                    $('#droptimeopt').hide();
                    $('#dropdateb').hide();
                    $('#dropyear').hide();
                    $("#desc-id").text("The tables used for this query are book, book_loans, library_branch and publisher. Generate all the books that were borrowed more than the input number in which the publisher has the same address with the library branch.");
                } else if(this.value == '7'){
                    $("#settingsword").text("No Settings Allowed For Query.")
                    $("#dropopt").show();
                    $("#usingword").show();
                    $('#droptimeopt').hide();
                    $('#dropdateb').hide();
                    $('#dropyear').hide();
                    $("#desc-id").text("The tables used for this query are book, book_loans, library_branch and library_branch. Find borrowed books per borrower per branch that is borrowed not only once. Generate all the books that are borrowed by a borrower for more than once in a given branch. This query will be particularly for borrower statistics and records.");
                }
            });
        })
    </script>
</html>