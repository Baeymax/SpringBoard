package test.springboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.springboard.repository.MysqlUserRepository;
import test.springboard.repository.UserRepository;
import test.springboard.service.UserService;
import test.springboard.repository.MysqlBoardRepository;
import test.springboard.repository.BoardRepository;
import test.springboard.service.BoardService;

import javax.sql.DataSource;


// annotation - 클래스와 메서드에 추가하여 다양한 기능을 부여하는 역할
@Configuration // 설정파일을 만들기 위한 애노테이션 or Bean을 등록하기 위한 annotation
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Bean // 개발자가 컨트롤이 불가능한 외부 라이브러리들을 Bean으로 등록하고 싶은 경우에 사용, 메소드 또는 annotation 단위에 붙일 수 있음.
    public UserService userService() {
        return new UserService(userRepository());
    }
    @Bean
    UserRepository userRepository() { return new MysqlUserRepository(dataSource); }

    @Bean
    public BoardService boardService() { return new BoardService(boardRepository());}
    @Bean
    BoardRepository boardRepository() { return new MysqlBoardRepository(dataSource);}
}
