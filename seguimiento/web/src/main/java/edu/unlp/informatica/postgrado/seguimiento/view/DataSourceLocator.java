/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.unlp.informatica.postgrado.seguimiento.view;

import edu.unlp.informatica.postgrado.seguimiento.item.service.EstadoService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.ItemService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.PrioridadService;
import edu.unlp.informatica.postgrado.seguimiento.item.service.TipoItemService;

/**
 * Service locator class for contacts database
 * 
 * @author dariovmartine
 * 
 */
public class DataSourceLocator
{
	private ItemService itemService;

	private EstadoService estadoService;

	private TipoItemService tipoItemService;

	private PrioridadService prioridadService;
		
	private static DataSourceLocator dataSource;
	
	public DataSourceLocator() {
		super();
		dataSource = this;
	}
	/**
	 * @return contacts database
	 */
	public static DataSourceLocator getInstance()
	{
		return dataSource;
	}
	
	/**
	 * @return the itemService
	 */
	public ItemService getItemService() {
		return itemService;
	}

	/**
	 * @param itemService the itemService to set
	 */
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * @return the estadoService
	 */
	public EstadoService getEstadoService() {
		return estadoService;
	}

	/**
	 * @param estadoService the estadoService to set
	 */
	public void setEstadoService(EstadoService estadoService) {
		this.estadoService = estadoService;
	}
	
	public TipoItemService getTipoItemService() {
		// TODO Auto-generated method stub
		return tipoItemService;
	}
	public void setTipoItemService(TipoItemService tipoItemService) {
		this.tipoItemService = tipoItemService;
	}
	public PrioridadService getPrioridadService() {
		// TODO Auto-generated method stub
		return prioridadService;
	}
	public void setPrioridadService(PrioridadService prioridadService) {
		this.prioridadService = prioridadService;
	}
}
