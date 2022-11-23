package pl.zubkova.webuniversity.config.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import pl.zubkova.webuniversity.config.ApplicationConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
/**
 * @author Svetlana_Zubkova
 */
@Configuration
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    public void onStartup(ServletContext context) throws ServletException {
        super.onStartup(context);
       context.setInitParameter("spring.profiles.active", "Test");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ ApplicationConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
