var map = L.map('map')
    maxZoom = 18,
    minZoom = 5,
    realtyLayer = L.layerGroup(),
    info = L.control(),
    legend = L.control({position: 'bottomright'});

L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}' + (L.Browser.retina ? '@2x.png' : '.png'), {
  attribution:'&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>, &copy; <a href="https://carto.com/attributions">CARTO</a>',
  subdomains: 'abcd',
  maxZoom: maxZoom,
  minZoom: minZoom
}).addTo(map);

info.onAdd = function (map) {
  this._div = L.DomUtil.create('div', 'info');
  this.update();
  return this._div;
};

info.update = function (cluster) {
  if (cluster) {
    this._div.innerHTML = '<p>Total Count: ' + '<b>' + cluster.qnt + '</b></p>';
  } else {
    this._div.innerHTML = '<p>Hover or tap on a circle</p>';
  }
};

info.addTo(map);
function updateRealtyInfo(e) {
  var layer = e.target;
  info.update(layer.properties.cluster);
}

function resetRealtyInfo(e) {
  info.update();
}

function gradient(position, colorStops) {
  var i, cs1, cs2, c1, c2, r, g, b;
  position = Math.min(Math.max(position, colorStops[0].pos), colorStops[colorStops.length - 1].pos);
  for (i = 0; i < colorStops.length - 1; i++) {
    if (colorStops[i + 1].pos >= position) {
      cs1 = colorStops[i];
      cs2 = colorStops[i + 1];
      c2 = (position - cs1.pos) / (cs2.pos - cs1.pos);
      c1 = 1 - c2;
      r = Math.floor(cs1.r * c1 + cs2.r * c2);
      g = Math.floor(cs1.g * c1 + cs2.g * c2);
      b = Math.floor(cs1.b * c1 + cs2.b * c2);
      return 'rgb(' + [r, g, b].join(',') + ')';
    }
  }
  throw 'Color stops for position ' + position + ' are not found';
}

function clusterColor(position) {
  return gradient(position, [
    {pos: 0, r: 0, g: 255, b: 0},
    {pos: 0.5, r: 255, g: 255, b: 0},
    {pos: 1, r: 255, g: 0, b: 0}
  ]);
}

function priceCoefficient(cluster, stat) {
  var min = Math.max(stat.min, stat.avg - 3 * stat.sd),
      max = Math.min(stat.max, stat.avg + 3 * stat.sd),
      avg = stat.avg,
      p = cluster.avgPrice;
  if (p <= min) {
    return 0;
  } else if (p >= max) {
    return 1;
  } else if (p <= avg) {
    return 0.5 * (p - min) / (avg - min);
  } else if (p > avg) {
    return 1 - 0.5 * (max - p) / (max - avg);
  }
}

function clusterRadius(cluster, min, max) {
  var precision = cluster.geohash.length,
      k = 1 + 2 * (cluster.qnt - min + 1) / (max - min + 1);
  return 5 * Math.pow(2, Math.min(maxZoom - 2 * (precision + 1), 11)) * k;
}

function drawRealty(e) {
  var bounds = map.getBounds(),
      zoom = map.getZoom();

  axios.get('/osf/cluster', {
    params: {
      sw_lat: bounds.getSouthWest().lat,
      sw_lng: bounds.getSouthWest().lng,
      ne_lat: bounds.getNorthEast().lat,
      ne_lng: bounds.getNorthEast().lng,
      zoom: zoom
    }
  })
  .then(function (response) {
    var newRealtyLayer = L.layerGroup(),
        clusters = response.data.cluster;

    if (clusters.length > 0) {
      var quantities = clusters.map(function(cluster) { return cluster.qnt; }),
          min = Math.min.apply(null, quantities),
          max = Math.max.apply(null, quantities);

      clusters.forEach(function (cluster) {
    	  if(cluster.qnt > 1){
            circle = L.circle([cluster.lat, cluster.lng], {
              color: '#405C78',
              weight: 1,
              fillColor: '#FF33FF',
              fillOpacity: 0.5,
              radius: clusterRadius(cluster, min, max)
            })
            .addTo(newRealtyLayer);

            circle.properties = {
              cluster: cluster
            };

            circle.on({
              mouseover: updateRealtyInfo,
              mouseout: resetRealtyInfo
            });
      }else{
    	  marker = L.marker([cluster.lat, cluster.lng], {
              color: '#405C78',
              weight: 1,
              fillColor: '#FF33FF',
              fillOpacity: 0.5
            })
            .addTo(newRealtyLayer);

    	  marker.properties = {
              cluster: cluster
            };
      }
      });
    }

    realtyLayer.clearLayers();
    map.removeLayer(realtyLayer);
    realtyLayer = newRealtyLayer.addTo(map);
  })
  .catch(function (error) {
    console.log(error);
  });
}

map.on('load moveend', drawRealty);

map.setView([28.613514, 77.197646], 6);
