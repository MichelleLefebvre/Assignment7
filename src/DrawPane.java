// Assignment #: Arizona State University CSE205 #7
//         Name: Your Name
//    StudentID: Your ID
//      Lecture: Your lecture time (e.g., MWF 9:40am)
//  Description: The DrawPane class creates a canvas where we can use
//               mouse key to draw either a Rectangle or a Circle with different
//               colors. We can also use the the two buttons to erase the last
//				 drawn shape or clear them all.

//import any classes necessary here
//----
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

import static javafx.scene.paint.Color.*;

public class DrawPane extends BorderPane
{
    private Button undoBtn, eraseBtn;
    private ComboBox<String> colorCombo;
    private RadioButton rbRect, rbCircle;
    private ArrayList<Shape> shapeList;
    private Pane canvas;
    private Color colorPick;
    private String shape;
    private Rectangle rect = null;
    private Circle circle = null;
    private int shapeCounter = 0;


    //declare any other necessary instance variables here
    //----

    //Constructor
    public DrawPane()
    {
        Border border = new Border(new BorderStroke(BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        Font font = new Font("Arial", 14 );

        //Step #1: initialize each instance variable and set up layout
        undoBtn = new Button("Undo");
        eraseBtn = new Button("Erase");
        undoBtn.setFont(font);
        eraseBtn.setFont(font);
        undoBtn.setMinWidth(80.0);
        eraseBtn.setMinWidth(80.0);

        //Create the color comboBox and intial its default color
        //----
        colorCombo = new ComboBox<>();
        colorCombo.getItems().addAll("Black", "Blue", "Green", "Red",
                "Yellow", "Orange", "Pink");
        colorCombo.setStyle("-fx-font: 14 arial;");
        colorCombo.getSelectionModel().select(0);
        colorCombo.setMinWidth(100.0);

        //Create the two radio buttons and also a ToggleGroup
        //so that the two radio buttons can be selected
        //mutually exclusively. Otherwise they are indepedant of each other
        //----
        ToggleGroup tgroup = new ToggleGroup();
        rbRect = new RadioButton("Rectangle");
        rbRect.setSelected(true);
        rbRect.setMinWidth(100.0);
        rbRect.setFont(font);
        rbRect.setToggleGroup(tgroup);

        rbCircle = new RadioButton(("Circle"));
        rbCircle.setMinWidth(100.0);
        rbCircle.setFont(font);
        rbCircle.setToggleGroup(tgroup);

        //initialize shapeList, it is a data structure we used
        //to track the shape we created
        //----
        shapeList = new ArrayList<>();

        //canvas is a Pane where we will draw rectagles and circles on it
        canvas = new Pane();
        canvas.setStyle("-fx-background-color: beige;");
        //canvas.setMinSize(450, 200);
        canvas.setPadding(new Insets(15, 15,15,10));
        canvas.setBorder(border);

        //initialize the remaining instance variables and set up
        //the layout
        //----
        //----
        colorPick = BLACK;
        shape = "rectangle";
        VBox vb = new VBox();

        vb.setSpacing(35);

        vb.setPadding(new Insets(25, 10, 25, 15));
        vb.getChildren().addAll(colorCombo, rbRect, rbCircle);
        vb.setBorder(border);
        vb.setMinHeight(300);
        vb.setMinWidth(120);

        HBox hb = new HBox();
        hb.setSpacing(35);
        hb.setPadding(new Insets(10,15,15,20));
        hb.getChildren().addAll(undoBtn, eraseBtn);
        hb.setBorder(border);
        hb.setAlignment(Pos.CENTER);

        this.setLeft(vb);
        this.setCenter(canvas);
        this.setBottom(hb);
        this.getBorder();


        //Step #3: Register the source nodes with its handler objects
        ShapeHandler sh = new ShapeHandler();
        ButtonHandler bh = new ButtonHandler();
        ColorHandler ch = new ColorHandler();
        MouseHandler mh = new MouseHandler();
        canvas.setOnMousePressed(mh);
        canvas.setOnMouseDragged(mh);
        canvas.setOnMouseReleased(mh);
        rbCircle.setOnAction(sh);
        rbCircle.setOnAction(sh);
        eraseBtn.setOnAction(bh);
        undoBtn.setOnAction(bh);
        colorCombo.setOnAction(ch);


    }

    //Step #2(A) - MouseHandler
    private class MouseHandler implements EventHandler<MouseEvent>
    {

        public void handle(MouseEvent event)
        {


            //handle MouseEvent here
            //Note: you can use if(event.getEventType()== MouseEvent.MOUSE_PRESSED)
            //to check whether the mouse key is pressed, dragged or released
            //write your own codes here
            //----


            if(shape == "rectangle")
            {



                if(event.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    rect = new Rectangle();
                    rect.setVisible(true);
                    rect.setTranslateX(event.getX());
                    rect.setTranslateY(event.getY());
                    //rect.setTranslateX(event.getX());
                    //rect.setTranslateY(event.getY());


                }

                else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {
                    //rect.setX(rect.getTranslateX());
                    //rect.setY(rect.getTranslateY());
                    rect.setWidth(event.getX() - rect.getTranslateX());
                    rect.setHeight(event.getY() - rect.getTranslateY());
                    rect.setStroke(BLACK);

                    rect.setFill(WHITE);
                    if(canvas.getChildren().contains(rect))
                    {
                        canvas.getChildren().remove(canvas.getChildren().size() - 1);
                    }
                    canvas.getChildren().add(rect);


                }

                else if(event.getEventType() == MouseEvent.MOUSE_RELEASED)
                {
                    rect.setFill(colorPick);

                    System.out.println(shapeList.size());
                    System.out.println(canvas.getChildren());
                    canvas.getChildren().clear();
                    shapeList.add(rect);
                    canvas.getChildren().addAll(shapeList);

                }


            }
            else if(shape == "circle")
            {

                if(event.getEventType() == MouseEvent.MOUSE_PRESSED)
                {

                    circle = new Circle();
                    circle.setCenterX(event.getX());
                    circle.setCenterY(event.getY());
                }

                else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED)
                {


                }

                else if(event.getEventType() == MouseEvent.MOUSE_RELEASED)
                {

                }

            }




            //canvas.getChildren().addAll(shapeList);
           // canvas.getChildren()

        }//end handle()
    }//end MouseHandler

    //Step #2(B)- A handler class used to handle events from Undo & Erase buttons
    private class ButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            //write your codes here
            //----
            if(event.getSource() == undoBtn)
            {
                shapeList.remove(shapeList.size()-1);
                System.out.println(shapeList.size());
                canvas.getChildren().removeAll();
                canvas.getChildren().addAll(shapeList);
            }
            else if(event.getSource() == eraseBtn)
            {
                shapeList.removeAll(shapeList);
                canvas.getChildren().removeAll();
                canvas.getChildren().addAll(shapeList);
            }


        }
    }//end ButtonHandler

    //Step #2(C)- A handler class used to handle events from the two radio buttons
    private class ShapeHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            //write your own codes here
            //----
            if(event.getSource() == rbCircle)
            {
                shape = "circle";
            }
            else
            {
                shape = "rectangle";
            }


        }
    }//end ShapeHandler

    //Step #2(D)- A handler class used to handle colors from the combo box
    private class ColorHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {

            colorPick = Color.valueOf(colorCombo.getSelectionModel().getSelectedItem().toUpperCase());
            System.out.println(colorPick);
        }
    }//end ColorHandler

}//end class DrawPane