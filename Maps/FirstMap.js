(function(name,data){
 if(typeof onTileMapLoaded === 'undefined') {
  if(typeof TileMaps === 'undefined') TileMaps = {};
  TileMaps[name] = data;
 } else {
  onTileMapLoaded(name,data);
 }
 if(typeof module === 'object' && module && module.exports) {
  module.exports = data;
 }})("FirstMap",
{ "height":10,
 "layers":[
        {
         "data":[97, 90, 90, 90, 90, 90, 90, 90, 90, 98, 79, 75, 75, 75, 75, 75, 75, 75, 75, 80, 79, 75, 75, 75, 75, 75, 75, 75, 75, 80, 79, 75, 75, 75, 75, 75, 75, 75, 75, 80, 79, 75, 75, 75, 75, 75, 75, 75, 75, 80, 79, 75, 75, 75, 75, 75, 75, 75, 75, 80, 79, 75, 75, 75, 75, 75, 75, 75, 75, 80, 79, 75, 75, 75, 75, 75, 75, 75, 75, 80, 79, 75, 75, 75, 75, 75, 75, 75, 75, 80, 99, 119, 119, 100, 75, 75, 99, 119, 119, 100],
         "height":10,
         "name":"Calque de Tile 1",
         "opacity":1,
         "type":"tilelayer",
         "visible":true,
         "width":10,
         "x":0,
         "y":0
        }, 
        {
         "data":[0, 0, 194, 0, 0, 0, 0, 194, 0, 0, 0, 249, 0, 0, 249, 249, 0, 0, 249, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 0, 0, 0, 0, 0, 0, 249, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 0, 0, 0, 0, 0, 0, 249, 0, 0, 0, 0, 249, 0, 0, 249, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
         "height":10,
         "name":"Calque 2",
         "opacity":1,
         "type":"tilelayer",
         "visible":true,
         "width":10,
         "x":0,
         "y":0
        }],
 "nextobjectid":1,
 "orientation":"orthogonal",
 "renderorder":"right-down",
 "tileheight":64,
 "tilesets":[
        {
         "firstgid":1,
         "source":"FinalTileSet.tsx"
        }],
 "tilewidth":64,
 "type":"map",
 "version":"2017.05.16",
 "width":10
});