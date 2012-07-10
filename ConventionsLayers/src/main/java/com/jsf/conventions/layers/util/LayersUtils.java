/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.layers.util;

import java.io.FileNotFoundException;
import java.io.StringWriter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jboss.forge.parser.JavaParser;
import org.jboss.forge.parser.java.JavaClass;
import org.jboss.forge.parser.java.JavaInterface;
import org.jboss.forge.project.facets.JavaSourceFacet;

/**
 *
 * @author rmpestano Oct 25, 2011 10:35:59 PM
 */
public class LayersUtils {
    
    public static void saveJavaFile(String velocityTemplate,VelocityContext context,JavaSourceFacet java) throws FileNotFoundException{
         StringWriter writer = new StringWriter();
         Velocity.mergeTemplate(velocityTemplate, "UTF-8", context, writer);
         JavaClass mbean = JavaParser.parse(JavaClass.class, writer.toString());
         java.saveJavaSource(mbean); 
    }
    public static void saveJavaInterface(String velocityTemplate,VelocityContext context,JavaSourceFacet java) throws FileNotFoundException{
         StringWriter writer = new StringWriter();
         Velocity.mergeTemplate(velocityTemplate, "UTF-8", context, writer);
         JavaInterface mbean = JavaParser.parse(JavaInterface.class, writer.toString());
         java.saveJavaSource(mbean); 
    }
}
