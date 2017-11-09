package servlets;

import resources.DynamicSet;
import resources.Profile;
import resources.SQLHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "Search")
public class Search extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get the Query, Optimization, and the Time
        int query_no = Integer.parseInt(request.getParameter("query"));
        int optimize_no = Integer.parseInt(request.getParameter("optimize"));
        int time_no = Integer.parseInt(request.getParameter("time"));

        // Initialize the containers
        String query = "";
        String original = "";
        String appendix = "";
        ArrayList<String> mark_indexes = null;
        ArrayList<String> drop_indexes = null;
        boolean is_indexed = false;

        // Switch between all normal queries
        switch(query_no) {
            // Query 1, 1 Table - Overdue Books
            case 0:
                original = "SELECT count(*) nBookLate FROM book_loans WHERE date(DueDate) < date(DateReturned) AND ";
                if (time_no == 0)
                    appendix = "year(DueDate) < '" + request.getParameter("dateb") + "';";
                if (time_no == 1)
                    appendix = "year(DueDate) = '" + request.getParameter("dateb") + "';";
                if (time_no == 2)
                    appendix = "year(DueDate) >= '" + request.getParameter("dateb") + "' AND year(DueDate) <= '" + request.getParameter("datea") + "';";
                if (time_no == 3)
                    appendix = "year(DueDate) > '" + request.getParameter("dateb") + "';";
                break;

            // Query 2, 1 Table - Best Performing Branch of the Year
            case 1:
                original = "SELECT id, MAX(nLoan) FROM (SELECT BranchID 'id', COUNT(BranchID) 'nLoan' " +
                        "FROM book_loans WHERE year(DateOut) = '" + request.getParameter("year") + "' GROUP " +
                        "BY BranchID) loan;";
                break;

            // Query 3, 2 Tables - Overall Top Borrowers Per Year
            case 2:
                original = "SELECT bo.BorrowerFName, bo.BorrowerLName, COUNT(*) FROM book_loans bl, borrower bo " +
                        "WHERE bo.CardNo = bl.CardNo AND YEAR(bl.DateOut) = '" + request.getParameter("year") + "' " +
                        "GROUP BY bo.CardNo HAVING COUNT(*) >= ALL(SELECT COUNT(*) FROM book_loans bl1 " +
                        "WHERE YEAR(bl1.DateOut) = '" + request.getParameter("year") + "' GROUP BY bl1.CardNo);";
                break;

            // Query 4, 2 Tables - Borrower Return Rate
            case 3:
                original = "SELECT bo.BorrowerLName, bo.BorrowerFName, concat(cnt*100/COUNT(*), '%') 'rate' " +
                        "FROM borrower bo, book_loans bl, ( SELECT bo1.cardno, count(*) cnt FROM borrower bo1, " +
                        "book_loans bl1 WHERE bo1.cardno = bl1.cardno and date(bl1.duedate) >= date(bl1.datereturned) " +
                        "AND year(DateOut) = '" + request.getParameter("year") + "' GROUP BY bo1.cardno) t1 WHERE " +
                        "bo.cardno=bl.cardno and bo.cardno = t1.cardno AND year(DateOut) = '" +
                        request.getParameter("year") +"' GROUP BY bo.cardno ORDER BY bo.BorrowerLName;";
                break;

            // Query 5, 3 Tables - Top Borrowers Per Branch
            case 4:
                original = "SELECT t1.BranchName, t1.CardNo, t1.BorrowerFName, t1.BorrowerLName, MAX(t1.nBooks) " +
                        "'nBooks' FROM (SELECT lb.BranchName, bo.CardNo, bo.BorrowerFName, bo.BorrowerLName, " +
                        "COUNT(bo.CardNo) 'nBooks' FROM book_loans bl, borrower bo, library_branch lb WHERE bl.CardNo " +
                        "= bo.CardNo AND lb.BranchID = bl.BranchID AND YEAR(bl.DateOut) = " +
                        "'" + request.getParameter("year") + "' GROUP BY lb.BranchName, bo.CardNo ORDER BY lb.BranchName " +
                        "ASC, nBooks DESC) t1 GROUP BY t1.BranchName;";
                break;

            // Query 6, 3 Tables - Most Borrowed Book Per Branch
            case 5:
                original = "SELECT t1.BranchName, t1.bkID, t1.Title, MAX(t1.nBooks) 'nBooks' FROM (SELECT " +
                        "lb.BranchName, bk.bookid 'bkID', bk.Title, COUNT(bk.bookid) 'nBooks' FROM book_loans bl, " +
                        "book bk, library_branch lb WHERE bl.BookID = bk.bookid AND bl.BranchID = lb.BranchID AND " +
                        "YEAR(bl.DateOut) = '" + request.getParameter("year") +"' GROUP BY lb.BranchName, bk.bookid " +
                        "ORDER BY lb.BranchName ASC, nBooks DESC) t1 GROUP BY t1.BranchName;";
                break;

            // Query 7, 4 Tables - Top Publishers Per Branch
            case 6:
                original = "SELECT b.* , t1.BranchName, t1.BranchAddress, count(*) FROM (SELECT p.PublisherName, " +
                        "lb.* FROM publisher p, library_branch lb WHERE p.Address = lb.BranchAddress) t1, book b, " +
                        "book_loans bl WHERE b.bookid = bl.BookID AND bl.BranchID = t1.BranchID AND b.PublisherName = " +
                        "t1.PublisherName GROUP BY bl.BookID HAVING count(*) > 5;";
                break;

            // Query 8, 4 Tables - Average Of
            case 7:
                original = "SELECT bo.BorrowerFName, bo.BorrowerLName, b.Title, lb.BranchName, count(*) FROM book b, " +
                        "borrower bo, book_loans bl, library_branch lb WHERE b.bookid = bl.BookID AND bo.CardNo = " +
                        "bl.CardNo AND lb.BranchID = bl.BranchID AND lb.branchaddress = 'Los Angeles' GROUP BY " +
                        "bl.cardno, bl.bookid HAVING count(*) > 1;";
                break;

        }

        // Check if single indexes are used
        if (optimize_no == 1) {
            // Instantiate lists
            mark_indexes = new ArrayList<>();
            drop_indexes = new ArrayList<>();

            // Switch between queries and specify their indexes
            switch (query_no) {
                case 2:
                    mark_indexes.add("CREATE INDEX bo_cardno ON borrower(cardno);");
                    drop_indexes.add("DROP INDEX bo_cardno ON librarydb2.borrower;");
                    break;

                case 3:
                    mark_indexes.add("CREATE INDEX bo_cardno ON borrower(cardno);");
                    drop_indexes.add("DROP INDEX bo_cardno ON librarydb2.borrower;");
                    break;

                case 4:
                    mark_indexes.add("CREATE INDEX bo_cardno ON borrower(cardno);");
                    drop_indexes.add("DROP INDEX bo_cardno ON librarydb2.borrower;");
                    break;

                case 5:
                    mark_indexes.add("CREATE INDEX bo_cardno ON book(bookID);");
                    drop_indexes.add("DROP INDEX bo_cardno ON librarydb2.book;");
                    break;

                case 6:
                    mark_indexes.add("CREATE INDEX p_address ON publisher(address);");
                    drop_indexes.add("DROP INDEX p_address ON librarydb2.publisher;");
                    break;

                case 7:
                    mark_indexes.add("CREATE INDEX bo_cardno ON borrower(cardno);");
                    drop_indexes.add("DROP INDEX bo_cardno ON librarydb2.borrower;");
                    break;

            }

            // Execute the indexing and set the flag
            SQLHelper.queryIndexes(mark_indexes);
            is_indexed = true;
        }

        // Check if double indexes are used
        if (optimize_no == 2) {
            // Instantiate lists
            mark_indexes = new ArrayList<>();
            drop_indexes = new ArrayList<>();

            // Switch between queries and specify their indexes
            switch (query_no) {
                case 2:
                    mark_indexes.add("CREATE INDEX bl_cardno ON book_loans(cardno);");
                    mark_indexes.add("CREATE INDEX bo_cardno ON borrower(cardno);");
                    drop_indexes.add("DROP INDEX bl_cardno ON librarydb2.book_loans;");
                    drop_indexes.add("DROP INDEX bo_cardno ON librarydb2.borrower;");
                    break;

                case 3:
                    mark_indexes.add("CREATE INDEX bl_cardno ON book_loans(cardno);");
                    mark_indexes.add("CREATE INDEX bo_cardno ON borrower(cardno);");
                    drop_indexes.add("DROP INDEX bl_cardno ON librarydb2.book_loans;");
                    drop_indexes.add("DROP INDEX bo_cardno ON librarydb2.borrower;");
                    break;

                case 4:
                    mark_indexes.add("CREATE INDEX bo_cardno ON borrower(cardno);");
                    mark_indexes.add("CREATE INDEX bo_cardno ON library_branch(branchid);");
                    drop_indexes.add("DROP INDEX bo_cardno ON librarydb2.borrower;");
                    drop_indexes.add("DROP INDEX bo_cardno ON librarydb2.library_branch;");
                    break;

                case 5:
                    mark_indexes.add("CREATE INDEX bo_cardno ON book(bookID);");
                    mark_indexes.add("CREATE INDEX lb_branchid ON library_branch(branchID);");
                    drop_indexes.add("DROP INDEX bo_cardno ON librarydb2.book;");
                    drop_indexes.add("DROP INDEX lb_branchid ON librarydb2.library_branch;");
                    break;

                case 6:
                    mark_indexes.add("CREATE INDEX p_address ON publisher(address);");
                    mark_indexes.add("CREATE INDEX lb_address ON publisher(branchaddress);");
                    drop_indexes.add("DROP INDEX p_address ON librarydb2.publisher;");
                    drop_indexes.add("DROP INDEX lb_address ON librarydb2.publisher;");
                    break;

                case 7:
                    mark_indexes.add("CREATE INDEX bo_cardno ON borrower(cardno);");
                    mark_indexes.add("CREATE INDEX lb_branchid ON library_branch(branchid);");
                    drop_indexes.add("DROP INDEX bo_cardno ON librarydb2.borrower;");
                    drop_indexes.add("DROP INDEX lb_branchid ON librardb2.library_branch;");
                    break;

            }

            // Execute the indexing and set the flag
            SQLHelper.queryIndexes(mark_indexes);
            is_indexed = true;
        }

        // Inner Joins!
        if (optimize_no == 3) {
            switch (query_no) {
                case 2:
                    original = "SELECT t1.monthofy, t1.CardNo, bo.BorrowerFName, bo.BorrowerLName, MAX(t1.nBooks) " +
                            "'nBooks' FROM borrower bo INNER JOIN (SELECT MONTH(bl.DateOut) 'monthofy', bl.CardNo, " +
                            "COUNT(*) 'nBooks' FROM book_loans bl WHERE YEAR(bl.DateOut) = '" + request.getParameter("year") + "' GROUP BY " +
                            "MONTH(bl.DateOut), bl.CardNo ORDER BY MONTH(bl.DateOut), nBooks DESC) t1 ON bo.CardNo = " +
                            "t1.CardNo GROUP BY t1.monthofy;";
                    break;

                case 3:
                    original = "SELECT bo.BorrowerLName, bo.BorrowerFName, concat(cnt*100/COUNT(*), '%') 'rate' FROM " +
                            "borrower bo INNER JOIN (SELECT bl.CardNo, COUNT(*) 'cnt', YEAR(bl.DateOut) 'yearBL' " +
                            "FROM book_loans bl WHERE DATE(bl.duedate) >= DATE(bl.datereturned) AND YEAR(bl.DateOut) = " +
                            "'2015' GROUP BY bl.CardNo) t1 ON bo.CardNo = t1.CardNo INNER JOIN book_loans bl1 ON " +
                            "bo.CardNo = bl1.CardNo WHERE YEAR(bl1.DateOut) =  '" + request.getParameter("year") +
                            "' GROUP BY bl1.CardNo ORDER BY bo.BorrowerLName;";
                    break;

                case 4:
                    original = "SELECT t1.BranchName, bo.CardNo, bo.BorrowerFName, bo.BorrowerLName, MAX(t1.nBooks) " +
                            "'maxBooks' FROM borrower bo INNER JOIN (SELECT bl.BranchID, lb.BranchName, bl.CardNo, " +
                            "COUNT(*) 'nBooks' FROM book_loans bl INNER JOIN library_branch lb ON bl.BranchID = " +
                            "lb.BranchID WHERE YEAR(bl.DateOut) = '" + request.getParameter("year") + "' GROUP BY " +
                            "lb.BranchID, bl.CardNo ORDER BY bl.BranchID ASC, nBooks DESC) t1 ON bo.CardNo = t1.CardNo " +
                            "GROUP BY t1.BranchName ORDER BY t1.BranchName;";
                    break;

                case 5:
                    original = "SELECT t1.BranchName, bk.bookid, bk.Title, MAX(t1.nBooks) 'nBooks' FROM book bk INNER " +
                            "JOIN (SELECT bl.BranchID, lb.BranchName, bl.BookID, COUNT(*) 'nBooks' FROM book_loans bl " +
                            "INNER JOIN library_branch lb ON bl.BranchID = lb.BranchID WHERE YEAR(bl.DateOut) = '" +
                            request.getParameter("year") + "' GROUP BY lb.BranchID, bl.BookID ORDER BY bl.BranchID " +
                            "ASC, nBooks DESC) t1 ON bk.bookid = t1.BookID GROUP BY t1.BranchName ORDER BY " +
                            "t1.BranchName;";
                    break;

                case 6:
                    original = "SELECT b.* , t1.BranchName, t1.BranchAddress, count(*) FROM (book b INNER JOIN (SELECT " +
                            "p.PublisherName, lb.* FROM publisher p, library_branch lb WHERE p.Address = " +
                            "lb.BranchAddress) t1 ON  b.PublisherName = t1.PublisherName) INNER JOIN book_loans bl ON " +
                            "bl.BranchID = t1.BranchID AND bl.BookID = b.bookid GROUP BY bl.BookID HAVING count(*) > 5;";
                    break;

                case 7:
                    original = "SELECT bo.BorrowerFName, bo.BorrowerLName, b.Title, lb.BranchName, count(*) FROM " +
                            "(((book_loans bl INNER JOIN book b ON b.bookid = bl.BookID) INNER JOIN borrower bo ON " +
                            "bl.CardNo = bo.CardNo) INNER JOIN library_branch lb ON bl.BranchID = lb.BranchID) WHERE " +
                            "lb.branchaddress = 'Los Angeles' GROUP BY bl.cardno, bl.bookid HAVING count(*) > 1;";
                    break;
            }
        }

        // Execute, profile, and get the time (within the Profile holder)
        query = original + appendix;
        ArrayList<Profile> profiles = SQLHelper.queryDatabase(query);
        Profile p = profiles.get(0);

        // Get the result set as a DynamicSet container, then pass it to session to show it
        DynamicSet dset = SQLHelper.getDynamicSet(query);
        request.getSession().setAttribute("dynamicSet", dset);

        // Save the execution to the log
        ArrayList<String> log = (ArrayList<String>) request.getSession().getAttribute("executionLog");
        if (log == null)
            log = new ArrayList<>();

        // If indexes are used, add them to the log, then drop the indexes in the database
        if (is_indexed) {
            log.add("--USING INDEXES--");
            for (String index : mark_indexes)
                log.add(index);
            log.add("--END OF INDEXES--");
            SQLHelper.queryIndexes(drop_indexes);
        }

        // Add the duration to the log and save it to session
        log.add("[DURATION: " + p.getDuration() + "] " + p.getQuery_string() + "<br><br>");
        request.getSession().setAttribute("executionLog", log);


        // Dispatch requests
        RequestDispatcher rs = request.getRequestDispatcher("index.jsp");
        rs.include(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
