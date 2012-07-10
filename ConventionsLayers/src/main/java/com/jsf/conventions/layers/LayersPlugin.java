/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.layers;

import com.jsf.conventions.layers.model.BeanScopes;
import com.jsf.conventions.layers.model.BeanTypes;
import com.jsf.conventions.layers.model.ServiceTypes;
import com.jsf.conventions.layers.util.LayersUtils;
import java.io.FileNotFoundException;
import java.util.Properties;
import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jboss.forge.parser.java.JavaSource;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.JavaSourceFacet;
import org.jboss.forge.resources.java.JavaResource;
import org.jboss.forge.shell.PromptType;
import org.jboss.forge.shell.Shell;
import org.jboss.forge.shell.events.PickupResource;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.Command;
import org.jboss.forge.shell.plugins.Current;
import org.jboss.forge.shell.plugins.Help;
import org.jboss.forge.shell.plugins.Option;
import org.jboss.forge.shell.plugins.PipeOut;
import org.jboss.forge.shell.plugins.Plugin;
import org.jboss.forge.shell.plugins.RequiresFacet;

/**
 *
 * @author rmpestano Oct 23, 2011 1:16:47 PM
 */
@Alias("layers")
@RequiresFacet(JavaSourceFacet.class)
@Help("plugin for generating boilerplate code for conventions framework")
public class LayersPlugin implements Plugin {

    @Inject
    private Project project;
    @Inject
    BeanManager beanManager;
    @Inject
    private Event<PickupResource> pickup;
    @Inject
    @Current
    private JavaResource resource;
    @Inject
    private Shell shell;

    static {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(properties);
    }

    @Command(value = "generate-controller", help = "Generate managed bean and service from an entity class")
    public void generateController(PipeOut out,
            @Option(name = "entity", required = true, type = PromptType.JAVA_CLASS, help = "full qualified entity name which the mbean wll control") JavaResource domainClass,
            @Option(name = "bean", required = false, help = "Name of the managed bean") String beanName,
            @Option(name = "beanScope", required = false, help = "Scope of managed bean") String beanScope,
            @Option(name = "beanType", required = false, help = "Managed bean type") String beanType,
            @Option(name = "modalObserver", required = false, flagOnly = true, help = "Managed bean observers modal callback") boolean isModalObserver) throws FileNotFoundException {

        try {


            JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);
            JavaSource<?> javaSource = domainClass.getJavaSource();

            if (beanName == null || "".equals(beanName)) {
                beanName = shell.prompt("Managed bean name(leave blank to use entityName + MBean):");
                if ("".equals(beanName)) {
                    beanName = javaSource.getName().concat("MBean");
                }
            }

            //managed bean scope
            if (StringUtils.isEmpty(beanScope)) {
                beanScope = shell.promptChoiceTyped("Choose bean scope", BeanScopes.getScopes(), BeanScopes.VIEW_ACCESS_SCOPED.getName());
            }

            String scopePackage = this.getScopePackage(beanScope);
            StringBuilder managedBeanPackage = new StringBuilder(javaSource.getPackage().substring(0, javaSource.getPackage().lastIndexOf(".")));
            managedBeanPackage.append(".controller");
            String beanQualifier = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);

            //managed bean type

            if (StringUtils.isEmpty(beanType)) {
                beanType = shell.promptChoiceTyped("Choose bean type", BeanTypes.getTypes(), BeanTypes.BASE_MANAGED_BEAN.getName());
            }

            //managed bean service
            VelocityContext context = new VelocityContext();
            context.put("beanName", beanName);
            boolean serviceGenerated = this.generateService(context, javaSource);
            context.put("package", managedBeanPackage);
            context.put("beanScope", beanScope);
            context.put("scopePackage", scopePackage);
            context.put("entity", javaSource.getName());
            context.put("camelEntity", javaSource.getName().substring(0, 1).toLowerCase() + javaSource.getName().substring(1));
            context.put("beanType", beanType);
            context.put("beanQualifier", beanQualifier);
            context.put("hasService", serviceGenerated);
            context.put("isModalObserver", isModalObserver);
            context.put("entityPackage", javaSource.getPackage());
            context.put("newline", "\n");

            LayersUtils.saveJavaFile("TemplateController.vtl", context, java);
            shell.println("<<< Managed bean " + beanName + " created sucessfuly. >>>");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean generateService(VelocityContext context, JavaSource<?> javaSource) {
        try {
            String serviceName = null;;
            StringBuilder servicePackage = null;
            boolean existingService = false;
            boolean newService = false;
            boolean configFilters = false;
            if (newService = shell.promptBoolean("Generate Service for " + context.get("beanName") + " ?")) {
                serviceName = shell.prompt("Service name(leave blank to use entityName+service)?");
                if ("".equals(serviceName)) {
                    serviceName = javaSource.getName().concat("Service");
                }
                servicePackage = new StringBuilder(javaSource.getPackage().substring(0, javaSource.getPackage().lastIndexOf(".")));
                servicePackage.append(".service");
                configFilters = shell.promptBoolean("Configure filters(will genarate configFindPaginated skeleton) ?",true);
            } else if (existingService = shell.promptBoolean("Use existing Service for " + context.get("beanName") + " ?")) {//if no service was generated, ask to use existing one
                serviceName = shell.prompt("service name(leave blank to use entityName+service)?");
                if (StringUtils.isBlank(serviceName)) {
                    serviceName = javaSource.getName();
                }
                servicePackage = new StringBuilder(shell.prompt("service package(leave blank to use entityPackage.service)?"));
                if (servicePackage == null || "".equals(servicePackage.toString())) {
                    servicePackage = new StringBuilder(javaSource.getPackage().substring(0, javaSource.getPackage().lastIndexOf(".")));
                    servicePackage.append(".service");
                }

            }
            
            
            if(existingService || newService){
                context.put("servicePackage", servicePackage);
                context.put("serviceImplPackage", servicePackage + ".impl");
                context.put("entity", javaSource.getName());
                context.put("configFilters", configFilters);
                context.put("serviceName", serviceName);
                context.put("entityPackage", javaSource.getPackage());
                LayersUtils.saveJavaInterface("TemplateService.vtl", context, project.getFacet(JavaSourceFacet.class));
                String serviceType = shell.promptChoiceTyped("Choose service type", ServiceTypes.getTypes(), ServiceTypes.STATELESS.toString());
                if (serviceType.equals(ServiceTypes.STATELESS.toString())) {
                    LayersUtils.saveJavaFile("TemplateStatelessServiceImpl.vtl", context, project.getFacet(JavaSourceFacet.class));
                } else if (serviceType.equals(ServiceTypes.STATEFUL.toString())) {
                    LayersUtils.saveJavaFile("TemplateStatefulServiceImpl.vtl", context, project.getFacet(JavaSourceFacet.class));
                }
                System.out.println("servicePackage:"+servicePackage);
                System.out.println("Service Name:"+serviceName);
                
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getScopePackage(String beanScope) {
        String scopePackage = null;
        if (beanScope.equalsIgnoreCase(BeanScopes.VIEW_ACCESS_SCOPED.getName())) {
            scopePackage = BeanScopes.VIEW_ACCESS_SCOPED.getScopePackage();
        } else if (beanScope.equalsIgnoreCase(BeanScopes.CONVERSATION_SCOPED.getName())) {
            scopePackage = BeanScopes.CONVERSATION_SCOPED.getScopePackage();
        } else if (beanScope.equalsIgnoreCase(BeanScopes.SESSION_SCOPED.getName())) {
            scopePackage = BeanScopes.SESSION_SCOPED.getScopePackage();
        } else if (beanScope.equalsIgnoreCase(BeanScopes.REQUEST_SCOPED.getName())) {
            scopePackage = BeanScopes.REQUEST_SCOPED.getScopePackage();
        }

        return scopePackage;
    }
}
