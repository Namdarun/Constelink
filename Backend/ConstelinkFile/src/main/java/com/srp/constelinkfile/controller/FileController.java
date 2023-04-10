package com.srp.constelinkfile.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.srp.constelinkfile.dto.FileDto;
import com.srp.constelinkfile.service.FileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "파일", description = "파일 관련 api 입니다.")
@RequestMapping("files")
public class FileController {

	private final FileService fileService;

	@Operation(summary = "파일등록", description = "파일 등록 메서드.")
	@PostMapping(value = "saveimg", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file){
		FileDto upload = fileService.upload(file);

		return ResponseEntity.ok(upload);
	}

}
