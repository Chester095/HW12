public class Main {
    static final int size = 10000000;
    static final int half = size / 2;


    static void Array1() {
        float[] arr = new float[size];

        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long time = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Время выполнения задачи №4: " + time + " мс");
    }

    static void Array2() {
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
    }

    static class MyThread extends Thread {

        private float[] arr = new float[size];
        private String to;

        public MyThread(float[] arr) {
            this.arr[] = arr;
        }

        @Override
        public void run() {
            long time = System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            time = System.currentTimeMillis() - time;
            System.out.println("hello " + to);
        }
    }

    public static void main(String[] args) {
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        new MyThread(arr[size]).start();
    }

}
