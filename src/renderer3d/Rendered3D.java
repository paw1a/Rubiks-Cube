package renderer3d;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.util.Duration;
import solver.CubeModel;

public class Rendered3D extends Application {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;

    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    private CubeModel model;
    private SmartGroup root;
    private SmartGroup [][][] groups;

    @Override
    public void start(Stage primaryStage) {

        model = new CubeModel();
        init3D();

        showAlgorithm("R' U2 R' D' R U R' D R U' R' D' R U2 R' D R2");
        //showAlgorithm("R' U' F' R U R' U' R' F R2 U' R' U' R U R' U R");
        //showAlgorithm("B F F D' R R F D B' F D' U F' D' L L F D D U'");
        //showAlgorithm("F R U' R' U' R U R' F' R U R' U' R' F R F'");
        //showAlgorithm("U' D R' U' R U D' R R U R' U R U' R U' R R");

        Camera camera = new PerspectiveCamera();
        camera.setTranslateX(-500);
        camera.setTranslateY(-300);

        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        initMouseControl(root, scene);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W: root.setTranslateZ(root.getTranslateZ() + 100); break;
                case S: root.setTranslateZ(root.getTranslateZ() - 100); break;
                case R: showAlgorithm("R"); break;
                case L: showAlgorithm("L"); break;
                case F: showAlgorithm("F"); break;
                case B: showAlgorithm("B"); break;
                case U: showAlgorithm("U"); break;
                case D: showAlgorithm("D"); break;
            }
        });

        primaryStage.setTitle("3DRCube");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlgorithm(String alg) {
        String [] moves = alg.split(" ");
        SequentialTransition transition = new SequentialTransition();
        for (int i = 0; i < moves.length; i++) {
            if(moves[i].contains("2")) {
                transition.getChildren().addAll(showMove(moves[i].charAt(0)+""));
                transition.getChildren().addAll(showMove(moves[i].charAt(0)+""));
            } else transition.getChildren().addAll(showMove(moves[i]));
        }
        transition.play();
    }

    private Timeline showMove(String move) {
        double angle;
        if(move.contains("'")) angle = -90;
        else if(move.contains("2")) angle = 180;
        else angle = 90;
        if(move.contains("R") || move.contains("B") || move.contains("D")) angle *= -1;

        Point3D point3D = null;
        if(move.contains("R") || move.contains("L")) point3D = Rotate.X_AXIS;
        else if(move.contains("F") || move.contains("B")) point3D = Rotate.Z_AXIS;
        else if(move.contains("U") || move.contains("D")) point3D = Rotate.Y_AXIS;

        SmartGroup[][] side = getSide(move.charAt(0));
        SmartGroup[][] rotated = rotate(side, !move.contains("'"));
        setSide(move.charAt(0), rotated);
        Rotate r = new Rotate(angle, 106, 106, 106, point3D);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                getSide(move.charAt(0))[i][j].getTransforms().add(0, r);
            }
        }
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(r.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(r.angleProperty(), angle))
        );
        return timeline;
    }

    private SmartGroup[][] getSide(char c) {
        SmartGroup[][] side = new SmartGroup[3][3];

        switch (c) {
            case 'R': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        side[i][j] = groups[2][i][j];
                    }
                }
                break;
            }
            case 'L': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        side[i][j] = groups[0][i][2-j];
                    }
                }
                break;
            }
            case 'U': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        side[i][j] = groups[j][0][2-i];
                    }
                }
                break;
            }
            case 'D': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        side[i][j] = groups[j][2][i];
                    }
                }
                break;
            }
            case 'F': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        side[i][j] = groups[j][i][0];
                    }
                }
                break;
            }
            case 'B': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        side[i][j] = groups[2-j][i][2];
                    }
                }
                break;
            }
        }

        return side;
    }

    private void setSide(char c, SmartGroup[][] side) {
        switch (c) {
            case 'R': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        groups[2][i][j] = side[i][j];
                    }
                }
                break;
            }
            case 'L': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        groups[0][i][2-j] = side[i][j];
                    }
                }
                break;
            }
            case 'U': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        groups[j][0][2-i] = side[i][j];
                    }
                }
                break;
            }
            case 'D': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        groups[j][2][i] = side[i][j];
                    }
                }
                break;
            }
            case 'F': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        groups[j][i][0] = side[i][j];
                    }
                }
                break;
            }
            case 'B': {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        groups[2-j][i][2] = side[i][j];
                    }
                }
                break;
            }
        }
    }

    private SmartGroup[][] rotate(SmartGroup[][] side, boolean clock) {
        SmartGroup[][] newSide = new SmartGroup[3][3];

        if(clock) {
            newSide[0][0] = side[2][0];
            newSide[0][1] = side[1][0];
            newSide[0][2] = side[0][0];
            newSide[1][0] = side[2][1];
            newSide[1][1] = side[1][1];
            newSide[1][2] = side[0][1];
            newSide[2][0] = side[2][2];
            newSide[2][1] = side[1][2];
            newSide[2][2] = side[0][2];
        } else {
            newSide[0][0] = side[0][2];
            newSide[0][1] = side[1][2];
            newSide[0][2] = side[2][2];
            newSide[1][0] = side[0][1];
            newSide[1][1] = side[1][1];
            newSide[1][2] = side[2][1];
            newSide[2][0] = side[0][0];
            newSide[2][1] = side[1][0];
            newSide[2][2] = side[2][0];
        }

        return newSide;
    }



    public static void main(String[] args) {
        launch(args);
    }

    private class SmartGroup extends Group {
        Rotate r;
        Transform t = new Rotate();

        void rotateX(int ang) {
            r = new Rotate(ang, Rotate.X_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }

        void rotateY(int ang) {
            r = new Rotate(ang, Rotate.Y_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }

        void rotateZ(int ang) {
            r = new Rotate(ang, Rotate.Z_AXIS);
            t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }
    }

    private void initMouseControl(SmartGroup group, Scene scene) {
        Rotate xRotate;
        Rotate yRotate;

        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );

        xRotate.pivotXProperty().set(100);
        xRotate.pivotYProperty().set(100);
        xRotate.pivotZProperty().set(100);
        yRotate.pivotXProperty().set(100);
        yRotate.pivotYProperty().set(100);
        yRotate.pivotZProperty().set(100);

        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });
    }

    private void init3D() {
        groups = new SmartGroup[3][3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    groups[i][j][k] = new SmartGroup();
                    Box body = new Box(100, 100, 100);
                    body.setMaterial(new PhongMaterial(Color.BLACK.brighter().brighter()));
                    body.setTranslateX(i*100 + i*6);
                    body.setTranslateY(j*100 + j*6);
                    body.setTranslateZ(k*100 + k*6);
                    groups[i][j][k].getChildren().add(body);
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Box box1 = new Box(95, 1, 95);
                box1.setMaterial(new PhongMaterial(Color.YELLOW));
                box1.setTranslateX(groups[i][0][j].getChildren().get(0).getTranslateX());
                box1.setTranslateY(groups[i][0][j].getChildren().get(0).getTranslateY()-50);
                box1.setTranslateZ(groups[i][0][j].getChildren().get(0).getTranslateZ());
                groups[i][0][j].getChildren().add(box1);

                Box box4 = new Box(95, 1, 95);
                box4.setMaterial(new PhongMaterial(Color.WHITE));
                box4.setTranslateX(groups[i][2][j].getChildren().get(0).getTranslateX());
                box4.setTranslateY(groups[i][2][j].getChildren().get(0).getTranslateY()+50);
                box4.setTranslateZ(groups[i][2][j].getChildren().get(0).getTranslateZ());
                groups[i][2][j].getChildren().add(box4);

                Box box2 = new Box(95, 95, 1);
                box2.setMaterial(new PhongMaterial(Color.GREEN));
                box2.setTranslateX(groups[i][j][0].getChildren().get(0).getTranslateX());
                box2.setTranslateY(groups[i][j][0].getChildren().get(0).getTranslateY());
                box2.setTranslateZ(groups[i][j][0].getChildren().get(0).getTranslateZ()-50);
                groups[i][j][0].getChildren().add(box2);

                Box box5 = new Box(95, 95, 1);
                box5.setMaterial(new PhongMaterial(Color.BLUE));
                box5.setTranslateX(groups[i][j][2].getChildren().get(0).getTranslateX());
                box5.setTranslateY(groups[i][j][2].getChildren().get(0).getTranslateY());
                box5.setTranslateZ(groups[i][j][2].getChildren().get(0).getTranslateZ()+50);
                groups[i][j][2].getChildren().add(box5);

                Box box3 = new Box(1, 95, 95);
                box3.setMaterial(new PhongMaterial(Color.ORANGE));
                box3.setTranslateX(groups[2][i][j].getChildren().get(0).getTranslateX()+50);
                box3.setTranslateY(groups[2][i][j].getChildren().get(0).getTranslateY());
                box3.setTranslateZ(groups[2][i][j].getChildren().get(0).getTranslateZ());
                groups[2][i][j].getChildren().add(box3);

                Box box6 = new Box(1, 95, 95);
                box6.setMaterial(new PhongMaterial(Color.RED));
                box6.setTranslateX(groups[0][i][j].getChildren().get(0).getTranslateX()-50);
                box6.setTranslateY(groups[0][i][j].getChildren().get(0).getTranslateY());
                box6.setTranslateZ(groups[0][i][j].getChildren().get(0).getTranslateZ());
                groups[0][i][j].getChildren().add(box6);
            }
        }

        root = new SmartGroup();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    groups[i][j][k].setTranslateX((i*100 + i*6)/100f);
                    groups[i][j][k].setTranslateY((j*100 + j*6)/100f);
                    groups[i][j][k].setTranslateZ((k*100 + k*6)/100f);
                    root.getChildren().add(groups[i][j][k]);
                }
            }
        }
    }
}
