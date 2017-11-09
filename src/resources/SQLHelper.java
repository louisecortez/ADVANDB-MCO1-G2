package resources;

import java.sql.*;
import java.util.ArrayList;

public class SQLHelper {

    // Returns the resultset of the profiles
    public static ArrayList<Profile> queryDatabase(String query) {
        // Create the holders
        ResultSet rs = null;
        ArrayList<Profile> results = null;

        try {
            // Establish a connection
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb?user=root&password=p@ssword");
            Statement stmt = (Statement) conn.createStatement();

            // Set session for groupby clauses, set profiling to 1, execute and capture
            stmt.executeUpdate("SET SESSION sql_mode = '';");
            stmt.executeUpdate("SET PROFILING = 1;");
            stmt.executeQuery(query);
            stmt.executeQuery("SHOW PROFILES;");
            rs = stmt.getResultSet();

            // Take all results within the profiles
            results = new ArrayList<>();
            while (rs.next()) {
                int query_id = rs.getInt("Query_ID");
                double duration = rs.getDouble("Duration");
                String query_s = rs.getString("Query");
                results.add(new Profile(query_id, duration, query_s));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    public static void queryIndexes(ArrayList<String> mark_indexes) {
        try {
            // Establish a connection
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb2?user=root&password=password");
            Statement stmt = (Statement) conn.createStatement();

            // Set session for groupby clauses
            stmt.executeUpdate("SET SESSION sql_mode = '';");

            // For each index in the marks, set the index
            for (String index : mark_indexes) {
                stmt.executeUpdate(index);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gets the data form the result set in a dynamicset holder
    public static DynamicSet getDynamicSet(String query) {
        // Resultset holders and metadata
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        DynamicSet dset = null;

        try {
            // Establish connection
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb?user=root&password=p@ssword");
            Statement stmt = (Statement) conn.createStatement();

            // Set sessions then execute the query
            stmt.executeUpdate("SET SESSION sql_mode = '';");
            stmt.executeQuery(query);
            rs = stmt.getResultSet();
            rsmd = rs.getMetaData();

            // Get the headers
            ArrayList<String> headers = new ArrayList<>();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String header = rsmd.getColumnName(i);
                headers.add(header);
            }

            // capture the result set
            ArrayList<ArrayList<String>> data = new ArrayList<>();
            while (rs.next()) {
                ArrayList<String> datapoint = new ArrayList<>();
                for (String header : headers) {
                    datapoint.add(rs.getString(header));
                }
                data.add(datapoint);
            }

            // Return the dynamic set object
            dset = new DynamicSet(headers, data);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dset;
    }
}
