/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.sun.javafx.collections.ImmutableObservableList;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.util.Duration;
import lib.Library;
import models.DeviceEntity;
import models.Patient;

/**
 *
 * @author binhm
 */
public class PatientFormController implements Initializable {

    ObservableList<Patient> data_patient;
    ObservableList<DeviceEntity> data_device;
    FilteredList<DeviceEntity> viewList_device;   //
    int selectedPatient = 0;    //index of selected patient
    int selectedDevice = 0;    //index of selected device
    private boolean data_changed = false;

    private void initDeviceTableView() {
        TableColumn idCol = new TableColumn("Mã BN");
        TableColumn nameCol = new TableColumn("Tên TB");

        TableColumn timeCol = new TableColumn("Ngày sử dụng");
        //TableColumn emailCol = new TableColumn("Email");

        tv_device.getColumns().clear();
        tv_device.getColumns().addAll(idCol, nameCol, timeCol);

        //data_device = FXCollections.observableArrayList();
        data_device = Library.readFromFile(MainController.deviceDBFile);
        viewList_device = new FilteredList<>(data_device, (t) -> {
            return true; //To change body of generated lambdas, choose Tools | Templates.
        });

        idCol.setCellValueFactory(new PropertyValueFactory<>("id_patient"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("usageDate"));

        tv_device.setItems(viewList_device);
        //tv_device.getSelectionModel().
        tv_device.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

            selectedDevice = tv_device.getSelectionModel().getSelectedIndex();
            //System.out.println("Mouse clicked: "+selectedDevice );
            if (selectedDevice >= 0 && selectedDevice < data_device.size()) {
                tf_deviceName.setText(viewList_device.get(selectedDevice).getDeviceName());
                LocalDate date;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(viewList_device.get(selectedDevice).getUsageDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    dp_usageDate.setValue(date);
                } catch (ParseException ex) {
                    Logger.getLogger(PatientFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    private void initPatientTableView() {
        TableColumn idCol = new TableColumn("Mã BN");
        TableColumn firstNameCol = new TableColumn("Họ");
        TableColumn lastNameCol = new TableColumn("Tên");
        TableColumn sexCol = new TableColumn("Giới tính");
        TableColumn dobCol = new TableColumn("Ngày sinh");
        //TableColumn emailCol = new TableColumn("Email");
        TableColumn phoneCol = new TableColumn("SĐT");
        tv_patient.getColumns().clear();
        tv_patient.getColumns().addAll(idCol, firstNameCol, lastNameCol, sexCol, dobCol, phoneCol);

        //data_patient = FXCollections.observableArrayList();
        data_patient = Library.readFromFile(MainController.patientDBFile);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        tv_patient.setItems(data_patient);
        tv_patient.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            //System.out.println("Mouse clicked");
            selectedPatient = tv_patient.getSelectionModel().getSelectedIndex();
            if (selectedPatient >= 0 && selectedPatient < data_patient.size()) {
                String id_patient = data_patient.get(selectedPatient).getId();
                tf_id.setText(data_patient.get(selectedPatient).getId());
                tf_firstName.setText(data_patient.get(selectedPatient).getFirstName());
                tf_lastName.setText(data_patient.get(selectedPatient).getLastName());
                tf_phone.setText(data_patient.get(selectedPatient).getPhone());
                cb_sex.setValue(data_patient.get(selectedPatient).getSex());
                if (!data_patient.get(selectedPatient).getDob().equals("")) {
                    LocalDate dob;
                    try {
                        dob = new SimpleDateFormat("yyyy-MM-dd").parse(data_patient.get(selectedPatient).getDob()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        dp_dob.setValue(dob);
                    } catch (ParseException ex) {
                        Logger.getLogger(PatientFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    dp_dob.setValue(null);
                }

                viewList_device.setPredicate((t) -> t.getId_patient().equals(id_patient));
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPatientTableView();

//        dp_dob.setOnAction((event) -> {
//            LocalDate date = dp_dob.getValue();
//
//            //tf_dob.setText(date.toString());
//        });
        cb_sex.setItems(new ImmutableObservableList("Nam", "Nữ"));
        cb_sex.setValue("Nam");

        lb_error.setText("");

        initDeviceTableView();
    }

    //Variables for FXML controls
    @FXML
    TableView tv_patient;
    @FXML
    TableView tv_device;
    @FXML
    TextField tf_id;
    @FXML
    TextField tf_firstName;
    @FXML
    TextField tf_lastName;
    @FXML
    TextField tf_phone;
    @FXML
    ComboBox cb_sex;
    @FXML
    DatePicker dp_dob;

    @FXML
    TextField tf_deviceName;
    @FXML
    DatePicker dp_usageDate;

    @FXML
    Label lb_error;

    @FXML
    protected void handleClose(ActionEvent event) {
        //System.out.println("Close form now");
        if (data_changed) {
            Dialog<ButtonType> dlg = new Dialog<>();
            dlg.setContentText("Nội dung đã thay đổi, bạn có muốn lưu lại không?");
            ButtonType bt_yes = new ButtonType("Có", ButtonData.YES);
            ButtonType bt_no = new ButtonType("Không", ButtonData.NO);
            dlg.getDialogPane().getButtonTypes().addAll(bt_yes, bt_no);

            Optional<ButtonType> result = dlg.showAndWait();
            if (result.isPresent() && result.get() == bt_yes) {
                saveAll();
            }
        }
        MainController.stage.close();
        MainController.myApp.showMainMenu();

    }

    @FXML
    protected void handleAddPatient(ActionEvent event) {
        //System.out.println("Add new patient");
        String id_patient = tf_id.getText().trim();

        if (data_patient.size() > 0) {

            int i = 0;
            while (i < data_patient.size() && !data_patient.get(i).getId().equals(id_patient)) {
                i++;
            }
            if (i < data_patient.size()) {
                lb_error.setText("Mã bệnh nhân " + id_patient + " đã tồn tại!");
                Library.fadingObject(lb_error, 2.5);
                return;
            }
        }

        Patient p = new Patient();
        p.setId(id_patient);
        p.setFirstName(tf_firstName.getText().trim());
        p.setLastName(tf_lastName.getText().trim());
        p.setPhone(tf_phone.getText().trim());
        p.setSex(cb_sex.getValue().toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm/dd/yyyy");

        if (dp_dob.getValue() != null) {
            p.setDob(dp_dob.getValue().toString());
        } else {
            p.setDob("");
        }
        data_patient.add(p);
        tf_id.setText("");
        tf_firstName.setText("");
        tf_lastName.setText("");
        tf_phone.setText("");
        tv_patient.refresh();
        data_changed = true;
    }

    @FXML
    protected void handleModifyPatient(ActionEvent event) {
        //System.out.println("Modify patient");
        //Patient p = new Patient();
        if (selectedPatient >= 0 && selectedPatient < data_patient.size()) {
            String id_patient = tf_id.getText().trim();
            if (id_patient.isEmpty()) {
                lb_error.setText("Mã BN cần khác rỗng!");
                Library.fadingObject(lb_error, 2.5);
                return;
            }
            int i = 0;
            int count = 0;
            while (i < data_patient.size() && count == 0) {
                if (i != selectedPatient && data_patient.get(i).getId().equals(id_patient)) {
                    count++;
                }
                i++;
            }
            if (count >= 1) {
                lb_error.setText("Mã BN " + id_patient + " đã tồn tại!");
                Library.fadingObject(lb_error, 2.5);
                return;
            }
            data_patient.get(selectedPatient).setId(tf_id.getText().trim());
            data_patient.get(selectedPatient).setFirstName(tf_firstName.getText().trim());
            data_patient.get(selectedPatient).setLastName(tf_lastName.getText().trim());
            data_patient.get(selectedPatient).setPhone(tf_phone.getText().trim());
            String dob = "";
            if (dp_dob.getValue() != null) {
                dob = dp_dob.getValue().toString();
            }
            data_patient.get(selectedPatient).setDob(dob);
            data_patient.get(selectedPatient).setSex(cb_sex.getValue().toString());
            tv_patient.refresh();
            data_changed = true;
        }

    }

    @FXML
    protected void handleRemovePatient(ActionEvent event) {
        //System.out.println("Remove patient");
        if (selectedPatient >= 0 && selectedPatient < data_patient.size()) {
            Dialog<ButtonType> dlg = new Dialog<>();
            dlg.setContentText("Xóa bệnh nhân này sẽ không thể khôi phục lại, bạn có chắc chắn xóa?");
            ButtonType bt_yes = new ButtonType("Có", ButtonData.YES);
            ButtonType bt_no = new ButtonType("Không", ButtonData.NO);
            dlg.getDialogPane().getButtonTypes().addAll(bt_yes, bt_no);

            Optional<ButtonType> result = dlg.showAndWait();
            if (result.isPresent() && result.get() == bt_yes) {
                data_patient.remove(selectedPatient);
                if (selectedPatient >= data_patient.size()) {
                    selectedPatient--;
                }
                tv_patient.refresh();
                data_changed = true;
            }

        }
    }

    @FXML
    protected void handleSavePatient(ActionEvent event) {
//        //System.out.println("Remove patient");
//        if (data_patient.size() > 0) {
//            MainController.saveToFile(data_patient, MainController.patientDBFile);
//        }
        saveAll();
        data_changed = false;

    }

    @FXML
    protected void handleAddDevice(ActionEvent event) {
        //System.out.println("Add new patient");
        DeviceEntity p = new DeviceEntity();
        p.setId_patient(data_patient.get(selectedPatient).getId());
        p.setDeviceName(tf_deviceName.getText().trim());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm/dd/yyyy");

        p.setUsageDate(dp_usageDate.getValue().toString());
        data_device.add(p);
        tf_deviceName.setText("");

        tv_device.refresh();
        data_changed = true;
    }

    @FXML
    protected void handleModifyDevice(ActionEvent event) {
        if (selectedDevice >= 0 && selectedDevice < data_device.size()) {
            data_device.get(viewList_device.getSourceIndex(selectedDevice)).setDeviceName(tf_deviceName.getText().trim());
            data_device.get(viewList_device.getSourceIndex(selectedDevice)).setUsageDate(dp_usageDate.getValue().toString());

            tv_device.refresh();
            data_changed = true;
        }

    }

    @FXML
    protected void handleRemoveDevice(ActionEvent event) {
        //System.out.println("Remove patient");
        if (selectedDevice >= 0 && selectedDevice < data_device.size()) {
            Dialog<ButtonType> dlg = new Dialog<>();
            dlg.setContentText("Xóa thiết bị này sẽ không thể khôi phục lại, bạn có chắc chắn xóa?");
            ButtonType bt_yes = new ButtonType("Có", ButtonData.YES);
            ButtonType bt_no = new ButtonType("Không", ButtonData.NO);
            dlg.getDialogPane().getButtonTypes().addAll(bt_yes, bt_no);

            Optional<ButtonType> result = dlg.showAndWait();
            if (result.isPresent() && result.get() == bt_yes) {
                data_device.remove(viewList_device.getSourceIndex(selectedDevice));
                if (selectedDevice >= viewList_device.size()) {
                    selectedDevice--;
                }
                tv_device.refresh();
                data_changed = true;
            }

        }
    }

//    @FXML
//    protected void handleSaveDevice(ActionEvent event) {
//        //System.out.println("Remove patient");
//        if (data_device.size() > 0) {
//            MainController.saveToFile(data_device, MainController.deviceDBFile);
//        }
//
//    }
    private void saveAll() {
        Library.saveToFile(data_patient, MainController.patientDBFile);
        Library.saveToFile(data_device, MainController.deviceDBFile);

    }

}
