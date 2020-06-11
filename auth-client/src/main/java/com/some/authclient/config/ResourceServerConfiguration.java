//package com.some.authclient.config;
//
//import com.google.common.collect.Lists;
//
//import com.some.authclient.handler.AuthExceptionEntryPoint;
//import com.some.authclient.handler.CustomAccessDeniedHandler;
//import com.some.authclient.handler.CustomTokenEnhancer;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.vote.AffirmativeBased;
//import org.springframework.security.access.vote.AuthenticatedVoter;
//import org.springframework.security.access.vote.RoleVoter;
//import org.springframework.security.config.annotation.ObjectPostProcessor;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.web.access.expression.WebExpressionVoter;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//
///**
// * 资源服务器配置
// * @description:
// * @vsersion: V1.0
// * @author: qzq
// * @date: 2020-06-04 17:34
// */
//@Configuration
//@EnableResourceServer
//@Order(3)
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//    private static final String DEMO_RESOURCE_ID = "some_res";
//
//    @Autowired
//    private CustomAccessDeniedHandler accessDeniedHandler;
//
//
//    @Autowired
//    private AuthExceptionEntryPoint authExceptionEntryPoint;
//
//    @Autowired
//    CustomTokenEnhancer customTokenEnhancer;
//    @Autowired
//    private TokenStore tokenStore;
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources
//                .resourceId(DEMO_RESOURCE_ID).stateless(true)
//                .authenticationEntryPoint(authExceptionEntryPoint) // 外部定义的token错误进入的方法
//                .accessDeniedHandler(accessDeniedHandler); // 没有权限的进入方法
//
//        resources.tokenServices(tokenServices());
//    }
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .antMatcher("/**").authorizeRequests()
//                 .anyRequest().authenticated()
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
//                       // 权限获取自定义配置
//                        fsi.setSecurityMetadataSource(new PermissionFilterInvocationSecurityMetadataSource());
//                        return fsi;
//                    }
//                })
//                .accessDecisionManager(accessDecisionManager());
//
//    }
//
//
//    private AccessDecisionManager accessDecisionManager() {
//
//        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
//        webExpressionVoter.setExpressionHandler(new OAuth2WebSecurityExpressionHandler());
//        // 授权逻辑自定义配置
//        return new AffirmativeBased(Lists.newArrayList(new PermissionsVoter(), new RoleVoter(),
//                new AuthenticatedVoter(), webExpressionVoter));
//    }
//
//    @Primary
//    @Bean
//    public DefaultTokenServices tokenServices() {
//
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore);
//        return defaultTokenServices;
//    }
//
//
//
//}
