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
package com.piesoftsol.gis.geocluster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.piesoftsol.gis.geocluster.model.RealtyCluster;
import com.piesoftsol.gis.geocluster.repository.RealtyRepository;

@Component
public class RealtyService {

	//private static final String REALTY_PRICE_STDDEV_CACHE_NAME = "realty-price-stddev";

	@Autowired
	private RealtyRepository realtyRepository;
	
	@Autowired
	ZoomToGeohashPrecisionConverter zoomToGeohashPrecisionConverter;
	

	public List<RealtyCluster> findRealtyClustersWithinBounds(double southWestLat, double southWestLon,
			double northEastLat, double northEastLon, double zoom) {
		int precision = zoomToGeohashPrecisionConverter.toGeohashPrecision(zoom);
		return realtyRepository.findRealtyClustersWithinBounds(southWestLat, southWestLon, northEastLat, northEastLon,
				precision);
	}

}
