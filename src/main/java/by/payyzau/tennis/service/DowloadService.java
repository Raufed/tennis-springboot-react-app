package by.payyzau.tennis.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

//import static by.payyzau.tennis.service.UploadService.getPathToGoogleCredentials;
@Service
public class DowloadService {
    private static final String APPLICATION_NAME = "Tennis";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACOUNT_KEY_PATH = getPathToGoogleCredentials();
    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "cred.json");
        return filePath.toString();
    }
    private Drive cteareDriveService() throws GeneralSecurityException, IOException {
        GoogleCredential credentials = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credentials).build();
    }

    public File downloadImageFromDrive(String fileId) throws GeneralSecurityException, IOException {
        try {
            String currentDirectory = System.getProperty("user.dir");
            Drive drive = cteareDriveService();

            File file = drive.files().get(fileId).execute();
            String filePathString = currentDirectory + "/img/" + file.getName();

            OutputStream outputStream = new FileOutputStream(filePathString);
            drive.files().get(fileId).executeMediaAndDownloadTo(outputStream);
            outputStream.close();

            System.out.println("Image downloaded successfully.");
            return file;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
