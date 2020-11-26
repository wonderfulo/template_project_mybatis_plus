//package com.cxy.config.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//import javax.sql.DataSource;
//
///**
// * @program: template_project_mybatis_plus
// * @description: 数据库安全配置
// * @author: 陈翔宇
// * @create_time: 2020-11-18 21:23:45
// */
//@Configuration
//public class JdbcSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 注入数据源
//     */
//    @Autowired
//    private DataSource dataSource = null;
//
//    /**
//     * 注入配置的密钥
//     */
//    @Value("${system.user.password.secret}")
//    private String secret = null;
//
//    //使用用户名查询密码
//    String pwdQuery = " select user_name, pwd, available " +
//            " from t_user " +
//            " where user_name = ?";
//
//    //使用用户名查询角色
//    String roleQuery = " select u.user_name, r.role_name " +
//            " from t_user u , t_user_role ur, t_role r " +
//            " where u.id = ur.user_id " +
//            " and r.id = ur.role_id" +
//            " and u.user_name = ?";
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(this.secret);
//        MyPasswordEncoder passwordEncoder = new MyPasswordEncoder();
//        auth.jdbcAuthentication()
//                // 设置密码编译器
//                .passwordEncoder(passwordEncoder)
//                // 数据源
//                .dataSource(dataSource)
//                // 查询用户，自动判断密码是否一致
//                .usersByUsernameQuery(pwdQuery)
//                // 赋予用户角色
//                .authoritiesByUsernameQuery(roleQuery);
//
//    }
//
//    /**
//     * 定义授权规则
//     *
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        /**
//         * .authorizeRequests：表示验证请求
//         * .antMatchers(String... antPatterns)：使用 {@link AntPathRequestMatcher} 的匹配规则
//         * .antMatchers("/") : 表示应用的首页
//         * .permitAll()：表示允许一切用户访问，底层调用 access("permitAll")
//         * .hasRole(String role)：表示 antMatchers 中的 url 请求允许此角色访问
//         * .hasAnyRole(String... roles) : 表示 antMatchers 中的 url 请求允许这多个角色访问
//         * .access(String attribute)：表示允许访问的角色，permitAll、hasRole、hasAnyRole 底层都是调用 access 方法
//         * .access("permitAll") 等价于 permitAll()
//         * "/**" 表示匹配任意
//         */
//        http.authorizeRequests().antMatchers("/").permitAll();
//        http.authorizeRequests()
//                .antMatchers("/person/del/**").hasRole("administrators")
//                .antMatchers("/person/update").hasAnyRole("administrators", "auditor")
//                .antMatchers("/person/add").hasAnyRole("administrators", "operator")
//                .antMatchers("/person/findById/**", "/person/lists").access("permitAll");
//        /**
//         * http.authorizeRequests().anyRequest().hasRole("senior")：
//         *      表示约定以外的所有请求，都需要有 senior 角色才可以访问
//         * http.authorizeRequests().anyRequest().authenticated()：
//         *      表示约定以外的所有请求，必须要经过认证才能访问，但是认证的可以是任意角色，即只要认证就行，与角色的权限无关
//         * http.authorizeRequests().anyRequest().permitAll():
//         *      表示约定以外的所有请求，任何用户都可以访问.
//         */
//        http.authorizeRequests().anyRequest().permitAll();
//        /**
//         * formLogin：指定支持基于表单的身份验证
//         * 未使用 FormLoginConfigurer#loginPage(String) 指定登录页时，将自动生成一个登录页面，亲测此页面引用的是联网的 bootStrap 的样式，所以断网时，样式会有点怪
//         * 当用户没有登录、没有权限时就会自动跳转到登录页面(默认 /login)
//         * 当登录失败时，默认跳转到 /login?error
//         * 登录成功时会放行
//         */
//        http.formLogin();
//
//    }
//
//}
