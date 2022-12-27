package com.irinalyamina.InsuranceAgency;

import com.irinalyamina.InsuranceAgency.models.Car;
import com.irinalyamina.InsuranceAgency.models.Photo;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PhotoLayout;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InteractionPhoto {

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\photos";

    public static void checkMainDir() {
        File dir = new File(UPLOAD_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void createDir(Long carId) {
        String pathDir = UPLOAD_DIRECTORY + "\\" + carId;
        File dirCarPhotos = new File(pathDir);
        dirCarPhotos.mkdirs();
    }

    public static String uploadImage(MultipartFile file, Long carId) throws IOException {
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY + "\\" + carId, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        return file.getOriginalFilename();
    }

    public static List<PhotoLayout> getPhotos(Car car) throws IOException {
        List<PhotoLayout> photos = new ArrayList<>();
        for (var photoDB : car.getPhotos()) {
            String pathFile = UPLOAD_DIRECTORY + "\\" + car.getId() + "\\" + photoDB.getPath();
            PhotoLayout photoLayout = new PhotoLayout(photoDB.getId(), toBase64(pathFile), photoDB.getUploadDate());
            photos.add(photoLayout);
        }
        return photos;
    }

    public static PhotoLayout getPhoto(Photo photo) throws IOException {
        String pathFile = UPLOAD_DIRECTORY + "\\" + photo.getCar().getId() + "\\" + photo.getPath();
        PhotoLayout photoLayout = new PhotoLayout(photo.getId(), toBase64(pathFile), photo.getUploadDate());
        return photoLayout;
    }

    public static void deletePhoto(Photo photo) {
        String pathFile = UPLOAD_DIRECTORY + "\\" + photo.getCar().getId() + "\\" + photo.getPath();
        File file = new File(pathFile);
        file.delete();
    }

    private static String toBase64(String path) throws IOException {
        File file = new File(path);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        Base64 base64 = new Base64();
        byte[] encodeBase64 = base64.encode(fileContent);
        return new String(encodeBase64, StandardCharsets.UTF_8);
    }
}
