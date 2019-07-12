import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static final double RADIUS_OF_EARTH = 6371;

    /**
     * Calculates the distance between two points
     *
     * @param latitude1  Latitude of first coordinate
     * @param longitude1 Longitude of first coordinate
     * @param latitude2  Latitude of second coordinate
     * @param longitude2 Longitude of second coordinate
     * @return           Distance between the two points, in km
     */
    public static double Haversine(double latitude1, double longitude1, double latitude2, double longitude2) {
        latitude1 = Math.toRadians(latitude1);
        latitude2 = Math.toRadians(latitude2);
        longitude1 = Math.toRadians(longitude1);
        longitude2 = Math.toRadians(longitude2);

        return (2 * RADIUS_OF_EARTH * Math.asin(Math.sqrt(
                Math.sin((latitude2 - latitude1)/2) * Math.sin((latitude2 - latitude1)/2) +
                        Math.cos(latitude1) * Math.cos(latitude2) *
                                Math.sin((longitude2 - longitude1)/2) * Math.sin((longitude2 - longitude1) / 2)
        )));
    }

    /*
    Class to store Airport code strings and latitudes/longitudes
     */
    public static class Airport {
        private String id;
        private double lat;
        private double lon;

        public Airport(String id, double lat, double lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
        }

        public String getId() {
            return this.id;
        }

        public String toString() {
            return this.id + " " + lat + " " + lon;
        }

        public boolean withinTolerance(double currentLat, double currentLon) {
            double tol = Haversine(this.lat,this.lon,currentLat, currentLon);
            return tol > 0.1 && tol < 0.7;
        }
    }
    /*
      Class to store Flight's waypoints and output paths
     */
    public static class Flight {
        //        int numWaypoints;
        LinkedHashSet<String> flightPath;
        ArrayList<Waypoint> waypoints;

        public Flight(ArrayList<Waypoint> waypoints) {
            this.flightPath = new LinkedHashSet<>();
            this.waypoints = waypoints;
        }




    }
    /*
    Class to store flight path waypoints
     */
    public static class Waypoint {
        private double lat;
        private double lon;

        public Waypoint(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }

        public String toString() {
            return this.lat + " " + this. lon;
        }
    }

//    public


    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
//        Scanner scan = new Scanner(System.in);
        Scanner scan = new Scanner(new File("src/input003.txt"));


        ArrayList<Airport> airports = new ArrayList<>();
        ArrayList<Waypoint> waypoints = new ArrayList<>();

        //Airports, Num of Flights, Num of Waypoints at each flight
        ArrayList<Integer> lengths=  new ArrayList<>();
        while (scan.hasNextLine()) {
            String line[] = scan.nextLine().split(" ");
            if (line.length == 1) {
                lengths.add(Integer.parseInt(line[0]));
            }
            if (line.length == 2) {
                waypoints.add(new Waypoint(Double.parseDouble(line[0]), Double.parseDouble(line[1])));
            }
            if (line.length == 3) {
                airports.add(new Airport(line[0], Double.parseDouble(line[1]), Double.parseDouble(line[2])));
            }
        }

//        System.out.println(lengths);
        lengths.remove(0);
        int numFlights = lengths.get(0);
//        System.out.println(numFlights);
        lengths.remove(0);
        System.out.println(lengths);
//        int numFlights = lengths.get(1);



        if(numFlights == 0) {
            return;
        }

        if (numFlights == 1) {
            Set<String> output = new LinkedHashSet<>();

            int flightByMinute = waypoints.size()/60;


            for(int i = 0; i < waypoints.size()-1; i += flightByMinute) {
                for (Airport a: airports) {
                    if(a.withinTolerance(waypoints.get(i).getLat(), waypoints.get(i).getLon())) {
                        output.add(a.getId());
                    }
                }
            }

            for (String s: output) {
                System.out.println(s);
            }
        }
        else {
//            ArrayList<Flight> flights = new ArrayList<>();
//            for(int i = 0)

//            ArrayList<String> outputs = new ArrayList<>();
            Set<Set<String>> outputs = new LinkedHashSet<>();
            int startingIndex = 0;
            for (int i = 0; i < numFlights; i++) {

//                System.out.println(i);
                int numWays = lengths.get(0);
//                System.out.println(numWays);
                lengths.remove(0);

                ArrayList<Waypoint> w = new ArrayList<Waypoint>(waypoints.subList(startingIndex, (numWays)));

                System.out.println(w);
                Set<String> output = new LinkedHashSet<>();

                int flightByMinute = w.size()/60;

                for (int j = 0; j < w.size()-1; j += flightByMinute) {
                    for(Airport a: airports) {
                        if(a.withinTolerance(w.get(j).getLat(), w.get(j).getLon())) {
                            output.add(a.getId());
                        }
                    }
                }
                startingIndex = numWays;
                outputs.add(output);
            }
            System.out.println(outputs);
        }
    }
}
