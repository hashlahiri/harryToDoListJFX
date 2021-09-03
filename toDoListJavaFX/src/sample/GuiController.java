package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sample.LocalEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class GuiController implements Initializable { //uses the interface

    @Override
    public void initialize(URL url, ResourceBundle rb) { //here we set value to current date, part that's run first
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    Button addButton; //adding in the buttons
    @FXML
    Button deleteButton; //delete button
    @FXML
    Button timeToPrint; //print button
    @FXML
    TextField descriptionTextField;  //adding in the description fields
    @FXML
    TextField deleteItemTextField;
    @FXML
    DatePicker datePicker;  //adding in the date picker
    @FXML
    ListView<LocalEvent> eventList; //creating a list view of type localevent (when clicked)

    ObservableList<LocalEvent> list = FXCollections.observableArrayList();  //creating the list where items are stored

    @FXML
    private void addEvent(Event e) {  //upon add event button press/click
        list.add(new LocalEvent(descriptionTextField.getText(), datePicker.getValue()));  //add the data into list
        eventList.setItems(list); //send the updated list
        refresh(); //refresh the software/list

    }
    @FXML
    private void delEvent(Event e) { //upon delete event button press/click
        if (list.size() <= 0) //checks if the list is empty or not
            System.out.println("Empty list"); //print if empty
        else {
            //list.remove(list.size() - 1); //else delete the last entry in the list
            String item = deleteItemTextField.getText(); //get the item number to remove
            list.remove(Integer.parseInt(item) - 1); //deletes that entry according to array list
        }
        refresh();
    }
    private void printImage(BufferedImage image) //generates an image with the report entry items
    {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable((Graphics graphics, PageFormat pageFormat, int pageIndex) ->
        {
            // Get the upper left corner that it printable
            int x = (int) Math.ceil(pageFormat.getImageableX());
            int y = (int) Math.ceil(pageFormat.getImageableY());
            if (pageIndex != 0)
            {
                return NO_SUCH_PAGE;
            }
            graphics.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
            return PAGE_EXISTS;
        });
        try
        {
            printJob.print();
        }
        catch (PrinterException e1)
        {
            e1.printStackTrace();
        }
    }
    @FXML
    private void forPrinting(Event e) //prints the items into the image
    {
        //when an item is added, it is called to print that item
        WritableImage image = eventList.snapshot(new SnapshotParameters(), null);
        File file = new File("nodeImage.png");
        try
        {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

            Image imageToPrint = new Image(file.toURI().toString());
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageToPrint, null);
            printImage(bufferedImage);
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }

    private void refresh() { //basically resetting the fields
        datePicker.setValue(LocalDate.now());
        descriptionTextField.setText(null);
        deleteItemTextField.setText(null);
    }
}
