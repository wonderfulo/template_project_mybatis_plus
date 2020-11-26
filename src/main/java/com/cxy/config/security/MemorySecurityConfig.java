//package com.cxy.config.security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * spring security 安全框架配置
// * 1、 WebSecurityConfigurerAdapter(抽象类) implements 了 WebSecurityConfigurer<WebSecurity>
// * 2、@EnableWebSecurity ：表示开启 Spring Security 安全认证与授权
// *
// * @author wangmaoxiong
// * @version 1.0
// * @date 2020/5/20 20:39
// */
//@Configuration
//public class MemorySecurityConfig extends WebSecurityConfigurerAdapter {
//    /**
//     * 定义用户认证规则
//     *
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        /**inMemoryAuthentication()：添加内存用户认证，这些账号密码只存储在内存中，而不是数据库。
//         * jdbcAuthentication()：JdbcUserDetailsManagerConfigurer 数据库用户认证。
//         * <p>
//         * .passwordEncoder(PasswordEncoder passwordEncoder)：密码编码，Spring Security 高版本必须进行密码编码，否则报错
//         * .withUser(String username)：添加用户名称，返回 UserDetailsBuilder
//         * .password(String password)：为用户添加密码，不能为 null
//         * .roles(String... roles)：为用户添加角色，一个用户可以有多个角色.
//         * .and()：返回对象本身，方便链式编程，也可以分开写
//         * </p>
//         */
//        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
//                .withUser("zhangSanFeng").password("123456").roles("administrators")
//                .and()
//                .withUser("zhangCuiShan").password("123456").roles("auditor", "operator")
//                .and()
//                .withUser("zhangWuJi").password("123456").roles("operator");
//    }
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
//    }
//}
