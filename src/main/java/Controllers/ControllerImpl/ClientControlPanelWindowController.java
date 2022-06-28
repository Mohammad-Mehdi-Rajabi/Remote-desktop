package Controllers.ControllerImpl;

import Controllers.Controller;
import Core.File.ByteOfFile.ByteOfFile;
import Core.File.FileList.FileList;
import Core.KeyBoard.KeyBoard.Keyboard;
import Core.Manager.Client.ManagedClientStatic.ManagedClient;
import Core.Manager.ServerType.ServerType;
import Core.Massage.Massage;
import Core.Mouse.Mouse.Mouse;
import Core.ScreenShot.Runnable.CaptureScreenShotWithMouseRunnable;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class ClientControlPanelWindowController implements Initializable, Controller {
    public static Thread screenThread;
    public static Thread mouseThread;
    public static Thread keyBoardThread;
    public static Thread dataTransfer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ManagedClient.getDataTransferSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onActionStopBtn(ActionEvent actionEvent) {
        screenThread.stop();
        mouseThread.stop();
        keyBoardThread.stop();
        dataTransfer.stop();
    }

    public void onActionStartBtn(ActionEvent actionEvent) {
        CaptureScreenShotWithMouseRunnable captureScreenShotWithMouseRunnable =
                new CaptureScreenShotWithMouseRunnable();
        captureScreenShotWithMouseRunnable.setOutputStream(ManagedClient.getIp(),
                ManagedClient.getServers().get(ServerType.SCREEN_SERVER).getPort());
        screenThread = new Thread(captureScreenShotWithMouseRunnable);
        screenThread.start();
        mouseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket mouseSocket = new Socket(ManagedClient.getIp(),
                            ManagedClient.getServers().get(ServerType.MOUSE_SERVER).getPort());
                    ObjectInputStream objectInputStream =
                            new ObjectInputStream(mouseSocket.getInputStream());
                    while (true) {
                        Object o = objectInputStream.readObject();
                        ((Mouse) o).run(new Robot());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
        });
        mouseThread.start();
        keyBoardThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket keyBoardSocket = new Socket(ManagedClient.getIp(),
                            ManagedClient.getServers().get(ServerType.KEYBOARD_SERVER).getPort());
                    ObjectInputStream objectInputStream =
                            new ObjectInputStream(keyBoardSocket.getInputStream());
                    while (true) {
                        Object o = objectInputStream.readObject();
                        ((Keyboard) o).run(new Robot());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }
        });
        keyBoardThread.start();

        dataTransfer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket data = new Socket(ManagedClient.getIp(),
                            ManagedClient.getServers().get(ServerType.DATA_TRANSFER_SERVER).getPort());
                    ObjectOutputStream objectOutputStream =
                            new ObjectOutputStream(data.getOutputStream());
                    ObjectInputStream objectInputStream =
                            new ObjectInputStream(data.getInputStream());
                    while (true) {
                        try {
                            Massage massage = (Massage) objectInputStream.readObject();
                            if (massage.getMassage().equals(Massage.MassageType.GET_ROOT_OF_FILES.getMassage())) {
                                objectOutputStream.writeObject(new Massage(Massage.MassageType.SENDING_ROOT_OF_FILES));
                                objectOutputStream.flush();
                                objectOutputStream.writeObject(new FileList(File.listRoots()));
                                objectOutputStream.flush();
                            } else if (massage.getMassage().equals(Massage.MassageType.FILE_DOWNLOAD_REQUEST.getMassage())) {
                                String path = massage.getPath();
                                File file = new File(path);
                                if (file.isDirectory()) {
                                    objectOutputStream.writeObject(
                                            new Massage(Massage.MassageType.FILE_DOWNLOAD_REQUEST_REJECTED_CAUSE_DIRECTORY_SELECTED)
                                    );
                                    objectOutputStream.flush();
                                } else {
                                    objectOutputStream.writeObject(
                                            new Massage(Massage.MassageType.FILE_DOWNLOAD_REQUEST_ACCEPTED)
                                    );
                                    objectOutputStream.flush();
                                    objectOutputStream.writeObject(new ByteOfFile(Files.readAllBytes(file.toPath())));
                                    objectOutputStream.flush();
                                }
                            } else if (massage.getMassage().equals(Massage.MassageType.FILE_UPLOAD_REQUEST.getMassage())) {
                                String path = massage.getPath();
                                File file = new File(path);
                                if (!file.isDirectory()) {
                                    objectOutputStream.writeObject(
                                            new Massage(Massage.MassageType.FILE_UPLOAD_REQUEST_REJECTED_CAUSE_FILE_SELECTED)
                                    );
                                    objectOutputStream.flush();
                                } else {
                                    objectOutputStream.writeObject(
                                            new Massage(Massage.MassageType.FILE_UPLOAD_REQUEST_ACCEPTED)
                                    );
                                    objectOutputStream.flush();

                                    Object o = objectInputStream.readObject();
                                    ByteOfFile byteOfFile = (ByteOfFile) o;
                                    FileOutputStream fileOutputStream = new FileOutputStream(new File(massage.getPath() + "/" + massage.getFileName()));
                                    fileOutputStream.write(byteOfFile.getByteOfFile());
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                    objectOutputStream.writeObject(new Massage(Massage.MassageType.FILE_UPLOADED_SUCCESSFULLY));
                                    objectOutputStream.flush();
                                }
                            } else if (massage.getMassage().equals(Massage.MassageType.GET_FILES.getMassage())) {
                                objectOutputStream.writeObject(new Massage(Massage.MassageType.SENDING_FILES));
                                objectOutputStream.flush();
                                objectOutputStream.writeObject(new FileList(new File(massage.getPath()).listFiles()));
                                objectOutputStream.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        dataTransfer.start();
    }


}
