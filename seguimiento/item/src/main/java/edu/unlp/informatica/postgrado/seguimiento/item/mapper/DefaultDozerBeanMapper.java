package edu.unlp.informatica.postgrado.seguimiento.item.mapper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

public class DefaultDozerBeanMapper extends DozerBeanMapper {

		
	public DefaultDozerBeanMapper() {
		
		super();		
				
		try {
			for (Class c : findMyTypes("edu.unlp.informatica.postgrado.seguimiento.item.model")) {
		
				final List<String> fieldsToExcluded = new ArrayList<String>();
				
				for(Field f : c.getDeclaredFields()){

					updateFieldsToExlude(f, fieldsToExcluded);
				}
				for(Method m : c.getDeclaredMethods()){
				
					updateFieldsToExlude(m, fieldsToExcluded);
				}
			 
				addClass(c, fieldsToExcluded);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addClass(final Class c, final List<String> fieldsToExcluded) {
		
		addMapping(new BeanMappingBuilder() {
			
			protected void configure() {
				
				TypeMappingBuilder mapping = mapping(c, c);
	        	for (String string : fieldsToExcluded) {
	        		
	        		String firstChar = string.substring(3, 4).toLowerCase();
	        		String rest = string.substring(4, string.length());
	        		mapping = mapping.exclude(firstChar + rest);
	        	}		          
	      	}
		});
	}

	private void updateFieldsToExlude(AccessibleObject ao, List<String> fieldsToExcluded) {

		for (Annotation a : ao.getAnnotations()) {
			
			String name = null;
			if (ao instanceof Method) {
				name = ((Method)ao).getName();
			}
			if (ao instanceof Field) {
				name = ((Field)ao).getName();
			}
			
			if (a instanceof NotMapper) {
				fieldsToExcluded.add(name);
			}
   	 	}
		
	}
	
	private List<Class> findMyTypes(String basePackage) throws IOException, ClassNotFoundException
	{
	    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	    MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

	    List<Class> candidates = new ArrayList<Class>();
	    String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
	                               resolveBasePackage(basePackage) + "/" + "**/*.class";
	    Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
	    for (Resource resource : resources) {
	        if (resource.isReadable()) {
	            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
	            if (isCandidate(metadataReader)) {
	                candidates.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
	            }
	        }
	    }
	    return candidates;
	}

	private String resolveBasePackage(String basePackage) {
	    return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}

	private boolean isCandidate(MetadataReader metadataReader) throws ClassNotFoundException
	{
	    try {
	        Class c = Class.forName(metadataReader.getClassMetadata().getClassName());
	        if (c.getAnnotation(Entity.class) != null) {
	            return true;
	        }
	    }
	    catch(Throwable e){
	    }
	    return false;
	}

}
