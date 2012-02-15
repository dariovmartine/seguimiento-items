package edu.unlp.informatica.postgrado.seguimiento.configuracionitem;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import sun.reflect.ReflectionFactory;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionEstado;
import edu.unlp.informatica.postgrado.seguimiento.item.model.ConfiguracionItem;
import edu.unlp.informatica.postgrado.seguimiento.item.model.Estado;

@RunWith(JUnit4.class)
public class ConfiguracionItemTest {

  	@Test
	public void canChange() {
  		
  		ConfiguracionItem c = new ConfiguracionItem();
  		ConfiguracionEstado e = new ConfiguracionEstado();
  		
  		Estado actual = new Estado();
  		actual.setNombre("actual");
  		Estado nuevo = new Estado();
  		nuevo.setNombre("nuevo");
  		
  		e.setEstado(actual);
  		e.getProximosEstados().add(nuevo);
  		c.getProximosEstados().put(actual, e);
  		
  		assertTrue("Tendría que poder pasar de un estado al otro ", c.canChangeState(actual, nuevo));
  	}
  	
  	@Test
	public void cantChange() {
  		
  		ConfiguracionItem c = new ConfiguracionItem();
  		ConfiguracionEstado e = new ConfiguracionEstado();
  		
  		Estado actual = new Estado();
  		actual.setNombre("actual");
  		Estado nuevo = new Estado();
  		nuevo.setNombre("nuevo");
  		Estado invalido = new Estado();
  		invalido.setNombre("nuevo2");
  		
  		e.setEstado(actual);
  		e.getProximosEstados().add(nuevo);
  		c.getProximosEstados().put(actual, e);
  		
  		assertFalse("NO tendría que poder pasar de un estado al otro ", c.canChangeState(actual, invalido));
 	}
  	
  	@Test
	public void cantChangeBecauseNull() {
  		
  		ConfiguracionItem c = new ConfiguracionItem();
  		  		
  		Estado estado = new Estado();
  		estado.setNombre("nuevo");
  		  		 		
  		assertFalse("NO tendría que poder pasar de un estado al otro porque está en null", c.canChangeState(estado, estado));
 	}
  	
  	private <T extends Object, Y extends Object> void copyFields(T from, Y too) {
  		copyFields(from, too, new HashMap<Object, Object>());
  	}
  	
  	private <T extends Object, Y extends Object> void copyFields(T from, Y too, Map<Object,Object> converted) {

  	    Class<? extends Object> fromClass = from.getClass();
  	    Field[] fromFields = fromClass.getDeclaredFields();

  	    Class<? extends Object> tooClass = too.getClass();
  	    Field[] tooFields = tooClass.getDeclaredFields();

  	    converted.put(from, too);
  	    if (fromFields != null && tooFields != null) {
  	        for (Field tooF : tooFields) {
  	            
  	            try {
  	                // Check if that fields exists in the other method
  	                Field fromF = fromClass.getDeclaredField(tooF.getName());
  	                if (fromF.getType().equals(tooF.getType())) {
  	                	if (! tooF.isAccessible()) {
  	                		tooF.setAccessible(true);
  	                	}
  	                	Object value = fromF.get(from);
  	                	if (value != null) {
  	                		if (value.getClass().getPackage().getName().indexOf("java.lang") < 0) {
  	                			if (converted.get(value) == null) {
  	                				ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
  	                        		Constructor objDef = Object.class.getDeclaredConstructor();
  	                        		Constructor intConstr = rf.newConstructorForSerialization(value.getClass(), objDef);
  	                        		Object value2 = value.getClass().cast(intConstr.newInstance());
  	                				copyFields(value,value2,converted);
  	                				tooF.set(too, value2);
  	                			} else {
  	                				tooF.set(too, converted.get(value));
  	                			}
  	                		} else {
  	                			tooF.set(too, value);
  	                		}
  	                	}
  	                }
  	            } catch (Exception e) {
  	                e.printStackTrace();
  	            } 
  	        }
  	    }
  	}
  	
  	@Test
	public void unTest() {
  		
  		A a = new A();
  		a.setNombre("Dario");
  		R r = new R();
  		a.setR(r);
  		
  		A b = new A();
  		copyFields(a, b);
  		Assert.assertTrue(b.getNombre().equals(a.getNombre()));
  		Assert.assertTrue(b.getR().equals(a.getR()));
  		Assert.assertTrue(b.getR().getA().equals(a.getR().getA()));
  	}
  	
  	class A {
  		
  		String nombre;
  		
  		R r;

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public R getR() {
			return r;
		}

		public void setR(R r) {
			this.r = r;
			r.setA(this);
		}

		

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			A other = (A) obj;
		
			if (nombre == null) {
				if (other.nombre != null)
					return false;
			} else if (!nombre.equals(other.nombre))
				return false;
			if (r == null) {
				if (other.r != null)
					return false;
			} else if (!r.equals(other.r))
				return false;
			return true;
		}

	
		
  	}
  	
  	class R {
  		
  		A a;
  		
  		String apellido;

		public A getA() {
			return a;
		}

		public void setA(A a) {
			this.a = a;
		}

		public String getApellido() {
			return apellido;
		}

		public void setApellido(String apellido) {
			this.apellido = apellido;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			R other = (R) obj;
			
			if (apellido == null) {
				if (other.apellido != null)
					return false;
			} else if (!apellido.equals(other.apellido))
				return false;
			return true;
		}

		
  		
  	}
}
