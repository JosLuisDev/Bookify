package com.accenture.demobookify.dto;

import com.accenture.demobookify.model.FileData;

public record AuthorResponse(Long id, String firstname, String lastname, FileData fileData) {
}
