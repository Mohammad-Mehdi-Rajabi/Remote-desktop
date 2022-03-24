package MousePosition;

public class ReadMousePosition implements Runnable{


    private double xCordinate;
    private double yCordinate;

    public ReadMousePosition(double xCordinate, double yCordinate) {
        this.xCordinate = xCordinate;
        this.yCordinate = yCordinate;
    }

    @Override
    public void run() {


        //send to finalImage class


    }
}
