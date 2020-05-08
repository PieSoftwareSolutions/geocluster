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
package com.piesoftsol.gis.geocluster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piesoftsol.gis.geocluster.model.Coordinates;
import com.piesoftsol.gis.geocluster.model.Realty;
import com.piesoftsol.gis.geocluster.model.RealtyCluster;
import com.piesoftsol.gis.geocluster.model.RealtyClusterResponse;
import com.piesoftsol.gis.geocluster.repository.imp.RecordInsertRepository;
import com.piesoftsol.gis.geocluster.service.RealtyService;

@RestController
public class GeoclusterController {

	@Autowired
	RealtyService realtyService;
	
	@Autowired
	RecordInsertRepository recordInsertRepository;
	
	@GetMapping("/osf/cluster")
	public RealtyClusterResponse findRealtyClustersWithinBounds(@RequestParam("sw_lat") double southWestLat,
			@RequestParam("sw_lng") double southWestLon, @RequestParam("ne_lat") double northEastLat,
			@RequestParam("ne_lng") double northEastLon, @RequestParam("zoom") double zoom) {
		List<RealtyCluster> realty = realtyService.findRealtyClustersWithinBounds(southWestLat, southWestLon,
				northEastLat, northEastLon, zoom);

		return new RealtyClusterResponse(realty);
	}
	
	@GetMapping("/osf/getgeohsah")
	public Realty getGeoHash(@RequestParam("lat") double lat,
			@RequestParam("lng") double lon) {
		
		Realty realty = new Realty();
		realty.setLatitude(lat);
		realty.setLongitude(lon);
		Coordinates coordinates = new Coordinates(lat, lon);
		realty.setGeohash(coordinates.toGeohash(10));
		return realty;
	}
	
	@GetMapping("/osf/addlocation")
	public int putAddLocation(@RequestParam("lat") double lat,
			@RequestParam("lng") double lon) {
		
		Realty realty = new Realty();
		realty.setLatitude(lat);
		realty.setLongitude(lon);
		Coordinates coordinates = new Coordinates(lat, lon);
		realty.setGeohash(coordinates.toGeohash(10));
		return recordInsertRepository.insertWithQuery(realty);
	}
}
