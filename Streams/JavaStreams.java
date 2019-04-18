package javastreams;

import java.lang.String;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;

public class JavaStreams {
    
    public static void main(String[] args) throws IOException {
        //1. Integer Stream
        IntStream.range(1, 10).forEach(System.out::print);
        System.out.println();//output: 123456789
        
        //2. Integer Stream with skip
        IntStream.range(1, 10).skip(5).forEach(x -> System.out.println(x));
        System.out.println();// output: 6, 7, 8, 9
        
        //3. Integer Stream with sum
        System.out.println(IntStream.range(1, 5).sum());
        System.out.println(); // output: 10 (= 1+2+3+4)
        
        //4. Stream.of, sorted, and findFirst
        Stream.of("Ava", "Aneri", "Alberto").sorted()
                .findFirst()
                .ifPresent(System.out::println);//output: Alberto
        
        //5. Stream from Array, sort, filter, and print
        String[] names = {"Al", "Ankit", "Kushal", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah"};
        Arrays.stream(names) // same as Stream.of
                .filter(x -> x.startsWith("S"))
                .sorted().forEach(System.out::println); //output: Sarah Sarika Shivika
        
        //6. Average of squares of an int array
        Arrays.stream(new int[] {2, 4, 6, 8, 10})
                .map(x -> x * x).average().ifPresent(System.out::println);// output: 44.0
        
        //7. Stream from List, filter, print
        List<String> people = Arrays.asList("Al", "Ankit", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah");
        people.stream().map(String::toLowerCase)
                .filter(x -> x.startsWith("a")).forEach(System.out::println); //output: al ankit amanda
    
        //8. Stream rows from text file, sort, filter, and print
        Stream<String> bands = Files.lines(Paths.get("src/bands.txt"));
        bands.sorted().filter(x -> x.length() > 13).forEach(System.out::println);
        bands.close();// output: Jackson Browne
                               //Mumford and Sons
                               //Rolling Stones
                          
        //9. Stream rows from text file and save to List
        List<String> bands2 = Files.lines(Paths.get("src/bands.txt"))
              .filter(x -> x.contains("jit")).collect(Collectors.toList());
        bands2.forEach(x -> System.out.println(x)); //output: Arijit Singh
        
        //10. Stream rows from CSV file and count
        Stream<String> rows1 = Files.lines(Paths.get("src/data.txt"));
        int rowCount = (int)rows1.map(x -> x.split(",")).filter(x -> x.length == 3).count();
        System.out.println(rowCount); // output : 5
        rows1.close();
        
        //11. Stream rows from CSV file, parse data from rows
        Stream<String> rows2 =  Files.lines(Paths.get("src/data.txt"));
        rows2.map(x -> x.split(","))
            .filter(x -> x.length == 3)
            .filter(x -> Integer.parseInt(x[1]) > 15)
            .forEach(x -> System.out.println(x[0]+ " " + x[1]+ " " + x[2]));
        rows2.close();// output: B 17 2.8 
                            //   D 23 2.7
                            //   F 18 3.4
        
        //12. Stream row from CSV file, store fields in HashMap
        Stream<String> rows3 = Files.lines(Paths.get("src/data.txt"));
        Map<String, Integer> map = new HashMap<>();
        map = rows3
                .map(x -> x.split(","))
                .filter(x -> x.length == 3)
                .filter(x -> Integer.parseInt(x[1]) > 15)
                .collect(Collectors.toMap(x -> x[0], x -> Integer.parseInt(x[1])));
        rows3.close();
        for(String key : map.keySet()){
            System.out.println(key + " " + map.get(key));
        }                                                 // output: B 17
                                                                //   D 23
                                                                //   F 18
            
        //13. Reduction - sum
        double total = Stream.of(7.3, 1.5, 4.8).reduce(0.0, (Double a, Double b) -> a + b);
        System.out.println(total); // output: 13.600000000000001
        
        //14. Reduction -  summary statistics 
        IntSummaryStatistics summary = IntStream.of(7, 2, 19, 88, 73, 4, 10).summaryStatistics();
        System.out.println(summary); //output : IntSummaryStatistics{count=7, sum=203, min=2, average=29.000000, max=88} 
        
        }
       
}
