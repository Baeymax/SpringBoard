package test.springboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.springboard.repository.MemberRepository;
import test.springboard.repository.MemoryMemberRepository;
import test.springboard.service.MemberService;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
