package com.example.Gamesite.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.Gamesite.model.FileEntity;
import com.example.Gamesite.repository.FileRepository;

public class FileService {
	private final FileRepository fileRepository;
	
	@Autowired
	public FileService(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	//saving uploaded files (transforming MultipartFile object into FileEntity), 
	public void save(MultipartFile file) throws IOException{
		FileEntity fileEntity = new FileEntity();
		fileEntity.setName(StringUtils.cleanPath(file.getOriginalFilename()));
		fileEntity.setContentType(file.getContentType());
		fileEntity.setData(file.getBytes());
		fileEntity.setSize(file.getSize());
		
		fileRepository.save(fileEntity);
	}
	
	//uploading a single file by provided id
	public Optional<FileEntity> getFile(String id) {
		return fileRepository.findById(id);
	}
	
	//return a list of uploaded files
	public List<FileEntity> getAllFiles(){
		return fileRepository.findAll();
	}
}
