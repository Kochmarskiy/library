package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 class H2Database {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";
    static final String USER = "sa";
    static final String PASS = "";
    public H2Database(){
        createTableIfNotExist();
    }
    private void createTableIfNotExist(){
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE IF NOT EXISTS MODEL " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " category VARCHAR(255), " +
                    " authorname VARCHAR(255), " +
                    " modelname  VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
    private boolean isModel(String category, Model model){
      /*  ResultSet rs = stmt.executeQuery(sql);
        int rowCount = rs.last() ? rs.getRow() : 0;*/
        Connection conn = null;
        Statement stmt = null;
        boolean r = false;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = null;

                sql = "SELECT category, authorname, modelname FROM MODEL where category='" + category + "' AND authorname='"+model.getAuthorName()+"' AND modelname='"+model.getModelName()+"'";

            ResultSet rs = stmt.executeQuery(sql);
            int rowCount = rs.last() ? rs.getRow() : 0;
            if(rowCount>0) r=true;
            rs.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    return r;
    }
    public String add(String category,Model model){
        if(isModel(category,model)){
            return "There already exists the model in the database";
        }
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = "INSERT INTO Model(category,authorname,modelname) " + "VALUES ( '"+category+"', '"+model.getAuthorName()+"', '"+model.getModelName()+"')";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return category+" "+model.getAuthorName()+" \""+model.getModelName()+"\" was added";
    }
    public String remove(String category, Model model) {
        Connection conn = null;
        Statement stmt = null;
        int res = 0;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "DELETE FROM MODEL " + "WHERE category='"+category+"' AND authorname='"+model.getAuthorName()+"' AND modelname='"+model.getModelName()+"'";
            res = stmt.executeUpdate(sql);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

if(res==0)
    return "There is not requested model";
        else
        return category+" "+model.getAuthorName()+" \""+model.getModelName()+"\""+" was removed";
}
    public void show(String category){
        Connection conn = null;
        Statement stmt = null;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = null;
            if(category!=null && category.length()>0) {
                sql = "SELECT category, authorname, modelname FROM MODEL where category='" + category + "' ORDER BY modelname";
            }
            else{
                sql = "SELECT category, authorname, modelname FROM MODEL ORDER BY modelname";
            }
            ResultSet rs = stmt.executeQuery(sql);
            int a=0;
            while(rs.next()) {
                String c = rs.getString("category");
                String aname = rs.getString("authorname");
                String mname = rs.getString("modelname");
                System.out.print(aname+" ");
                System.out.println("\""+mname+"\"");
                a++;
            }
            if(a==0)
            {
                System.out.println("No result found");
            }
            rs.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    public String edit(String category, Model oldOne, Model newOne){
        String remRes = remove(category,oldOne);
        if(remRes.contains("was removed")) {
           String addRes =  add(category, newOne);
           if(addRes.contains("was added")){
               return category+" "+oldOne.getAuthorName()+" "+oldOne.getModelName()+" was edited to "+newOne.getAuthorName()+" "+newOne.getModelName();
           }
           else{
               return addRes;
           }
        }
            return remRes;


    }

    public static void main(String[] args) {
    }
}
