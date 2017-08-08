package com.rest;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/download")
public class DownloadController {
	
	@Value("${app.files.archiveDir}")
	private String archiveDir;

	@RequestMapping("/archive/{csvFile}")
	public ResponseEntity<Resource> downloadArchive(@PathVariable("csvFile") String csvFile) throws IOException {
		if (csvFile.endsWith(".csv") == false ) {
			csvFile += ".csv";
		}
		
		String fullPath = archiveDir + csvFile;
		String header = "attachment;filename=" + csvFile;
		InputStreamResource resource = new InputStreamResource(new FileInputStream(fullPath));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", header)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}

}
