module com.tracker.internship_tracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tracker.internship_tracker to javafx.fxml;
    exports com.tracker.internship_tracker;
}