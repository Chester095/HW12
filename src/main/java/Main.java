public class Main {
    static final int size = 10000000;
    static float[] arrThreads = new float[size];


    /*** однопоточный метод обработки массива
     *
     */
    static void Array1() {
        float[] arr = new float[size];

        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        double summ = 0;
        long time = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            summ += arr[i];
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Поток " + Thread.currentThread().getName() + " завершил свою работу и выполнил задачу за " + time + " мс    Сумма " + summ);
    }

    static class MyThread extends Thread {
        private float[] arr;
        private int firstPos = 0;

        public MyThread(float[] arr, int firstPos) {
            this.arr = arr;
            this.firstPos = firstPos;
        }

        @Override
        public void run() {
            long time = System.currentTimeMillis();
            double summ = 0;
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + (i + firstPos) / 5) * Math.cos(0.2f + (i + firstPos) / 5) * Math.cos(0.4f + (i + firstPos) / 2));
                summ += arr[i];
            }
            time = System.currentTimeMillis() - time;
            System.arraycopy(arr, 0, arrThreads, firstPos, arr.length);
            System.out.println("Поток " + Thread.currentThread().getName() + " посчитался и добавил данные в общий массив за " + time + " мс    Сумма " + summ);
        }
    }

    public static void main(String[] args) {

        Array1();
        System.out.println();
        //заполняем массив
        for (int i = 0; i < size; i++) {
            arrThreads[i] = 1;
        }
        int quantityOfThreads = 5;
        int firstPos = 0, lastPos = 0;
        Thread[] myThreads = new Thread[quantityOfThreads];
        long time = System.currentTimeMillis(); //засекаем время разбивки массива на quantityOfThreads частей
        for (int i = 0; i < quantityOfThreads; i++) {
            lastPos = (arrThreads.length / quantityOfThreads + firstPos) - 1;
            float[] arr1 = new float[lastPos - firstPos + 1];
            System.arraycopy(arrThreads, firstPos, arr1, 0, lastPos - firstPos + 1);
            myThreads[i] = new Thread(new MyThread(arr1, firstPos), "№" + (i + 1));
            firstPos = lastPos + 1;
            myThreads[i].start();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Время разбивки массива на " + quantityOfThreads + " части = " + time + " мс");

        //проверка по сумме
        for (Thread myThread : myThreads) {
            try {
                myThread.join();
            } catch (InterruptedException ex) {
                System.out.println("Выполнение было прервано.");
            }
        }
        double summ = 0;
        for (int i = 0; i < size; i++) {
            summ += arrThreads[i];
        }
        System.out.println("Сумма элементов массива = " + summ);

        System.out.printf("Поток %s завершил свою работу ...\n", Thread.currentThread().getName());

    }
}
