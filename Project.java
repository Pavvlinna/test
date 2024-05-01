

import java.util.Stack;

public class Project extends Application {

    private static final int NUM_DISKS = 5;
    private static final int DISK_WIDTH = 30;
    private static final int INITIAL_PEG_WIDTH = 10;

    private final HanoiTower hanoiTower = new HanoiTower(NUM_DISKS);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ханойски кули");

        HBox layout = new HBox(50);
        layout.setStyle("-fx-background-color: #DDDDDD; -fx-padding: 10;");

        PegView pegA = new PegView("A", INITIAL_PEG_WIDTH);
        PegView pegB = new PegView("B", INITIAL_PEG_WIDTH);
        PegView pegC = new PegView("C", INITIAL_PEG_WIDTH);

        // Инициализиране на първата кула с дискове
        for (int i = NUM_DISKS; i > 0; i--) {
            DiskView disk = new DiskView(i);
            pegA.addDisk(disk);
        }

        // Добавяне на кулите към интерфейса
        layout.getChildren().addAll(pegA, pegB, pegC);

        // Добавяне на бутон за стартиране на алгоритъма
        Button startButton = new Button("Стартирай Ханойски кули");
        startButton.setOnAction(event -> runHanoiAlgorithm(pegA, pegB, pegC, NUM_DISKS));
        layout.getChildren().add(startButton);

        // Създаване на сцена
        Scene scene = new Scene(layout, 400, 300);

        // Задаване на сцената
        primaryStage.setScene(scene);

        // Показване на прозореца
        primaryStage.show();
    }

    private void runHanoiAlgorithm(PegView source, PegView auxiliary, PegView target, int numDisks) {
        // Извикване на рекурсивната функция за Ханойските кули
        hanoiAlgorithm(numDisks, source, auxiliary, target);
    }

    private void hanoiAlgorithm(int n, PegView source, PegView auxiliary, PegView target) {
        if (n > 0) {
            // Прехвърляне на n-1 диска от източната кула на втората
            hanoiAlgorithm(n - 1, source, target, auxiliary);

            // Прехвърляне на един диск от източната кула на целевата
            DiskView movedDisk = source.removeTopDisk();
            target.addDisk(movedDisk);

            // Пауза между стъпките за анимационен ефект
            pause();

            // Прехвърляне на n-1 диска от втората кула на целевата
            hanoiAlgorithm(n - 1, auxiliary, source, target);
        }
    }

    private void pause() {
        try {
            Thread.sleep(1000); // Пауза от 1 секунда
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class HanoiTower {

        private final Stack<DiskView> sourcePeg;
        private final Stack<DiskView> auxiliaryPeg;
        private final Stack<DiskView> targetPeg;

        public HanoiTower(int numDisks) {
            sourcePeg = new Stack<>();
            auxiliaryPeg = new Stack<>();
            targetPeg = new Stack<>();
        }

        public Stack<DiskView> getSourcePeg() {
            return sourcePeg;
        }

        public Stack<DiskView> getAuxiliaryPeg() {
            return auxiliaryPeg;
        }

        public Stack<DiskView> getTargetPeg() {
            return targetPeg;
        }
    }

    private static class PegView extends HBox {

        private final String name;
        private final Stack<DiskView> disks = new Stack<>();

        public PegView(String name, double width) {
            this.name = name;
            setStyle("-fx-border-color: black; -fx-padding: 10; -fx-background-color: #EEEEEE;");
            setMinWidth(width);
        }

        public void addDisk(DiskView disk) {
            disks.push(disk);
            getChildren().add(disk);
        }

        public DiskView removeTopDisk() {
            if (!disks.isEmpty()) {
                DiskView topDisk = disks.pop();
                getChildren().remove(topDisk);
                return topDisk;
            }
            return null;
        }

        public String getName() {
            return name;
        }
    }

    private static class DiskView extends Rectangle {

        public DiskView(int size) {
            super(size * DISK_WIDTH, 20);
            setFill(Color.BLUE);
            setStroke(Color.BLACK);
        }
    }
}
