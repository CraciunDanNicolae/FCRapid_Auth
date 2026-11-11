package com.fcrapid.auth.service;

import com.fcrapid.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    @Value("${supabase.project-id}")
    private String supabaseProjectId;

    @Value("${supabase.service-role-key}")
    private String serviceRoleKey;

    private AuthRepository repository;

    public ImageService(AuthRepository repository){
        this.repository = repository;
    }

    public String uploadImage(MultipartFile image) throws Exception {
        String fileName = System.currentTimeMillis() + ".jpg";
        String uploadUrl = "https://" + supabaseProjectId + ".supabase.co/storage/v1/object/UsersImages/" + fileName;
        String publicUrl = "https://" + supabaseProjectId + ".supabase.co/storage/v1/object/public/UsersImages/" + fileName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + serviceRoleKey);
        headers.setContentType(MediaType.IMAGE_JPEG);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(image.getBytes(), headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(uploadUrl, HttpMethod.PUT, requestEntity, String.class);

        return uploadUrl;
    }


}
