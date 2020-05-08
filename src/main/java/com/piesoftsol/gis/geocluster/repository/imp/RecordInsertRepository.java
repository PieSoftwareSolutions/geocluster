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
package com.piesoftsol.gis.geocluster.repository.imp;

import org.springframework.stereotype.Repository;

import com.piesoftsol.gis.geocluster.model.Realty;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class RecordInsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int  insertWithQuery(Realty realty) {
    	return entityManager.createNativeQuery("INSERT INTO TB_CLUSTERDATA (latitude, longitude, geohash) VALUES (?,?,?)")
	      .setParameter(1, realty.getLatitude())
	      .setParameter(2, realty.getLongitude())
	      .setParameter(3, realty.getGeohash())
	      .executeUpdate();
    }

}
