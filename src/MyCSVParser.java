import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Angus on 12/11/2017.
 */
public class MyCSVParser {
    public static ArrayList<String[]> parse(String csvName) {
        ArrayList<String[]> output = new ArrayList<>();
        try{
            String line;
            BufferedReader br = new BufferedReader(new FileReader(csvName));
            while((line=br.readLine())!=null) {
                String[] strLine = line.split(",");
                output.add(strLine);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    //data format must be as shown in testCSV
    public static List<List<Double>> getX(List<String[]> data) {
        if(data.size() > 0 && data.get(0) != null && data.get(0).length > 0) {
            List<List<Double>> X = new ArrayList<>(data.size());
            for(int i = 0; i < data.size(); i++) {
                X.add(new ArrayList<>());
                for(int j = 0; j < data.get(i).length-1; j++) {
                    X.get(i).add(j,Double.parseDouble(data.get(i)[j]));
                }
            }
            return X;
        } else {
            return null;
        }
    }

    public static int[] getT(ArrayList<String[]> data) {
        if(data.size() > 0 && data.get(0) != null && data.get(0).length > 0) {
            int[] ts = new int[data.size()-1];
            for(int i = 1; i < data.size(); i++) {
                ts[i-1] = Integer.parseInt(data.get(i)[data.get(0).length-1]);
            }
            return ts;
        } else {
            return null;
        }
    }


    public static int[][] convertT(int[] ts) {
        Arrays.sort(ts);
        int[][] newTs = new int[ts.length][ts[ts.length-1]+1];
        for(int i = 0; i < ts.length; i++) {
            newTs[i][ts[i]] = 1;
        }
        return newTs;
    }

}
