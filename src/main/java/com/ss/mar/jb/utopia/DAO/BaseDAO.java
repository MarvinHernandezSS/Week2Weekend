package com.ss.mar.jb.utopia.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDAO<T> {
    protected Connection conn = null;

    public BaseDAO(Connection conn) {
        this.conn = conn;
    }

    public Integer saveReturnPK(String sql, Object[] vals)  throws ClassNotFoundException, SQLException {
        PreparedStatement pstmt =
                conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int count = 1;
        for(Object o: vals) {
            pstmt.setObject(count, o);
            count++;
        }
        pstmt.executeUpdate();
//		ResultSet rs = pstmt.getGeneratedKeys(); //try this
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            return rs.getInt(1);//try 0
        }
        return null;
    }

    public void save(String sql, Object[] vals)  throws ClassNotFoundException, SQLException {
        PreparedStatement pstmt =
                conn.prepareStatement(sql);
        int count = 1;
        for(Object o: vals) {
            pstmt.setObject(count, o);
            count++;
        }
        pstmt.executeUpdate();
    }

    public List<T> read(String sql, Object[] vals)  throws ClassNotFoundException, SQLException {
        PreparedStatement pstmt =
                conn.prepareStatement(sql);
        int count = 1;
        for(Object o: vals) {
            pstmt.setObject(count, o);
            count++;
        }
        ResultSet rs = pstmt.executeQuery();
        return extractData(rs);
    }

    abstract public List<T> extractData(ResultSet rs) throws ClassNotFoundException, SQLException ;
}
