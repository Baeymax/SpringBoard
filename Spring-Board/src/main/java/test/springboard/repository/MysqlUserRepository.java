package test.springboard.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import test.springboard.domain.User;
import test.springboard.domain.UserCheck;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class MysqlUserRepository implements UserRepository{
    private final DataSource dataSource;

    public MysqlUserRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public User save(User user) {
        String sql = "insert into User(id, password, nickname, email) values(?,?,?,?)"; //sql 구문 - 각 컬럼에 value값을 넣어줌
        Connection conn = null; //
        PreparedStatement pstmt = null; // PreparedStatement 클래스 사용시 sql 쿼리에 매개변수를 부여하여 실행할 수 있음 || statement 클래스의 기능 향상, 코드 안전성 높음, 가능성 높음
        ResultSet rs = null; // ResultSet 객체 - Statement 객체로 SELECT 문을 사용하여 얻어온 레코드값들을 테이블의 형태로 갖는 객체

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getEmail());

            pstmt.executeUpdate(); //insert, update, delete등 리턴 값이 필요 없는 쿼리문일 때 사용
            rs = pstmt.getGeneratedKeys();
            if(rs.next()){ //rs.next()는 boolean을 리턴하는데 다음 레코드가 존재하면 true를 반환
                user.setUserno(rs.getLong(1));
            }else {
                throw new SQLException("아이디 조회 실패");
            }
            return user;
        }catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "select * from User where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery(); // select등 리턴 값이 필요한 쿼리문일 때 사용
            if(rs.next()){
                User user = new User();
                user.setId(rs.getString("id"));
                user.setNickname(rs.getString("nickname"));
                return Optional.of(user);
            }else{
                return Optional.empty();
            }
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public User login(UserCheck usercheck) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String ID = usercheck.getId();
        String PW = usercheck.getPassword();
        String sql = "select * from User where id = '"+ID+"'";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            rs = pstmt.executeQuery();
            if(rs.next()){
                if(ID.equals(rs.getString(2))==true && PW.equals(rs.getString(3))==true){
                    throw new SQLException("로그인되었습니다.");
                }else if(PW.equals(rs.getString(3))==false){
                    throw new SQLException("비밀번호가 일치 하지 않습니다.");
                }else {
                    throw new SQLException("존재하지 않는 아이디입니다.");
                }
            }
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
        return null;
    }



    private  Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try{
            if(rs != null){
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
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
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
