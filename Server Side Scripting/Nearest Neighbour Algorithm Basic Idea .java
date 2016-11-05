/* If the distance should be lesser than 8 KM */

private double distance(double latitudePos1, double longitudePos1, double latitudePos2, double longitudePos2) {
    double radiusOfEarth = 3958.75; 
    double lat = Math.toRadians(latitudePos2-latitudePos1);
    double lng = Math.toRadians(longitudePos2-longitudePos1)
    double a = Math.pow(Math.sin(lat/2), 2) + Math.pow(Math.sin(lng/2), 2)
        * Math.cos(Math.toRadians(latitudePos1)) * Math.cos(Math.toRadians(latitudePos2));
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    return earthRadius * c;
}