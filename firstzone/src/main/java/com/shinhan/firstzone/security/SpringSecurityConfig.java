package com.shinhan.firstzone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shinhan.firstzone.security.jwt.JwtAuthFilter;
import com.shinhan.firstzone.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration //설정파일임을 의미 ...app 시작 시 해석된다.
@EnableWebSecurity //웹에서 시큐리티를 사용 가능
@RequiredArgsConstructor //Autowired와 같다.
public class SpringSecurityConfig{
	//상수로 접근권한에 따른 요청주소들을 저장
	private static final String[] WHITE_LIST = {"security/all", "/auth/**", "/api/board/**", "/v3/**", "/swagger-ui/**"};
	private static final String[] USER_LIST = {"security/user", "/webboard/**", "/replies/**"};
	private static final String[] ADMIN_LIST = {"security/admin"};
	private static final String[] MANAGER_LIST = {"security/manager"};
	private final MemberService MemberService ;
	private final JwtUtil JwtUtil ;
	
//	@Bean //객체를 만든다.
//	PasswordEncoder passwordEncoder(){
//		return new BCryptPasswordEncoder();
//	}
	
	@Bean
	public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
		//http.csrf(c->c.disable());//csrf(사이트 간 요청 위조) 토큰을 사용하지 않을 경우, default는 토큰을 사용
		http.authorizeHttpRequests(auth->{
			auth.requestMatchers(WHITE_LIST).permitAll(); //로그인 불필요
			auth.requestMatchers(USER_LIST).hasRole("USER"); //이 이후 애들은 로그인을 해야 들어감
			auth.requestMatchers(ADMIN_LIST).hasRole("ADMIN");
			auth.requestMatchers(MANAGER_LIST).hasRole("MANAGER");
			auth.anyRequest().authenticated();
		});
		
		http.csrf(c->c.disable()); //csrf토큰을 사용하지않음 , default는 토큰을 사용함
	    //JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
        http.addFilterBefore(new JwtAuthFilter(MemberService, JwtUtil),
        		UsernamePasswordAuthenticationFilter.class);
        
//		http.formLogin(login->login.loginPage("/auth/login")
//				.usernameParameter("mid") //default 이름이 username, 변경하고자 한다면 반드시 설정하기
//				.defaultSuccessUrl("/auth/loginSuccess")//성공페이지와 연결
//				.permitAll());
//		http.logout(out->out.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
//				.logoutSuccessUrl("/auth/loginSuccess")
//				.invalidateHttpSession(true));
//		http.exceptionHandling(handling->handling.accessDeniedPage("/auth/accessDenied"));
		return http.build();
	}
}
