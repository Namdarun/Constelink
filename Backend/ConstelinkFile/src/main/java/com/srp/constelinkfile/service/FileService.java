package com.srp.constelinkfile.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.srp.constelinkfile.dto.FileDto;
import com.srp.constelinkfile.util.FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

	private final FileUtil fileUtil;
	public FileDto upload(MultipartFile file) {
		String originName = file.getOriginalFilename();
		String contentType = file.getContentType();

		FileDto fileDto = fileUtil.uploadFile(file, originName, contentType);
		return fileDto;
	}
}
