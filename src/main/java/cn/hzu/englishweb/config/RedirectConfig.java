package cn.hzu.englishweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @className RedirectConfig
 * @description 路径重定向配置类
 * @author Hzurang
 * @createDate 2021/11/30
 */
@Configuration
public class RedirectConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/selectWord").setViewName("select-word");
    }
}
