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

public class Coordinates {

	private static class Range {

		double start;
		double end;

		double getMiddle() {
			return (start + end) / 2;
		}

		int divideByValue(double value) {
			double middle = getMiddle();
			if (value >= middle) {
				this.start = middle;
				return 1;
			} else {
				this.end = middle;
				return 0;
			}
		}

		void divideByBit(int bit) {
			double middle = getMiddle();
			if (bit > 0) {
				this.start = middle;
			} else {
				this.end = middle;
			}
		}

		public Range(double start, double end) {
			super();
			this.start = start;
			this.end = end;
		}

	}

	private static final String BASE_32 = "0123456789bcdefghjkmnpqrstuvwxyz";

	private final double latitude;
	private final double longitude;

	public String toGeohash(int precision) {
		Range latRange = new Range(-90.0, 90.0);
		Range lonRange = new Range(-180.0, 180.0);
		boolean isEven = true;
		int bit = 0;
		int base32CharIndex = 0;
		StringBuilder geohash = new StringBuilder();

		while (geohash.length() < precision) {
			if (isEven) {
				base32CharIndex = (base32CharIndex << 1) | lonRange.divideByValue(longitude);
			} else {
				base32CharIndex = (base32CharIndex << 1) | latRange.divideByValue(latitude);
			}

			isEven = !isEven;

			if (bit < 4) {
				bit++;
			} else {
				geohash.append(BASE_32.charAt(base32CharIndex));
				bit = 0;
				base32CharIndex = 0;
			}
		}

		return geohash.toString();
	}

	public static Coordinates fromGeohash(String geohash) {
		Range latRange = new Range(-90.0, 90.0);
		Range lonRange = new Range(-180.0, 180.0);
		boolean isEvenBit = true;

		for (int i = 0; i < geohash.length(); i++) {
			int base32CharIndex = BASE_32.indexOf(geohash.charAt(i));
			for (int j = 4; j >= 0; j--) {
				if (isEvenBit) {
					lonRange.divideByBit((base32CharIndex >> j) & 1);
				} else {
					latRange.divideByBit((base32CharIndex >> j) & 1);
				}
				isEvenBit = !isEvenBit;
			}
		}

		return new Coordinates(latRange.getMiddle(), lonRange.getMiddle());
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public Coordinates(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
}
