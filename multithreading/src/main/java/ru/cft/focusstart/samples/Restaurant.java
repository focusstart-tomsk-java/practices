package ru.cft.focusstart.samples;

class Meal {
    private final int orderNum;

    Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal " + orderNum;
    }
}

class Client implements Runnable {
    private Restaurant restaurant;

    Client(Restaurant r) {
        restaurant = r;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.getMeal() == null) {
                        wait();
                    }
                }

                System.out.println("Client got " + restaurant.getMeal());

                synchronized (restaurant.getCook()) {
                    restaurant.setMeal(null);
                    restaurant.getCook().notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Client interrupted");
        }
    }
}

class Cook implements Runnable {
    private Restaurant restaurant;
    private int count = 0;

    Cook(Restaurant r) {
        restaurant = r;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.getMeal() != null) {
                        wait();
                    }
                }

                System.out.println("Order up!");
                synchronized (restaurant.getClient()) {
                    restaurant.setMeal(new Meal(++count));
                    restaurant.getClient().notifyAll();
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}

public class Restaurant {
    private Meal meal;
    private final Client client = new Client(this);
    private final Cook cook = new Cook(this);

    private Restaurant() throws InterruptedException {
        Thread clientThread = new Thread(client);
        Thread cookThread = new Thread(cook);
        clientThread.start();
        cookThread.start();

        Thread.sleep(2000);
        clientThread.interrupt();
        cookThread.interrupt();
    }

    Client getClient() {
        return client;
    }

    Cook getCook() {
        return cook;
    }

    void setMeal(Meal meal) {
        this.meal = meal;
    }

    Meal getMeal() {
        return meal;
    }

    public static void main(String[] args) throws InterruptedException {
        new Restaurant();
    }
}
