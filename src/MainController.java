import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class MainController {
    public StackPane root;
    public HBox bookInfo;
    public TextField txtBookInfoInput;
    public Text txtBookName;
    public Text txtAuthor;
    public Text txtMembername;
    public Text txtContact;
    public TextField txtMemberInfoInput;
    public TextField txtLoadIDInput;
    public ListView<Object> lstBooks;
    public Text txtAvailable;

    public void initialize(){

    }

    public void btnAddMemberOnAction(ActionEvent actionEvent) {
        loadWindow("view/AddMemeberForm.fxml","Add Members");
    }

    public void btnAddBookOnAction(ActionEvent actionEvent) {
        loadWindow("view/AddBookForm.fxml","Add Books");
    }

    public void btnViewMemberOnAction(ActionEvent actionEvent) {
        loadWindow("view/MemberList.fxml","View Members");
    }

    public void btnViewBookOnAction(ActionEvent actionEvent) {
        loadWindow("view/BookList.fxml","View Books");
    }

    public void loadWindow(String location ,String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Scene scene =new Scene(parent);
            Stage stage = new Stage(StageStyle.DECORATED);

            stage.setTitle(title);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void txtloadBookInfoOnAction(ActionEvent actionEvent) {
        String id = txtBookInfoInput.getText();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book where id = ?");
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean isExist = resultSet.next();
            if (isExist){
                String bookName = resultSet.getString(2);
                String bookAuthor = resultSet.getString(3);
                boolean isavalable = resultSet.getBoolean(6);

                txtAvailable.setText(String.valueOf(isavalable));
                txtBookName.setText(bookName);
                txtAuthor.setText(bookAuthor);

            }else {
                txtBookName.setText("Not Available");
                txtAuthor.setText("");
                txtAvailable.setText("");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void txtloadMemberInfoOnAction(ActionEvent actionEvent) {
        String memberid = txtMemberInfoInput.getText();
        Connection connection = DBConnection.getInstance().getConnection();


        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from member where id =? ");
            preparedStatement.setObject(1,memberid);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean memberExist = resultSet.next();

            if (memberExist){
                String memberName = resultSet.getString(2);
                String memberMobile = resultSet.getString(5);
                txtMembername.setText(memberName);
                txtContact.setText(memberMobile);
            }else {
                txtMembername.setText("Not Available");
                txtContact.setText("");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void btnIssueOnAction(ActionEvent actionEvent) {
        String memberId = txtMemberInfoInput.getText();
        String bookId = txtBookInfoInput.getText();
        Connection connection = DBConnection.getInstance().getConnection();

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to issue " + txtBookName.getText() + " to " + txtMembername.getText()).showAndWait();
        if (buttonType.get()== ButtonType.OK) {


            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into issue (bookID,memberID) values (?,?)");
                preparedStatement.setObject(1, bookId);
                preparedStatement.setObject(2,memberId);
                int i = preparedStatement.executeUpdate();
                if (i!=0){
                    new Alert(Alert.AlertType.CONFIRMATION, "Book Issue Complete " ).showAndWait();

                    PreparedStatement preparedStatement2 = connection.prepareStatement("update book set available = false where id =? ");
                    preparedStatement2.setObject(1,bookId);
                    preparedStatement2.executeUpdate();

                }else {
                    new Alert(Alert.AlertType.ERROR, "Book issue Failed " ).showAndWait();
                }




            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }else
            {
            System.out.println("Unsuccess");
        }
    }



    public void txtLoadBookID(ActionEvent actionEvent) {
        String loadDetails = txtLoadIDInput.getText();
        Connection connection = DBConnection.getInstance().getConnection();

        ObservableList<Object> issueData = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from issue where bookID = ? ");
            preparedStatement.setObject(1,loadDetails);
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()){
                String bookId = resultSet.getString(1);
                String memberId = resultSet.getString(2);
                Timestamp issueTime = resultSet.getTimestamp(3);

                issueData.add("Issue Date & Time : " +issueTime);
                issueData.add(" " );
                issueData.add(" Book Information ");
                PreparedStatement preparedStatement1 = connection.prepareStatement("select * from book where id = ? ");
                preparedStatement1.setObject(1,bookId);
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                while (resultSet1.next()){
                    String bookname = resultSet1.getString(2);
                    String author = resultSet1.getString(3);
                    String publisher = resultSet1.getString(4);
                    issueData.add("1. Book Name : "+bookname);
                    issueData.add("2. Author: "+author);
                    issueData.add("3. Publisher : "+publisher);
                }
                issueData.add("");
                issueData.add(" Member Information ");
                PreparedStatement preparedStatement2 = connection.prepareStatement("select * from member where id = ? ");
                preparedStatement2.setObject(1,memberId);
                ResultSet resultSet2 = preparedStatement2.executeQuery();

                while (resultSet2.next()){
                    String name = resultSet2.getString(2);
                    String Email = resultSet2.getString(3);
                    String address = resultSet2.getString(4);
                    String mobile = resultSet2.getString(5);

                    issueData.add("1. Member Name : "+name);
                    issueData.add("2. Email: "+Email);
                    issueData.add("3. Address : "+address);
                    issueData.add("4. Mobile : "+mobile);
                }


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        lstBooks.getItems().setAll(issueData);


    }

    public void btnRenewonOnAction(ActionEvent actionEvent) {
        String id = txtLoadIDInput.getText();
        Connection connection = DBConnection.getInstance().getConnection();

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Renew" ).showAndWait();
        if (buttonType.get()== ButtonType.OK) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("update issue set issued_at = CURRENT_TIMESTAMP where bookID = ?");
                preparedStatement.setObject(1,id);
                int i = preparedStatement.executeUpdate();
                if (i!=0){
                    new Alert(Alert.AlertType.CONFIRMATION, "Book ReNew Complete " ).showAndWait();
                }




            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public void btnSubmissionOnAction(ActionEvent actionEvent) {
        String id = txtLoadIDInput.getText();
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from issue where bookID =? ");
            PreparedStatement preparedStatement2 = connection.prepareStatement("update book set available = true where id =? ");
            preparedStatement.setObject(1,id);
            preparedStatement2.setObject(1,id);
            int i = preparedStatement .executeUpdate();
            int i1 = preparedStatement2.executeUpdate();



            if (i !=0 && i1 !=0){
                new Alert(Alert.AlertType.CONFIRMATION, "Book Has Been Submitted. " ).showAndWait();
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
