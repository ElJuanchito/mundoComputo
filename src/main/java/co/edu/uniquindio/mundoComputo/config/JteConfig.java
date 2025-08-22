package co.edu.uniquindio.mundoComputo.config;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class JteConfig {

    @Bean
    public TemplateEngine templateEngine() {
        var resolver = new DirectoryCodeResolver(Path.of("src/main/jte"));
        return TemplateEngine.create(resolver, Path.of("target/jte-classes"), ContentType.Html);
    }
}
