package com.example.todolist.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// Spring Security 6.x以降で推奨される書き方
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(request -> request
                                                // H2 Console のパスを許可
                                                .requestMatchers(
                                                                AntPathRequestMatcher.antMatcher("/userRegister"),
                                                                AntPathRequestMatcher.antMatcher("/css/**"),
                                                                AntPathRequestMatcher.antMatcher("/js/**"),
                                                                AntPathRequestMatcher.antMatcher("/images/**"),
                                                                AntPathRequestMatcher.antMatcher("/login"),
                                                                AntPathRequestMatcher.antMatcher("/h2/**") // ここを
                                                                                                           // AntPathRequestMatcher.antMatcher
                                                                                                           // に変更
                                                )
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(login -> login
                                                .loginProcessingUrl("/login")
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/effect", true)
                                                .failureUrl("/login?error")
                                                .permitAll())
                                .logout(logout -> logout
                                                // ログアウト処理のURL (デフォルトは /logout)
                                                .logoutUrl("/logout")
                                                // ログアウト成功時のリダイレクト先
                                                .logoutSuccessUrl("/login?logout") // 例: ログインページにリダイレクトし、ログアウトメッセージを表示
                                                // ログアウト時にセッションを無効にする (推奨)
                                                .invalidateHttpSession(true)
                                                // ログアウト時にCookieを削除する (任意、例: JSESSIONID)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll() // ログアウトURLも誰でもアクセス可能
                                );

                // ★追加: H2 Console が iframe で表示されるように、フレームオプションを無効にする
                http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

                // ★追加: H2 Console へのPOSTリクエストを許可するため、CSRF保護を無効にする
                // 注意: 本番環境ではCSRF保護を無効にすることは推奨されません。
                // 開発時のみH2 Consoleのパスに対して無効にすることを検討してください。
                http.csrf(csrf -> csrf
                                .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2/**")));

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}