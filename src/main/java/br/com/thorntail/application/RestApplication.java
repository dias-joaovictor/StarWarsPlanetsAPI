	package br.com.thorntail.application;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@ApplicationPath("/api")
@SwaggerDefinition (
info = @Info (
        title = "Planet Basic Crud",
        description = "The way to manage your Planet!!! 05/04(May the Forth) be with you",
        contact = @Contact (
            name = "João Victor Dias",
            email = "kvj1610@hotmail.com",
            url = "https://github.com/silverfoxjv"
        ), version = "v1.0"
    ),
    basePath = "/api",
    schemes = {SwaggerDefinition.Scheme.HTTP}
)
public class RestApplication extends Application {

}
