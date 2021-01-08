package com.mongodb.jdbc.debug;

import java.sql.*;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mongodb.jdbc.MongoDriver";
   private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

   public static void main(String[] args) {
	   String url = args[0];
	   String query = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

      try{
         java.util.Properties p = new java.util.Properties();
         // These properties will be added to the URI.
         // Uncomment if you wish to specify user and password.
         // p.setProperty("user", "user");
         // p.setProperty("password", "foo");
         p.setProperty("database", "claas");
         System.out.println("Connecting to database...");
         Connection conn = DriverManager.getConnection(url, p);

        DatabaseMetaData dbmd = conn.getMetaData();
        System.out.println(dbmd.getDriverVersion());
        System.out.println(dbmd.getDriverMajorVersion());
        System.out.println(dbmd.getDriverMinorVersion());

		System.out.println("Creating statement...");
		Statement stmt = conn.createStatement();
		System.out.println("Created statement..\n");

		System.out.println("Executing query...");
		ResultSet rs = stmt.executeQuery(query);
		System.out.println("Received ResultSet handle.\n");

		System.out.println("Iterating resultset...");
		ArrayList<String> vals = new ArrayList<String>();
		while (rs.next()) {
			vals.add(rs.getString(1));
		}
		System.out.println("Done iterating resultset\n");

		System.out.println(vals);

      } catch (Exception e) {
          throw new RuntimeException(e);
      }
   }

   public static void displayResultSet(ResultSet rs) throws java.sql.SQLException {
	   Calendar c = new GregorianCalendar();
	   c.setTimeZone(UTC);
       while(rs.next()){
          //Retrieve by column name
          double a = rs.getDouble("a");
		  String as = rs.getString("a");
          String b = rs.getString("b");
		  java.sql.Timestamp bd;
		  try {
		  		bd = rs.getTimestamp("b", c);
				System.out.println("b as a Timestamp is: " + bd);
		  } catch (SQLException e) {
				System.out.println(e);
		  } catch (Exception e) {
				throw new RuntimeException(e);
		  }
          ResultSetMetaData metaData = rs.getMetaData();
		  System.out.println("a is: " + a + " as double"
				  + " b is: " + b + " as string");
		  System.out.println("a as a string is: " + as);
       }
   }
}
