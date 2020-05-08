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

public class RealtyCluster {

	@JsonProperty("lat")
	private double latitude;

	@JsonProperty("lng")
	private double longitude;

	@JsonProperty("geohash")
	private String geohashPrefix;

	@JsonProperty("qnt")
	private long quantity;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getGeohashPrefix() {
		return geohashPrefix;
	}

	public void setGeohashPrefix(String geohashPrefix) {
		this.geohashPrefix = geohashPrefix;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

}
