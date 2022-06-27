package Controllers.ControllerImpl;

import Controllers.Controller;
import Core.File.ByteOfFile.ByteOfFile;
import Core.Manager.Server.ManagedServerStatic.ManagedServer;
import Core.Massage.Massage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class ControllerImplFileTransferWindowController implements Controller, Initializable {


    @FXML
    private TabPane tabView;

    @FXML
    private Tab downloadTab;

    @FXML
    private TreeView<String> fullTreeView;

    @FXML
    private Tab uploadTab;

    @FXML
    private Button downloadBtn;

    @FXML
    private TreeView<String> distTreeView;
    @FXML
    private WebView logTab1BebView;
    @FXML
    private TreeView<String> fullTreeViewTab2;

    @FXML
    private TreeView<String> distTreeViewTab2;

    @FXML
    private WebView logWebviewTab2;

    @FXML
    private Button uploadBtnTab2;

    private static String filePathToDownload;
    private static String filePathToSave;
    private static String filePathOfUploadFile;
    private static String filePathToSaveUploadFile;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;
    private static StringBuilder s;
    private static StringBuilder s1;

    private static Thread initServerTreeView;
    private static Thread initClientTreeView;

    static {
        objectOutputStream = null;
        objectInputStream = null;
        initServerTreeView = null;
        initClientTreeView = null;
        filePathToDownload = null;
        filePathToSave = null;
        filePathOfUploadFile = null;
        filePathToSaveUploadFile = null;
        s = new StringBuilder();
        s.append("<html>");
        s.append("<head>");
        s.append("   <script language=\"javascript\" type=\"text/javascript\">");
        s.append("       function toBottom(){");
        s.append("           window.scrollTo(0, document.body.scrollHeight);");
        s.append("       }");
        s.append("   </script>");
        s.append("</head>");
        s.append("<body onload='toBottom()'>");
        s1 = new StringBuilder();
        s1.append("<html>");
        s1.append("<head>");
        s1.append("   <script language=\"javascript\" type=\"text/javascript\">");
        s1.append("       function toBottom(){");
        s1.append("           window.scrollTo(0, document.body.scrollHeight);");
        s1.append("       }");
        s1.append("   </script>");
        s1.append("</head>");
        s1.append("<body onload='toBottom()'>");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initClientTreeView = new Thread(new Runnable() {
            @Override
            public void run() {
                ImageView client = new ImageView();
                try {
                    client.setImage(
                            SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().getResource("images/clientIcon.png").toURI().toURL()),
                                    null)
                    );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                File[] files = null;
                TreeItem<String> base = new TreeItem<String>("Client files", client);
                base.setExpanded(true);
                fullTreeView.setRoot(base);
                Socket accept = null;
                try {
                    accept = ManagedServer.getDataTransferSocket();
                    objectOutputStream =
                            new ObjectOutputStream(accept.getOutputStream());
                    objectInputStream =
                            new ObjectInputStream(accept.getInputStream());
                    objectOutputStream.writeObject(new Massage(Massage.MassageType.GET_ROOT_OF_FILES));
                    objectOutputStream.flush();
                    if (((Massage) objectInputStream.readObject()).getMassage().equals(Massage.MassageType.SENDING_ROOT_OF_FILES.getMassage())) {
                        Object o = objectInputStream.readObject();
                        files = (File[]) o;

                        for (File f : files) {
                            TreeItem<String> item = new TreeItem<String>(f.getPath());
                            ImageView folder1 = new ImageView();
                            try {
                                folder1.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                                getResource("images/purple-folder-icon-9 (1).png").toURI().toURL()),
                                        null));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            ImageView file = new ImageView();
                            try {
                                file.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                                getResource("images/files (1).png").toURI().toURL()),
                                        null));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            if (f.isDirectory()) {
                                item.setExpanded(true);
                                item.setGraphic(folder1);
                            } else {
                                item.setExpanded(false);
                                item.setGraphic(file);
                            }

                            base.getChildren().add(item);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                fullTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                        if (selectedItem.isExpanded()) {
                            for (File f : new File(selectedItem.getValue()).listFiles()) {
                                TreeItem<String> item = new TreeItem<String>(f.getPath());
                                ImageView folder1 = new ImageView();
                                try {
                                    folder1.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                                    getResource("images/purple-folder-icon-9 (1).png").toURI().toURL()),
                                            null));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                ImageView file = new ImageView();
                                try {
                                    file.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                                    getResource("images/files (1).png").toURI().toURL()),
                                            null));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                if (f.isDirectory()) {
                                    item.setExpanded(true);
                                    item.setGraphic(folder1);
                                } else {
                                    item.setExpanded(false);
                                    item.setGraphic(file);
                                }

                                selectedItem.getChildren().add(item);

                            }
                        }
                    }
                });

                fullTreeView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<TreeItem>() {
                    @Override
                    public void onChanged(Change<? extends TreeItem> change) {
                        ObservableList<TreeItem<String>> selectedItems = fullTreeView.getSelectionModel().getSelectedItems();
                        String s = selectedItems.get(0).toString();
                        s = removeByIndex(s, 0, 18);
                        s = removeByIndex(s, s.length() - 2, s.length());
                        filePathToDownload = s;
                    }
                });
                //--------upload tree view init
                client = new ImageView();
                try {
                    client.setImage(
                            SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().getResource("images/clientIcon.png").toURI().toURL()),
                                    null)
                    );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                base = new TreeItem<String>("Client files", client);
                base.setExpanded(true);
                try {
                    distTreeViewTab2.setRoot(base);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (File f : files) {
                    TreeItem<String> item = new TreeItem<String>(f.getPath());
                    ImageView folder1 = new ImageView();
                    try {
                        folder1.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                        getResource("images/purple-folder-icon-9 (1).png").toURI().toURL()),
                                null));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    ImageView file = new ImageView();
                    try {
                        file.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                        getResource("images/files (1).png").toURI().toURL()),
                                null));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    if (f.isDirectory()) {
                        item.setExpanded(true);
                        item.setGraphic(folder1);
                    } else {
                        item.setExpanded(false);
                        item.setGraphic(file);
                    }

                    base.getChildren().add(item);
                }
                distTreeViewTab2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                        if (selectedItem.isExpanded()) {
                            for (File f : new File(selectedItem.getValue()).listFiles()) {
                                TreeItem<String> item = new TreeItem<String>(f.getPath());
                                ImageView folder1 = new ImageView();
                                try {
                                    folder1.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                                    getResource("images/purple-folder-icon-9 (1).png").toURI().toURL()),
                                            null));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                ImageView file = new ImageView();
                                try {
                                    file.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                                    getResource("images/files (1).png").toURI().toURL()),
                                            null));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                if (f.isDirectory()) {
                                    item.setExpanded(true);
                                    item.setGraphic(folder1);
                                } else {
                                    item.setExpanded(false);
                                    item.setGraphic(file);
                                }

                                selectedItem.getChildren().add(item);

                            }
                        }
                    }
                });

                distTreeViewTab2.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<TreeItem>() {
                    @Override
                    public void onChanged(Change<? extends TreeItem> change) {
                        ObservableList<TreeItem<String>> selectedItems = distTreeViewTab2.getSelectionModel().getSelectedItems();
                        String s = selectedItems.get(0).toString();
                        s = removeByIndex(s, 0, 18);
                        s = removeByIndex(s, s.length() - 2, s.length());
                        filePathToSaveUploadFile = s;
                    }
                });
            }
        });
        initServerTreeView = new Thread(new Runnable() {
            @Override
            public void run() {

                ImageView client = new ImageView();
                try {
                    client.setImage(
                            SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().getResource("images/pcIcon.png").toURI().toURL()),
                                    null)
                    );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                File[] files = File.listRoots();
                TreeItem<String> base = new TreeItem<String>("PC files", client);
                base.setExpanded(true);
                distTreeView.setRoot(base);
                for (File f : files) {
                    TreeItem<String> item = new TreeItem<String>(f.getPath());
                    ImageView folder1 = new ImageView();
                    try {
                        folder1.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                        getResource("images/purple-folder-icon-9 (1).png").toURI().toURL()),
                                null));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    ImageView file = new ImageView();
                    try {
                        file.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                        getResource("images/files (1).png").toURI().toURL()),
                                null));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    if (f.isDirectory()) {
                        item.setExpanded(true);
                        item.setGraphic(folder1);
                    } else {
                        item.setExpanded(false);
                        item.setGraphic(file);
                    }

                    base.getChildren().add(item);
                }
                distTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                        if (selectedItem.isExpanded()) {
                            for (File f : new File(selectedItem.getValue()).listFiles()) {
                                TreeItem<String> item = new TreeItem<String>(f.getPath());
                                ImageView folder1 = new ImageView();
                                try {
                                    folder1.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                                    getResource("images/purple-folder-icon-9 (1).png").toURI().toURL()),
                                            null));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                ImageView file = new ImageView();
                                try {
                                    file.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                                    getResource("images/files (1).png").toURI().toURL()),
                                            null));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                if (f.isDirectory()) {
                                    item.setExpanded(true);
                                    item.setGraphic(folder1);
                                } else {
                                    item.setExpanded(false);
                                    item.setGraphic(file);
                                }

                                selectedItem.getChildren().add(item);

                            }
                        }
                    }
                });

                distTreeView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<TreeItem>() {
                    @Override
                    public void onChanged(Change<? extends TreeItem> change) {
                        ObservableList<TreeItem<String>> selectedItems = distTreeView.getSelectionModel().getSelectedItems();
                        String s = selectedItems.get(0).toString();
                        s = removeByIndex(s, 0, 18);
                        s = removeByIndex(s, s.length() - 2, s.length());
                        filePathToSave = s;
                    }
                });
                //--------------------------------------
                client = new ImageView();
                try {
                    client.setImage(
                            SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().getResource("images/pcIcon.png").toURI().toURL()),
                                    null)
                    );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                base = new TreeItem<String>("PC files", client);
                base.setExpanded(true);
                fullTreeViewTab2.setRoot(base);
                for (File f : files) {
                    TreeItem<String> item = new TreeItem<String>(f.getPath());
                    ImageView folder1 = new ImageView();
                    try {
                        folder1.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                        getResource("images/purple-folder-icon-9 (1).png").toURI().toURL()),
                                null));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    ImageView file = new ImageView();
                    try {
                        file.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                        getResource("images/files (1).png").toURI().toURL()),
                                null));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    if (f.isDirectory()) {
                        item.setExpanded(true);
                        item.setGraphic(folder1);
                    } else {
                        item.setExpanded(false);
                        item.setGraphic(file);
                    }

                    base.getChildren().add(item);
                }
                fullTreeViewTab2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                        if (selectedItem.isExpanded()) {
                            for (File f : new File(selectedItem.getValue()).listFiles()) {
                                TreeItem<String> item = new TreeItem<String>(f.getPath());
                                ImageView folder1 = new ImageView();
                                try {
                                    folder1.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                                    getResource("images/purple-folder-icon-9 (1).png").toURI().toURL()),
                                            null));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                ImageView file = new ImageView();
                                try {
                                    file.setImage(SwingFXUtils.toFXImage(ImageIO.read(getClass().getClassLoader().
                                                    getResource("images/files (1).png").toURI().toURL()),
                                            null));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                if (f.isDirectory()) {
                                    item.setExpanded(true);
                                    item.setGraphic(folder1);
                                } else {
                                    item.setExpanded(false);
                                    item.setGraphic(file);
                                }

                                selectedItem.getChildren().add(item);

                            }
                        }
                    }
                });

                fullTreeViewTab2.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<TreeItem>() {
                    @Override
                    public void onChanged(Change<? extends TreeItem> change) {
                        ObservableList<TreeItem<String>> selectedItems = fullTreeViewTab2.getSelectionModel().getSelectedItems();
                        String s = selectedItems.get(0).toString();
                        s = removeByIndex(s, 0, 18);
                        s = removeByIndex(s, s.length() - 2, s.length());
                        filePathOfUploadFile = s;
                    }
                });
            }
        });

        initClientTreeView.start();
        initServerTreeView.start();
        logTab1BebView.getEngine().setUserStyleSheetLocation(getClass().getClassLoader().getResource("styles/htmlStyle.css").toString());
        logWebviewTab2.getEngine().setUserStyleSheetLocation(getClass().getClassLoader().getResource("styles/htmlStyle.css").toString());

    }


    public void onActionDownloadBtn(ActionEvent event) {
        try {
            downloadBtn.setDisable(true);
            if (filePathToSave == null) {
                s.append("<p class=\"error\">no directory selected for destination" + "<br>");
                s.append("<p class=\"black\">-----------------------------------------------------------------------------</p><br>");
                logTab1BebView.getEngine().loadContent(s.toString());
                downloadBtn.setDisable(false);
                return;
            }
            if (filePathToDownload == null) {
                s.append("<p class=\"error\">no file selected for downloading" + "<br>");
                s.append("<p class=\"black\">-----------------------------------------------------------------------------</p><br>");
                logTab1BebView.getEngine().loadContent(s.toString());
                downloadBtn.setDisable(false);
                return;
            }
            objectOutputStream.writeObject(new Massage(Massage.MassageType.FILE_DOWNLOAD_REQUEST, filePathToDownload));
            objectOutputStream.flush();
            s.append("<p class=\"simple\">send request to download: " + filePathToDownload + "<br>");
            logTab1BebView.getEngine().loadContent(s.toString());
            Object massageObj = objectInputStream.readObject();
            Massage massage = (Massage) massageObj;
            if (massage.getMassage().equals(Massage.MassageType.FILE_DOWNLOAD_REQUEST_REJECTED_CAUSE_DIRECTORY_SELECTED.getMassage())) {
                s.append("<p class=\"error\">request to download Rejected: " +
                        Massage.MassageType.FILE_DOWNLOAD_REQUEST_REJECTED_CAUSE_DIRECTORY_SELECTED.getMassage()
                        + "<br>");
                s.append("<p class=\"black\">-----------------------------------------------------------------------------</p><br>");
                logTab1BebView.getEngine().loadContent(s.toString());
            } else if (massage.getMassage().equals(Massage.MassageType.FILE_DOWNLOAD_REQUEST_ACCEPTED.getMassage())) {
                s.append("<p class=\"simple\">request to download Accepted: " + "<br>");
                logTab1BebView.getEngine().loadContent(s.toString());
                String[] split = filePathToDownload.split("\\\\");
                if (new File(filePathToSave).isDirectory()) {
                    s.append("<p class=\"simple\">start to download<br>");
                    logTab1BebView.getEngine().loadContent(s.toString());
                    Object o = objectInputStream.readObject();
                    ByteOfFile byteOfFile = (ByteOfFile) o;
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(filePathToSave + "/" + split[split.length - 1]));
                    fileOutputStream.write(byteOfFile.getByteOfFile());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    s.append("<p class=\"success\">downloading finished file save to: " + filePathToSave + "<br>");
                    logTab1BebView.getEngine().loadContent(s.toString());
                    s.append("<p class=\"black\">-----------------------------------------------------------------------------</p><br>");
                    logTab1BebView.getEngine().loadContent(s.toString());
                } else {
                    s.append("<p class=\"error\">can not to save file: Because your Distention is not directory " + "<br>");
                    s.append("<p class=\"black\">-----------------------------------------------------------------------------</p><br>");
                    logTab1BebView.getEngine().loadContent(s.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        downloadBtn.setDisable(false);
    }


    public void onActionUploadBtnTab2(ActionEvent event) {
        try {
            uploadBtnTab2.setDisable(true);
            if (filePathOfUploadFile == null) {
                s1.append("<p class=\"error\">no file selected for uploading" + "<br>");
                s1.append("<p class=\"black\">-----------------------------------------------------------------------------</p><br>");
                logWebviewTab2.getEngine().loadContent(s1.toString());
                uploadBtnTab2.setDisable(false);
                return;
            }
            if (filePathToSaveUploadFile == null) {
                s1.append("<p class=\"error\">no directory selected for destination" + "<br>");
                s1.append("<p class=\"black\">-----------------------------------------------------------------------------</p><br>");
                logWebviewTab2.getEngine().loadContent(s1.toString());
                uploadBtnTab2.setDisable(false);
                return;
            }
            if (new File(filePathOfUploadFile).isDirectory()) {
                s1.append("<p class=\"error\">can not upload Directory: Because your selected file is a directory" + "<br>");
                s1.append("<p class=\"black\">-----------------------------------------------------------------------------</p><br>");
                logWebviewTab2.getEngine().loadContent(s1.toString());
            } else {
                String[] split = filePathOfUploadFile.split("\\\\");
                objectOutputStream.writeObject(new Massage(Massage.MassageType.FILE_UPLOAD_REQUEST, filePathToSaveUploadFile, split[split.length - 1]));
                objectOutputStream.flush();
                s1.append("<p class=\"simple\">send request to Upload: " + filePathOfUploadFile + "<br>");
                logWebviewTab2.getEngine().loadContent(s1.toString());
                Object massageObj = objectInputStream.readObject();
                Massage massage = (Massage) massageObj;
                if (massage.getMassage().equals(Massage.MassageType.FILE_UPLOAD_REQUEST_REJECTED_CAUSE_FILE_SELECTED.getMassage())) {
                    s1.append("<p class=\"error\">request to Upload Rejected: " +
                            Massage.MassageType.FILE_UPLOAD_REQUEST_REJECTED_CAUSE_FILE_SELECTED.getMassage()
                            + "<br>");
                    s1.append("<p class=\"black\">-----------------------------------------------------------------------------</p><br>");
                    logWebviewTab2.getEngine().loadContent(s1.toString());
                } else if (massage.getMassage().equals(Massage.MassageType.FILE_UPLOAD_REQUEST_ACCEPTED.getMassage())) {
                    s1.append("<p class=\"simple\">request to Upload Accepted: " + "<br>");
                    logWebviewTab2.getEngine().loadContent(s1.toString());
                    s1.append("<p class=\"simple\">start to Upload<br>");
                    logWebviewTab2.getEngine().loadContent(s1.toString());
                    objectOutputStream.writeObject(new ByteOfFile(Files.readAllBytes(new File(filePathOfUploadFile).toPath())));
                    objectOutputStream.flush();
                    Object o = objectInputStream.readObject();
                    Massage massage1 = (Massage) o;
                    if (massage1.getMassage().equals(Massage.MassageType.FILE_UPLOADED_SUCCESSFULLY.getMassage())) {
                        s1.append("<p class=\"success\">Uploading finished file upload to: " + filePathToSaveUploadFile + "<br>");
                        logWebviewTab2.getEngine().loadContent(s1.toString());
                        s1.append("<p class=\"black\">-----------------------------------------------------------------------------</p><br>");
                        logWebviewTab2.getEngine().loadContent(s1.toString());
                        uploadBtnTab2.setDisable(false);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String removeByIndex(String str, int indexStart, int indexEnd) {
        StringBuilder sb = new StringBuilder(str);
        if (indexStart < 0 && indexEnd < 0) {
            throw new IllegalArgumentException("index can not be negative");
        }
        if (indexStart <= str.length() && indexEnd <= str.length()) {
            sb.replace(indexStart, indexEnd, "");
        }
        return sb.toString();
    }


}

