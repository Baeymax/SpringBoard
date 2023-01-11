package test.springboard.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import test.springboard.domain.Board;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class MysqlBoardRepository implements BoardRepository {

    private  final DataSource dataSource;

    public MysqlBoardRepository(DataSource dataSource) { this.dataSource = dataSource;}

    @Override
    public Board save(Board board) {
        String sql = "insert into Board() values(?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        /*
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, board.getContents());
            pstmt.setString(2, board.getTitle());
        }*/
        return null;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try{
            if(rs != null){
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace(); // 예외발생 당시의 호출스택(Call Stack)에 있었던 메소드의 정보와 예외 메시지를 화면에 출력
        }
        try{
            if(pstmt != null){
                pstmt.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException { DataSourceUtils.releaseConnection(conn, dataSource); }


}
