package edu.unlp.informatica.postgrado.seguimiento.item.mapper;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.dozer.DozerBeanMapper;
import org.dozer.DozerEventListener;
import org.dozer.event.DozerEvent;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import edu.unlp.informatica.postgrado.seguimiento.item.model.Numerable;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Proyecto;

@Service
@Scope(SCOPE_PROTOTYPE) 
public class DefaultDozerBeanMapper<E extends Numerable> extends DozerBeanMapper {
			
	private boolean initialized = false;
	
	private Map<Class<E>, DefaultBeanMappingBuilder<E>> mapperBuilders = new HashMap<Class<E>, DefaultBeanMappingBuilder<E>>();
	
	public DefaultDozerBeanMapper() {
		
		super();		
				
		try {
			for (Class<E> c : findMyTypes("edu.unlp.informatica.postgrado.seguimiento.item.model")) {
		
				final List<String> fieldsToExcluded = new ArrayList<String>();
				
				for(Field f : c.getDeclaredFields()){

					updateFieldsToExlude(f, fieldsToExcluded);
				}
				for(Method m : c.getDeclaredMethods()){
				
					updateFieldsToExlude(m, fieldsToExcluded);
				}	
				
				mapperBuilders.put(c, getDefaultBeanMappingBuilder(c, fieldsToExcluded));
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
	
	public boolean isInitialized() {
		return initialized;
	}

	public Map<Class<E>, DefaultBeanMappingBuilder<E>> getMapperBuilders() {
		return mapperBuilders;
	}
		
	DefaultBeanMappingBuilder<E> getDefaultBeanMappingBuilder(final Class<E> c, final List<String> fieldsToExcluded) {
		
		return new DefaultBeanMappingBuilder<E>(c, fieldsToExcluded);
	}
	
	public static class DefaultBeanMappingBuilder<E> extends BeanMappingBuilder {
			
		private Class<E> clazz;
		
		private List<String> fieldsToExcluded;
		
		private TypeMappingBuilder mapping;
		
		public DefaultBeanMappingBuilder(Class<E> c,
				List<String> fieldsToExcluded) {
			this.clazz = c;
			this.fieldsToExcluded = fieldsToExcluded;
			
		}
		
		public List<String> getFieldsToExcluded() {
			return fieldsToExcluded;
		}
				
		protected void configure() {
					
			mapping = mapping(clazz, clazz);
			
			// este orden es importante
			if (clazz.equals(Proyecto.class)) {
				
				mapping.fields("id","id");
				mapping.fields("nombre","nombre");
				mapping.fields("integrantes","integrantes");
				mapping.fields("lider","lider");
				mapping.fields("tipoItems","tipoItems");
				mapping.fields("items","items");
			}

			String field;
			for (String string : fieldsToExcluded) {
    		
				if ("get".equals(string.substring(0, 3)) || "set".equals(string.substring(0, 3)) ) {
					String firstChar = string.substring(3, 4).toLowerCase();
					String rest = string.substring(4, string.length());
					field = firstChar + rest;
				} else {
					field = string;
				}
				mapping = mapping.exclude(field);
			}		          
		}			
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
	
	private List<Class<E>> findMyTypes(String basePackage) throws IOException, ClassNotFoundException
	{
	    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	    MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

	    List<Class<E>> candidates = new ArrayList<Class<E>>();
	    String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
	                               resolveBasePackage(basePackage) + "/" + "**/*.class";
	    Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
	    for (Resource resource : resources) {
	        if (resource.isReadable()) {
	            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
	            if (isCandidate(metadataReader)) {
	                candidates.add((Class<E>)Class.forName(metadataReader.getClassMetadata().getClassName()));
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
	        Class<?> c = Class.forName(metadataReader.getClassMetadata().getClassName());
	        if (c.getAnnotation(Entity.class) != null) {
	            return true;
	        }
	    }
	    catch(Throwable e){
	    }
	    return false;
	}

	public void initialize() {
		
		for (Class<E> clazz: mapperBuilders.keySet()) {
			addMapping(mapperBuilders.get(clazz));	
		}			
		initialized = true;
	}

}
