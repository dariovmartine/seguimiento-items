package edu.unlp.informatica.postgrado.seguimiento.item.mapper;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.PostPersist;

import org.dozer.DozerBeanMapper;
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
import edu.unlp.informatica.postgrado.seguimiento.item.repository.AbstractRepository;
import edu.unlp.informatica.postgrado.seguimiento.item.service.AbstractService;

@Service
@Scope(SCOPE_PROTOTYPE) 
public class DefaultDozerBeanMapper<E extends Numerable,S extends AbstractService<E ,? extends AbstractRepository<E,? extends Serializable>>> extends DozerBeanMapper {
			
	private Map<Class<E>, DefaultBeanMappingBuilder<E>> mapperBuilders = new HashMap<Class<E>, DefaultBeanMappingBuilder<E>>();
	
	S s;
	
	private void initialize() {
		
		try {
			for (Class<E> c : findMyTypes("edu.unlp.informatica.postgrado.seguimiento.item.model")) {
		
				final List<String> removeList = new ArrayList<String>();
				
				final String addList[] = new String[c.getDeclaredFields().length];
				
				// Exluyo todos los methodos de la clase
				for(Method m : c.getDeclaredMethods()){
					
					removeList.add(m.getName());
				}	
				
				// Incorporo en orden los fiels usando solo los atributos que tienen 
				// la anotacion: @MappingOptions
				for(Field f : c.getDeclaredFields()){

					configureField(f, addList);
				}
				
				
				mapperBuilders.put(c, new DefaultBeanMappingBuilder(c, Arrays.asList(addList), removeList));
			}
			
			for (Class<E> clazz: mapperBuilders.keySet()) {
				addMapping(mapperBuilders.get(clazz));	
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
		
	private void configureField(Field f, String[] addList) {

		String temp = null;
		for (Annotation a : f.getAnnotations()) {
			
			if (a instanceof MappingOptions) {
				MappingOptions ma = (MappingOptions) a;
				
				for (Class clazz : ma.exclude()) {
					
					if (clazz == s.getClass()) {
						return;
					}
				} 
				if (ma.order() == -1) {
					temp = f.getName();
				} else if (ma.order() < addList.length) {
					addList[ma.order()] = f.getName();
				} else {
					throw new IllegalArgumentException("El orden es mayor que el numero de atributos de la clase");
				}
			}
   	 	}
		
		// Lleno los espacios vacios del array con los que no tenian orden
		if (temp != null) {
			for (int i=0; i < addList.length; i++) {
				if (addList[i] == null) {
					addList[i] = temp;
					return;	
				}
			}
		}
	}
	
	class DefaultBeanMappingBuilder<E> extends BeanMappingBuilder {
			
		private Class<E> clazz;
		
		private List<String> addList;
		
		private List<String> removeList;
				
		private TypeMappingBuilder mapping;
		
		public DefaultBeanMappingBuilder(Class<E> c, List<String> addList, 
				List<String> fieldsToExcluded) {
			
			this.clazz = c;
			this.addList = addList;			
			this.removeList = fieldsToExcluded;
		}
							
		protected void configure() {
					
			mapping = mapping(clazz, clazz);
									
			// este orden es importante
			for (String string : addList) {
				if (string != null) {
					mapping = mapping.fields(string,string);
				}
			}

			String field;
			for (String string : removeList) {
    		
				if ("get".equals(string.substring(0, 3)) || "set".equals(string.substring(0, 3)) ) {
					String firstChar = string.substring(3, 4).toLowerCase();
					String rest = string.substring(4, string.length());
					field = firstChar + rest;
				} else {
					field = string;
				}
				
				if (!addList.contains(field)) {
					mapping = mapping.exclude(field);
				}
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

	public void setS(AbstractService<? extends Numerable, ? extends AbstractRepository<?, ? extends Serializable>> s) {
		this.s = (S) s;
		initialize();
	}
}
