package com.srp.constelinkfile.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.srp.constelinkfile.Exception.CustomException;
import com.srp.constelinkfile.Exception.CustomExceptionType;
import com.srp.constelinkfile.dto.FileDto;

@Component
public class FileUtil {
	@Value("${spring.gcp.config.file}")
	private String gcpConfigFile;
	@Value("${spring.gcp.project.id}")
	private String gcpProjectId;
	@Value("${spring.gcp.bucket.id}")
	private String gcpBucketId;
	@Value("${spring.gcp.dir.name}")
	private String gcpDirectoryName;

	public FileDto uploadFile(MultipartFile multipartFile, String originalName, String contentType) {
		try{
			byte[] fileData = multipartFile.getBytes();

			InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();

			StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
				.setCredentials(GoogleCredentials.fromStream(inputStream)).build();
			Storage storage = options.getService();
			Bucket bucket = storage.get(gcpBucketId,Storage.BucketGetOption.fields());

			UUID uuid = UUID.randomUUID();
			checkFileExtension(originalName);
			Blob blob = bucket.create(gcpDirectoryName + "/"+uuid.toString() +  "-" + originalName, fileData, contentType);
			if(blob != null){
				System.out.println(blob.getSelfLink());
				return new FileDto(blob.getName(), blob.getMediaLink());
			}

		}catch (Exception e){
			e.printStackTrace();
			throw new CustomException(CustomExceptionType.GCS_FILE_EXCEPTION);
		}
		throw new CustomException(CustomExceptionType.GCS_FILE_EXCEPTION);
	}

	public void DeleteFile(String fileName) throws IOException {
		InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();

		StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
			.setCredentials(GoogleCredentials.fromStream(inputStream)).build();

		Storage storage = options.getService();
		storage.delete(BlobId.of(gcpBucketId, fileName));
	}


	private String checkFileExtension(String fileName) {
		if(fileName != null && fileName.contains(".")){
			String[] extensionList = {".PNG", ".JPEG", ".JPG", "PDF"};

			for(String extension: extensionList) {
				if (fileName.toUpperCase().endsWith(extension)) {
					return extension;
				}
			}
		}
		throw new CustomException(CustomExceptionType.FILE_TYPE_EXCEPTION);
	}


}
