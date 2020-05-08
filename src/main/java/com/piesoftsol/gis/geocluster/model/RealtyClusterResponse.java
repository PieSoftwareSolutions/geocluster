/*******************************************************************************
 * Copyright 2020 Kiran B
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.piesoftsol.gis.geocluster.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RealtyClusterResponse {

	@JsonProperty("cluster")
	private List<RealtyCluster> realty;

	public List<RealtyCluster> getRealty() {
		return realty;
	}

	public void setRealty(List<RealtyCluster> realty) {
		this.realty = realty;
	}

	public RealtyClusterResponse(List<RealtyCluster> realty) {
		super();
		this.realty = realty;
	}

}
