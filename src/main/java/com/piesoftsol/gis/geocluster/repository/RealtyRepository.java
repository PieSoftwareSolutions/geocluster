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
package com.piesoftsol.gis.geocluster.repository;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.piesoftsol.gis.geocluster.model.Realty;
import com.piesoftsol.gis.geocluster.model.RealtyCluster;

@Repository
@Transactional
public interface RealtyRepository extends CrudRepository<Realty, Long> {

  @Query("select "
  		+ "AVG(LATITUDE) latitude, "
  		+ "AVG(LONGITUDE) longitude, "
  		+ "geohash_prefix, "
  		+ "count(1) quantity "
  		+ "from("
  		+ "select "
  		+ "latitude, "
  		+ "longitude, "
  		+ "SUBSTR(GEOHASH, 1, :precision) geohash_prefix "
  		+ "from TB_CLUSTERDATA "
  		+ "where LATITUDE BETWEEN :southWestLat AND :northEastLat "
  		+ "and LONGITUDE BETWEEN :southWestLon AND :northEastLon) "
  		+ "group by geohash_prefix")
  List<RealtyCluster> findRealtyClustersWithinBounds(
      @Param("southWestLat") double southWestLat,
      @Param("southWestLon") double southWestLon,
      @Param("northEastLat") double northEastLat,
      @Param("northEastLon") double northEastLon,
      @Param("precision") int precision);

}
