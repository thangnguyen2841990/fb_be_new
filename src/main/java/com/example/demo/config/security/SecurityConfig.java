package com.example.demo.config.security;






import com.example.demo.config.CustomAccessDeniedHandler;
import com.example.demo.config.JwtAuthenticationFilter;
import com.example.demo.config.RestAuthenticationEntryPoint;
import com.example.demo.model.entity.GroupStatus;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.StatusPost;
import com.example.demo.model.entity.User;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.repository.IStatusRepository;
import com.example.demo.repository.IUserRepository;

import com.example.demo.service.groupStatus.IGroupStatusService;
import com.example.demo.service.role.IRoleService;
import com.example.demo.service.user.IUSerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IUSerService userService;
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IStatusRepository statusRepository;

    @Autowired
    private IGroupStatusService groupStatusService;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() { //bean mã hóa pass
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //lấy user từ DB
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @PostConstruct
    public void init() {
        List<User> users = userRepository.findAll();
        List<Role> roleList = roleRepository.findAll();
        List<StatusPost> statusPostList = this.statusRepository.findAll();
        List<GroupStatus> groupStatusList = this.groupStatusService.findAll();
        if (statusPostList.isEmpty()) {
            this.statusRepository.save(new StatusPost("Tất cả"));
            this.statusRepository.save(new StatusPost("Chỉ bạn bè"));
            this.statusRepository.save(new StatusPost("Chỉ mình tôi"));
        }
        if (groupStatusList.isEmpty()) {
            this.groupStatusService.save(new GroupStatus("Công khai"));
            this.groupStatusService.save(new GroupStatus("Riêng tư"));
        }

        if (roleList.isEmpty()) {
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleService.save(roleAdmin);
            Role roleUser = new Role("ROLE_USER");
            roleService.save(roleUser);
            Role roleHost = new Role("ROLE_HOST");
            roleService.save(roleHost);
        }
        if (users.isEmpty()) {
            User admin = new User("admin", "thuthuyda1");
            userService.saveAdmin(admin);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**");
        http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());//Tùy chỉnh lại thông báo 401 thông qua class restEntryPoint
        http.authorizeRequests()
                .antMatchers("/login",
                        "/register", "/**").permitAll() // tất cả truy cập được
                .anyRequest().authenticated()  //các request còn lại cần xác thực
                .and().csrf().disable(); // vô hiệu hóa bảo vệ của csrf (kiểm soát quyền truy cập)
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
    }
}