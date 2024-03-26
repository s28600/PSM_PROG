package C03;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        double a = Math.toRadians(45);
        double w = 0, t = 0, dt = 0.004, l = 1, g = -9.81;

        System.out.print("""
                Would you like to provide initial data yourself?
                Otherwise standard configuration will be used.
                Please enter (y/n):\s""");
        if (scanner.next().equals("y")){
            System.out.print("Enter starting degree: ");
            a = Math.toRadians(Double.parseDouble(scanner.next()));
            System.out.print("Enter length of pendulum in meters: ");
            l = Double.parseDouble(scanner.next());
            System.out.println("Data accepted.");
        } else System.out.println("Standard configuration will be used.");

        List<String> euler = euler(a, w, t, dt, l, g);

        writeDataToFile(euler);
    }

    public static List<String> euler(
            Double a, //starting degree
            Double w, //starting rotational speed
            Double t,
            Double dt,
            Double l,
            Double g
    ){
        List<String> data = new ArrayList<>();
        data.add(t+","+a+","+w+"\n");

        while(t<20) {
            t += dt;
            double e = g/l*Math.sin(a); //rotational acceleration
            a += w*dt;
            w += e*dt;

            data.add(t+","+a+","+w+"\n");
        }

        return data;
    }

    public static void writeDataToFile(List<String> data) throws IOException {
        Path outputFilePath = Paths.get("data.csv");
        Files.deleteIfExists(outputFilePath);
        Files.createFile(outputFilePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv", true));

        for (String str : data) {
            writer.append(str);
        }

        writer.close();
        System.out.print("Trajectory data was written to file, path: " + outputFilePath.toAbsolutePath());
    }
}