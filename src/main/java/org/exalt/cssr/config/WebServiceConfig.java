package org.exalt.cssr.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Soap web service configurations
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    /**
     * Configure the servlet to dispatch traffic on /api/ws/reservations/*
     * @param applicationContext applicationContext
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/api/ws/reservations/*");
    }

    /**
     * Creating the wsdl with name of reservations.wsdl
     * @param reservationsSchema reservationsSchema
     * @return DefaultWsdl11Definition
     */
    @Bean(name = "reservations")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema reservationsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ReservationsPort");
        wsdl11Definition.setLocationUri("/api/ws/reservation");
        wsdl11Definition.setTargetNamespace("http://cssr.reservations.org");
        wsdl11Definition.setSchema(reservationsSchema);
        return wsdl11Definition;
    }

    /**
     * Registering the XSD file
     * @return XsdSchema
     */
    @Bean
    public XsdSchema reservationsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("reservations.xsd"));
    }
}