package test.springboard.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import test.springboard.domain.User;
import test.springboard.domain.UserCheck;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class MysqlUserRepository implements UserRepository{
    private final DataSource dataSource;
    // 커넥션풀에는 여러개의 Connection 객체가 생성되어 운용되는데,
    // 이를 직접 웹 애플리케이션에서 다루기 힘들기 때문에 DataSource라는 개념을 도입하여 사용

    public MysqlUserRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public User save(User user) {
        String sql = "insert into User(id, password, nickname, email) values(?,?,?,?)"; //sql 구문 - 각 컬럼에 value값을 넣어줌
        Connection conn = null; //
        PreparedStatement pstmt = null; // PreparedStatement 클래스 사용시 sql 쿼리에 매개변수를 부여하여 실행할 수 있음 || statement 클래스의 기능 향상, 코드 안전성 높음, 가능성 높음
        ResultSet rs = null; // ResultSet 객체 - Statement 객체로 SELECT 문을 사용하여 얻어온 레코드값들을 테이블의 형태로 갖는 객체

        try{ //처리할 예외가 발생할지도 모를 코드 블록을 정의하는 역할
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS); //AUTO_INCREMENT로 인해 자동으로 생성되어진 key를 가져오는 쿼리

            pstmt.setString(1, user.getId()); //setString - 새로운 값을 칼럼의 값으로 덮어씀
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getEmail());

            pstmt.executeUpdate(); //insert, update, delete등 리턴 값이 필요 없는 쿼리문일 때 사용
            rs = pstmt.getGeneratedKeys();
            if(rs.next()){ //rs.next()는 boolean을 리턴하는데 다음 레코드가 존재하면 true를 반환
                user.setUserno(rs.getLong(1));
            }else {
                throw new SQLException("아이디 조회 실패"); //throw = 강제로 예외 발생시키기 위해 사용
                // throw - 억지로 에러를 발생시키고자 할 때 사용되거나 현재 메소드의 에러를 처리한 후에 상위 메소드에 에러 정보를 줌으로써 상위 메서드에서도 에러가 발생한 것을 감지
                // throws - 현재 메서드에서 자신을 호출한 상위 메서드로 Exception을 발생 → 즉, 예외를 자신이 처리하지 않고, 자신을 호출한 메소드에게 책임 전가
                // SQLException - SQL Server에서 경고 또는 오류를 반환할 때 throw되는 예외
            }
            return user;
        }catch (Exception e) { //try 블록 내부에서 예외가 발생할 경우 호출되는 문장 블록
            throw new IllegalStateException(e); // 객체의 상태가 매소드 호출에는 부적절한 경우
        } finally { // 앞서 try 블록에서 일어난 일에 관계없이 항상 실행이 보장되어야 할 뒷정리용 코드가 포함됨.
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
                if(ID.equals(rs.getString(2))==true && PW.equals(rs.getString(3))==true){  //getSring - 특정 칼럼값 문자열로 가져옴
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
    // 스프링을 통해서 데이터베이스 커넥션을 하므로, DataSourceUtils를 통해서 Connection을 획득해야 함
    // 그래야 Transaction 같은 것에 걸릴 수 있는데 이때 Database Connection을 똑같은 걸 유지시켜야 함.
    // 이때 똑같은 걸 유지하는 것이 DataSourceUtils가 해결해줌.
    // 물론 반납할 때도 동일하게 DataSourceUtils로 release 해야함.
}
