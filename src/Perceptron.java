import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angus on 12/12/2017.
 */
public class Perceptron {
    double eta;
    int gens;
    List<Double> ws;
    ArrayList<Integer> errors;

    public Perceptron() {
        eta = 0.01;
        gens = 50;
    }

    public Perceptron(double eta, int gens) {
        this.eta = eta;
        this.gens = gens;
    }

    private List<Double> add(List<Double> a, List<Double> b) {
        if(a.size() == b.size()) {
            List<Double> result = new ArrayList<>();
            for(int i = 0; i < a.size(); i++) {
                result.add(a.get(i) + b.get(i));
            }
            return result;
        } else {
            System.out.println("Error! Vectors of different lengths cannot be added");
            return null;
        }
    }

    private List<Double> multiply(List<Double> a, double b) {
        List<Double> result = new ArrayList<>();
        for(Double d : a) {
            result.add(d*b);
        }
        return result;
    }

    private Double dot(List<Double> a, List<Double> b) {
        Double result = 0.0;
        for(int i = 0; i < a.size(); i++) {
            result += a.get(i)*b.get(i);
        }
        return result;
    }

    public void train(List<List<Double>> X, int[] y) {
        ws = new ArrayList<>(X.size() + 1);
        ws.add(0.0);
        for(int i = 0; i < X.get(0).size(); i++) {
            ws.add(0.0);
        }
        System.out.println("ws size: " + ws.size());
        errors = new ArrayList<>();
        for(int g = 0; g < gens; g++) {
            int errorCount = 0;
            for(int i = 0; i < X.size(); i++) {
                Double update = eta * (y[i] - predict(X.get(i)));
                ws = add(ws.subList(1,ws.size()),multiply(X.get(i),update));
                List<Double> ws0 = new ArrayList<>();
                ws0.add(ws.get(0) + update);
                ws0.addAll(ws);
                ws = ws0;
                if(update!=0) {
                    errorCount++;
                }
            }
            errors.add(errorCount);
        }
    }

    private Double netInput(List<Double> X) {
        return dot(X,ws.subList(1,ws.size()));
    }

    public int predict(List<Double> X) {
        if(netInput(X)>=0.0) {
            return 1;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        List<String[]> parsed = MyCSVParser.parse("src/iris.data");
        List<List<Double>> X = MyCSVParser.getX(parsed);
        X = X.subList(0,100);
        for(int i = 0; i < X.size(); i++) {
            List<Double> temp = new ArrayList<>();
            temp.add(X.get(i).get(0));
            temp.add(X.get(i).get(2));
            X.set(i, temp);
        }
        System.out.println(X.toString());
        Perceptron p = new Perceptron();
        int[] y = new int[X.size()];
        for(int i = 0; i < 100; i++) {
            if(i > 49) {
                y[i] = 1;
            } else {
                y[i] = -1;
            }
        }
        p.train(X, y);
        for(List<Double> x : X) {
            System.out.println(p.predict(x));
        }
    }

}
