package dragonkk.rs2rsps.rscache;

import dragonkk.rs2rsps.events.Task;

import static dragonkk.rs2rsps.Server.getEntityExecutor;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        getEntityExecutor().scheduleAtFixedRate(new Task() {
            @Override
            public void run() {
                String crash = null;
                assert false;
                if (crash.equals(null)) {
                }
            }
        }, 0, 1000);

    }

}
